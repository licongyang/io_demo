package netty.decoder.msgpack.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class EchoClient {
	
	private void connect(String host, int port,int sendNumber) {
//		1.处理网络请求读写线程池
		NioEventLoopGroup group = new NioEventLoopGroup();
//		2.构建客户端bootstrap
		try {
		Bootstrap b = new Bootstrap();
		
//		3.配置启动类 通道、配置、 处理
		b.group(group)
		.channel(NioSocketChannel.class)
		.option(ChannelOption.TCP_NODELAY, true)
//		.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
		.handler(new  ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline =ch. pipeline();
				//在处理类增加解码器
				pipeline.addLast("framedecoder", new LengthFieldBasedFrameDecoder(port, 0, 2, 0, 2));
				pipeline.addLast("msgpack decoder", new MsgpackDecoder());
//				在MessagePack编码器之前增加LengthFieldPrepender,它将在ByteBuf之前增加2个字节的消息长度字段
				pipeline.addLast("frameencoder", new LengthFieldPrepender(2));
				pipeline.addLast("msgpack encoder", new MsgpackEncoder());
				pipeline.addLast("handler" ,new EchoClientHandler(sendNumber));
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
		new EchoClient().connect("127.0.0.1", port, 100);
	}

	

}
