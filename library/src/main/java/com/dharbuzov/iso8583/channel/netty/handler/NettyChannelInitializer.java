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

import com.dharbuzov.iso8583.factory.ISOPackagerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

/**
 * The channel initializer class, the main responsibility of this class is to initialize the netty
 * {@link Channel} and provide the chain of {@link io.netty.channel.ChannelHandler} handlers.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Builder
@RequiredArgsConstructor
public class NettyChannelInitializer extends ChannelInitializer<Channel> {

  protected final ISOPackagerFactory packagerFactory;
  protected final NettyMessageHandler nettyMessageHandler;

  /**
   * Inits the pipeline of the channel.
   *
   * @param ch the {@link Channel} which was registered.
   * @throws Exception if the channel's pipeline add operation is failed
   */
  @Override
  protected void initChannel(Channel ch) throws Exception {
    final ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(createMessageDecoder());
    pipeline.addLast(createMessageEncoder());
    pipeline.addLast(nettyMessageHandler);
  }

  /**
   * Creates message decoder new instance per channel.
   *
   * @return netty message decoder
   */
  protected NettyMessageDecoder createMessageDecoder() {
    return new NettyMessageDecoder(packagerFactory);
  }

  /**
   * Creates message encoder new instance per channel.
   *
   * @return netty message encoder
   */
  protected NettyMessageEncoder createMessageEncoder() {
    return new NettyMessageEncoder(packagerFactory);
  }
}

