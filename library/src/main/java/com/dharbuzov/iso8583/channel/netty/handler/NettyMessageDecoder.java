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

import java.util.List;

import com.dharbuzov.iso8583.exception.ISOPackageException;
import com.dharbuzov.iso8583.factory.ISOPackagerFactory;
import com.dharbuzov.iso8583.model.ISOMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * The byte decoder class, the main responsibility of this class is to decode of readable bytes from
 * {@link ByteBuf} buffer into {@link ISOMessage} class and put the message into out list for future
 * processing by the next handlers.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class NettyMessageDecoder extends ByteToMessageDecoder {

  private final ISOPackagerFactory packagerFactory;

  public NettyMessageDecoder(ISOPackagerFactory packagerFactory) {
    this.packagerFactory = packagerFactory;
  }

  /**
   * Decodes the incoming bytes into the {@link ISOMessage} message.
   *
   * @param ctx the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs
   *            to
   * @param in  the {@link ByteBuf} from which to read data
   * @param out the {@link List} to which decoded messages should be added
   * @throws Exception if the exception occurred during message parsing
   */
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    if (!in.isReadable()) {
      return;
    }
    final byte[] bytes = new byte[in.readableBytes()];
    in.readBytes(bytes);
    final ISOMessage msg = packagerFactory.unpack(bytes);
    if (msg != null) {
      out.add(msg);
    } else {
      throw new ISOPackageException("Couldn't parse the incoming bytes into ISOMessage!");
    }
  }
}
