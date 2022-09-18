package com.dharbuzov.iso8583.client;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOSyncClient extends ISOClient {

  ISOMessage send(ISOMessage msg);
}
