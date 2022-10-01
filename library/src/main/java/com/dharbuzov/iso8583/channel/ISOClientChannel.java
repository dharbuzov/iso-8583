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

import java.util.concurrent.Future;

import com.dharbuzov.iso8583.client.config.ISOClientProperties;
import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * The interface which represents the client level channel abstraction to work with network
 * protocol.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOClientChannel extends ISOChannel {
  /**
   * Connects the channel to the target server.
   */
  void connect() throws ISOException;

  /**
   * Disconnects the channel from the target server.
   */
  void disconnect() throws ISOException;

  /**
   * Returns flag which indicates that the channel is connected.
   *
   * @return {@code true} if channel connected, otherwise {@code false}
   */
  boolean isConnected() throws ISOException;

  /**
   * Sends the message asynchronously.
   *
   * @param msg message to send
   */
  void sendAsync(ISOMessage msg) throws ISOException;

  /**
   * Sends the message synchronously and waits the response message from the server. The
   * {@link ISOClientProperties#getRequestTimeoutMsOrDefault()} timeout would be used. This method
   * is blocking thread.
   *
   * @param msg message to send
   * @return response message from the server
   */
  ISOMessage send(ISOMessage msg) throws ISOException;

  /**
   * Sends the message synchronously and waits the response message from the server. This method is
   * blocking active thread.
   *
   * @param msg              message to send
   * @param requestTimeoutMs request timeout to wait for response in milliseconds
   * @return response message from the server
   */
  ISOMessage send(ISOMessage msg, long requestTimeoutMs) throws ISOException;

  /**
   * Sends the message asynchronously and wraps the response into the {@link Future} object, this
   * method is not blocking the active thread.
   *
   * @param msg message to send
   * @return the future of message response from the server
   */
  Future<ISOMessage> sendFuture(ISOMessage msg) throws ISOException;

  /**
   * {@inheritDoc}
   */
  @Override
  default boolean isActive() {
    return isConnected();
  }
}
