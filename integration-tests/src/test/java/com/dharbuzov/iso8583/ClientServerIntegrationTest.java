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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.dharbuzov.iso8583.channel.ISOReplyChannel;
import com.dharbuzov.iso8583.client.config.ISOClientProperties;
import com.dharbuzov.iso8583.config.ISOConnProperties;
import com.dharbuzov.iso8583.listener.ISOMessageListener;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.MessageClass;
import com.dharbuzov.iso8583.model.MessageFunction;
import com.dharbuzov.iso8583.model.MessageOrigin;
import com.dharbuzov.iso8583.model.MessageType;
import com.dharbuzov.iso8583.model.MessageVersion;
import com.dharbuzov.iso8583.server.ISOServer;
import com.dharbuzov.iso8583.server.config.ISOServerProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * Test to ensure that client and server can communicate properly with each other.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Slf4j
public class ClientServerIntegrationTest extends AbstractIntegrationTest {
  private static final ISOClientProperties clientProperties = ISOClientProperties.builder()
      .connection(ISOConnProperties.builder().host("localhost").port(50000).build()).build();
  private static final ISOServerProperties serverProperties =
      ISOServerProperties.builder().connection(ISOConnProperties.builder().port(50000).build())
          .build();

  public ClientServerIntegrationTest() {
    super(clientProperties, serverProperties);
  }

  @Override
  protected void configureServer(ISOServer server) {
    server.addMessageListener(new ISOMessageListener() {
      @Override
      public void onMessage(ISOReplyChannel replyChannel, ISOMessage message) {
        if (message.isRequest()) {
          message.setResponseType();
          replyChannel.reply(message);
        }
      }

      @Override
      public boolean isApplicable(ISOMessage message) {
        return true;
      }
    });
  }

  @Test
  @Disabled
  public void clientServerIntegrationEchoMessage() {
    final ISOMessage response = client.send(ISOMessage.builder().type(
            MessageType.builder().version(MessageVersion.V1987).function(MessageFunction.REQUEST)
                .clazz(MessageClass.NETWORK_MANAGEMENT).origin(MessageOrigin.ACQUIRER).build()).build(),
        500);

    Assertions.assertNotNull(response);
    final MessageType resMsgType = response.getType();
    Assertions.assertNotNull(resMsgType);
    Assertions.assertEquals(resMsgType.getVersion(), MessageVersion.V1987);
    Assertions.assertEquals(resMsgType.getFunction(), MessageFunction.REQUEST_RESPONSE);
    Assertions.assertEquals(resMsgType.getClazz(), MessageClass.NETWORK_MANAGEMENT);
    Assertions.assertEquals(resMsgType.getOrigin(), MessageOrigin.ACQUIRER);
  }
}
