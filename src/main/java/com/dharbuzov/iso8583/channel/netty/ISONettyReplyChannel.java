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

import com.dharbuzov.iso8583.channel.ISOReplyChannel;
import com.dharbuzov.iso8583.model.ISOMessage;

import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;

/**
 * Netty based reply channel, which writes to the channel message and flushes.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@RequiredArgsConstructor
public class ISONettyReplyChannel implements ISOReplyChannel {

  private final ChannelHandlerContext ctx;

  /**
   * {@inheritDoc}
   */
  @Override
  public void reply(ISOMessage message) {
    ctx.writeAndFlush(message);
  }
}
