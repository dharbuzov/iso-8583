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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

import com.dharbuzov.iso8583.model.ISOMessage;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISONettyMessageObservable {

  private List<ExpiredMessageObserver> observers = new LinkedList<>();

  public boolean notifyMessageIn(ISOMessage msg) {
    observers.removeIf(expiredMessageObserver -> expiredMessageObserver.getExpiredAt()
        .isAfter(LocalDateTime.now()));
    for (ExpiredMessageObserver observer : observers) {
      if (observer.getObserver().notifyMessageIn(msg)) {
        observers.remove(observer);
        return true;
      }
    }
    return false;
  }

  public void addObserver(ISONettyMessageObserver observer, long expirationMs) {
    observers.add(ExpiredMessageObserver.builder()
        .expiredAt(LocalDateTime.now().plus(expirationMs, ChronoUnit.MILLIS)).observer(observer)
        .build());
  }

  @Getter
  @Builder
  @RequiredArgsConstructor
  private class ExpiredMessageObserver {
    private final ISONettyMessageObserver observer;
    private final LocalDateTime expiredAt;
  }
}
