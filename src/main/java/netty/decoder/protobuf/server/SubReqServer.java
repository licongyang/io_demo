package netty.decoder.protobuf.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.decoder.protobuf.SubscribeReqProto;

/**
 * 
* @ClassName: SubReqServer 
* @Description: 商品订单服务端
* 利用netty+protobuf，对客户端请求的商品订单，进行接收并返回订单信息
* @author lcy
* @date 2017年11月29日 下午2:35:23 
*  
 */
public class SubReqServer {
	public void bind(int port){
//	3.创建处理客户端连接的线程池
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
//	4.创建处理客户端读写的线程池
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		try{
//	5.创建辅助启动类
			ServerBootstrap b = new ServerBootstrap();
//	6.定义线程池
			b.group(bossGroup,workGroup);
//	7.定义服务端通道
			b.channel(NioServerSocketChannel.class);
//	8.定义初始连接等待队列大小
			b.option(ChannelOption.SO_BACKLOG, 1024);
//	9.定义logging处理记录
			b.handler(new LoggingHandler(LogLevel.INFO));
//	10.定义自定义编解码器和读写处理器
			b.childHandler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					//在解码器前面处理半包
//					ProtoBufDecoder仅仅负责解码，它不支持读半包。因此，在ProtobufDecoder前面一定要有能处理读半包的解码器
//					有以下三种方式可以选择：
//					1.使用Netty提供的ProtobufVarint32FrameDecoder,它可以处理半包
//					2.继承Netty提供的通用半包解码器LengthFieldBasedFrameDecoder;
//					3.继承ByteToMessageDecoder类，自己处理半包问题。
					pipeline.addLast(new ProtobufVarint32FrameDecoder());
//					解码器并通过参数指明解码的目标类
					pipeline.addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));
					//在编码器前面处理半包
					pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
					//编码器
					pipeline.addLast(new ProtobufEncoder());
					//处理客户端请求和响应
					pipeline.addLast(new SubReqServerHandler());
				}
				
			});
//	11.绑定位置信息
			ChannelFuture f = b.bind(port).sync();
//	12.等待监听端口断开
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
//	13.释放线程池资源
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
		}
	}
	public static void main(String[] args) {
//		1.初始化端口
		int port = 9999;
		if(args != null && args.length > 0){
			port = Integer.parseInt(args[0]);
		}
//		2.绑定位置信息
		new SubReqServer().bind(port);
	}

}
