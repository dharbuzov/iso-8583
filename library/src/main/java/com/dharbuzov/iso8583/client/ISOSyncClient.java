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

import java.util.concurrent.Future;

import com.dharbuzov.iso8583.client.config.ISOClientProperties;
import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * The main synchronous client interface to interact with. This interface contains additional
 * methods to work with an ISO-8583 server in synchronous mode where the client tries to receive the
 * response message for the requested one.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOSyncClient extends ISOClient {

  /**
   * Sends the message synchronously and waits the response message from the server. The
   * {@link ISOClientProperties#getRequestTimeoutMsOrDefault()} timeout would be used. This method
   * is blocking thread.
   *
   * @param msg message to send
   * @return response message from the server
   */
  ISOMessage send(ISOMessage msg);

  /**
   * Sends the message synchronously and waits the response message from the server. This method is
   * blocking thread.
   *
   * @param msg              message to send
   * @param requestTimeoutMs request timeout to wait for response in milliseconds
   * @return response message from the server
   */
  ISOMessage send(ISOMessage msg, long requestTimeoutMs);

  /**
   * Sends the message asynchronously and wraps the response into the {@link Future} object, this
   * method is not blocking.
   *
   * @param msg message to send
   * @return the future of message response from the server
   */
  Future<ISOMessage> sendFuture(ISOMessage msg);
}
