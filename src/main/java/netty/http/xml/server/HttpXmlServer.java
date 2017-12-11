package netty.http.xml.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.http.xml.bean.Order;
import netty.http.xml.coding.decoder.HttpXmlRequestDecoder;
import netty.http.xml.coding.encoder.HttpXmlResponseEncoder;
import netty.http.xml.coding.encoder.HttpXmlServerHandler;

public class HttpXmlServer {
	
	private void bind(final int port){
//		3.构建处理客户端连接请求的线程池
		EventLoopGroup bossGroup = new NioEventLoopGroup();
//		4.构建处理客户端读写请求的连接池
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
//		7.创建启动类
			ServerBootstrap b = new ServerBootstrap();
//		8.启动类定义连接池
			b.group(bossGroup, workGroup);
//		9.启动类定义服务端网络套接字通道
			b.channel(NioServerSocketChannel.class);
//		10.启动类定义客户端连接等待队列初始大小等配置
			b.option(ChannelOption.SO_BACKLOG, 1024);
//		11.启动类定义编解码处理责任链
			b.handler(new LoggingHandler(LogLevel.INFO));
			b.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline channelPipeline = ch.pipeline();
					channelPipeline.addLast(new HttpRequestDecoder());
					channelPipeline.addLast(new HttpObjectAggregator(65536));
					channelPipeline.addLast(new HttpXmlRequestDecoder(Order.class, true));
					
					channelPipeline.addLast(new HttpResponseEncoder());
					channelPipeline.addLast(new HttpXmlResponseEncoder());
					channelPipeline.addLast(new HttpXmlServerHandler());
				}
			});
//		12.绑定端口
			ChannelFuture f = b.bind(new InetSocketAddress(port)).sync();
			System.out.println("Http 订购服务器启动，网址为：" + "http://localhost:" + port);
			f.channel().closeFuture().sync();
//		13.监听端口关闭
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			
//		5.关闭连接线程池
			bossGroup.shutdownGracefully();
//		6.关闭读写线程池
			workGroup.shutdownGracefully();
		}
	}
	public static void main(String[] args) {
//		1.初始化端口
		int port = 9999;
		if(args != null && args.length > 1){
			port = Integer.parseInt(args[0]);
		}
//		2.绑定端口
		new HttpXmlServer().bind(port);
	}

}
