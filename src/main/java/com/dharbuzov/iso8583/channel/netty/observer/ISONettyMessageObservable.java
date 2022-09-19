package com.dharbuzov.iso8583.channel.netty.observer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
