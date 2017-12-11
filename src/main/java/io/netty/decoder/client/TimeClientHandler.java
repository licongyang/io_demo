package io.netty.decoder.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
	
	private int counter;
	private byte[] req;
	
	public TimeClientHandler(){
		req = ("query time" + System.getProperty("line.separator")).getBytes();
		
	}
	
	 @Override
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		 	客户端与服务端链路建立成功后，循环发送100条消息，
//		 	每发送一次就刷新一次，保证每条消息都会被写进套接字通道中
		 	ByteBuf message = null;
		 	for(int i = 0; i < 100; i++){
		 		message = Unpooled.buffer(req.length);
		 		message.writeBytes(req);
		 		ctx.writeAndFlush(message);
		 	}
	    }

	 @Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
				//当出现异常就关闭连接
				cause.printStackTrace();
				ctx.close();
		}
	 @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		 客户端每接受服务端一条应答消息后，就打印一次计数器
//	       ByteBuf byteB = (ByteBuf)msg;
//	       byte[] bytes = new byte[byteB.readableBytes()];
//	       byteB.readBytes(bytes);
//	       String body = new String(bytes, "UTF-8");
//	       System.out.println("Now is :" + body + "; the counter is :" + ++counter);
		 
//		 System.out.println((String)msg);
	       //实际客户端收到：Now is bad order, bad order ; the counter is 1,表明客户端接受Tcp包时发生了粘包
		 
		 //利用处理器类的解码器，客户端获取到的服务端响应信息已经是字符串格式
		 String body = (String)msg;
		 System.out.println(" Now is :" + body + "; the counter is :" + ++counter);
	       
	    }
	 
}
