package netty.decoder.protobuf.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import netty.decoder.protobuf.SubscribeRespProto;

public class SubReqClient {
	public void connect(String ip ,int port){
		
//	3.创建连接服务器的线程池
		EventLoopGroup group = new NioEventLoopGroup();
		try{
//	4.创建辅助启动类
		Bootstrap b = new Bootstrap();
//	5.定义启动类线程池
		b.group(group);
//	6.定义客户端通道
		b.channel(NioSocketChannel.class);
//	7.定期连接延迟配置
		b.option(ChannelOption.TCP_NODELAY, true);
		b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
//	8.定义码流处理类
		b.handler(new ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				//在解码器前面处理半包
				pipeline.addLast(new ProtobufVarint32FrameDecoder());
				//解码器通过参数指明要解析的数据结构（服务器响应信息）
				pipeline.addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()));
				//在编码器前面处理半包
				pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
				//编码器
				pipeline.addLast(new ProtobufEncoder());
				//处理码流
				pipeline.addLast(new SubReqClientHandler());
			}
			
		});
//	9连接服务器
		ChannelFuture f = b.connect(ip, port).sync();
//	10.等待客户端链接断开
		f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
	//	11.释放资源
			group.shutdownGracefully();
		}

	}
	public static void main(String[] args) {
//		1.初始化端口
		int port = 9999;
		String ip = "127.0.0.1";
		if(args != null && args.length > 0){
			port = Integer.parseInt(args[0]);
		}
//		2.连接服务器位置信息
		new SubReqClient().connect(ip,port);
	}

}
