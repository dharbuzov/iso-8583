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
package com.dharbuzov.iso8583.channel.netty;

import com.dharbuzov.iso8583.config.ISOBaseProperties;
import com.dharbuzov.iso8583.config.ISOConnProperties;
import com.dharbuzov.iso8583.factory.ISOMessageListenerFactory;
import com.dharbuzov.iso8583.factory.ISOPackagerFactory;

import io.netty.channel.Channel;

/**
 * The abstract implementation of netty based channel, which defines the default channel handlers
 * and required classes for message handling.
 *
 * @param <T> the generic subtype of iso base properties
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class ISOBaseNettyChannel<T extends ISOBaseProperties> {

  protected final T properties;
  protected final ISOConnProperties connProperties;
  protected final ISOPackagerFactory packagerFactory;
  protected final ISOMessageListenerFactory listenerFactory;
  protected Channel nettyChannel;

  /**
   * Base netty channel constructor.
   *
   * @param properties      iso properties
   * @param packagerFactory packager factory
   * @param listenerFactory listener factory
   */
  public ISOBaseNettyChannel(T properties, ISOPackagerFactory packagerFactory,
      ISOMessageListenerFactory listenerFactory) {
    this.properties = properties;
    this.connProperties = properties.getConnection();
    this.packagerFactory = packagerFactory;
    this.listenerFactory = listenerFactory;
  }
}
