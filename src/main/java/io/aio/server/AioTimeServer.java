package io.aio.server;

/**
 * 
* @ClassName: AioTimeServer 
* @Description: AIO 异步非阻塞I/O
* 支持连接、读、写处理结束后回调通知函数
* @author lcy
* @date 2017年11月24日 上午10:06:19 
*  
 */

public class AioTimeServer {

	public static void main(String[] args) {
		int port = 9999;
		if(args != null && args.length > 0){
			port = Integer.parseInt(args[0]);
		}
		//初始化监听端口
//		构建接受客户端连接处理类线程
		SyncTimeServerHandler handler = new SyncTimeServerHandler(port);
		new Thread(handler, "aio-time-server-handler").start();
	}

}
