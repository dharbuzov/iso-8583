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
package com.dharbuzov.iso8583.client;

import com.dharbuzov.iso8583.channel.ISOClientChannel;
import com.dharbuzov.iso8583.client.config.ISOClientProperties;
import com.dharbuzov.iso8583.listener.ISOEventListener;
import com.dharbuzov.iso8583.listener.ISOMessageListener;
import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * The main client interface to interact with. This interface contains all needed methods for
 * library client to send and receive the messages.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOClient {

  /**
   * Connects the client to the target ISO-8583 server, by using the {@link ISOClientProperties}.
   */
  void connect();

  /**
   * Returns the flag which indicates that the client is connected to the server.
   *
   * @return {@code true} if client is connected and active, otherwise {@code false}.
   */
  boolean isConnected();

  /**
   * Disconnects the client from the server.
   */
  void disconnect();

  /**
   * Sends the message asynchronously to the target ISO-8583 server.
   *
   * @param msg message to send
   */
  void sendAsync(ISOMessage msg);

  /**
   * Gets the client channel associated with the client interface.
   *
   * @return associated channel
   */
  ISOClientChannel getChannel();

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
   * Removes all message listeners attached to the client.
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
   * Removes all event listeners attached to the client.
   */
  void removeEventListeners();
}
