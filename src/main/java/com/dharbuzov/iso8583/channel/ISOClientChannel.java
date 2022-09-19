

package com.dharbuzov.iso8583.channel;

import java.util.concurrent.Future;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOClientChannel extends ISOChannel {
  void connect();

  void disconnect();

  boolean isConnected();

  boolean sendAsync(ISOMessage msg);

  ISOMessage send(ISOMessage msg);

  ISOMessage send(ISOMessage msg, long requestTimeoutMs);

  Future<ISOMessage> sendFuture(ISOMessage msg);

  @Override
  default boolean isActive() {
    return isConnected();
  }
}
