package netty.decoder.msgpack.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {
	
	public void bind(int port){
//		1.构造处理客户端连接的线程池
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
//		2.构造处理客户端读写请求的线程池
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		try{
//		5.构造辅助类
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup);
//		6.辅助类配置通道
			b.channel(NioServerSocketChannel.class);
//		7.辅助类配置参数
//			　ChannelOption.SO_BACKLOG对应的是tcp/ip协议listen函数中的backlog参数，函数listen(int socketfd,int backlog)用来初始化服务端可连接队列，
//			　　　　服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，
//			    backlog参数指定了队列的大小
			b.option(ChannelOption.SO_BACKLOG, 1024);
//		8.辅助类配置处理
			b.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					//增加粘包拆包编码处理
//					在解码器之前增加LengthFieldBaseFrameDecoder，用于处理半包消息，这样后面的MessagePack接受的永远是整包信息
//					maxFrameLength 
//						the maximum length of the frame. If the length of the frame is greater than this value, TooLongFrameException will be thrown.
//					lengthFieldOffset 
//						the offset of the length field
//					lengthFieldLength 
//						the length of the length field
//					lengthAdjustment 
//						the compensation value to add to the value of the length 
//					fieldinitialBytesToStrip 
//						the number of first bytes to strip out from the decoded frame
					pipeline.addLast("framedecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
					pipeline.addLast("msgpack decoder", new MsgpackDecoder());
//					在MessagePack编码器之前增加LengthFieldPrepender,它将在ByteBuf之前增加2个字节的消息长度字段
//					lengthFieldLength the length of the prepended length field. Only 1, 2, 3, 4, and 8 are allowed.
					pipeline.addLast("frameencoder", new LengthFieldPrepender(2));
					pipeline.addLast("msgpack encoder", new MsgpackEncoder());
					pipeline.addLast("handler", new EchoServerHandler());
				}
				
			});
//		9.辅助类配置子处理，在初始化中增加MessagePack解码器和编码器
//		10.绑定服务器位置信息
			ChannelFuture f = b.bind(port).sync();
//		11.等待监听端口关闭
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
			
		}
//		3.释放连接线程池
//		4.释放读写线程池
	}

	public static void main(String[] args) {
		int port = 9999;
		if(args != null && args.length > 0){
			port = Integer.parseInt(args[0]);
		}
		new EchoServer().bind(port);
	}

}
