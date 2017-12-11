package io.aio.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer>{

	private AsynchronousSocketChannel asynchousSocketChannel;
	
	public ReadCompletionHandler(AsynchronousSocketChannel ayschronousSocketChannel){
		asynchousSocketChannel = ayschronousSocketChannel;
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		exc.printStackTrace();
		if (asynchousSocketChannel != null) {
			try {
				asynchousSocketChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		attachment.flip();
		// 3.将缓冲区数据编码成字节数组
		byte[] bytes = new byte[attachment.remaining()];
		// 4.如果请求信息时请求时间服务，则响应当前时间
		attachment.get(bytes);
		try {
			String body = new String(bytes, "utf-8");
			System.out.println("The time server receives order :" + body);
			String currrentTime = "query time".equalsIgnoreCase(body)
					? new Date(System.currentTimeMillis()).toString() : "bad query";
			// 5.将当前时间写到异步客户端套接字通道中，并在写完回调函数中处理未写完情况，通过不断递归回调完成全部响应信息输出
			handleWritable(currrentTime);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	private void handleWritable(String currentTime) {
		if (currentTime != null && currentTime.trim().length() > 0) {
			// 6.将响应信息封装到缓冲区中
			ByteBuffer byteBuffer = ByteBuffer.allocate(currentTime.length());
			byte[] bytes = currentTime.getBytes();
			byteBuffer.put(bytes);
			byteBuffer.flip();
			asynchousSocketChannel.write(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {

				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					if (attachment.hasRemaining()) {
						//如果没有发送完，继续发送
						asynchousSocketChannel.write(attachment, attachment, this);
					}
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					exc.printStackTrace();
					if (asynchousSocketChannel != null) {
						try {
							asynchousSocketChannel.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			});
		}

	}

}
