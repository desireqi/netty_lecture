package com.shengsiyuan.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.ArrayList;
import java.util.List;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String>{

  private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    Channel ch = ctx.channel();
    channelGroup.forEach(channel -> {
      if (ch != channel){
        channel.writeAndFlush(ch.remoteAddress()+" 发送的消息： "+msg+"\n");
      }else {
        channel.writeAndFlush("[自己]" + msg +"\n");
      }
    });

  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    channelGroup.writeAndFlush("[服务器]- "+channel.remoteAddress()+" 加入\n");
    channelGroup.add(channel);
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    channelGroup.writeAndFlush("[服务器]- "+channel.remoteAddress()+" 离开\n");
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    channelGroup.writeAndFlush("[服务器]- "+channel.remoteAddress()+" 上线\n");
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    channelGroup.writeAndFlush("[服务器]- "+channel.remoteAddress()+" 下线\n");
  }
}
