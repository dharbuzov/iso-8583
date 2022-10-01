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
package com.dharbuzov.iso8583.binder;

import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.MessageType;

import com.dharbuzov.iso8583.channel.ISOChannel;
import com.dharbuzov.iso8583.client.ISOSyncClient;

/**
 * The interface which is used to bind the request message to incoming message by the client, this
 * interface is used to establish proper synchronous communication, client fully relies on this
 * class to identify whatever incoming message is bind to original request.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 * @see ISOSyncClient
 * @see MessageKeyGenerator
 */
public interface MessageBinder {

  /**
   * Returns flag which indicates that sent request message is bind to incoming message.
   *
   * @param reqMsgType request message type
   * @param reqMsgKey  request message key
   * @param inMsg      incoming message received by the
   *                   {@link ISOChannel}
   * @return {@code true} if request message is bind to incoming message, otherwise {@code false}
   */
  boolean isBind(MessageType reqMsgType, String reqMsgKey, ISOMessage inMsg);
}
