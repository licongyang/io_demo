package io.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NioTimeServerHandler implements Runnable {

	private ServerSocketChannel serverSocketChannel;

	private Selector selector ;

	private volatile boolean stop;

	public NioTimeServerHandler(String ip, int port) {
		// 初始化资源
		// 1.打开服务端通道
		try {
			serverSocketChannel = ServerSocketChannel.open();
			// 2.服务端通道绑定位置地址ip + port
			// 非阻塞模式
			serverSocketChannel.configureBlocking(false);
			// endpoint The IP address and port number to bind to.
			// backlog requested maximum length of the queue of incoming
			// connections
			serverSocketChannel.socket().bind(new InetSocketAddress(ip,port), 1024);
			// 也可以设置发送或者接受的数据块（Buffer）大小
			// 3.创建多路复用器 保持所有客户端对应一个相应的多路复用器
//			Selector selector = Selector.open();//全局selector为null
			 selector = Selector.open();
			// 4.将服务器通道注册在多路复用器上，并监听连接事件
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("The time server is start in port: " + port);
		} catch (IOException e) {
			e.printStackTrace();
			// 连接失败系统退出
			System.exit(1);
		}
	}

	public void Stop() {
		this.stop = true;
	}

	@Override
	public void run() {
		// 5.在循环中多路复用器不断轮询就绪通道事件
		while (!stop) {
			// 每一秒线程唤醒一次，查看是否有就绪的事件
			try {
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				SelectionKey key = null;
				while (iterator.hasNext()) {
					key = iterator.next();//将多路复用器中的各通道就绪事件存储临时变量中
					iterator.remove();//将多路复用器中的各通道就绪事件移除，防止重复处理
					try {
						handleEvent(key);
					} catch (Exception e) {
						if (key != null) {
							key.cancel();
							if (key.channel() != null) {
								key.channel().close();
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 6.处理就绪事件
		// 多路复用器关闭后，所有注册上面的Channel或者Pipe自动关闭
		if (selector != null) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void handleEvent(SelectionKey key) throws IOException {
		if (key.isValid()) {
			// 6.对于连接就绪事件，创建客户端通道
			if (key.isAcceptable()) {
				// 接受一个新连接
				handleAcceptable(key);
			}

			// 9.对于读就绪事件，异步通过通道获取数据，并进行解码，进行业务处理，最后将客户端通道注册到多路复用器上，监听写就绪事件

			if (key.isReadable()) {
				handleReadable(key);
			}
			// 10.对于写就绪事件，将返回信息解码后存入响应数据输出
			// 服务器端对于客户端读请求后，直接响应数据返回
			// if(key.isWritable()){
			// handleWritable(key);
			// }

		}
	}

	private void handleAcceptable(SelectionKey key) {
		ServerSocketChannel servChannel = (ServerSocketChannel) key.channel();
		try {
			SocketChannel socketChannel = servChannel.accept();
			// 7.客户端通道配置
			// 配置客户端通道
			socketChannel.configureBlocking(false);
			// 8.将客户端通道注册到多路复用器上，并监听客户端通道就绪读事件
			//可以对TCP参数进行设置，例如TCP接受和发送缓冲区的大小等
			socketChannel.register(selector, SelectionKey.OP_READ);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void handleWritable(SocketChannel channel, String response) {
		// 注意写是异步处理，如果内容没有完全写入缓冲区，要对缓冲区进行标记移位处理，保证未写入的内容不会丢失
		// 主要是写完后数据，循环判断是否还有（Bytebuf），如果解码没有数据，则复位退出，否则读取数据放到业务队列处理。
		// 然后再判断数据是否有遗留，有compact处理，否则clear。
		// 最后在队列中遗留的数据处理
		if (response != null && response.trim().length() > 0) {

			byte[] bytes = response.getBytes();
			ByteBuffer byteBuffer = ByteBuffer.allocate(response.length());
			byteBuffer.put(bytes);
			// Buffer写完之后要flip，将limit置成position，position置成0
			byteBuffer.flip();
			try {
				channel.write(byteBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private void handleReadable(SelectionKey key) throws IOException {
		// 注意读时异步处理，对于读取的内容要根据情况分别处理
		// 读取到内容 >0 ，业务处理，并考虑读取半包的问题
		// 没有读取内容 =0 正常忽略
		// 读取内容 < 0 ,读取结束，释放资源
		// 获取客户端通道
		SocketChannel socketChannel = (SocketChannel) key.channel();
		// 通过客户端通道获取数据块，由于无法事先得知客户端发送的码流大小，这里开辟1MB的缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//		try {
			//调用客户端通道read方法读取请求码流
			int num = socketChannel.read(byteBuffer);
			if (num > 0) {
				// 将缓冲区的数据解码到解码到1M的字节数组中
				//写完Buffer，记得flip
				//Flips this buffer. 
//				The limit is set to the current position and then the position is set to zero. If the mark is defined then it is discarded

				byteBuffer.flip();
//				Returns the number of elements between the current position and the limit.
				byte[] bytes = new byte[byteBuffer.remaining()];
				byteBuffer.get(bytes);
				String body = new String(bytes, "utf-8");
				System.out.println("client said:" + body);
				String currentTime = "query time".equalsIgnoreCase(body)
						? new Date(System.currentTimeMillis()).toString() : "bad query";
				handleWritable(socketChannel, currentTime);
			}else if(num == 0){
				//读取到0字节，忽略
			}else {
				//读取结束,释放资源
				key.cancel();
				socketChannel.close();
			}
		} 
	//客户端关闭后，依然会调用本方法，此时如果直接以打印堆栈的形式处理报错，则客户端套接字通道和多路复用器依然无法关闭，造成服务器不断强制关闭客户端进程
	
//		catch (IOException e) {
//			e.printStackTrace();
//		}

//	}
}
