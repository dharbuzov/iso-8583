package com.dharbuzov.iso8583.listener;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOMessageListener extends ISOPrecedenceListener {

  void onMessage(ISOMessage message);

  boolean isApplicable(ISOMessage message);

}
