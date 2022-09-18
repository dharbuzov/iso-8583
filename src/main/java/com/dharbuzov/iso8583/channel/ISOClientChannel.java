

package com.dharbuzov.iso8583.channel;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOClientChannel extends ISOChannel {
  void connect();

  void disconnect();

  boolean isConnected();

  void send(ISOMessage msg);

  @Override
  default boolean isActive() {
    return isConnected();
  }
}
