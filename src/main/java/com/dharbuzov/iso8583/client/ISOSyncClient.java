package com.dharbuzov.iso8583.client;

import java.util.concurrent.Future;

import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOSyncClient extends ISOClient {

  Future<ISOMessage> sendFuture(ISOMessage msg) throws ISOException;

  ISOMessage send(ISOMessage msg) throws ISOException;
}
