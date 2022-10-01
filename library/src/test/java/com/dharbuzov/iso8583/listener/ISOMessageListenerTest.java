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
package com.dharbuzov.iso8583.listener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.dharbuzov.iso8583.channel.ISOReplyChannel;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.MessageClass;
import com.dharbuzov.iso8583.model.MessageType;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOMessageListenerTest {

  @Test
  void administrativeMessageListenerTest() {
    final ISOAdministrativeMessageListener administrativeMessageListener =
        (replyChannel, message) -> {
          //NOOP
        };
    final ISOMessage validMessage =
        ISOMessage.builder().type(MessageType.builder().clazz(MessageClass.ADMINISTRATIVE).build())
            .build();
    final ISOMessage notValidMessage = ISOMessage.builder()
        .type(MessageType.builder().clazz(MessageClass.NETWORK_MANAGEMENT).build()).build();
    Assertions.assertTrue(administrativeMessageListener.isApplicable(validMessage));
    Assertions.assertFalse(administrativeMessageListener.isApplicable(notValidMessage));
  }

  @Test
  void authorizationMessageListenerTest() {
    final ISOAuthorizationMessageListener authorizationMessageListener =
        ((replyChannel, message) -> {
          //NOOP
        });
    final ISOMessage validMessage =
        ISOMessage.builder().type(MessageType.builder().clazz(MessageClass.AUTHORIZATION).build())
            .build();
    final ISOMessage notValidMessage = ISOMessage.builder()
        .type(MessageType.builder().clazz(MessageClass.NETWORK_MANAGEMENT).build()).build();
    Assertions.assertTrue(authorizationMessageListener.isApplicable(validMessage));
    Assertions.assertFalse(authorizationMessageListener.isApplicable(notValidMessage));
  }

  @Test
  void feeCollectionMessageListenerTest() {
    final ISOFeeCollectionMessageListener feeCollectionMessageListener =
        ((replyChannel, message) -> {
          //NOOP
        });
    final ISOMessage validMessage =
        ISOMessage.builder().type(MessageType.builder().clazz(MessageClass.FEE_COLLECTION).build())
            .build();
    final ISOMessage notValidMessage = ISOMessage.builder()
        .type(MessageType.builder().clazz(MessageClass.NETWORK_MANAGEMENT).build()).build();
    Assertions.assertTrue(feeCollectionMessageListener.isApplicable(validMessage));
    Assertions.assertFalse(feeCollectionMessageListener.isApplicable(notValidMessage));
  }

  @Test
  void fileActionMessageListenerTest() {
    final ISOFileActionMessageListener fileActionMessageListener = ((replyChannel, message) -> {
      //NOOP
    });
    final ISOMessage validMessage =
        ISOMessage.builder().type(MessageType.builder().clazz(MessageClass.FILE_ACTIONS).build())
            .build();
    final ISOMessage notValidMessage = ISOMessage.builder()
        .type(MessageType.builder().clazz(MessageClass.NETWORK_MANAGEMENT).build()).build();
    Assertions.assertTrue(fileActionMessageListener.isApplicable(validMessage));
    Assertions.assertFalse(fileActionMessageListener.isApplicable(notValidMessage));
  }

  @Test
  void financialMessageListenerTest() {
    final ISOFinancialMessageListener financialMessageListener = ((replyChannel, message) -> {
      //NOOP
    });
    final ISOMessage validMessage =
        ISOMessage.builder().type(MessageType.builder().clazz(MessageClass.FINANCIAL).build())
            .build();
    final ISOMessage notValidMessage = ISOMessage.builder()
        .type(MessageType.builder().clazz(MessageClass.NETWORK_MANAGEMENT).build()).build();
    Assertions.assertTrue(financialMessageListener.isApplicable(validMessage));
    Assertions.assertFalse(financialMessageListener.isApplicable(notValidMessage));
  }

  @Test
  void genericMessageListenerTest() {
    final ISOMessageListener messageListener = new ISOMessageListener() {
      @Override
      public void onMessage(ISOReplyChannel replyChannel, ISOMessage message) {
        replyChannel.reply(message);
      }

      @Override
      public boolean isApplicable(ISOMessage message) {
        return true;
      }
    };
    final ISOMessage message = ISOMessage.builder().build();
    final ISOReplyChannel replyChannel = Mockito.mock(ISOReplyChannel.class);

    messageListener.onMessage(replyChannel, message);


    Assertions.assertTrue(messageListener.isApplicable(message));
    Mockito.verify(replyChannel, Mockito.times(1)).reply(Mockito.any(ISOMessage.class));
  }

  @Test
  void networkMessageListenerTest() {
    final ISONetworkMessageListener networkMessageListener = ((replyChannel, message) -> {
      //NOOP
    });
    final ISOMessage validMessage = ISOMessage.builder()
        .type(MessageType.builder().clazz(MessageClass.NETWORK_MANAGEMENT).build()).build();
    final ISOMessage notValidMessage =
        ISOMessage.builder().type(MessageType.builder().clazz(MessageClass.ADMINISTRATIVE).build())
            .build();
    Assertions.assertTrue(networkMessageListener.isApplicable(validMessage));
    Assertions.assertFalse(networkMessageListener.isApplicable(notValidMessage));
  }

  @Test
  void reconciliationMessageListenerTest() {
    final ISOReconciliationMessageListener reconciliationMessageListener =
        ((replyChannel, message) -> {
          //NOOP
        });
    final ISOMessage validMessage =
        ISOMessage.builder().type(MessageType.builder().clazz(MessageClass.RECONCILIATION).build())
            .build();
    final ISOMessage notValidMessage = ISOMessage.builder()
        .type(MessageType.builder().clazz(MessageClass.NETWORK_MANAGEMENT).build()).build();
    Assertions.assertTrue(reconciliationMessageListener.isApplicable(validMessage));
    Assertions.assertFalse(reconciliationMessageListener.isApplicable(notValidMessage));
  }

  @Test
  void reversalMessageListenerTest() {
    final ISOReversalMessageListener reversalMessageListener = ((replyChannel, message) -> {
      //NOOP
    });
    final ISOMessage validMessage = ISOMessage.builder()
        .type(MessageType.builder().clazz(MessageClass.REVERSAL_CHARGEBACK).build()).build();
    final ISOMessage notValidMessage = ISOMessage.builder()
        .type(MessageType.builder().clazz(MessageClass.NETWORK_MANAGEMENT).build()).build();
    Assertions.assertTrue(reversalMessageListener.isApplicable(validMessage));
    Assertions.assertFalse(reversalMessageListener.isApplicable(notValidMessage));
  }
}
