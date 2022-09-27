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

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.dharbuzov.iso8583.channel.ISOReplyChannel;
import com.dharbuzov.iso8583.listener.ISOMessageListener;
import com.dharbuzov.iso8583.model.ISOMessage;

import lombok.Getter;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOMessageListenerFactoryTest {

  private final ISOMessageListenerFactory messageListenerFactory =
      new ISODefaultMessageListenerFactory();

  @Test
  void onMessageCompositeTest() {
    final HigherPriorityMessageListener higherPriorityMessageListener =
        new HigherPriorityMessageListener();
    final LowerPriorityMessageListener lowerPriorityMessageListener =
        new LowerPriorityMessageListener();
    final NotApplicableMessageListener notApplicableMessageListener =
        new NotApplicableMessageListener();

    messageListenerFactory.addMessageListener(higherPriorityMessageListener);
    messageListenerFactory.addMessageListener(lowerPriorityMessageListener);
    messageListenerFactory.addMessageListener(notApplicableMessageListener);

    final ISOReplyChannel replyChannel = Mockito.mock(ISOReplyChannel.class);

    messageListenerFactory.onMessage(replyChannel, ISOMessage.builder().build());

    Assertions.assertNotNull(higherPriorityMessageListener.getExecutedAt());
    Assertions.assertNotNull(lowerPriorityMessageListener.getExecutedAt());
    Assertions.assertNull(notApplicableMessageListener.getExecutedAt());

    Mockito.verify(replyChannel, Mockito.times(2))
        .reply(Mockito.any(ISOMessage.class));

    // Checks that listener with higher priority executed earlier
    Assertions.assertTrue(higherPriorityMessageListener.getExecutedAt()
        .isBefore(lowerPriorityMessageListener.getExecutedAt()));

    Mockito.reset(replyChannel);

    messageListenerFactory.removeMessageListener(higherPriorityMessageListener);
    messageListenerFactory.removeMessageListener(lowerPriorityMessageListener);

    messageListenerFactory.onMessage(replyChannel, ISOMessage.builder().build());

    Mockito.verify(replyChannel, Mockito.times(0))
        .reply(Mockito.any(ISOMessage.class));
  }

  @Getter
  static class HigherPriorityMessageListener implements ISOMessageListener {

    private LocalDateTime executedAt;

    @Override
    public void onMessage(ISOReplyChannel replyChannel, ISOMessage message) {
      executedAt = LocalDateTime.now();
      replyChannel.reply(message);
    }

    @Override
    public boolean isApplicable(ISOMessage message) {
      return true;
    }

    @Override
    public int getOrder() {
      return HIGHEST_PRECEDENCE;
    }
  }


  @Getter
  static class LowerPriorityMessageListener implements ISOMessageListener {
    private LocalDateTime executedAt;

    @Override
    public void onMessage(ISOReplyChannel replyChannel, ISOMessage message) {
      executedAt = LocalDateTime.now();
      replyChannel.reply(message);
    }

    @Override
    public boolean isApplicable(ISOMessage message) {
      return true;
    }
  }


  @Getter
  static class NotApplicableMessageListener implements ISOMessageListener {
    private LocalDateTime executedAt;

    @Override
    public void onMessage(ISOReplyChannel replyChannel, ISOMessage message) {
      executedAt = LocalDateTime.now();
    }

    @Override
    public boolean isApplicable(ISOMessage message) {
      return false;
    }
  }
}
