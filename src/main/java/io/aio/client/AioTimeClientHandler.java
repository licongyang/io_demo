package io.aio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AioTimeClientHandler implements Runnable {
	
	private int port;
	private String ip;
	public CountDownLatch countdownLatch;
	public AsynchronousSocketChannel asynchronousSocketChannel;
	//1.在线程构造函数中初始化异步客户端套接字通道
	public AioTimeClientHandler(String ip, int port){
		this.ip = ip == null ? "127.0.0.1" : ip;
		this.port = port;
		try {
			asynchronousSocketChannel = AsynchronousSocketChannel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
//		2.设置栅栏阻塞该线程，
		countdownLatch = new CountDownLatch(1);
//		3.连接操作
		handleConnection();
		try {
			countdownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {//调用结束关闭异步客户端套接字通道
			asynchronousSocketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleConnection() {
//		4.在连接成功后调用请求回调通知，发送请求信息到缓冲区，通过异步客户端套接字通道发送出去
		asynchronousSocketChannel.connect(new InetSocketAddress(ip, port), this,
				new ConnectionCompletionHandler(asynchronousSocketChannel));
//		5.请求发送成功后调用响应处理回调通知，将客户端套接字通道中服务端响应读取到缓冲区去，并获取到字节数组中
//		6.栅栏直到客户端连接服务器等一系列操作完成，释放该线程	
	}

}
