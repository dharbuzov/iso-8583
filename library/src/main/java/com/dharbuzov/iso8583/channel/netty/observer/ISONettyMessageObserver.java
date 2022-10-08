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

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import com.dharbuzov.iso8583.binder.MessageBinder;
import com.dharbuzov.iso8583.binder.MessageKeyGenerator;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.MessageType;
import com.dharbuzov.iso8583.util.ValidationUtils;

import lombok.Builder;

/**
 * The class message observer which relies on {@link MessageBinder} to identify that request and
 * response messages matches each other.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISONettyMessageObserver implements ISOMessageObserver {

  private final MessageType reqMsgType;
  private final String reqMsgKey;
  private final MessageBinder messageBinder;
  private final CompletableFuture<ISOMessage> awaitFuture;

  /**
   * Constructor of message observer.
   *
   * @param messageBinder message binder
   * @param keyGenerator  message key generator
   * @param reqMsg        request message which will sent by the client
   * @param awaitFuture   future which accepts the bind response from the server
   */
  @Builder
  public ISONettyMessageObserver(MessageBinder messageBinder, MessageKeyGenerator keyGenerator,
      ISOMessage reqMsg, CompletableFuture<ISOMessage> awaitFuture) {
    ValidationUtils.validateNotNull(messageBinder, "MessageBinder is missing!");
    ValidationUtils.validateNotNull(keyGenerator, "MessageKeyGenerator is missing!");
    ValidationUtils.validateNotNull(reqMsg, "Request ISOMessage is missing!");
    ValidationUtils.validateNotNull(awaitFuture, "Future is missing!");
    this.reqMsgType = reqMsg.getType();
    this.reqMsgKey = keyGenerator.generate(reqMsg);
    this.messageBinder = messageBinder;
    this.awaitFuture = awaitFuture;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean onMessage(ISOMessage inMsg) {
    if (messageBinder.isBind(reqMsgType, reqMsgKey, inMsg)) {
      // Completes the future with a response message.
      awaitFuture.complete(inMsg);
      return true;
    }
    return false;
  }
}
