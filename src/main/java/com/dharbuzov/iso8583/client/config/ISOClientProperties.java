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

  private String name;

  private boolean keepAlive;

  private ISOReconnectProperties reconnect;

  public static class ISOClientPropertiesBuilder {

  }
}
