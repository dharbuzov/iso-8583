package com.dharbuzov.iso8583.client.config;

import com.dharbuzov.iso8583.channel.ChannelType;
import com.dharbuzov.iso8583.channel.ISOClientChannel;
import com.dharbuzov.iso8583.channel.netty.ISOClientNettyChannel;
import com.dharbuzov.iso8583.client.ISOClient;
import com.dharbuzov.iso8583.client.ISODefaultClient;
import com.dharbuzov.iso8583.config.ISOBaseConfiguration;
import com.dharbuzov.iso8583.exception.ISOException;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOClientConfiguration
    extends ISOBaseConfiguration<ISOClientProperties, ISOClientChannel> {

  @Getter
  protected final ISOClient client;

  @Builder
  public ISOClientConfiguration(ISOClientProperties properties) {
    super(properties);
    this.client = createClient(properties, channel);
  }

  @Override
  public ISOClientChannel createChannel(ISOClientProperties properties) {
    final ChannelType channelType;
    if (properties.getChannelType() == null) {
      channelType = ChannelType.defaultChannelType();
    } else {
      channelType = properties.getChannelType();
    }
    switch (channelType) {
      case NETTY:
        return new ISOClientNettyChannel(properties.getConnection(), properties.getReconnect(),
            messageFactory, messageCoordinator);
      default:
        throw new ISOException("Can't create a channel for type: '%s'", channelType);
    }
  }

  protected ISOClient createClient(ISOClientProperties properties, ISOClientChannel channel) {
    return new ISODefaultClient(properties, channel);
  }
}
