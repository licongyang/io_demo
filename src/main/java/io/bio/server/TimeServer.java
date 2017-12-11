package io.bio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
* @ClassName: TimeServer 
* @Description:Bio(同步阻塞io演示) 提供时间服务的服务器端
* 每次客户端请求服务器连接，在服务器端都会新起线程去处理客户端的请求
* 缺点就是：这种一对一请求-响应模式，当并发数较高时，比较消耗服务器端资源（线程数）
* 适合：低并发访问
* @author lcy
* @date 2017年11月22日 上午9:48:50 
*  
 */

public class TimeServer {

	public static void main(String[] args) {
		//默认端口 8080
		int port = 8080;
		ServerSocket serverSocket = null;
		//端口可以运行时传入确认
		if(args.length > 0 && args != null){
			port = Integer.parseInt(args[0]);
		}
		try {
			//初始化服务器套接字
			serverSocket = new ServerSocket(port);
			System.out.println("The time server is started in port :" + port);
			//在死循环中监听客户端的连接
			Socket socket = null;
			while(true){
				//如果没有客户端连接，则服务器端线程阻塞；直到有客户端连接，返回客户端套接字
				socket = serverSocket.accept();//注意： 这个socket 服务器端只有一个，否则服务器不能正常读取客户端信息
				//有客户端连接后，将客户端套接字作为参数由新的线程处理客户端请求并作出响应
				new Thread(new TimeServerHandler(socket)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(serverSocket != null){
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			serverSocket = null;
		}
		
	}

}
