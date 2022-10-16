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

import java.nio.charset.Charset;
import java.util.BitSet;

import com.dharbuzov.iso8583.model.MessageType;
import com.dharbuzov.iso8583.model.field.ISOField;
import com.dharbuzov.iso8583.packager.ISOBaseFieldPackager;
import com.dharbuzov.iso8583.packager.model.ISOFieldPackContext;
import com.dharbuzov.iso8583.packager.model.ISOFieldUnpackContext;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class BinaryFieldPackager extends ISOBaseFieldPackager {

  @Override
  protected byte[] packMessageType(ISOFieldPackContext fieldPackContext) {
    final Charset encoding = fieldPackContext.getEncoding();
    final MessageType messageType = fieldPackContext.getFieldValue(MessageType.class);

    return new byte[0];
  }

  @Override
  protected MessageType unpackMessageType(ISOFieldUnpackContext fieldUnpackContext) {
    return null;
  }

  @Override
  protected byte[] packBitmap(ISOFieldPackContext fieldPackContext) {
    return new byte[0];
  }

  @Override
  protected BitSet unpackBitmap(ISOFieldUnpackContext fieldUnpackContext) {
    return null;
  }

  @Override
  protected byte[] packAlpha(ISOFieldPackContext fieldPackContext) {
    return new byte[0];
  }

  @Override
  protected ISOField unpackAlpha(ISOFieldUnpackContext fieldUnpackContext) {
    return null;
  }

  @Override
  protected byte[] packNumeric(ISOFieldPackContext fieldPackContext) {
    return new byte[0];
  }

  @Override
  protected ISOField unpackNumeric(ISOFieldUnpackContext fieldUnpackContext) {
    return null;
  }

  @Override
  protected byte[] packLLVAR(ISOFieldPackContext fieldPackContext) {
    return new byte[0];
  }

  @Override
  protected ISOField unpackLLVAR(ISOFieldUnpackContext fieldUnpackContext) {
    return null;
  }

  @Override
  protected byte[] packLLLVAR(ISOFieldPackContext fieldPackContext) {
    return new byte[0];
  }

  @Override
  protected ISOField unpackLLLVAR(ISOFieldUnpackContext fieldUnpackContext) {
    return null;
  }

  @Override
  protected byte[] packLLLLVAR(ISOFieldPackContext fieldPackContext) {
    return new byte[0];
  }

  @Override
  protected ISOField unpackLLLLVAR(ISOFieldUnpackContext fieldUnpackContext) {
    return null;
  }

  @Override
  protected byte[] packBinary(ISOFieldPackContext fieldPackContext) {
    return new byte[0];
  }

  @Override
  protected ISOField unpackBinary(ISOFieldUnpackContext fieldUnpackContext) {
    return null;
  }

  @Override
  protected byte[] packLLBinary(ISOFieldPackContext fieldPackContext) {
    return new byte[0];
  }

  @Override
  protected ISOField unpackLLBinary(ISOFieldUnpackContext fieldUnpackContext) {
    return null;
  }

  @Override
  protected byte[] packLLLBinary(ISOFieldPackContext fieldPackContext) {
    return new byte[0];
  }

  @Override
  protected ISOField unpackLLLBinary(ISOFieldUnpackContext fieldUnpackContext) {
    return null;
  }

  @Override
  protected byte[] packLLLLBinary(ISOFieldPackContext fieldPackContext) {
    return new byte[0];
  }

  @Override
  protected ISOField unpackLLLLBinary(ISOFieldUnpackContext fieldUnpackContext) {
    return null;
  }
}
