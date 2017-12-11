package io.bnio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import io.bio.server.TimeServerHandler;

/**
 * 
* @ClassName: TimeServer 
* @Description: 同步阻塞io,增加线程池处理客户端请求 
* 优点：
* 采用线程池实现，避免为每个请求都创建一个线程处理避免资源耗尽。
* 由于线程池和消息队列都是有界的，因此，无论客户端并发连接数有多大，它都不会导致线程个数过于膨胀或者内存溢出，
* 缺点：
* 底层仍然是同步阻塞i/o
* @author lcy
* @date 2017年11月22日 上午11:15:51 
*  
 */

public class TimeServer {

	public static void main(String[] args) {
		int port = 8080;
		if(args != null && args.length > 0){
			port = Integer.parseInt(args[0]);
		}
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("The time server is start in port:" + port);
			Socket socket = null;
			//定义多线程池
			TimeServerHandlerExecutorPool pool = new TimeServerHandlerExecutorPool(50, 1000);
			while(true){
				socket = serverSocket.accept();
				pool.execute(new TimeServerHandler(socket));
				
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			if(serverSocket != null){
				try {
					serverSocket.close();
					System.out.println("The time server  close");
				} catch (IOException e) {
					e.printStackTrace();
				}
				serverSocket = null;
			}
		}
		
	}

}
