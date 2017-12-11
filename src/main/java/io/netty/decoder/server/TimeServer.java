package io.netty.decoder.server;

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
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

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
				//增加解码器处理编码和读半包问题
//				1.LineBasedFrameDecoder依次遍历ByteBuf中的可读字节，判断是否有"\n" 或"\r\n".如果有，就把此位置作为结束位置，
//				从可读索引到结束位置区间的字节组成一行。
//				它是以换行符为结束标志的解码器，支持携带结束符或者不携带结束符两种解码格式，同时支持配置单行的最大长度。
//				如果连续读取到最大长度后仍然没有发现换行符，就会抛出异常，同时忽略掉之前读到的异常码流
//				后面的ChannelHandler接受的msg对象就是完整的消息包，
//				2.如果要指定特定的分隔符，则将分隔符封装成ByteBuf(客户端同样这样)，
//				注意handler处理的时候，获取的消息是过滤掉分隔符的
//				ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
//				ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
//				3.如果接受的信息时固定长度的，则用FixedLengthFrameDecoder，设置指定长度解码,
//				解码器无论一次接受多少数据，如果是半包数据，解码器会缓存半包数据并等待下个包到达后进行拼包，直到读取到一个完整的包
//				ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
				ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
//				StringDecodr就是将接受的对象ByteBuf转成字符串，然后继续调用后面的handler
				ch.pipeline().addLast(new StringDecoder());
//				LineBasedFrameDecoder + StringDecoder组合就是按行切换的文本编辑器，它被设计用来支持TCP粘包和拆包
//				这个ChanelHandler接受的msg对象就是解码后的字符串对象
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
