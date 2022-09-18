package com.dharbuzov.iso8583.packager;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOMessagePackager {

  byte[] encode(ISOMessage message);

  ISOMessage decode(byte[] msgBytes);
}
