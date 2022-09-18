

package com.dharbuzov.iso8583.factory;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOMessageFactory {

  byte[] pack(ISOMessage message);

  ISOMessage unpack(byte[] msgBytes);
}
