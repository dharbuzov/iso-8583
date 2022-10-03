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

package com.dharbuzov.iso8583;

import com.dharbuzov.iso8583.channel.ISOReplyChannel;
import com.dharbuzov.iso8583.client.ISOSyncClient;
import com.dharbuzov.iso8583.client.config.ISOClientConfiguration;
import com.dharbuzov.iso8583.client.config.ISOClientProperties;
import com.dharbuzov.iso8583.config.ISOConnProperties;
import com.dharbuzov.iso8583.listener.ISOMessageListener;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.MessageClass;
import com.dharbuzov.iso8583.model.MessageFunction;
import com.dharbuzov.iso8583.model.MessageOrigin;
import com.dharbuzov.iso8583.model.MessageType;
import com.dharbuzov.iso8583.model.MessageVersion;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Slf4j
public class ClientApplication {

  private static final ISOClientProperties clientProperties = ISOClientProperties.builder()
      .connection(ISOConnProperties.builder().host("localhost").port(8080).build()).build();

  public static void main(String[] args) {
    ISOSyncClient client = new ISOClientConfiguration(clientProperties).getClient();
    client.addMessageListener(new ISOMessageListener() {
      @Override
      public void onMessage(ISOReplyChannel replyChannel, ISOMessage message) {
        log.info("Received message from the server: '{}'", message);
      }

      @Override
      public boolean isApplicable(ISOMessage message) {
        return true;
      }
    });
    client.connect();
    final ISOMessage request = ISOMessage.builder().type(
        MessageType.from(MessageVersion.V1993, MessageClass.NETWORK_MANAGEMENT,
            MessageFunction.REQUEST, MessageOrigin.ACQUIRER)).build();
    final ISOMessage response = client.send(request);
    log.info("Message response from client: {}", response);
  }
}
