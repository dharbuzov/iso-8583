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

import com.dharbuzov.iso8583.factory.ISOMessagePackagerFactory;
import com.dharbuzov.iso8583.model.ISOMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * The byte encoder class, the main responsibility of this class is to encode of outgoing message
 * into the bytes and write them to the out {@link ByteBuf} buffer for future delivery via network.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class NettyMessageEncoder extends MessageToByteEncoder<ISOMessage> {

  private final ISOMessagePackagerFactory packagerFactory;

  public NettyMessageEncoder(ISOMessagePackagerFactory packagerFactory) {
    this.packagerFactory = packagerFactory;
  }

  /**
   * The method to encode the outgoing {@link ISOMessage} message into the bytes.
   *
   * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToByteEncoder} belongs
   *            to
   * @param msg the message to encode
   * @param out the {@link ByteBuf} into which the encoded message will be written
   * @throws Exception if an error occurred during message packaging
   */
  @Override
  protected void encode(ChannelHandlerContext ctx, ISOMessage msg, ByteBuf out) throws Exception {
    final byte[] bytes = packagerFactory.pack(msg);
    out.writeBytes(bytes);
  }
}
