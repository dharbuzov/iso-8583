/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.channel;

import java.util.Objects;

import com.dharbuzov.iso8583.ISOChannel;
import com.dharbuzov.iso8583.config.ISOClientProperties;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOChannelImpl implements ISOChannel {
  private final String host;
  private final int port;
  private ChannelState state;

  public ISOChannelImpl(ISOClientProperties properties) {
    Objects.requireNonNull(properties);
    this.host = Objects.requireNonNull(properties.getHost());
    this.port = Objects.requireNonNull(properties.getPort());
    this.state = ChannelState.CREATED;
  }

  @Override
  public void connect() {
    this.state = ChannelState.CONNECTED;
  }

  @Override
  public boolean isConnected() {
    return this.state != null && ChannelState.CONNECTED == this.state;
  }

  @Override
  public void send(byte[] bytes) {
    if (!this.isConnected()) {
      this.connect();
    }
    //TODO: impl
  }

  @Override
  public byte[] receive() {
    return new byte[0];
  }
}
