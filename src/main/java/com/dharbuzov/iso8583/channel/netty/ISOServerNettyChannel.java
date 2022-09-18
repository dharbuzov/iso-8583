package com.dharbuzov.iso8583.channel.netty;

import com.dharbuzov.iso8583.channel.ISOServerChannel;
import com.dharbuzov.iso8583.config.ISOConnProperties;
import com.dharbuzov.iso8583.coordinator.ISOMessageCoordinator;
import com.dharbuzov.iso8583.factory.ISOMessageFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOServerNettyChannel extends ISOBaseNettyChannel implements ISOServerChannel {

  public ISOServerNettyChannel(ISOConnProperties connProperties, ISOMessageFactory messageFactory,
      ISOMessageCoordinator messageCoordinator) {
    super(connProperties, messageFactory, messageCoordinator);
  }

  @Override
  public void start() {
    if (nettyChannel == null) {
      final EventLoopGroup parentGroup = new NioEventLoopGroup(1);
      final EventLoopGroup childGroup = new NioEventLoopGroup();
      final ServerBootstrap bootstrap =
          new ServerBootstrap().group(parentGroup, childGroup).channel(NioServerSocketChannel.class)
              .handler(NettyChannelInitializer.builder()
                  .nettyMessageDecoder(new NettyMessageDecoder(messageFactory))
                  .nettyMessageEncoder(new NettyMessageEncoder(messageFactory))
                  .nettyMessageHandler(new NettyMessageHandler(messageCoordinator)).build());
      try {
        final ChannelFuture f = bootstrap.bind(connProperties.getPort()).sync();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } finally {
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
      }
    }
  }

  @Override
  public void shutdown() {
    if (nettyChannel != null) {
      nettyChannel.deregister();
      nettyChannel.close();
    }
  }

  @Override
  public boolean isRunning() {
    return nettyChannel != null && nettyChannel.isOpen() && nettyChannel.isActive();
  }
}
