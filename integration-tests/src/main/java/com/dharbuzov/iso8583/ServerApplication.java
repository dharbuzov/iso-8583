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
import com.dharbuzov.iso8583.config.ISOConnProperties;
import com.dharbuzov.iso8583.listener.ISOMessageListener;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.schema.ISOKnownSchema;
import com.dharbuzov.iso8583.model.schema.ISOSchemas;
import com.dharbuzov.iso8583.server.ISOServer;
import com.dharbuzov.iso8583.server.config.ISOServerConfiguration;
import com.dharbuzov.iso8583.server.config.ISOServerProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Slf4j
public class ServerApplication {

  private static final ISOServerProperties serverProperties =
      ISOServerProperties.builder().schema(ISOSchemas.knownSchema(ISOKnownSchema.ISO_87_ASCII))
          .connection(ISOConnProperties.builder().port(8080).build()).build();

  public static void main(String[] args) {
    ISOServer server = null;
    try {
      server = new ISOServerConfiguration(serverProperties).getServer();
      server.addMessageListener(new ISOMessageListener() {
        @Override
        public void onMessage(ISOReplyChannel replyChannel, ISOMessage message) {
          log.info("Received message from the client, '{}'", message.getType());
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
      server.start();
    } finally {
      if (server != null) {
        server.shutdown();
      }
    }
  }
}
