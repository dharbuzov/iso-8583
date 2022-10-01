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
import com.dharbuzov.iso8583.order.ISOOrdered;

import lombok.Getter;

/**
 * Represents the message observer which expires based on request timeout.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Getter
public class ISONettyExpiryMessageObserver implements ISOMessageObserver, ISOOrdered {

  private final ISONettyMessageObserver observer;
  private final long expiredAtMs;

  /**
   * Constructor based on message observer and request timeout.
   *
   * @param observer       observer which waits the proper incoming message
   * @param requestTimeout message request timeout
   */
  public ISONettyExpiryMessageObserver(ISONettyMessageObserver observer, long requestTimeout) {
    this.observer = observer;
    this.expiredAtMs = System.currentTimeMillis() + requestTimeout;
  }

  @Override
  public boolean onMessage(ISOMessage inMsg) {
    return observer.onMessage(inMsg);
  }
}
