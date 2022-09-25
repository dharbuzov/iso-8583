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
package com.dharbuzov.iso8583.server.config;

import com.dharbuzov.iso8583.channel.ChannelType;
import com.dharbuzov.iso8583.channel.ISOServerChannel;
import com.dharbuzov.iso8583.channel.netty.ISOServerNettyChannel;
import com.dharbuzov.iso8583.config.ISOBaseConfiguration;
import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.server.ISODefaultServer;
import com.dharbuzov.iso8583.server.ISOServer;

import lombok.Builder;
import lombok.Getter;

/**
 * Server configuration class.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOServerConfiguration
    extends ISOBaseConfiguration<ISOServerProperties, ISOServerChannel> {

  @Getter
  protected final ISOServer server;

  /**
   * Server configuration class.
   *
   * @param properties server properties
   */
  @Builder
  public ISOServerConfiguration(ISOServerProperties properties) {
    super(properties);
    this.server = createServer(properties, channel);
  }

  @Override
  public ISOServerChannel createChannel(ISOServerProperties properties) {
    final ChannelType channelType = properties.getChannelTypeOrDefault();
    switch (channelType) {
      case NETTY:
        return new ISOServerNettyChannel(properties, packagerFactory, listenerFactory);
      default:
        throw new ISOException("Can't create a channel for type: '%s'", channelType);
    }
  }

  protected ISOServer createServer(ISOServerProperties properties, ISOServerChannel channel) {
    return new ISODefaultServer(properties, channel);
  }
}
