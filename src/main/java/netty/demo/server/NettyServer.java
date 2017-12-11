package netty.demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
* @ClassName: NettyServer 
* @Description: Netty中，通讯的双方建立连接后，会把数据按照ByteBuf的方式进行传输
* 例如http协议中，就是通过HttpRequestDecoder对ByteBuf数据进行处理，转换成http对象
* @author lcy
* @date 2017年11月21日 上午10:09:39 
*  
 */

public class NettyServer {
	
	private int port;

	public NettyServer(int port){
		this.port = port;
	}
	
	public void run(){
		//EventLoopGroup 是用来处理IO操作的多线程事件循环器
		//bossGroup用来接收进来的连接
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//workGroup 用来处理已经被接收的连接
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			//启动NIO服务的辅助启动类
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					ChannelPipeline pipeline = socketChannel.pipeline();
					pipeline.addLast(new SimpleServerHandler());
				}
			});
			b.option(ChannelOption.SO_BACKLOG, 128);
			b.childOption(ChannelOption.SO_KEEPALIVE, true);
			//绑定端口，开始接收进来的连接
			ChannelFuture f = b.bind(port).sync();
			//等待服务器socket关闭
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	public static void main(String[] args) {
		new NettyServer(9999).run();
	}

}
