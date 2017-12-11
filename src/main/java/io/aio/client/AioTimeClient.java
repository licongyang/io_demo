package io.aio.client;

public class AioTimeClient {

	public static void main(String[] args) {
		//初始化资源
		int port = 9999;
		String ip = "127.0.0.1";
		if(args != null && args.length > 0){
			port = Integer.parseInt(args[0]);
		}
		//创建线程
//		新建一个线程处理异步非阻塞连接和请求、读取响应处理
		new Thread(new AioTimeClientHandler(ip,port)).start();
	}

}
