package io.aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptorCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, SyncTimeServerHandler> {

	@Override
	public void failed(Throwable exc, SyncTimeServerHandler attachment) {
		exc.printStackTrace();
		//接受客户端连接失败，主线程放开栅栏结束线程
		attachment.countdownLatch.countDown();
	}

	@Override
	public void completed(AsynchronousSocketChannel result, SyncTimeServerHandler attachment) {
		// 1.在接受客户端成功后的回调函数，接受新的客户端请求
//		每当接受一个客户连接成功后，再异步接受新的客户端连接
		attachment.asyncServerSocketChannel.accept(attachment, this);
		//客户端接受成功，物理链路建立成功
		// 2.接受的客户端套接字通道中读取请求信息到缓冲区,预设1MB
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		//用一个CompletionHandler<Integer, ? super A>的handler实例接受客户读成功后通知回调业务
		result.read(byteBuffer, byteBuffer, new ReadCompletionHandler(result));
	}

}
