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
package com.dharbuzov.iso8583.config;

import com.dharbuzov.iso8583.channel.ISOChannel;
import com.dharbuzov.iso8583.factory.ISODefaultEventFactory;
import com.dharbuzov.iso8583.factory.ISODefaultMessageListenerFactory;
import com.dharbuzov.iso8583.factory.ISODefaultPackagerFactory;
import com.dharbuzov.iso8583.factory.ISOEventFactory;
import com.dharbuzov.iso8583.factory.ISOMessageListenerFactory;
import com.dharbuzov.iso8583.factory.ISOPackagerFactory;
import com.dharbuzov.iso8583.model.schema.ISOSchema;
import com.dharbuzov.iso8583.packager.ASCIIMessagePackager;
import com.dharbuzov.iso8583.packager.HEXMessagePackager;

/**
 * Base implementation of library configuration.
 *
 * @param <T> the generic type for library properties
 * @param <C> the generic type for desired channel
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class ISOBaseConfiguration<T extends ISOBaseProperties, C extends ISOChannel>
    implements ISOConfiguration<T, C> {

  protected final C channel;
  protected final ISOMessageListenerFactory listenerFactory;
  protected final ISOEventFactory eventFactory;
  protected final ISOPackagerFactory packagerFactory;
  protected final ISOSchema schema;

  /**
   * Base configuration constructor.
   *
   * @param properties configuration properties
   */
  public ISOBaseConfiguration(T properties) {
    this.beforeConstruct(properties);
    this.schema = properties.getSchema();
    this.listenerFactory = createListenerFactory(properties);
    this.eventFactory = createEventFactory(properties);
    this.packagerFactory = createPackagerFactory(properties);
    this.channel = createChannel(properties);
    addDefaultPackagers(this.packagerFactory);
  }

  /**
   * Method which would be called before calling the constructor of base configuration.
   *
   * @param properties configuration properties
   */
  protected void beforeConstruct(T properties) {
    //NOOP
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOEventFactory createEventFactory(T properties) {
    return new ISODefaultEventFactory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOMessageListenerFactory createListenerFactory(T properties) {
    return new ISODefaultMessageListenerFactory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOPackagerFactory createPackagerFactory(T properties) {
    return new ISODefaultPackagerFactory(properties);
  }

  /**
   * Adds default message packagers to the packager factory.
   *
   * @param packagerFactory packager factory
   */
  protected void addDefaultPackagers(ISOPackagerFactory packagerFactory) {
    packagerFactory.addMessagePackager(new ASCIIMessagePackager());
    packagerFactory.addMessagePackager(new HEXMessagePackager());
  }
}
