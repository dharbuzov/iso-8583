package com.dharbuzov.iso8583.coordinator;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOMessageCoordinator {
  void in(ISOMessage msg);
}
