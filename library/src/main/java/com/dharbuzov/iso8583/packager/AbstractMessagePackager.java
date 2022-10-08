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

import com.dharbuzov.iso8583.model.ISOField;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.schema.ISOSchema;
import com.dharbuzov.iso8583.util.StringUtils;

import lombok.SneakyThrows;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class AbstractMessagePackager implements ISOMessagePackager {

  private final ISOSchema schema = new ISOSchema();

  @Override
  @SneakyThrows
  public byte[] pack(ISOMessage message) {
    final ByteArrayOutputStream dataBout = new ByteArrayOutputStream();
    packHeader(dataBout, message);
    packMessageType(dataBout, message);
    packBitMap(dataBout, message);
    packFields(dataBout, message);
    final byte[] data = dataBout.toByteArray();
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    packLength(out, data);
    out.write(data);
    packEtx(out, message);
    return out.toByteArray();
  }

  protected abstract void packLength(ByteArrayOutputStream out, byte[] data);

  @SneakyThrows
  protected void packHeader(ByteArrayOutputStream bout, ISOMessage message) {
    if (StringUtils.isNotEmpty(message.getHeaderStr())) {
      bout.write(message.getHeaderStr().getBytes(schema.getEncoding()));
    } else if(message.getHeaderBytes() != null) {
      bout.write(message.getHeaderBytes());
    }
  }

  protected void packEtx(ByteArrayOutputStream bout, ISOMessage message) {
    final int etx = message.getEtx();
    if (etx != -1) {
      bout.write(etx);
    }
  }

  protected abstract void packMessageType(ByteArrayOutputStream bout, ISOMessage message);

  protected abstract void packBitMap(ByteArrayOutputStream bout, ISOMessage message);

  @SneakyThrows
  protected void packFields(ByteArrayOutputStream bout, ISOMessage message) {
    final ByteArrayOutputStream fieldsBout = new ByteArrayOutputStream();
    final ISOField[] fields = message.getFields();
    // Starting from the field 2, since the MTI and BITMAP already packed above
    for (int i = 2; i < fields.length; i++) {
      ISOField field = fields[i];
      if (field != null) {
        packField(bout, i, field);
      }
    }
    fieldsBout.writeTo(bout);
  }

  protected abstract void packField(ByteArrayOutputStream bout, int position, ISOField field);

  @Override
  public ISOMessage unpack(byte[] msgBytes) {
    return null;
  }
}
