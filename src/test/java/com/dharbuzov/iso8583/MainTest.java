

package com.dharbuzov.iso8583;

import org.junit.jupiter.api.Test;

import com.dharbuzov.iso8583.client.ISOClient;
import com.dharbuzov.iso8583.client.config.ISOClientConfiguration;
import com.dharbuzov.iso8583.client.config.ISOClientProperties;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.MessageType;
import com.dharbuzov.iso8583.server.ISOServer;
import com.dharbuzov.iso8583.server.config.ISOServerConfiguration;
import com.dharbuzov.iso8583.server.config.ISOServerProperties;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class MainTest {

  @Test
  public void testClient() {

    final ISOClientConfiguration clientConfiguration = ISOClientConfiguration.builder()
        .properties(ISOClientProperties.builder()
            .name("MyISO8583Client")
            .build())
        .build();
    final ISOClient client = clientConfiguration.getClient();

    client.sendAsync(ISOMessage.builder()
            .type(MessageType.builder()
                .build())
        .build());
  }

  @Test
  public void testServer() {
    final ISOServerConfiguration serverConfiguration = ISOServerConfiguration.builder()
        .properties(ISOServerProperties.builder()
            .build())
        .build();
    final ISOServer server = serverConfiguration.getServer();
    server.start();
  }
}
