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
import com.dharbuzov.iso8583.order.ISOOrderedContainer;

/**
 * Class represents the netty message observable which works as an interceptor in
 * {@link com.dharbuzov.iso8583.channel.netty.handler.NettySyncMessageHandler} to catch messages
 * that should be returned by {@link java.util.concurrent.Future} in
 * {@link com.dharbuzov.iso8583.client.ISOSyncClient#send(ISOMessage, long)},
 * {@link com.dharbuzov.iso8583.client.ISOSyncClient#sendFuture(ISOMessage)} or
 * {@link com.dharbuzov.iso8583.client.ISOSyncClient#send(ISOMessage)}
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISONettyMessageObservable extends ISOOrderedContainer<ISONettyExpiryMessageObserver>
    implements ISOMessageObserver {

  /**
   * Handles the message and returns flag which indicates that message is applicable for particular
   * {@link java.util.concurrent.Future} which waits response message to return.
   *
   * @param inMsg incoming message
   * @return {@code true} if message is handled by await future, otherwise {@code false} which means
   * that message should be processed by another listeners in default chain of
   * {@link com.dharbuzov.iso8583.factory.ISOMessageListenerFactory}
   */
  @Override
  public boolean onMessage(ISOMessage inMsg) {
    removeExpiredObservers();
    for (ISONettyExpiryMessageObserver messageObserver : this.queue) {
      if (messageObserver.onMessage(inMsg)) {
        removeFromQueue(messageObserver);
        return true;
      }
    }
    return false;
  }

  /**
   * Adds observer to the queue.
   *
   * @param observer         observer which waits proper incoming message
   * @param requestTimeoutMs message request timeout
   */
  public void addObserver(ISONettyMessageObserver observer, long requestTimeoutMs) {
    removeExpiredObservers();
    addToQueue(new ISONettyExpiryMessageObserver(observer, requestTimeoutMs));
  }

  /**
   * Removes the observers which are already expired.
   */
  protected void removeExpiredObservers() {
    final long currentTimeMillis = System.currentTimeMillis();
    removeAllFromQueue(
        (expiryMsgObserver) -> expiryMsgObserver.getExpiredAtMs() < currentTimeMillis);
  }
}
