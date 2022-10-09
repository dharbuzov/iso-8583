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
import com.dharbuzov.iso8583.factory.ISODefaultFieldPackagerFactory;
import com.dharbuzov.iso8583.factory.ISODefaultMessageListenerFactory;
import com.dharbuzov.iso8583.factory.ISODefaultMessagePackagerFactory;
import com.dharbuzov.iso8583.factory.ISOEventFactory;
import com.dharbuzov.iso8583.factory.ISOFieldPackagerFactory;
import com.dharbuzov.iso8583.factory.ISOMessageListenerFactory;
import com.dharbuzov.iso8583.factory.ISOMessagePackagerFactory;
import com.dharbuzov.iso8583.model.schema.ISOSchema;
import com.dharbuzov.iso8583.packager.ascii.ASCIIMessagePackager;
import com.dharbuzov.iso8583.packager.hex.HEXMessagePackager;

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
  protected final ISOFieldPackagerFactory fieldPackagerFactory;
  protected final ISOMessagePackagerFactory messagePackagerFactory;
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
    this.fieldPackagerFactory = createFieldPackagerFactory(properties);
    this.messagePackagerFactory =
        createMessagePackagerFactory(properties, this.fieldPackagerFactory);
    this.channel = createChannel(properties);
    addDefaultPackagers(this.messagePackagerFactory, this.fieldPackagerFactory);
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

  @Override
  public ISOFieldPackagerFactory createFieldPackagerFactory(T properties) {
    return new ISODefaultFieldPackagerFactory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOMessagePackagerFactory createMessagePackagerFactory(T properties,
      ISOFieldPackagerFactory fieldPackagerFactory) {
    return new ISODefaultMessagePackagerFactory(properties, fieldPackagerFactory);
  }

  /**
   * Adds default message and field packagers to the packager factories.
   *
   * @param messagePackagerFactory message packager factory
   * @param fieldPackagerFactory   field packager factory
   */
  protected void addDefaultPackagers(ISOMessagePackagerFactory messagePackagerFactory,
      ISOFieldPackagerFactory fieldPackagerFactory) {
    messagePackagerFactory.addMessagePackager(new ASCIIMessagePackager());
    messagePackagerFactory.addMessagePackager(new HEXMessagePackager());
  }
}
