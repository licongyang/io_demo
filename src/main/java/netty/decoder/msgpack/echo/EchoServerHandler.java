package netty.decoder.msgpack.echo;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			
		@SuppressWarnings("unchecked")
//		java.lang.ClassCastException: org.msgpack.type.IntValueImpl cannot be cast to java.util.List
//		存在粘包问题，需要在包前加入分割字段
		List<UserInfo> infos = (List<UserInfo>)msg;
		System.out.println("server receive the msgpack message :" + infos);
		
		ctx.write(msg);
	}
	
	 @Override
	    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	        ctx.flush();
	    }


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
