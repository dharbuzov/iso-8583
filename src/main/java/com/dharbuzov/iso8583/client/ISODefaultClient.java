package com.dharbuzov.iso8583.client;

import java.util.Objects;

import com.dharbuzov.iso8583.channel.ISOClientChannel;
import com.dharbuzov.iso8583.client.config.ISOClientProperties;
import com.dharbuzov.iso8583.factory.ISOMessageFactory;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.util.StringUtils;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultClient implements ISOSyncClient {

  private final String name;
  private final ISOClientChannel channel;

  public ISODefaultClient(ISOClientProperties properties,
      ISOClientChannel channel) {
    assert properties.isKeepAlive();
    Objects.requireNonNull(properties);
    this.name = getOrCreateName(properties);
    this.channel = channel;
  }

  private String getOrCreateName(ISOClientProperties properties) {
    if (StringUtils.isEmpty(properties.getName())) {
      return properties.getConnection().getInetSocketAddress().toString();
    }
    return properties.getName();
  }

  @Override
  public void connect() {
    channel.connect();
  }

  @Override
  public boolean isConnected() {
    return channel.isConnected();
  }

  @Override
  public void sendAsync(ISOMessage message) {
    channel.send(message);
  }

  @Override
  public ISOMessage send(ISOMessage msg) {
    return null;
  }

  @Override
  public ISOClientChannel getChannel() {
    return channel;
  }
}
