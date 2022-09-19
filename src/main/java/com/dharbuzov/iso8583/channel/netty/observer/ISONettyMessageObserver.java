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

import java.util.concurrent.CompletableFuture;

import com.dharbuzov.iso8583.generator.MessageKeyGenerator;
import com.dharbuzov.iso8583.model.ISOMessage;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Builder
@RequiredArgsConstructor
public class ISONettyMessageObserver {
  private final String outMsgKey;
  private final MessageKeyGenerator keyGenerator;
  private final CompletableFuture<ISOMessage> awaitFuture;

  public boolean notifyMessageIn(ISOMessage inMsg) {
    final String inMsgKey = keyGenerator.generate(inMsg);
    if (outMsgKey.equals(inMsgKey)) {
      awaitFuture.complete(inMsg);
      return true;
    }
    return false;
  }
}
