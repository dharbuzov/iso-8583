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
package com.dharbuzov.iso8583.channel;

import com.dharbuzov.iso8583.exception.ISOException;

/**
 * The interface which represents the server level channel abstraction to work with network
 * protocol. This channel is one per server instance and binds to the open port of the server.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOServerChannel extends ISOChannel {

  /**
   * Starts the server instance.
   */
  void start() throws ISOException;;

  /**
   * Shutdowns the server instance.
   */
  void shutdown() throws ISOException;;

  /**
   * Returns flag which indicates the server is in running state.
   *
   * @return {@code true} if server is running, otherwise {@code false}
   */
  boolean isRunning();

  /**
   * {@inheritDoc}
   */
  @Override
  default boolean isActive() {
    return isRunning();
  }
}
