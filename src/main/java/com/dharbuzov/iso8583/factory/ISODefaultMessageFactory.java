package com.dharbuzov.iso8583.factory;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultMessageFactory implements ISOMessageFactory {

  @Override
  public byte[] pack(ISOMessage message) {
    return new byte[0];
  }

  @Override
  public ISOMessage unpack(byte[] msgBytes) {
    return null;
  }
}
