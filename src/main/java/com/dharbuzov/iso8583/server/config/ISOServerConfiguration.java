package com.dharbuzov.iso8583.server.config;

import com.dharbuzov.iso8583.channel.ChannelType;
import com.dharbuzov.iso8583.channel.ISOServerChannel;
import com.dharbuzov.iso8583.channel.netty.ISOServerNettyChannel;
import com.dharbuzov.iso8583.config.ISOBaseConfiguration;
import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.server.ISODefaultServer;
import com.dharbuzov.iso8583.server.ISOServer;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOServerConfiguration
    extends ISOBaseConfiguration<ISOServerProperties, ISOServerChannel> {

  @Getter
  protected final ISOServer server;

  @Builder
  public ISOServerConfiguration(ISOServerProperties properties) {
    super(properties);
    this.server = createServer(properties, channel);
  }

  @Override
  public ISOServerChannel createChannel(ISOServerProperties properties) {
    final ChannelType channelType = properties.getChannelTypeOrDefault();
    switch (channelType) {
      case NETTY:
        return new ISOServerNettyChannel(properties.getConnection(), messageFactory,
            messageCoordinator);
      default:
        throw new ISOException("Can't create a channel for type: '%s'", channelType);
    }
  }

  protected ISOServer createServer(ISOServerProperties properties, ISOServerChannel channel) {
    return new ISODefaultServer(properties, channel);
  }
}
