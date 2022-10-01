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
package com.dharbuzov.iso8583.server;

import com.dharbuzov.iso8583.channel.ISOServerChannel;
import com.dharbuzov.iso8583.factory.ISOEventFactory;
import com.dharbuzov.iso8583.factory.ISOMessageListenerFactory;
import com.dharbuzov.iso8583.listener.ISOEventListener;
import com.dharbuzov.iso8583.listener.ISOMessageListener;
import com.dharbuzov.iso8583.server.config.ISOServerProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link ISOServer} which works properly. This class could be easily
 * extended based on specific needs.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Slf4j
public class ISODefaultServer implements ISOServer {

  protected final ISOServerProperties serverProperties;
  protected final ISOServerChannel serverChannel;
  protected final ISOMessageListenerFactory listenerFactory;
  protected final ISOEventFactory eventFactory;

  /**
   * Default server constructor.
   *
   * @param serverProperties server properties
   * @param serverChannel    server channel
   * @param listenerFactory  message listener factory
   * @param eventFactory     event factory
   */
  public ISODefaultServer(ISOServerProperties serverProperties, ISOServerChannel serverChannel,
      ISOMessageListenerFactory listenerFactory, ISOEventFactory eventFactory) {
    this.serverProperties = serverProperties;
    this.serverChannel = serverChannel;
    this.listenerFactory = listenerFactory;
    this.eventFactory = eventFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void start() {
    this.serverChannel.start();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isRunning() {
    return serverChannel.isRunning();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void shutdown() {
    serverChannel.shutdown();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addMessageListener(ISOMessageListener messageListener) {
    listenerFactory.addMessageListener(messageListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeMessageListener(ISOMessageListener messageListener) {
    listenerFactory.removeMessageListener(messageListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeMessageListeners() {
    listenerFactory.removeMessageListeners();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addEventListener(ISOEventListener eventListener) {
    eventFactory.addEventListener(eventListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeEventListener(ISOEventListener eventListener) {
    eventFactory.removeEventListener(eventListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeEventListeners() {
    eventFactory.removeEventListeners();
  }
}
