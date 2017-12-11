package io.netty.errorscenarios.server;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerTimeHandler extends ChannelInboundHandlerAdapter {
	
	private int counter;
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       ByteBuf byteBuf = (ByteBuf)msg;
       byte[] bytes = new byte[byteBuf.readableBytes()];
       byteBuf.readBytes(bytes);
       String body = new String(bytes, "UTF-8").substring(0, bytes.length - System.getProperty("line.separator").length());
       System.out.println("The time server receive order:" + body + "; the counter is :" + ++counter);
       String currentTime = "query time".equalsIgnoreCase(body) 
    		   ? new Date(System.currentTimeMillis()).toString()
    				  : "bad order";
       currentTime = currentTime + System.getProperty("line.separator");
       byteBuf.release();
//		ctx.writeAndFlush(currentTime);
    	//避免每次服务端响应信息都调用多路复用器向通道中写入信息，可以先将输出内容放到发送缓冲区（数组）   
    	ByteBuf buf = ctx.alloc().buffer(4 * currentTime.length());
//    	另一种写法：填充缓冲区
//    	ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
    	buf.writeBytes(currentTime.getBytes());
    	//保证处理服务端写到通道中数据结构和客户端接受时数据结构要格式一致,好像都是讲交互信息写入ButeBuf再写入通道传输
    	ctx.write(buf);
    }

	 @Override
	    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		 //待所有输出信息完成，再将发送缓冲区的内容写到客户端套接字通道中
	        ctx.flush();
	    }
	 
	 @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	            throws Exception {
	      ctx.close();
	    }
	 
//	 The time server receive order:query time
//	 此处省略87行query time
//	 qu; the counter is :1
//	 The time server receive order:y time
//	 此处省略13行query time
//	 query time; the counter is :2
//	 服务端运行结果：表明它只接受了2条消息，第一条包括87个query,第二条包括13个query.这表明发生了TCP粘包


}
