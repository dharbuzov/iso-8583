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
package com.dharbuzov.iso8583.factory;

import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.packager.ISOMessagePackager;

/**
 * A Packager factory responsible for orchestrating the {@link ISOMessage} message packaging into
 * the protocol level representation and vice versa.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 * @see com.dharbuzov.iso8583.packager.ISOMessagePackager
 */
public interface ISOPackagerFactory {

  /**
   * Packs the message into ISO-8583 byte representation.
   *
   * @param msg message to pack
   * @return the packed message
   */
  byte[] pack(ISOMessage msg);

  /**
   * Un packs the message from byte into message object representation.
   *
   * @param msgBytes message byte representation
   * @return unpacked message object
   */
  ISOMessage unpack(byte[] msgBytes);

  /**
   * Adds message packager to factory.
   *
   * @param messagePackager message packager to add
   */
  void addMessagePackager(ISOMessagePackager messagePackager);

  /**
   * Removes message packager from factory.
   *
   * @param messagePackager message packager to remove
   */
  void removeMessagePackager(ISOMessagePackager messagePackager);
}
