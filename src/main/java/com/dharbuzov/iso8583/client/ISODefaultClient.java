package com.dharbuzov.iso8583.client;

import java.util.Objects;
import java.util.concurrent.Future;

import com.dharbuzov.iso8583.channel.ISOClientChannel;
import com.dharbuzov.iso8583.client.config.ISOClientProperties;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.util.StringUtils;

import lombok.Getter;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultClient implements ISOSyncClient {

  @Getter
  private final String name;

  @Getter
  private final ISOClientChannel channel;

  public ISODefaultClient(ISOClientProperties properties, ISOClientChannel channel) {
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
    channel.sendAsync(message);
  }

  @Override
  public Future<ISOMessage> sendFuture(ISOMessage msg) {
    return channel.sendFuture(msg);
  }

  @Override
  public ISOMessage send(ISOMessage msg) {
    return channel.send(msg);
  }

  @Override
  public ISOMessage send(ISOMessage msg, long requestTimeoutMs) {
    return channel.send(msg, requestTimeoutMs);
  }
}
