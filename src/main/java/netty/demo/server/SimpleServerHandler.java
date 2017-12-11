package netty.demo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){
		System.out.println("SimpleServerHandler.channelRead");
		//msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
		ByteBuf result = (ByteBuf)msg;
		byte[] bytes = new byte[result.readableBytes()];
		result.readBytes(bytes);
		String resultStr = new String(bytes);
		//接收并打印客户端的信息
		System.out.println("client said: " + resultStr);
		//释放资源，
		result.release();
		
		//向客户端发送信息
		String response = "hello client";
		//在当前场景下，发送的数据必须转换成ByteBuf数组
		ByteBuf byteBuf = ctx.alloc().buffer(4 * response.length());
		byteBuf.writeBytes(response.getBytes());
		ctx.writeAndFlush(byteBuf);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		 //当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx){
		ctx.flush();
	}
}
