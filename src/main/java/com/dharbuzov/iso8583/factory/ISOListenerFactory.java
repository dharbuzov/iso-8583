package com.dharbuzov.iso8583.factory;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOListenerFactory {

  ISOMessage onMessage(ISOMessage message);
}
