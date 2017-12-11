package io.netty.delimiterdecoder.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	int counter = 0;
	static final String ECHO_REQ = "Hi, welcome to Netty!$_";
	public EchoClientHandler(){
		
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx){
		for(int i = 0; i < 10; i++){
			ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
		}
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){
		System.out.println("This is " + ++counter + " time receive server: [" + msg + "]");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable caught){
		caught.printStackTrace();
		ctx.close();
	}
}
