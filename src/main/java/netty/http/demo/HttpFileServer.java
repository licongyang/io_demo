package netty.http.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {
	private static final String DEFAULT_URL = "/src/main/java/netty/";
	
	public void bind(final int port, final String url){
		
//	3.创建处理客户端请求的线程池
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
//	4.创建处理客户端读写的线程池
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		try{
//	5.创建辅助启动类
		ServerBootstrap b = new ServerBootstrap();
//	6.启动类定义线程池
		b.group(bossGroup, workGroup);
//	7.启动类定义通道
		b.channel(NioServerSocketChannel.class);
//	8.启动类定义初始化客户端连接等待队列大小
		b.option(ChannelOption.SO_BACKLOG, 1024);
//	9.启动类定义处理类，包括客户端请求解码器、客户端请求消息对象合并、服务端响应编码器、避免大的码流占用内存的编码器
		b.handler(new LoggingHandler(LogLevel.INFO));
		b.childHandler(new ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
//				1.客户端请求解码器
//				它负责把字节解码成Http请求。
				pipeline.addLast("http-decoder",new HttpRequestDecoder());
//				2.请求消息合并对象为FullHttpRequest
//				它负责把多个HttpMessage组装成一个完整的Http请求或者响应。到底是组装成请求还是响应，则取决于它所处理的内容是请求的内容，还是响应的内容。这其实可以通过Inbound和Outbound来判断，对于Server端而言，在Inbound 端接收请求，在Outbound端返回响应。
//				maxContentLength the maximum length of the aggregated content. If the length of the aggregated content exceeds this value, handleOversizedMessage(ChannelHandlerContext, HttpMessage) will be called.
				pipeline.addLast("http-aggregator", new HttpObjectAggregator(65536));
//				3.服务器响应编码器
//				当Server处理完消息后，需要向Client发送响应。那么需要把响应编码成字节，再发送出去
				pipeline.addLast("http-encoder", new HttpResponseEncoder());
//				4.服务器响应输出码流防止码流过大，内存泄露
//				该通道处理器主要是为了处理大文件传输的情形。大文件传输时，需要复杂的状态管理，而ChunkedWriteHandler实现这个功能
				pipeline.addLast("http-chunked", new ChunkedWriteHandler());
//				5.接受读写码流处理器
//				自定义的通道处理器，其目的是实现文件服务器的业务逻辑。
				pipeline.addLast("fileServerHandler", new HttpFileServerHandler(url));
				
			}});
//	码流输入输出处理器
//	10.绑定位置
//		因为在Netty中所有的事件都是异步的，因此bind操作是一个异步操作，通道的关闭也是一个异步操作。因此使用ChannelFuture来作为一个 palceholder,代表操作执行之后的结果。
		ChannelFuture f = b.bind(port).sync();
		System.out.println("HTTP 文件服务器启动，地址： " + "http://localhost:"+ port + url);
		f.channel().closeFuture().sync();
//	11.监听端口
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
//	12.释放资源
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
//		2.绑定服务器位置信息
		String url = DEFAULT_URL;
		if(args.length > 1){
			url = args[1];
		}
		new HttpFileServer().bind(port, url);
	}

}
