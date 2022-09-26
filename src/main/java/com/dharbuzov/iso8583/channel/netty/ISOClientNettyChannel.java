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

import com.dharbuzov.iso8583.binder.MessageBinder;
import com.dharbuzov.iso8583.binder.MessageKeyGenerator;
import com.dharbuzov.iso8583.channel.ISOClientChannel;
import com.dharbuzov.iso8583.channel.netty.observer.ISONettyMessageObservable;
import com.dharbuzov.iso8583.channel.netty.observer.ISONettyMessageObserver;
import com.dharbuzov.iso8583.client.config.ISOClientProperties;
import com.dharbuzov.iso8583.client.config.ISOReconnectProperties;
import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.factory.ISOMessageListenerFactory;
import com.dharbuzov.iso8583.factory.ISOPackagerFactory;
import com.dharbuzov.iso8583.model.ISOMessage;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Builder;

/**
 * The netty client channel implementation, this type of channel establishes connection with
 * ISO-8583 tcp/ip server and implements the methods for sending and handling the messages.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOClientNettyChannel extends ISOBaseNettyChannel<ISOClientProperties>
    implements ISOClientChannel {

  private final MessageBinder messageBinder;
  private final MessageKeyGenerator messageKeyGenerator;
  private final ISOReconnectProperties reconnectProperties;
  private final ISONettyMessageObservable messageObservable;
  private final long requestTimeoutMs;

  /**
   * Netty client channel constructor.
   *
   * @param clientProperties    client properties of netty channel
   * @param packagerFactory     message packager factory
   * @param listenerFactory     message listener factor
   * @param messageKeyGenerator message key generator
   */
  public ISOClientNettyChannel(ISOClientProperties clientProperties,
      ISOPackagerFactory packagerFactory, ISOMessageListenerFactory listenerFactory,
      MessageBinder messageBinder, MessageKeyGenerator messageKeyGenerator) {
    super(clientProperties, packagerFactory, listenerFactory);
    this.messageBinder = messageBinder;
    this.messageKeyGenerator = messageKeyGenerator;
    this.reconnectProperties = clientProperties.getReconnect();
    this.messageObservable = new ISONettyMessageObservable();
    this.requestTimeoutMs = clientProperties.getRequestTimeoutMsOrDefault();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isConnected() {
    return nettyChannel != null && nettyChannel.isOpen() && nettyChannel.isActive();
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void disconnect() {
    try {
      nettyChannel.disconnect().sync();
    } catch (InterruptedException e) {
      throw new ISOException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOMessage send(ISOMessage msg) {
    return send(msg, requestTimeoutMs);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOMessage send(ISOMessage msg, long requestTimeoutMs) {
    try {
      return sendFuture(msg).get(requestTimeoutMs, TimeUnit.MILLISECONDS);
    } catch (InterruptedException | TimeoutException | ExecutionException e) {
      throw new ISOException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Future<ISOMessage> sendFuture(ISOMessage msg) {
    final String outMsgKey = messageKeyGenerator.generate(msg);
    sendAsync(msg);
    final CompletableFuture<ISOMessage> awaitFuture = new CompletableFuture<>();
    final ISONettyMessageObserver messageObserver =
        ISONettyMessageObserver.builder().outMsgType(msg.getType()).outMsgKey(outMsgKey)
            .awaitFuture(awaitFuture).messageBinder(messageBinder).build();
    messageObservable.addObserver(messageObserver, requestTimeoutMs);
    return awaitFuture;
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * Class which decorates the netty message handler to be able to handle the messages in
   * {@link Future} futures that are awaiting a response message. This handler uses
   * {@link MessageKeyGenerator} to identify that the response message is applicable for a request
   * message.
   */
  protected static class SyncNettyMessageHandler extends NettyMessageHandler {

    protected final ISONettyMessageObservable messageObservable;

    /**
     * Constructor based on listener factory and message observable.
     *
     * @param listenerFactory   listener factory
     * @param messageObservable message observable
     */
    @Builder
    public SyncNettyMessageHandler(ISOMessageListenerFactory listenerFactory,
        ISONettyMessageObservable messageObservable) {
      super(listenerFactory);
      this.messageObservable = messageObservable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ISOMessage msg) throws Exception {
      final boolean consumed = messageObservable.onMessage(msg);
      if (consumed) {
        // This message has been consumed by await future in #sendFuture() method
        return;
      }
      super.channelRead0(ctx, msg);
    }
  }
}
