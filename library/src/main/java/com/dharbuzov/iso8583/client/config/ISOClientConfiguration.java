/*
 * Copyright 2022.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dharbuzov.iso8583.client.config;

import com.dharbuzov.iso8583.channel.ChannelType;
import com.dharbuzov.iso8583.channel.ISOClientChannel;
import com.dharbuzov.iso8583.channel.netty.ISOClientNettyChannel;
import com.dharbuzov.iso8583.client.ISODefaultClient;
import com.dharbuzov.iso8583.client.ISOSyncClient;
import com.dharbuzov.iso8583.config.ISOBaseConfiguration;
import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.factory.ISOEventFactory;
import com.dharbuzov.iso8583.factory.ISOMessageListenerFactory;

import lombok.Builder;
import lombok.Getter;

/**
 * Client configuration class.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOClientConfiguration
    extends ISOBaseConfiguration<ISOClientProperties, ISOClientChannel> {

  @Getter
  protected final ISOSyncClient client;

  /**
   * Client configuration constructor.
   *
   * @param properties client configuration properties
   */
  @Builder
  public ISOClientConfiguration(ISOClientProperties properties) {
    super(properties);
    this.client = createClient(properties, channel, listenerFactory, eventFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOClientChannel createChannel(ISOClientProperties properties) {
    final ChannelType channelType;
    if (properties.getChannelType() == null) {
      channelType = ChannelType.defaultChannelType();
    } else {
      channelType = properties.getChannelType();
    }
    switch (channelType) {
      case NETTY:
        return new ISOClientNettyChannel(properties, packagerFactory, listenerFactory,
            messageBinder, messageKeyGenerator);
      default:
        throw new ISOException("Can't create a channel for type: '%s'", channelType);
    }
  }

  /**
   * Creates the client based on properties and created channel.
   *
   * @param properties      properties to configure the client
   * @param channel         client created channel
   * @param listenerFactory message listener factory
   * @param eventFactory    event factory
   * @return created client interface
   */
  protected ISOSyncClient createClient(ISOClientProperties properties, ISOClientChannel channel,
      ISOMessageListenerFactory listenerFactory, ISOEventFactory eventFactory) {
    return new ISODefaultClient(properties, channel, listenerFactory, eventFactory);
  }
}
