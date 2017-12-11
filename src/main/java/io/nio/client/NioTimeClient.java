package io.nio.client;

public class NioTimeClient {

	public static void main(String[] args) {
		//初始化端口
		int port = 9999;
		String ip = "127.0.0.1";
		if(args != null && args.length > 0){
			port = Integer.parseInt(args[0]);
		}
		NioTimeClientHandler handler = new NioTimeClientHandler(ip,port);
//		启动一个新线程进行创建套接字通道和多路复用器，并将套接字通道的连接事件注册到多路复用器上，并向服务端发送查询时间请求；
//		然后不断轮询多路复用器就绪事件，对于连接时间，注册读事件或者重新注册；对于读事件，获取服务器端响应数据
		new Thread(handler, "nio-time-client-handler").start();
		
	}

}
