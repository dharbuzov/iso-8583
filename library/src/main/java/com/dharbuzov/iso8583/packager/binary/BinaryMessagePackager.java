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
package com.dharbuzov.iso8583.packager.binary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.dharbuzov.iso8583.exception.ISOPackageException;
import com.dharbuzov.iso8583.packager.ISOBaseMessagePackager;
import com.dharbuzov.iso8583.packager.ISOFieldPackager;
import com.dharbuzov.iso8583.packager.model.ISOMessagePackContext;
import com.dharbuzov.iso8583.packager.model.ISOMessageUnpackContext;
import com.dharbuzov.iso8583.util.ByteStreamUtils;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class BinaryMessagePackager extends ISOBaseMessagePackager {

  public static final int MAX_LENGTH_DIGITS = 4;

  @Override
  protected void packLength(ISOMessagePackContext messagePackContext, int length) {
    final int lengthDigits = messagePackContext.getMessageLengthDigits();
    if (length < 0) {
      throw new ISOPackageException("Invalid negative length '%s'", length);
    }
    if (lengthDigits > MAX_LENGTH_DIGITS) {
      throw new ISOPackageException(
          "Invalid message length digits '%s', for binary packager message length could not be "
          + "more than '%s'", lengthDigits, MAX_LENGTH_DIGITS);
    }
    final byte[] lengthBytes = new byte[lengthDigits];
    switch (lengthDigits) {
      case 1:
        lengthBytes[0] = (byte) (length);
        break;
      case 2:
        lengthBytes[0] = (byte) (length >> 8);
        lengthBytes[1] = (byte) (length);
        break;
      case 3:
        lengthBytes[0] = (byte) (length >> 16);
        lengthBytes[1] = (byte) (length >> 8);
        lengthBytes[2] = (byte) (length);
        break;
      case 4:
        lengthBytes[0] = (byte) (length >> 24);
        lengthBytes[1] = (byte) (length >> 16);
        lengthBytes[2] = (byte) (length >> 8);
        lengthBytes[3] = (byte) (length);
        break;
    }
    final ByteArrayOutputStream bout = messagePackContext.getBout();
    ByteStreamUtils.writeBytes(bout, lengthBytes);
  }

  @Override
  protected Class<? extends ISOFieldPackager> getDefaultFieldPackagerClass() {
    return BinaryFieldPackager.class;
  }

  @Override
  protected int unpackLength(ISOMessageUnpackContext messageUnpackContext) {
    final int lengthDigits = messageUnpackContext.getMessageLengthDigits();
    final ByteArrayInputStream bin = messageUnpackContext.getBin();
    final byte[] lengthBytes = new byte[lengthDigits];
    ByteStreamUtils.readBytes(bin, lengthBytes, 0, lengthDigits);
    int length = 0;
    switch (lengthDigits) {
      case 1:
        length = lengthBytes[0] & 0xFF;
        break;
      case 2:
        length = ((int) lengthBytes[0] & 0xFF) << 8 | (int) lengthBytes[1] & 0xFF;
        break;
      case 3:
        length = ((int) lengthBytes[0] & 0xFF) << 16 | ((int) lengthBytes[1] & 0xFF) << 8
                 | (int) lengthBytes[2] & 0xFF;
        break;
      case 4:
        length = ((int) lengthBytes[0] & 0xFF) << 24 | ((int) lengthBytes[1] & 0xFF) << 16
                 | ((int) lengthBytes[2] & 0xFF) << 8 | (int) lengthBytes[3] & 0xFF;
        break;
    }
    return length;
  }
}
