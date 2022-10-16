/*
 * Copyright 2022.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dharbuzov.iso8583.packager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.BitSet;

import com.dharbuzov.iso8583.exception.ISOPackageException;
import com.dharbuzov.iso8583.factory.ISOFieldPackagerFactory;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.MessageHeader;
import com.dharbuzov.iso8583.model.MessageSource;
import com.dharbuzov.iso8583.model.MessageType;
import com.dharbuzov.iso8583.model.field.ISOBitSetValue;
import com.dharbuzov.iso8583.model.field.ISOField;
import com.dharbuzov.iso8583.model.field.ISOFieldType;
import com.dharbuzov.iso8583.model.field.ISOMessageTypeValue;
import com.dharbuzov.iso8583.model.field.ISOPrimitiveField;
import com.dharbuzov.iso8583.model.field.ISOValueType;
import com.dharbuzov.iso8583.model.schema.ISOFieldSchema;
import com.dharbuzov.iso8583.model.schema.ISOMessageSchema;
import com.dharbuzov.iso8583.packager.model.ISOFieldContext;
import com.dharbuzov.iso8583.packager.model.ISOFieldPackContext;
import com.dharbuzov.iso8583.packager.model.ISOFieldUnpackContext;
import com.dharbuzov.iso8583.packager.model.ISOMessagePackContext;
import com.dharbuzov.iso8583.packager.model.ISOMessageUnpackContext;
import com.dharbuzov.iso8583.util.ByteStreamUtils;
import com.dharbuzov.iso8583.util.ValidationUtils;

/**
 * Base implementation of message packager.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class ISOBaseMessagePackager implements ISOMessagePackager {

  protected final Class<? extends ISOFieldPackager> defaultFieldPackagerClass;
  protected final ISOFieldSchema defaultMessageTypeSchema;
  protected final ISOFieldSchema defaultBitmapSchema;

  /**
   * Constructor of base message packager.
   */
  public ISOBaseMessagePackager() {
    this.defaultFieldPackagerClass = getDefaultFieldPackagerClass();
    this.defaultMessageTypeSchema = getDefaultMessageTypeSchema();
    this.defaultBitmapSchema = getDefaultBitmapSchema();
  }

  /**
   * Gets default bitmap schema.
   *
   * @return default bitmap schema
   */
  protected ISOFieldSchema getDefaultBitmapSchema() {
    return ISOFieldSchema.builder().name("bitmap").valueType(ISOValueType.BITMAP)
        .fieldType(ISOFieldType.PRIMITIVE).build();
  }

  /**
   * Gets the default message type schema.
   *
   * @return default message type schema
   */
  protected ISOFieldSchema getDefaultMessageTypeSchema() {
    return ISOFieldSchema.builder().name("type").valueType(ISOValueType.MESSAGE_TYPE)
        .fieldType(ISOFieldType.PRIMITIVE).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] pack(ISOMessagePackContext messagePackContext) {
    final ByteArrayOutputStream dataBout = new ByteArrayOutputStream();
    messagePackContext.setBout(dataBout);
    packHeader(messagePackContext);
    packFields(messagePackContext);
    final byte[] data = dataBout.toByteArray();
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    messagePackContext.setBout(out);
    packLength(messagePackContext, data.length);
    ByteStreamUtils.writeBytes(out, data);
    packEtx(messagePackContext);
    return out.toByteArray();
  }

  /**
   * Packs the message length at the beginning of the message.
   *
   * @param packagerContext packager context
   * @param length          length of packed data
   */
  protected abstract void packLength(ISOMessagePackContext packagerContext, int length);

  /**
   * Packs the message header.
   *
   * @param messagePackContext packager context
   */
  protected void packHeader(ISOMessagePackContext messagePackContext) {
    final ByteArrayOutputStream bout = messagePackContext.getBout();
    final Charset encoding = messagePackContext.getEncoding();
    final ISOMessage message = messagePackContext.getMessage();
    final MessageHeader header = message.getHeader();
    if (header != null) {
      ByteStreamUtils.writeBytes(bout, header.getValue(encoding));
    }
  }

  /**
   * Packs the message trailer if exists.
   *
   * @param messagePackContext packager context
   */
  protected void packEtx(ISOMessagePackContext messagePackContext) {
    final ByteArrayOutputStream bout = messagePackContext.getBout();
    final ISOMessage message = messagePackContext.getMessage();
    final int etx = message.getEtx();
    if (etx != -1) {
      bout.write(etx);
    }
  }

  /**
   * Returns the default field packager applicable for this message packager.
   *
   * @return field packager class
   */
  protected abstract Class<? extends ISOFieldPackager> getDefaultFieldPackagerClass();

  /**
   * Packs the message fields.
   *
   * @param messagePackContext message packager context
   */
  protected void packFields(ISOMessagePackContext messagePackContext) {
    final ISOMessage message = messagePackContext.getMessage();
    final ByteArrayOutputStream bout = messagePackContext.getBout();
    final ByteArrayOutputStream fieldsBout = new ByteArrayOutputStream();
    final ISOField[] fields = message.getFields();
    fields[0] = createMessageTypeField(message);
    fields[1] = createBitmapField(message);
    final ISOFieldPackContext fieldPackContext =
        ISOFieldPackContext.builder().encoding(messagePackContext.getEncoding()).build();
    for (int i = 0; i < fields.length; i++) {
      ISOField field = fields[i];
      if (field != null) {
        ByteStreamUtils.writeBytes(fieldsBout,
            packField(messagePackContext, fieldPackContext, i, field));
      }
    }
    ByteStreamUtils.writeBout(fieldsBout, bout);
  }

  /**
   * Creates message type field.
   *
   * @param message iso message
   * @return created message type field
   */
  protected ISOField createMessageTypeField(ISOMessage message) {
    final ISOMessageTypeValue value = new ISOMessageTypeValue();
    value.setValue(message.getType());
    final ISOPrimitiveField field = new ISOPrimitiveField();
    field.setValue(value);
    return field;
  }

  /**
   * Creates bitmap field.
   *
   * @param message iso message
   * @return created bitmap field
   */
  protected ISOField createBitmapField(ISOMessage message) {
    final ISOBitSetValue value = new ISOBitSetValue();
    value.setValue(createBitmap(message));
    final ISOPrimitiveField field = new ISOPrimitiveField();
    field.setValue(value);
    return field;
  }

  /**
   * Creates the bitmap set.
   *
   * @param message iso message
   * @return created bitmap set
   */
  protected BitSet createBitmap(ISOMessage message) {
    final BitSet bitmap = new BitSet();
    final ISOField[] fields = message.getFields();
    for (int i = 2; i < fields.length; i++) {
      bitmap.set(i - 1, message.hasField(i));
    }
    return bitmap;
  }

  /**
   * Packs the field.
   *
   * @param messagePackContext message packager context
   * @param fieldPackContext   field packager context
   * @param position           field's position
   * @param field              field value
   * @return packed field into the byte array
   */
  protected byte[] packField(ISOMessagePackContext messagePackContext,
      ISOFieldPackContext fieldPackContext, int position, ISOField field) {
    ISOFieldSchema fieldSchema = messagePackContext.getSchema().getField(position);
    if (fieldSchema == null && position == 0) {
      // For position 0 (Message Type) we are defining the default schema
      fieldSchema = defaultMessageTypeSchema;
    }
    if (fieldSchema == null && position == 1) {
      // For position 1 (Bitmap) we are defining the default schema
      fieldSchema = defaultBitmapSchema;
    }
    if (fieldSchema == null) {
      throw new ISOPackageException(
          "Can't package field '%s', since there is no defined schema for it!", position);
    }
    fieldPackContext.setField(field);
    fieldPackContext.setPosition(position);
    fieldPackContext.setSchema(fieldSchema);
    final ISOFieldPackager fieldPackager = getFieldPackager(fieldPackContext);
    return fieldPackager.pack(fieldPackContext);
  }

  @Override
  public ISOMessage unpack(ISOMessageUnpackContext messageUnpackContext) {
    final ISOMessage message = createMessage(messageUnpackContext);
    final int length = unpackLength(messageUnpackContext);
    final ByteArrayInputStream binOriginal = messageUnpackContext.getBin();
    final byte[] msgBytes = new byte[length];
    ByteStreamUtils.readBytes(binOriginal, msgBytes, 0, length);
    final ByteArrayInputStream bin = new ByteArrayInputStream(msgBytes);
    messageUnpackContext.setMessage(message);
    messageUnpackContext.setBin(bin);
    unpackFields(messageUnpackContext);
    return message;
  }

  /**
   * Creates the message.
   *
   * @param packagerContext packager context
   * @return created message
   */
  protected ISOMessage createMessage(ISOMessageUnpackContext packagerContext) {
    final ISOMessage message = new ISOMessage();
    message.setSource(MessageSource.IN);
    return message;
  }

  /**
   * Unpacks the fields based on packager context.
   *
   * @param packagerContext packager context
   */
  protected void unpackFields(ISOMessageUnpackContext packagerContext) {
    final ISOMessage message = packagerContext.getMessage();
    final ISOMessageSchema messageSchema = packagerContext.getSchema();
    final ISOFieldSchema[] fieldSchemas = messageSchema.getFields();
    final ISOFieldUnpackContext fieldUnpackContext = ISOFieldUnpackContext.builder().build();
    for (int i = 0; i < fieldSchemas.length; i++) {
      ISOFieldSchema fieldSchema = fieldSchemas[i];
      fieldUnpackContext.setSchema(fieldSchema);
      fieldUnpackContext.setPosition(i);
      ISOField field = unpackField(fieldUnpackContext, i);
      if (i == 0) {
        message.setType((MessageType) field.getValue().getValue());
      }
      message.setField(i, unpackField(fieldUnpackContext, i));
    }
  }

  /**
   * Unpacks the field based on packager context.
   *
   * @param fieldUnpackContext packager context
   * @return unpacked field
   */
  protected ISOField unpackField(ISOFieldUnpackContext fieldUnpackContext, int position) {
    ISOFieldSchema fieldSchema = fieldUnpackContext.getSchema();
    if (fieldSchema == null && position == 0) {
      // For position 0 (Message Type) we are defining the default schema
      fieldSchema = defaultMessageTypeSchema;
    }
    if (fieldSchema == null && position == 1) {
      // For position 1 (Bitmap) we are defining the default schema
      fieldSchema = defaultBitmapSchema;
    }
    if (fieldSchema == null) {
      throw new ISOPackageException(
          "Can't unpack field '%s', since there is no defined schema for it!", position);
    }
    return getFieldPackager(fieldUnpackContext).unpack(fieldUnpackContext);
  }


  /**
   * Unpacks the length of the message.
   *
   * @param messageUnpackContext packager context
   * @return length of the message
   */
  protected abstract int unpackLength(ISOMessageUnpackContext messageUnpackContext);

  /**
   * Gets the field packager provided by field schema, or default provided by message packager.
   *
   * @param fieldPackContext packager context
   * @return found field packager
   */
  protected ISOFieldPackager getFieldPackager(ISOFieldContext fieldPackContext) {
    final ISOFieldSchema fieldSchema = fieldPackContext.getSchema();
    final ISOFieldPackagerFactory fieldPackagerFactory = fieldPackContext.getFieldPackagerFactory();
    final Class<? extends ISOFieldPackager> fieldPackagerClass;
    if (fieldSchema.getPackager() != null) {
      fieldPackagerClass = fieldSchema.getPackager();
    } else {
      fieldPackagerClass = defaultFieldPackagerClass;
    }
    final ISOFieldPackager fieldPackager =
        fieldPackagerFactory.getFieldPackager(fieldPackagerClass);
    ValidationUtils.validateNotNull(fieldPackager, String.format(
        "Field packager '%s' is not found! Please check that this packager is exist and added to the factory!",
        fieldPackagerClass));
    return fieldPackager;
  }
}
