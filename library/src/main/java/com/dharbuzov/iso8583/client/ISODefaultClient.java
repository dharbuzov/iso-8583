package com.dharbuzov.iso8583.client;

import java.util.Objects;
import java.util.concurrent.Future;

import com.dharbuzov.iso8583.channel.ISOClientChannel;
import com.dharbuzov.iso8583.client.config.ISOClientProperties;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.util.StringUtils;

import lombok.Getter;

/**
 * Default implementation of {@link ISOSyncClient} which works properly. This class could be easily
 * extended based on specific needs.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultClient implements ISOSyncClient {

  @Getter
  protected final String name;

  @Getter
  protected final ISOClientChannel channel;

  /**
   * Constructor based on properties and client channel.
   *
   * @param properties properties to configure the client
   * @param channel    client channel
   */
  public ISODefaultClient(ISOClientProperties properties, ISOClientChannel channel) {
    Objects.requireNonNull(properties);
    this.name = getOrCreateName(properties);
    this.channel = channel;
  }

  /**
   * Gets or creates the default client name based on host and port of the service.
   *
   * @param properties client properties
   * @return provided of default name of the client
   */
  private String getOrCreateName(ISOClientProperties properties) {
    if (StringUtils.isEmpty(properties.getName())) {
      return properties.getConnection().getInetSocketAddress().toString();
    }
    return properties.getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void connect() {
    channel.connect();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isConnected() {
    return channel.isConnected();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sendAsync(ISOMessage message) {
    channel.sendAsync(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Future<ISOMessage> sendFuture(ISOMessage msg) {
    return channel.sendFuture(msg);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOMessage send(ISOMessage msg) {
    return channel.send(msg);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOMessage send(ISOMessage msg, long requestTimeoutMs) {
    return channel.send(msg, requestTimeoutMs);
  }
}
