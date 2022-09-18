

package com.dharbuzov.iso8583.client;

import com.dharbuzov.iso8583.channel.ISOClientChannel;
import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOClient {

  String DEFAULT_CLIENT_NAME_FORMAT = "%s:%s";

  void connect();

  boolean isConnected();

  void sendAsync(ISOMessage msg);

  ISOClientChannel getChannel();
}
