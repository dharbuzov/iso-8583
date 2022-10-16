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

import java.io.ByteArrayOutputStream;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;

import com.dharbuzov.iso8583.exception.ISOPackageException;
import com.dharbuzov.iso8583.exception.ISOValidationException;
import com.dharbuzov.iso8583.factory.ISOFieldPackagerFactory;
import com.dharbuzov.iso8583.model.MessageClass;
import com.dharbuzov.iso8583.model.MessageFunction;
import com.dharbuzov.iso8583.model.MessageOrigin;
import com.dharbuzov.iso8583.model.MessageType;
import com.dharbuzov.iso8583.model.MessageVersion;
import com.dharbuzov.iso8583.model.field.ISOAbstractCompoundField;
import com.dharbuzov.iso8583.model.field.ISOBitSetValue;
import com.dharbuzov.iso8583.model.field.ISOCompositeField;
import com.dharbuzov.iso8583.model.field.ISOField;
import com.dharbuzov.iso8583.model.field.ISOFieldType;
import com.dharbuzov.iso8583.model.field.ISOMessageTypeValue;
import com.dharbuzov.iso8583.model.field.ISOPrimitiveField;
import com.dharbuzov.iso8583.model.field.ISOValueType;
import com.dharbuzov.iso8583.model.schema.ISOFieldSchema;
import com.dharbuzov.iso8583.packager.model.ISOFieldContext;
import com.dharbuzov.iso8583.packager.model.ISOFieldPackContext;
import com.dharbuzov.iso8583.packager.model.ISOFieldUnpackContext;
import com.dharbuzov.iso8583.util.ByteStreamUtils;
import com.dharbuzov.iso8583.util.StringUtils;
import com.dharbuzov.iso8583.util.ValidationUtils;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class ISOBaseFieldPackager implements ISOFieldPackager {

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] pack(ISOFieldPackContext fieldPackContext) {
    final ISOFieldSchema schema = fieldPackContext.getSchema();
    final ISOFieldType fieldType = schema.getFieldType();
    switch (fieldType) {
      case PRIMITIVE:
        return packPrimitiveField(fieldPackContext);
      case COMPOSITE:
      case CONSTRUCTED:
        return packCompoundField(fieldPackContext);
      default:
        throw new ISOPackageException("Can't pack the field type '%s'", fieldType);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOField unpack(ISOFieldUnpackContext fieldUnpackContext) {
    final ISOFieldSchema schema = fieldUnpackContext.getSchema();
    final ISOFieldType fieldType = schema.getFieldType();
    switch (fieldType) {
      case PRIMITIVE:
        return unpackPrimitiveField(fieldUnpackContext);
      case COMPOSITE:
      case CONSTRUCTED:
        return unpackCompoundField(fieldUnpackContext);
      default:
        throw new ISOPackageException("Can't pack the field type '%s'", fieldType);
    }
  }

  /**
   * Packs the primitive type field.
   *
   * @param fieldPackContext packager context
   * @return packed primitive field
   */
  protected byte[] packPrimitiveField(ISOFieldPackContext fieldPackContext) {
    final ISOFieldSchema schema = fieldPackContext.getSchema();
    final ISOValueType valueType = schema.getValueType();
    switch (valueType) {
      case MESSAGE_TYPE:
        return packMessageType(fieldPackContext);
      case BITMAP:
        return packBitmap(fieldPackContext);
      case ALPHA:
        return packAlpha(fieldPackContext);
      case NUMERIC:
        return packNumeric(fieldPackContext);
      case LLVAR:
        return packLLVAR(fieldPackContext);
      case LLLVAR:
        return packLLLVAR(fieldPackContext);
      case LLLLVAR:
        return packLLLLVAR(fieldPackContext);
      case BINARY:
        return packBinary(fieldPackContext);
      case LLBIN:
        return packLLBinary(fieldPackContext);
      case LLLBIN:
        return packLLLBinary(fieldPackContext);
      case LLLLBIN:
        return packLLLLBinary(fieldPackContext);
      default:
        throw new ISOPackageException("Can't pack the value type '%s'", valueType);
    }
  }

  /**
   * Packs the compound type field.
   *
   * @param fieldPackContext packager context
   * @return packed primitive field
   */
  protected byte[] packCompoundField(ISOFieldPackContext fieldPackContext) {
    final ISOFieldSchema schema = fieldPackContext.getSchema();
    final ISOValueType valueType = schema.getValueType();
    final ISOAbstractCompoundField field = (ISOAbstractCompoundField) fieldPackContext.getField();
    ValidationUtils.validateCompoundValueType(valueType);
    final ByteArrayOutputStream fieldBout = new ByteArrayOutputStream();
    final List<ISOField> subFields = field.getFields();
    for (int i = 0; i < subFields.size(); i++) {
      if (field.hasField(i)) {
        ISOField subField = field.getField(i);
        fieldPackContext.setSchema(schema.getField(i));
        fieldPackContext.setField(subField);
        ISOFieldPackager fieldPackager = getFieldPackager(fieldPackContext);
        ByteStreamUtils.writeBytes(fieldBout, fieldPackager.pack(fieldPackContext));
      }
    }
    return fieldBout.toByteArray();
  }

  /**
   * Unpacks the primitive field.
   *
   * @param fieldUnpackContext field packager context
   * @return unpacks the primitive field
   */
  protected ISOField unpackPrimitiveField(ISOFieldUnpackContext fieldUnpackContext) {
    final ISOFieldSchema schema = fieldUnpackContext.getSchema();
    final ISOValueType valueType = schema.getValueType();
    switch (valueType) {
      case MESSAGE_TYPE:
        final ISOMessageTypeValue messageTypeValue = new ISOMessageTypeValue();
        messageTypeValue.setValue(unpackMessageType(fieldUnpackContext));
        final ISOField messageTypeField = new ISOPrimitiveField();
        messageTypeField.setValue(messageTypeValue);
        return messageTypeField;
      case BITMAP:
        final ISOBitSetValue bitSetValue = new ISOBitSetValue();
        bitSetValue.setValue(unpackBitmap(fieldUnpackContext));
        final ISOField bitSetField = new ISOPrimitiveField();
        bitSetField.setValue(bitSetValue);
        return bitSetField;
      case ALPHA:
        return unpackAlpha(fieldUnpackContext);
      case NUMERIC:
        return unpackNumeric(fieldUnpackContext);
      case LLVAR:
        return unpackLLVAR(fieldUnpackContext);
      case LLLVAR:
        return unpackLLLVAR(fieldUnpackContext);
      case LLLLVAR:
        return unpackLLLLVAR(fieldUnpackContext);
      case BINARY:
        return unpackBinary(fieldUnpackContext);
      case LLBIN:
        return unpackLLBinary(fieldUnpackContext);
      case LLLBIN:
        return unpackLLLBinary(fieldUnpackContext);
      case LLLLBIN:
        return unpackLLLLBinary(fieldUnpackContext);
      default:
        throw new ISOPackageException("Can't unpack the value type '%s'", valueType);
    }
  }

  /**
   * Unpacks the compound field.
   *
   * @param fieldUnpackContext field packager context
   * @return unpacks the compound field
   */
  protected ISOField unpackCompoundField(ISOFieldUnpackContext fieldUnpackContext) {
    return new ISOCompositeField();
  }

  /**
   * Gets the field packager provided by field schema, or default provided by message packager.
   *
   * @param fieldContext packager field context
   * @return found field packager
   */
  protected ISOFieldPackager getFieldPackager(ISOFieldContext fieldContext) {
    final ISOFieldSchema fieldSchema = fieldContext.getSchema();
    final ISOFieldPackagerFactory fieldPackagerFactory = fieldContext.getFieldPackagerFactory();
    final Class<? extends ISOFieldPackager> fieldPackagerClass;
    if (fieldSchema.getPackager() != null) {
      fieldPackagerClass = fieldSchema.getPackager();
    } else {
      // Current class is a default field packager
      fieldPackagerClass = this.getClass();
    }
    final ISOFieldPackager fieldPackager =
        fieldPackagerFactory.getFieldPackager(fieldPackagerClass);
    ValidationUtils.validateNotNull(fieldPackager, String.format(
        "Field packager '%s' is not found! Please check that this packager is exist and added to the factory!",
        fieldPackagerClass));
    return fieldPackager;
  }

  protected abstract byte[] packMessageType(ISOFieldPackContext fieldPackContext);

  /**
   * Converts message type into MTI format string 'xxxx'.
   *
   * @param messageType message to convert
   * @return converted message type in MTI format
   */
  protected String convertMessageTypeToMTIString(MessageType messageType) {
    ValidationUtils.validateNotNull(messageType, "Message Type is missing!");
    String version =
        Optional.of(messageType).map(MessageType::getVersion).map(MessageVersion::getValue)
            .map(String::valueOf).orElse("0");
    String clazz = Optional.of(messageType).map(MessageType::getClazz).map(MessageClass::getValue)
        .map(String::valueOf).orElse("0");
    String function =
        Optional.of(messageType).map(MessageType::getFunction).map(MessageFunction::getValue)
            .map(String::valueOf).orElse("0");
    String origin =
        Optional.of(messageType).map(MessageType::getOrigin).map(MessageOrigin::getValue)
            .map(String::valueOf).orElse("0");
    return version + clazz + function + origin;
  }

  /**
   * Converts MTI string into Message Type object.
   *
   * @param mti mti string to convert from
   * @return converted message type
   */
  protected MessageType convertMTIStringToMessageType(String mti) {
    if (StringUtils.isEmpty(mti)) {
      throw new ISOValidationException("MTI header is missing!");
    }
    if (mti.length() != 4) {
      throw new ISOValidationException("MTI header should have length equals to 4!");
    }
    final char[] mtiChars = mti.toCharArray();
    return MessageType.builder().version(MessageVersion.fromChar(mtiChars[0]))
        .clazz(MessageClass.fromChar(mtiChars[1])).function(MessageFunction.fromChar(mtiChars[2]))
        .origin(MessageOrigin.fromChar(mtiChars[3])).build();
  }

  protected abstract MessageType unpackMessageType(ISOFieldUnpackContext fieldUnpackContext);

  protected abstract byte[] packBitmap(ISOFieldPackContext fieldPackContext);

  protected abstract BitSet unpackBitmap(ISOFieldUnpackContext fieldUnpackContext);

  protected abstract byte[] packAlpha(ISOFieldPackContext fieldPackContext);

  protected abstract ISOField unpackAlpha(ISOFieldUnpackContext fieldUnpackContext);

  protected abstract byte[] packNumeric(ISOFieldPackContext fieldPackContext);

  protected abstract ISOField unpackNumeric(ISOFieldUnpackContext fieldUnpackContext);

  protected abstract byte[] packLLVAR(ISOFieldPackContext fieldPackContext);

  protected abstract ISOField unpackLLVAR(ISOFieldUnpackContext fieldUnpackContext);

  protected abstract byte[] packLLLVAR(ISOFieldPackContext fieldPackContext);

  protected abstract ISOField unpackLLLVAR(ISOFieldUnpackContext fieldUnpackContext);

  protected abstract byte[] packLLLLVAR(ISOFieldPackContext fieldPackContext);

  protected abstract ISOField unpackLLLLVAR(ISOFieldUnpackContext fieldUnpackContext);

  protected abstract byte[] packBinary(ISOFieldPackContext fieldPackContext);

  protected abstract ISOField unpackBinary(ISOFieldUnpackContext fieldUnpackContext);

  protected abstract byte[] packLLBinary(ISOFieldPackContext fieldPackContext);

  protected abstract ISOField unpackLLBinary(ISOFieldUnpackContext fieldUnpackContext);

  protected abstract byte[] packLLLBinary(ISOFieldPackContext fieldPackContext);

  protected abstract ISOField unpackLLLBinary(ISOFieldUnpackContext fieldUnpackContext);

  protected abstract byte[] packLLLLBinary(ISOFieldPackContext fieldPackContext);

  protected abstract ISOField unpackLLLLBinary(ISOFieldUnpackContext fieldUnpackContext);
}
