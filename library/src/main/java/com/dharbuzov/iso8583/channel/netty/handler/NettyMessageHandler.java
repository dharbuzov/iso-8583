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
package com.dharbuzov.iso8583.channel.netty.handler;

import com.dharbuzov.iso8583.channel.ISOReplyChannel;
import com.dharbuzov.iso8583.channel.netty.ISONettyReplyChannel;
import com.dharbuzov.iso8583.factory.ISOMessageListenerFactory;
import com.dharbuzov.iso8583.model.ISOMessage;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * The message handler class, the main responsibility of this class is to obtain the decoded
 * incoming message and notify the message listeners {@link ISOMessageListenerFactory} defined in
 * the library.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@ChannelHandler.Sharable
public class NettyMessageHandler extends SimpleChannelInboundHandler<ISOMessage> {

  protected final ISOMessageListenerFactory listenerFactory;

  public NettyMessageHandler(ISOMessageListenerFactory listenerFactory) {
    this.listenerFactory = listenerFactory;
  }

  /**
   * Reads the message from the channel out list.
   *
   * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
   *            belongs to
   * @param msg the message to handle
   * @throws Exception if listener factory is not able to handle the message
   */
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ISOMessage msg) throws Exception {
    final ISOReplyChannel replyChannel = new ISONettyReplyChannel(ctx);
    listenerFactory.onMessage(replyChannel, msg);
  }
}
