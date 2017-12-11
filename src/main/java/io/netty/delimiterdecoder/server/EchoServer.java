package io.netty.delimiterdecoder.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 
 * @ClassName: EchoServer 
 * @Description: 回声异步非堵塞服务器 利用特定分隔符“&_”来解析客户端请求，并将接受信息读取后返回给客户端 
 * @author lcy
 * @date 2017年11月27日 下午3:48:44    
 */

public class EchoServer {

	public void bind(int port){
	System.out.println("The echo server is start in port :" + port);
//	3.创建服务器连接客户端的线程池
	NioEventLoopGroup bossGroup = new NioEventLoopGroup();
//	4.创建服务器处理客户端读写请求的线程池
	NioEventLoopGroup workGroup = new NioEventLoopGroup();
	try {
//	5.创建辅助启动类
	ServerBootstrap b = new ServerBootstrap();
//	6.将两个线程池加到辅助启动类
	b.group(bossGroup, workGroup);
//	7.设置通道
	b.channel(NioServerSocketChannel.class);
//	8.设置辅助启动类的配置
	b.option(ChannelOption.SO_BACKLOG, 1024);
//	9.设置辅助启动类的子处理
	b.handler(new LoggingHandler(LogLevel.INFO));
	b.childHandler(new ChannelInitializer<SocketChannel>(){

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
			ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
			ch.pipeline().addLast(new StringDecoder());
			ch.pipeline().addLast(new EchoServerHandler());
		}
		
	});
//	9.绑定端口，同步等待成功
		ChannelFuture f = b.bind(port).sync();
//	10.等待服务器监听端口关闭，
//		!!!!注意这里是closeFuture而不是close方法，否则，客户端找不到服务器了
		f.channel().closeFuture().sync();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}finally{
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
	}
	}

	public static void main(String[] args) {
		// 1.初始化端口
		int port = 9999;
		if (args != null && args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		// 2.绑定位置信息
		new EchoServer().bind(port);

	}

}
