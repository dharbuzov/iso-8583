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
import com.dharbuzov.iso8583.factory.ISOEventFactory;
import com.dharbuzov.iso8583.factory.ISOFieldPackagerFactory;
import com.dharbuzov.iso8583.factory.ISOMessageListenerFactory;
import com.dharbuzov.iso8583.factory.ISOMessagePackagerFactory;

/**
 * A Configuration interface is responsible for specifying the proper configuration of client or
 * server should be used for a particular application.
 *
 * @param <T> the generic type for library properties
 * @param <C> the generic type for desired channel
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOConfiguration<T extends ISOBaseProperties, C extends ISOChannel> {

  /**
   * Creates the channel abstraction of the library.
   *
   * @param properties properties to configure the channel
   * @return created channel of desired type
   */
  C createChannel(T properties);

  /**
   * Creates the event factory.
   *
   * @param properties properties to configure the event factory
   * @return created event factory
   */
  ISOEventFactory createEventFactory(T properties);

  /**
   * Creates the listener factory.
   *
   * @param properties properties to configure the listener factory
   * @return created listener factory
   */
  ISOMessageListenerFactory createListenerFactory(T properties);

  /**
   * Creates the message packager factory.
   *
   * @param properties           properties to configure the message packager factory
   * @param fieldPackagerFactory field packager factory
   * @return created message packager factory
   */
  ISOMessagePackagerFactory createMessagePackagerFactory(T properties,
      ISOFieldPackagerFactory fieldPackagerFactory);

  /**
   * Creates the field packager factory.
   *
   * @param properties properties to configure the field packager factory
   * @return created field packager factory
   */
  ISOFieldPackagerFactory createFieldPackagerFactory(T properties);
}
