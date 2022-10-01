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

import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.listener.ISOEventListener;
import com.dharbuzov.iso8583.listener.ISOMessageListener;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOServer {

  /**
   * Starts the ISO-8583 server instance.
   */
  void start() throws ISOException;;

  /**
   * Returns flag which indicates that server is running.
   *
   * @return {@code true} if server is running, otherwise {@code false}
   */
  boolean isRunning();

  /**
   * Method for shutting down the ISO-8583 server.
   */
  void shutdown() throws ISOException;;

  /**
   * Adds message listener.
   *
   * @param messageListener event listener to add
   */
  void addMessageListener(ISOMessageListener messageListener);

  /**
   * Removes message listener.
   *
   * @param messageListener message listener to remove
   */
  void removeMessageListener(ISOMessageListener messageListener);

  /**
   * Removes all message listeners attached to the server.
   */
  void removeMessageListeners();

  /**
   * Adds event listener.
   *
   * @param eventListener event listener to add
   */
  void addEventListener(ISOEventListener eventListener);

  /**
   * Removes event listener.
   *
   * @param eventListener event listener to remove
   */
  void removeEventListener(ISOEventListener eventListener);

  /**
   * Removes all event listeners attached to the server.
   */
  void removeEventListeners();
}
