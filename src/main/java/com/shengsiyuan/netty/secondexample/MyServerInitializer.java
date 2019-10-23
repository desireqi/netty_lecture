package com.shengsiyuan.netty.secondexample;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class MyServerInitializer extends ChannelInitializer<SocketChannel>{

  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    ChannelPipeline channelPipeline = socketChannel.pipeline();
    channelPipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
    channelPipeline.addLast(new LengthFieldPrepender(4));
    channelPipeline.addLast(new StringDecoder());
    channelPipeline.addLast(new StringEncoder());
    channelPipeline.addLast(new MyServerHandler());
  }
}
