package io.nio.server;

/**
 * 
* @ClassName: NioTimeServer 
* @Description: NIO(Non-block io)同步非阻塞
* 单独一个线程处理客户端的连接请求，通过多路复用器轮询就绪的客户端通道，对就绪客户端通道事件做出处理
* @author lcy
* @date 2017年11月22日 下午4:41:36 
*  
 */

public class NioTimeServer {

	public static void main(String[] args) {
		//初始化服务端监听端口
		int port = 9999;
		String ip = "127.0.0.1";
		if(args != null && args.length > 0){
			ip = args[0];
			port = Integer.parseInt(args[0]);
			
		}
//		定义一个Runnable实现类来处理客户端连接请求
		NioTimeServerHandler handler = new NioTimeServerHandler(ip,port);
//		新建一个线程，并启动上面的Acceptors梳理
		new Thread(handler, "nio-server-handler").start();
	}

}
