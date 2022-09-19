package com.dharbuzov.iso8583.factory;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultPackagerFactory implements ISOPackagerFactory {

  @Override
  public byte[] pack(ISOMessage msg) {
    return new byte[0];
  }

  @Override
  public ISOMessage unpack(byte[] msgBytes) {
    return null;
  }
}
