package com.dharbuzov.iso8583.factory;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultListenerFactory implements ISOListenerFactory {

  @Override
  public ISOMessage onMessage(ISOMessage message) {
    return null;
  }
}
