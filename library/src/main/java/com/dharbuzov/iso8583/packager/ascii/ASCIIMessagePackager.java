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
package com.dharbuzov.iso8583.packager.ascii;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;

import com.dharbuzov.iso8583.exception.ISOPackageException;
import com.dharbuzov.iso8583.packager.ISOBaseMessagePackager;
import com.dharbuzov.iso8583.packager.ISOFieldPackager;
import com.dharbuzov.iso8583.packager.model.ISOMessagePackContext;
import com.dharbuzov.iso8583.packager.model.ISOMessageUnpackContext;
import com.dharbuzov.iso8583.util.ByteStreamUtils;
import com.dharbuzov.iso8583.util.PaddingUtils;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ASCIIMessagePackager extends ISOBaseMessagePackager {

  @Override
  protected void packLength(ISOMessagePackContext messagePackContext, int length) {
    final Charset encoding = messagePackContext.getEncoding();
    final int lengthDigits = messagePackContext.getMessageLengthDigits();
    if (length < 0) {
      throw new ISOPackageException("Invalid negative length '%s'", length);
    }
    // Maximum possible length based on number of digits of length is 10^lengthDigits - 1
    final int maxLen = BigInteger.TEN.pow(lengthDigits).intValue() - 1;
    if (length > maxLen) {
      throw new ISOPackageException("Length '%s' exceeds the maximum possible value '%'", length,
          maxLen);
    }
    final ByteArrayOutputStream bout = messagePackContext.getBout();
    final byte[] lengthBytes = PaddingUtils.padLeftZeros(length, lengthDigits).getBytes(encoding);
    ByteStreamUtils.writeBytes(bout, lengthBytes);
  }

  @Override
  protected int unpackLength(ISOMessageUnpackContext messageUnpackContext) {
    final Charset encoding = messageUnpackContext.getEncoding();
    final int lengthDigits = messageUnpackContext.getMessageLengthDigits();
    final ByteArrayInputStream bin = messageUnpackContext.getBin();
    final byte[] lengthBytes = new byte[lengthDigits];
    ByteStreamUtils.readBytes(bin, lengthBytes, 0, lengthDigits);
    return Integer.parseInt(new String(lengthBytes, encoding));
  }


  @Override
  protected Class<? extends ISOFieldPackager> getDefaultFieldPackagerClass() {
    return ASCIIFieldPackager.class;
  }
}
