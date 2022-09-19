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
