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

import java.util.List;

import com.dharbuzov.iso8583.config.ISOBaseProperties;
import com.dharbuzov.iso8583.config.ISOConnProperties;
import com.dharbuzov.iso8583.exception.ISOPackageException;
import com.dharbuzov.iso8583.factory.ISOListenerFactory;
import com.dharbuzov.iso8583.factory.ISOPackagerFactory;
import com.dharbuzov.iso8583.model.ISOMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

/**
 * The base implementation of netty based channel, which defines the default channel handlers and
 * required classes for message handling.
 *
 * @param <T> the generic subtype of iso base properties
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class ISOBaseNettyChannel<T extends ISOBaseProperties> {

  protected final T properties;
  protected final ISOConnProperties connProperties;
  protected final ISOPackagerFactory packagerFactory;
  protected final ISOListenerFactory listenerFactory;

  protected Channel nettyChannel;

  /**
   * Base constructor.
   *
   * @param properties      iso properties
   * @param packagerFactory packager factory
   * @param listenerFactory listener factory
   */
  public ISOBaseNettyChannel(T properties, ISOPackagerFactory packagerFactory,
      ISOListenerFactory listenerFactory) {
    this.properties = properties;
    this.connProperties = properties.getConnection();
    this.packagerFactory = packagerFactory;
    this.listenerFactory = listenerFactory;
  }

  /**
   * The channel initializer class, the main responsibility of this class is to initialize the netty
   * {@link Channel} and provide the chain of {@link io.netty.channel.ChannelHandler} handlers.
   */
  @Builder
  @RequiredArgsConstructor
  protected static class NettyChannelInitializer extends ChannelInitializer<Channel> {

    private final NettyMessageDecoder nettyMessageDecoder;
    private final NettyMessageEncoder nettyMessageEncoder;
    private final NettyMessageHandler nettyMessageHandler;

    /**
     * Inits the pipeline of the channel.
     *
     * @param ch the {@link Channel} which was registered.
     * @throws Exception if the channel's pipeline add operation is failed
     */
    @Override
    protected void initChannel(Channel ch) throws Exception {
      final ChannelPipeline pipeline = ch.pipeline();
      pipeline.addLast(nettyMessageDecoder);
      pipeline.addLast(nettyMessageEncoder);
      pipeline.addLast(nettyMessageHandler);
    }
  }


  /**
   * The byte decoder class, the main responsibility of this class is to decode of readable bytes
   * from {@link ByteBuf} buffer into {@link ISOMessage} class and put the message into out list for
   * future processing by the next handlers.
   */
  @RequiredArgsConstructor
  protected static class NettyMessageDecoder extends ByteToMessageDecoder {

    private final ISOPackagerFactory packagerFactory;

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
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
        throws Exception {
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


  /**
   * The byte encoder class, the main responsibility of this class is to encode of outgoing message
   * into the bytes and write them to the out {@link ByteBuf} buffer for future delivery via
   * network.
   */
  @RequiredArgsConstructor
  protected static class NettyMessageEncoder extends MessageToByteEncoder<ISOMessage> {

    private final ISOPackagerFactory packagerFactory;

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


  /**
   * The message handler class, the main responsibility of this class is to obtain the decoded
   * incoming message and notify the message listeners {@link ISOListenerFactory} defined in the
   * library.
   */
  @RequiredArgsConstructor
  protected static class NettyMessageHandler extends SimpleChannelInboundHandler<ISOMessage> {

    protected final ISOListenerFactory listenerFactory;

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
      listenerFactory.onMessage(msg);
    }
  }
}
