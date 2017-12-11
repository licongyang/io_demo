package netty.http.xml.client;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import netty.http.xml.bean.Order;
import netty.http.xml.coding.decoder.HttpXmlResponseDecoder;
import netty.http.xml.coding.encoder.HttpXmlClientHandler;
import netty.http.xml.coding.encoder.HttpXmlRequestEncoder;

public class HttpXmlClient {
	
	private void connect(String ip, int port){
		ip = ip == null ? "127.0.0.1" : ip;
//		3.构建线程池（这个什么作用）
		EventLoopGroup group = new NioEventLoopGroup();
		try{
//		5.构建启动类
		Bootstrap b  = new Bootstrap();
//		6.启动类定义线程池
		b.group(group);
//		7.启动类定义通道
		b.channel(NioSocketChannel.class);
//		8.启动类定义参数
//		在TCP/IP协议中，无论发送多少数据，总是要在数据前面加上协议头，同时，对方接收到数据，也需要发送ACK表示确认。为了尽可能的利用网络带宽，TCP总是希望尽可能的发送足够大的数据。这里就涉及到一个名为Nagle的算法，该算法的目的就是为了尽可能发送大块数据，避免网络中充斥着许多小数据块。
//		 TCP_NODELAY就是用于启用或关于Nagle算法。如果要求高实时性，有数据发送时就马上发送，就将该选项设置为true关闭Nagle算法；如果要减少发送次数减少网络交互，就设置为false等累积一定大小后再发送。默认为false。
		b.option(ChannelOption.TCP_NODELAY, true);
//		9.启动类定义处理类
		b.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
//				将二进制码流解码成Http的应答消息
				pipeline.addLast(new HttpResponseDecoder());
//				将一个Http请求消息的多个部分合并成一条完整的Http请求消息
				pipeline.addLast(new HttpObjectAggregator(65536));
//				实现http+xml应答消息的自动解码
				pipeline.addLast(new HttpXmlResponseDecoder(Order.class, true));
				
				pipeline.addLast(new HttpRequestEncoder());
				pipeline.addLast(new HttpXmlRequestEncoder());
				pipeline.addLast(new HttpXmlClientHandler());
			}
		});
//		10.启动类连接服务器位置
		ChannelFuture f = b.connect(new InetSocketAddress(port)).sync();
		System.out.println("客户端绑定服务端口："  + port );
//		11.关闭连接
		f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
//		4.优雅关闭线程池
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
//		1.初始化端口号
		int port = 9999;
		String ip = "127.0.0.1";
		if(args != null && args.length > 0){
			port = Integer.parseInt(args[0]);
		}
//		2.连接服务端位置
		new HttpXmlClient().connect(ip, port);
	}

}
