package io.netty.errorscenarios.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {
	
	private void bind(int port) {
//		1.服务器处理客户端请求连接的NioEventLoopGroup线程组，处理网络请求读写的NioEventLoopGroup
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		
//		2.构建辅助启动类
		try {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workGroup)
		.channel(NioServerSocketChannel.class)
		.option(ChannelOption.SO_BACKLOG, 1024)
		.childHandler(new ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new ServerTimeHandler());
			}
			
		});
//		3.配置辅助启动类 异步服务端网络套接字通道、通道处理
		
//		4.异步绑定位置信息，并同步等待成功
			ChannelFuture f = b.bind(new InetSocketAddress(port)).sync();
//		5.等待服务监听端口关闭
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			//优雅退出，释放线程池资源
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
	}


	public static void main(String[] args) {
		//初始化端口
		int port = 9999;
		if(args != null && args.length > 0){
			port = Integer.parseInt(args[0]);
		}
		new TimeServer().bind(port);
		//服务器端绑定监听端口

	}

	
}
