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
package com.dharbuzov.iso8583.channel.netty;

import com.dharbuzov.iso8583.channel.ISOServerChannel;
import com.dharbuzov.iso8583.factory.ISOMessageListenerFactory;
import com.dharbuzov.iso8583.factory.ISOPackagerFactory;
import com.dharbuzov.iso8583.server.config.ISOServerProperties;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * The netty server channel implementation, this type of channel creates the ISO-8583 tcp/ip server
 * and implements the methods for starting the server and receive the messages from different
 * clients.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOServerNettyChannel extends ISOBaseNettyChannel<ISOServerProperties>
    implements ISOServerChannel {

  /**
   * Netty server constructor based on properties and packager factory.
   *
   * @param serverProperties server properties
   * @param packagerFactory  packager factory
   * @param listenerFactory  listener factory
   */
  public ISOServerNettyChannel(ISOServerProperties serverProperties,
      ISOPackagerFactory packagerFactory, ISOMessageListenerFactory listenerFactory) {
    super(serverProperties, packagerFactory, listenerFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void start() {
    if (nettyChannel == null) {
      final EventLoopGroup parentGroup = new NioEventLoopGroup(1);
      final EventLoopGroup childGroup = new NioEventLoopGroup();
      final ServerBootstrap bootstrap =
          new ServerBootstrap().group(parentGroup, childGroup).channel(NioServerSocketChannel.class)
              .handler(NettyChannelInitializer.builder()
                  .nettyMessageDecoder(new NettyMessageDecoder(packagerFactory))
                  .nettyMessageEncoder(new NettyMessageEncoder(packagerFactory))
                  .nettyMessageHandler(new NettyMessageHandler(listenerFactory)).build());
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void shutdown() {
    if (nettyChannel != null) {
      nettyChannel.deregister();
      nettyChannel.close();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isRunning() {
    return nettyChannel != null && nettyChannel.isOpen() && nettyChannel.isActive();
  }
}
