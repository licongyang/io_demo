package io.netty.decoder.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient {
	
	private void connect(String host, int port) {
//		1.处理网络请求读写线程池
		NioEventLoopGroup group = new NioEventLoopGroup();
//		2.构建客户端bootstrap
		try {
		Bootstrap b = new Bootstrap();
		
//		3.配置启动类 通道、配置、 处理
		b.group(group)
		.channel(NioSocketChannel.class)
		.option(ChannelOption.TCP_NODELAY, true)
		.handler(new  ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				//在处理类增加解码器
				ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new TimeClientHandler());
			}});
//		4.连接服务端，直到成功
		ChannelFuture f;
			f = b.connect(host, port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		5.释放资源
		finally{
			group.shutdownGracefully();
		}
		
	}

	public static void main(String[] args) {
//		初始化端口
		int port = 9999;
		if(args != null && args.length > 0){
			port = Integer.parseInt(args[0]);
		}
//		连接服务器位置信息
		new TimeClient().connect("127.0.0.1", port);
	}

	

}
