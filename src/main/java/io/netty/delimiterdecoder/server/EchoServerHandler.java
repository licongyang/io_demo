package io.netty.delimiterdecoder.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	int counter = 0;
	 @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	        String body = (String)msg;
	        System.out.println("This is " +  ++counter + "times receive client: [" + body + "]");
	        body += "$_";
	        ByteBuf buf = Unpooled.copiedBuffer(body.getBytes());
	        ctx.writeAndFlush(buf);
	    }
	 @Override
	 public void exceptionCaught(ChannelHandlerContext ctx, Throwable ex){
		 ex.printStackTrace();
		 ctx.close();
	 }

}
