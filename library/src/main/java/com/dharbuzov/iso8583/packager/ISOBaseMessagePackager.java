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
import java.io.IOException;
import java.util.BitSet;

import com.dharbuzov.iso8583.exception.ISOPackageException;
import com.dharbuzov.iso8583.model.ISOField;
import com.dharbuzov.iso8583.model.ISOFieldType;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.ISOValueType;
import com.dharbuzov.iso8583.model.schema.ISOFieldSchema;
import com.dharbuzov.iso8583.packager.model.ISOPackagerContext;
import com.dharbuzov.iso8583.util.StringUtils;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class ISOBaseMessagePackager implements ISOMessagePackager {

  @Override
  public byte[] pack(ISOPackagerContext packagerContext) {
    final ByteArrayOutputStream dataBout = new ByteArrayOutputStream();
    packagerContext.setBout(dataBout);
    packHeader(packagerContext);
    packFields(packagerContext);
    final byte[] data = dataBout.toByteArray();
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    packagerContext.setBout(out);
    packLength(packagerContext, data);
    writeBytes(out, data);
    packEtx(packagerContext);
    return out.toByteArray();
  }

  /**
   * Packs the message length at the beginning of the message.
   *
   * @param packagerContext packager context
   * @param data            packed data of the message to set the length
   */
  protected abstract void packLength(ISOPackagerContext packagerContext, byte[] data);

  /**
   * Packs the message header.
   *
   * @param packagerContext packager context
   */
  protected void packHeader(ISOPackagerContext packagerContext) {
    final ByteArrayOutputStream bout = packagerContext.getBout();
    final ISOMessage message = packagerContext.getMessage();
    byte[] headerBytes = null;
    if (StringUtils.isNotEmpty(message.getHeaderStr())) {
      headerBytes = message.getHeaderStr().getBytes(packagerContext.getEncoding());
    } else if (message.getHeaderBytes() != null) {
      headerBytes = message.getHeaderBytes();
    }
    if (headerBytes != null) {
      writeBytes(bout, headerBytes);
    }
  }

  /**
   * Packs the message trailer if exists.
   *
   * @param packagerContext packager context
   */
  protected void packEtx(ISOPackagerContext packagerContext) {
    final ByteArrayOutputStream bout = packagerContext.getBout();
    final ISOMessage message = packagerContext.getMessage();
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
   * @param packagerContext packager context
   */
  protected void packFields(ISOPackagerContext packagerContext) {
    final ISOMessage message = packagerContext.getMessage();
    final ByteArrayOutputStream bout = packagerContext.getBout();
    final ByteArrayOutputStream fieldsBout = new ByteArrayOutputStream();
    final ISOField[] fields = message.getFields();
    // Packing the message type
    final ISOField type = ISOField.builder().value(message.getType().toMTIString()).build();
    writeBytes(fieldsBout, packField(packagerContext, 0, type));
    // Packing the BITMAP
    final ISOField bitmap = ISOField.builder().value(new BitSet()).build();
    writeBytes(fieldsBout, packField(packagerContext, 1, bitmap));
    // Starting from the field 2, since the MTI and BITMAP already packed above
    for (int i = 2; i < fields.length; i++) {
      ISOField field = fields[i];
      if (field != null) {
        writeBytes(fieldsBout, packField(packagerContext, i, field));
      }
    }
    writeBout(fieldsBout, bout);
  }

  /**
   * Packs the field.
   *
   * @param packagerContext packager context
   * @param position        field's position
   * @param field           field value
   * @return packed field into the byte array
   */
  protected byte[] packField(ISOPackagerContext packagerContext, int position, ISOField field) {
    ISOFieldSchema fieldSchema = packagerContext.getMessageSchema().getField(position);
    if (fieldSchema == null && position == 0) {
      // For position 0 (Message Type) we are defining the default schema
      fieldSchema = ISOFieldSchema.builder().name("type").valueType(ISOValueType.MESSAGE_TYPE)
          .fieldType(ISOFieldType.PRIMITIVE).build();
    }
    if (fieldSchema == null && position == 1) {
      // For position 1 (Bitmap) we are defining the default schema
      fieldSchema = ISOFieldSchema.builder().name("bitmap").valueType(ISOValueType.BITMAP)
          .fieldType(ISOFieldType.PRIMITIVE).build();
    }
    if (fieldSchema == null) {
      throw new ISOPackageException(
          "Can't package field '%s', since there is no defined schema for it!", position);
    }
    Class<? extends ISOFieldPackager> fieldPackagerClass = fieldSchema.getPackager() == null
                                                           ? getDefaultFieldPackagerClass()
                                                           : fieldSchema.getPackager();
    ISOFieldPackager fieldPackager =
        packagerContext.getFieldPackagerFactory().getFieldPackager(fieldPackagerClass);
    packagerContext.setField(field);
    packagerContext.setFieldPosition(position);
    packagerContext.setFieldSchema(fieldSchema);
    return fieldPackager.pack(packagerContext);
  }

  /**
   * Writes the bytes into output byte stream
   *
   * @param bout  output byte stream
   * @param bytes bytes to write
   */
  protected void writeBytes(ByteArrayOutputStream bout, byte[] bytes) {
    try {
      bout.write(bytes);
    } catch (IOException e) {
      throw new ISOPackageException(e);
    }
  }

  /**
   * Writes the output byte stream into another output byte stream.
   *
   * @param from from byte stream
   * @param to   to byte stream
   */
  protected void writeBout(ByteArrayOutputStream from, ByteArrayOutputStream to) {
    try {
      from.writeTo(to);
    } catch (IOException e) {
      throw new ISOPackageException(e);
    }
  }

  @Override
  public ISOMessage unpack(byte[] msgBytes) {
    return null;
  }
}
