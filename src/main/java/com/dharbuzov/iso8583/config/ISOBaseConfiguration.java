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
import com.dharbuzov.iso8583.factory.ISODefaultListenerFactory;
import com.dharbuzov.iso8583.factory.ISODefaultPackagerFactory;
import com.dharbuzov.iso8583.factory.ISOListenerFactory;
import com.dharbuzov.iso8583.factory.ISOPackagerFactory;

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
  protected final ISOListenerFactory listenerFactory;
  protected final ISOPackagerFactory packagerFactory;

  /**
   * Base configuration constructor.
   *
   * @param properties configuration properties
   */
  public ISOBaseConfiguration(T properties) {
    this.channel = createChannel(properties);
    this.listenerFactory = createListenerFactory(properties);
    this.packagerFactory = createPackagerFactory(properties);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOListenerFactory createListenerFactory(T properties) {
    return new ISODefaultListenerFactory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOPackagerFactory createPackagerFactory(T properties) {
    return new ISODefaultPackagerFactory();
  }
}
