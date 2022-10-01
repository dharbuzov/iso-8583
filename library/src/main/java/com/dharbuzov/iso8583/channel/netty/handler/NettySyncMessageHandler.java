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

import java.util.concurrent.Future;

import com.dharbuzov.iso8583.binder.MessageBinder;
import com.dharbuzov.iso8583.binder.MessageKeyGenerator;
import com.dharbuzov.iso8583.channel.netty.observer.ISONettyMessageObservable;
import com.dharbuzov.iso8583.factory.ISOMessageListenerFactory;
import com.dharbuzov.iso8583.model.ISOMessage;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Builder;

/**
 * Class is a synchronous message handler. Decorates the netty message handler to be able to handle
 * the messages in {@link Future} futures that are awaiting a response message. This handler uses
 * {@link MessageKeyGenerator} and {@link MessageBinder} to identify that the response message is
 * applicable for a request message.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 * @see ISONettyMessageObservable
 */
@ChannelHandler.Sharable
public class NettySyncMessageHandler extends NettyMessageHandler {

  protected final ISONettyMessageObservable messageObservable;

  /**
   * Constructor based on listener factory and message observable.
   *
   * @param listenerFactory   listener factory
   * @param messageObservable message observable
   */
  @Builder
  public NettySyncMessageHandler(ISOMessageListenerFactory listenerFactory,
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
