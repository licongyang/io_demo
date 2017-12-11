package netty.demo.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){
		System.out.println("SimpleClientHandler.channelRead");
		ByteBuf result = (ByteBuf)msg;
		byte[] bytes = new byte[result.readableBytes()];
		result.readBytes(bytes);
		System.out.println("server said: " + new String(bytes));
		result.release();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
			//当出现异常就关闭连接
			cause.printStackTrace();
			ctx.close();
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx){
		//连接成功后，向server发送消息
		String msg = "hello server!";
		ByteBuf byteBuf = ctx.alloc().buffer(4 * msg.length());
		byteBuf.writeBytes(msg.getBytes());
		ctx.writeAndFlush(byteBuf);
	}
}
