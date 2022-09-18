package com.dharbuzov.iso8583.channel.netty;

import java.util.List;

import com.dharbuzov.iso8583.config.ISOConnProperties;
import com.dharbuzov.iso8583.coordinator.ISOMessageCoordinator;
import com.dharbuzov.iso8583.exception.ISOParseException;
import com.dharbuzov.iso8583.factory.ISOMessageFactory;
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
public abstract class ISOBaseNettyChannel {

  protected final ISOConnProperties connProperties;
  protected final ISOMessageFactory messageFactory;
  protected final ISOMessageCoordinator messageCoordinator;

  protected Channel nettyChannel;

  public ISOBaseNettyChannel(ISOConnProperties connProperties,
      ISOMessageFactory messageFactory, ISOMessageCoordinator messageCoordinator) {
    this.connProperties = connProperties;
    this.messageFactory = messageFactory;
    this.messageCoordinator = messageCoordinator;
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

    private final ISOMessageFactory messageFactory;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
        throws Exception {
      if (!in.isReadable()) {
        return;
      }
      final byte[] bytes = new byte[in.readableBytes()];
      in.readBytes(bytes);
      final ISOMessage msg = messageFactory.unpack(bytes);
      if(msg != null) {
        out.add(msg);
      } else {
        throw new ISOParseException("Couldn't parse the incoming bytes into ISOMessage!");
      }
    }
  }

  @RequiredArgsConstructor
  protected static class NettyMessageEncoder extends MessageToByteEncoder<ISOMessage> {

    private final ISOMessageFactory messageFactory;

    @Override
    protected void encode(ChannelHandlerContext ctx, ISOMessage msg, ByteBuf out) throws Exception {
      final byte[] bytes = messageFactory.pack(msg);
      out.writeBytes(bytes);
    }
  }

  @RequiredArgsConstructor
  protected static class NettyMessageHandler extends SimpleChannelInboundHandler<ISOMessage> {

    private final ISOMessageCoordinator messageCoordinator;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ISOMessage msg) throws Exception {
      messageCoordinator.in(msg);
    }
  }
}
