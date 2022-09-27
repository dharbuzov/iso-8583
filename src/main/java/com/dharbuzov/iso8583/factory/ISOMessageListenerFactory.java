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

import com.dharbuzov.iso8583.channel.ISOReplyChannel;
import com.dharbuzov.iso8583.listener.ISOMessageListener;
import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * A Listener factory responsible for handling the incoming {@link ISOMessage} messages.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOMessageListenerFactory {

  /**
   * Method which is invoked once the client receives incoming message.
   *
   * @param replyChannel channel is used to reply the response for request messages
   * @param message      incoming message
   */
  void onMessage(ISOReplyChannel replyChannel, ISOMessage message);

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
}
