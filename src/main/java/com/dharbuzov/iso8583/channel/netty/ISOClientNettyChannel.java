package com.dharbuzov.iso8583.channel.netty;

import com.dharbuzov.iso8583.channel.ISOClientChannel;
import com.dharbuzov.iso8583.client.config.ISOReconnectProperties;
import com.dharbuzov.iso8583.config.ISOConnProperties;
import com.dharbuzov.iso8583.coordinator.ISOMessageCoordinator;
import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.factory.ISOMessageFactory;
import com.dharbuzov.iso8583.model.ISOMessage;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOClientNettyChannel extends ISOBaseNettyChannel implements ISOClientChannel {

  private final ISOReconnectProperties reconnectProperties;

  public ISOClientNettyChannel(ISOConnProperties connProperties,
      ISOReconnectProperties reconnectProperties, ISOMessageFactory messageFactory,
      ISOMessageCoordinator messageCoordinator) {
    super(connProperties, messageFactory, messageCoordinator);
    this.reconnectProperties = reconnectProperties;
  }

  @Override
  public boolean isConnected() {
    return nettyChannel != null && nettyChannel.isOpen() && nettyChannel.isActive();
  }

  @Override
  public void connect() {
    if (nettyChannel == null) {
      final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
      final Bootstrap bootstrap =
          new Bootstrap().group(eventLoopGroup).channel(NioSocketChannel.class).handler(
              NettyChannelInitializer.builder()
                  .nettyMessageDecoder(new NettyMessageDecoder(messageFactory))
                  .nettyMessageEncoder(new NettyMessageEncoder(messageFactory))
                  .nettyMessageHandler(new NettyMessageHandler(messageCoordinator)).build());
      try {
        ChannelFuture channelFuture =
            bootstrap.connect(connProperties.getInetSocketAddress()).sync();
        nettyChannel = channelFuture.channel();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } finally {
        eventLoopGroup.shutdownGracefully();
      }
    }
  }

  @Override
  public void disconnect() {
    try {
      nettyChannel.disconnect().sync();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void send(ISOMessage msg) {
    if (!isConnected()) {
      throw new ISOException("Channel is not connected!");
    }
    if (!nettyChannel.isWritable()) {
      throw new ISOException("Channel is not writable!");
    }
    nettyChannel.writeAndFlush(msg);
  }
}
