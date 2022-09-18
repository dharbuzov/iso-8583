

package com.dharbuzov.iso8583;

import org.junit.jupiter.api.Test;

import com.dharbuzov.iso8583.client.config.ISOClientConfiguration;
import com.dharbuzov.iso8583.client.config.ISOClientProperties;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class MainTest {

  @Test
  public void test() {

    final ISOClientConfiguration clientConfiguration = ISOClientConfiguration.builder()
        .properties(ISOClientProperties.builder()
            .keepAlive(true)
            .name("MyISO8583Client")
            .build())
        .build();
    clientConfiguration.getClient();

  }
}
