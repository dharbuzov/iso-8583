package com.dharbuzov.iso8583.channel.netty;

import java.util.List;

import com.dharbuzov.iso8583.config.ISOBaseProperties;
import com.dharbuzov.iso8583.config.ISOConnProperties;
import com.dharbuzov.iso8583.exception.ISOParseException;
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
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class ISOBaseNettyChannel<T extends ISOBaseProperties> {

  protected final T properties;
  protected final ISOConnProperties connProperties;
  protected final ISOPackagerFactory packagerFactory;
  protected final ISOListenerFactory listenerFactory;

  protected Channel nettyChannel;

  public ISOBaseNettyChannel(T properties,
      ISOPackagerFactory packagerFactory, ISOListenerFactory listenerFactory) {
    this.properties = properties;
    this.connProperties = properties.getConnection();
    this.packagerFactory = packagerFactory;
    this.listenerFactory = listenerFactory;
  }

  @Builder
  @RequiredArgsConstructor
  protected static class NettyChannelInitializer extends ChannelInitializer<Channel> {

    private final NettyMessageDecoder nettyMessageDecoder;
    private final NettyMessageEncoder nettyMessageEncoder;
    private final NettyMessageHandler nettyMessageHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
      final ChannelPipeline pipeline = ch.pipeline();
      pipeline.addLast(nettyMessageDecoder);
      pipeline.addLast(nettyMessageEncoder);
      pipeline.addLast(nettyMessageHandler);
    }
  }

  @RequiredArgsConstructor
  protected static class NettyMessageDecoder extends ByteToMessageDecoder {

    private final ISOPackagerFactory packagerFactory;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
        throws Exception {
      if (!in.isReadable()) {
        return;
      }
      final byte[] bytes = new byte[in.readableBytes()];
      in.readBytes(bytes);
      final ISOMessage msg = packagerFactory.unpack(bytes);
      if(msg != null) {
        out.add(msg);
      } else {
        throw new ISOParseException("Couldn't parse the incoming bytes into ISOMessage!");
      }
    }
  }

  @RequiredArgsConstructor
  protected static class NettyMessageEncoder extends MessageToByteEncoder<ISOMessage> {

    private final ISOPackagerFactory packagerFactory;

    @Override
    protected void encode(ChannelHandlerContext ctx, ISOMessage msg, ByteBuf out) throws Exception {
      final byte[] bytes = packagerFactory.pack(msg);
      out.writeBytes(bytes);
    }
  }

  @RequiredArgsConstructor
  protected static class NettyMessageHandler extends SimpleChannelInboundHandler<ISOMessage> {

    protected final ISOListenerFactory listenerFactory;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ISOMessage msg) throws Exception {
      listenerFactory.onMessage(msg);
    }
  }
}
