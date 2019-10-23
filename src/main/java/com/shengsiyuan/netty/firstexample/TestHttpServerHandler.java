package com.shengsiyuan.netty.firstexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject>{

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject)
      throws Exception {

    System.out.println(httpObject.getClass());

    if (httpObject instanceof HttpRequest){

      System.out.println("执行 channelRead0");

      ByteBuf content = Unpooled.copiedBuffer("Hello World",CharsetUtil.UTF_8);
      FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,content);
      response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
      response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
      channelHandlerContext.writeAndFlush(response);
      channelHandlerContext.channel().close();
    }
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("channel active");
    super.channelActive(ctx);
  }

  @Override
  public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    System.out.println("channel registered");
    super.channelRegistered(ctx);
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    System.out.println("hanlder added");
    super.handlerAdded(ctx);
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    System.out.println("hanlder removed");
    super.handlerAdded(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("channel inactive");
    super.channelInactive(ctx);
  }

  @Override
  public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    System.out.println("channel unregistered");
    super.channelUnregistered(ctx);
  }
}
