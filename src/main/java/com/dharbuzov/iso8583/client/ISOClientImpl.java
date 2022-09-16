/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.client;

import java.util.Objects;
import java.util.UUID;

import com.dharbuzov.iso8583.ISOChannel;
import com.dharbuzov.iso8583.ISOClient;
import com.dharbuzov.iso8583.ISOMessageFactory;
import com.dharbuzov.iso8583.config.ISOClientProperties;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.util.StringUtils;

/**
 * @author Dmytriy Harbuzov (dmitriy.harbuzov@paydock.com).
 */
public class ISOClientImpl implements ISOClient {

  private final String name;
  private final ISOMessageFactory messageFactory;
  private final ISOChannel channel;

  public ISOClientImpl(ISOClientProperties properties, ISOMessageFactory messageFactory,
      ISOChannel channel) {
    Objects.requireNonNull(properties);
    Objects.requireNonNull(messageFactory);
    this.name = getOrCreateName(properties);
    this.messageFactory = messageFactory;
    this.channel = createChannel(properties);
  }

  private String getOrCreateName(ISOClientProperties properties) {
    if (StringUtils.isEmpty(properties.getName())) {
      return String.format(DEFAULT_CLIENT_NAME_FORMAT, properties.getHost(), properties.getPort(),
          UUID.randomUUID());
    }
    return properties.getName();
  }

  private ISOChannel createChannel(ISOClientProperties properties) {
    return null;
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
    final byte[] msgBytes = this.messageFactory.encode(message);
    channel.send(msgBytes);
  }

  @Override
  public ISOMessage send(ISOMessage message) {
    return null;
  }

  @Override
  public ISOChannel getChannel() {
    return this.channel;
  }
}
