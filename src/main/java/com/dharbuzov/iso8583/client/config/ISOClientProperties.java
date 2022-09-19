package com.dharbuzov.iso8583.client.config;

import com.dharbuzov.iso8583.channel.ChannelType;
import com.dharbuzov.iso8583.config.ISOBaseProperties;
import com.dharbuzov.iso8583.config.ISOConnProperties;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ISOClientProperties extends ISOBaseProperties {

  public static long DEFAULT_REQUEST_TIMEOUT = 60000;

  private String name;

  private boolean keepAlive;

  private ISOReconnectProperties reconnect;

  private long requestTimeoutMs;

  public static class ISOClientPropertiesBuilder {

  }

  public long getRequestTimeoutMsOrDefault() {
    return requestTimeoutMs == 0L ? DEFAULT_REQUEST_TIMEOUT: requestTimeoutMs;
  }
}
