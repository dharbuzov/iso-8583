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
package com.dharbuzov.iso8583.channel.netty.observer;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * The interface represents the observer of incoming messages from ISO-8583 server.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOMessageObserver {

  /**
   * Handles the message and returns flag which indicates that message is applicable for particular
   * {@link java.util.concurrent.Future} which waits response message to return.
   *
   * @param inMsg incoming message
   * @return {@code true} if message is handled by await future, otherwise {@code false} which means
   * that message should be processed by another listeners in default chain of
   * {@link com.dharbuzov.iso8583.factory.ISOMessageListenerFactory}
   */
  boolean onMessage(ISOMessage inMsg);
}
