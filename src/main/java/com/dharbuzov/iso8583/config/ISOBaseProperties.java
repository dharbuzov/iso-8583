package com.dharbuzov.iso8583.config;

import com.dharbuzov.iso8583.channel.ChannelType;

import lombok.Data;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
public abstract class ISOBaseProperties {

  private ISOMessageProperties messages;

  private ISOConnProperties connection;
  private ChannelType channelType;

  public ChannelType getChannelTypeOrDefault() {
    if (this.channelType == null) {
      return ChannelType.defaultChannelType();
    }
    return this.channelType;
  }
}
