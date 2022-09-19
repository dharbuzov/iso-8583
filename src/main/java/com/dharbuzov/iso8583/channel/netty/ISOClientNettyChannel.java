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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.dharbuzov.iso8583.channel.ISOClientChannel;
import com.dharbuzov.iso8583.channel.netty.observer.ISONettyMessageObservable;
import com.dharbuzov.iso8583.channel.netty.observer.ISONettyMessageObserver;
import com.dharbuzov.iso8583.client.config.ISOClientProperties;
import com.dharbuzov.iso8583.client.config.ISOReconnectProperties;
import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.factory.ISOListenerFactory;
import com.dharbuzov.iso8583.factory.ISOPackagerFactory;
import com.dharbuzov.iso8583.generator.MessageKeyGenerator;
import com.dharbuzov.iso8583.model.ISOMessage;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Builder;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOClientNettyChannel extends ISOBaseNettyChannel<ISOClientProperties>
    implements ISOClientChannel {

  private final MessageKeyGenerator messageKeyGenerator;
  private final ISOReconnectProperties reconnectProperties;
  private final ISONettyMessageObservable messageObservable;

  private final long requestTimeoutMs;

  public ISOClientNettyChannel(ISOClientProperties clientProperties,
      ISOPackagerFactory packagerFactory, ISOListenerFactory listenerFactory,
      MessageKeyGenerator messageKeyGenerator) {
    super(clientProperties, packagerFactory, listenerFactory);
    this.messageKeyGenerator = messageKeyGenerator;
    this.reconnectProperties = clientProperties.getReconnect();
    this.messageObservable = new ISONettyMessageObservable();
    this.requestTimeoutMs = clientProperties.getRequestTimeoutMsOrDefault();
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
                  .nettyMessageDecoder(new NettyMessageDecoder(packagerFactory))
                  .nettyMessageEncoder(new NettyMessageEncoder(packagerFactory))
                  .nettyMessageHandler(
                      new SyncNettyMessageHandler(listenerFactory, messageObservable)).build());
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
  public ISOMessage send(ISOMessage msg) {
    return send(msg, requestTimeoutMs);
  }

  @Override
  public ISOMessage send(ISOMessage msg, long requestTimeoutMs) {
    try {
      return sendFuture(msg).get(requestTimeoutMs, TimeUnit.MILLISECONDS);
    } catch (InterruptedException | TimeoutException | ExecutionException e) {
      throw new ISOException(e);
    }
  }

  @Override
  public Future<ISOMessage> sendFuture(ISOMessage msg) {
    final String outMsgKey = messageKeyGenerator.generate(msg);
    sendAsync(msg);
    final CompletableFuture<ISOMessage> awaitFuture = new CompletableFuture<>();
    final ISONettyMessageObserver messageObserver = ISONettyMessageObserver.builder()
        .outMsgKey(outMsgKey)
        .awaitFuture(awaitFuture)
        .keyGenerator(messageKeyGenerator)
        .build();
    messageObservable.addObserver(messageObserver, requestTimeoutMs);
    return awaitFuture;
  }

  @Override
  public void sendAsync(ISOMessage msg) {
    if (!isConnected()) {
      throw new ISOException("Channel is not connected!");
    }
    if (!nettyChannel.isWritable()) {
      throw new ISOException("Channel is not writable!");
    }
    nettyChannel.writeAndFlush(msg);
  }

  @Builder
  protected static class SyncNettyMessageHandler extends NettyMessageHandler {

    protected final ISONettyMessageObservable messageObservable;

    public SyncNettyMessageHandler(ISOListenerFactory listenerFactory,
        ISONettyMessageObservable messageObservable) {
      super(listenerFactory);
      this.messageObservable = messageObservable;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ISOMessage msg) throws Exception {
      final boolean consumed = messageObservable.notifyMessageIn(msg);
      if (consumed) {
        // This message has been consumed by await future in #sendFuture() method
        return;
      }
      super.channelRead0(ctx, msg);
    }
  }
}
