package io.aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class SyncTimeServerHandler implements Runnable {
	
	public AsynchronousServerSocketChannel asyncServerSocketChannel;
	public CountDownLatch countdownLatch;
	
	public SyncTimeServerHandler(int port){
		//初始化资源
//		1.打开异步服务器套接字通道
		try {
			asyncServerSocketChannel = AsynchronousServerSocketChannel.open();
//		2.配置异步服务器套接字位置信息
			asyncServerSocketChannel.bind(new InetSocketAddress(port));
			System.out.println("The time server is start in port:" + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
//		3.由栅栏阻塞线程，直到所有操作处理完成
		countdownLatch = new CountDownLatch(1);
//		4.异步服务器套接字通道接受客户端连接请求
		doAcceptor();
		try {
			countdownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void doAcceptor() {
//		5.连接请求由CompletionHandler接口的实现类处理
//		传递一个CompletionHandler<AysnchronousSocketChannel,? super A>类型的handler实例
//		接受accpt操作成功的通知消息
		asyncServerSocketChannel.accept(this, new AcceptorCompletionHandler());;
	}

}
