package io.aio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ConnectionCompletionHandler implements CompletionHandler<Void, AioTimeClientHandler> {
	//这里为了方便专门传入了异步客户端套接字通道，其实回调时传入的AioTimeClientHandler的成员变量也可以获取到
	private AsynchronousSocketChannel client;

	public ConnectionCompletionHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
		client = asynchronousSocketChannel;
	}

	@Override
	public void failed(Throwable exc, AioTimeClientHandler attachment) {
		exc.printStackTrace();
		//连接出现异常，客户端栅栏打开，阻塞线程会结束
		attachment.countdownLatch.countDown();
	}

	@Override
	public void completed(Void result, AioTimeClientHandler aioTimeClientHandler) {
		//连接成功后回调通知
//		向服务器端发送请求消息
		byte[] bytes = "query time".getBytes();
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
		byteBuffer.put(bytes);
		byteBuffer.flip();
		client.write(byteBuffer, byteBuffer, 
				new CompletionHandler<Integer, ByteBuffer>(){

					@Override
					public void completed(Integer result, ByteBuffer attachment) {
						if(attachment.hasRemaining()){
							//如果向服务器发送的消息没有发送完毕，回调时重新迭代发送，直到发送完毕
							client.read(attachment, attachment, this);
						}else{
//							如果请求结束，则准备接受服务器响应并进行处理
							ByteBuffer byteBuff = ByteBuffer.allocate(1024);
							client.read(byteBuff, byteBuff, new CompletionHandler<Integer,ByteBuffer>(){

								@Override
								public void completed(Integer result, ByteBuffer attachment) {
									//从异步客户端套接字通道读取服务器响应信息成功后，调用回调通知进行处理
									attachment.flip();
									byte[] bytes = new byte[attachment.remaining()];
									attachment.get(bytes);
									try {
										String body = new String(bytes, "UTF-8");
										System.out.println("The time server said:" + body);
										//客户端调用服务端请求结束，可以放开栅栏
										aioTimeClientHandler.countdownLatch.countDown();
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
									
								}

								@Override
								public void failed(Throwable exc, ByteBuffer attachment) {
									exc.printStackTrace();
									if(client != null){
										try {
											client.close();
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								}
								
							});
						}
					}

					@Override
					public void failed(Throwable exc, ByteBuffer attachment) {
						exc.printStackTrace();
						if(client != null){
							try {
								client.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

		});
				
	}

}
