package io.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NioTimeClientHandler implements Runnable {
	private int port;
	private String ip;
	private SocketChannel socketChannel;
	private Selector selector;
	private volatile boolean stop;
	private Set<Object> messageList = new HashSet<>();

	// 在构造方法中初始化资源
	public NioTimeClientHandler(String ip, int port) {
		this.ip = ip == null ? "127.0.0.1" : ip;
		this.port = port;
		// 1.打开客户端套接字通道
		try {
			socketChannel = SocketChannel.open();
			// 2.配置客户端套接字通道，如非阻塞，接受或发送缓冲区大小
			socketChannel.configureBlocking(false);
			// 3.打开多路复用器
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
			// 初始化失败，退出程序
			System.exit(1);

		}

	}

	public void stop() {
		this.stop = true;
	}

	@Override
	public void run() {
		// 4.客户端套接字通道连接多路复用器
		try {
			doConnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		// 6.不断轮询多路复用器上是否有就绪的事件
		while (!stop) {
			try {
				selector.select(1000);
				Set<SelectionKey> selectionkeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionkeys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						hanleEvent(key);
					} catch (Exception e) {
						if (key != null) {
							key.cancel();
							if (key.channel() != null) {
								try {
									key.channel().close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				// 如果出现异常，释放资源
				System.exit(1);
			}
		}
		// 跳出循环时，即客户端不需要服务器服务时，释放资源
		if (selector != null) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void hanleEvent(SelectionKey key) {
		if (key.isValid()) {
			// 7.如果就绪的事件是连接，则判断是否连接成功，如果连接，则在多路复用器注册读事件，并向服务器请求时间服务
			if (key.isConnectable()) {
				handleConnectable(key);
			}
			if (key.isReadable()) {
				// 如果客户端接受到了服务端的消息，则客户端套接字通道的状态时可读的
				handleReadable(key);
			}
		}

	}

	private void handleReadable(SelectionKey key) {
//		客户端套接字通道的读写操作都是异步的，如果没有可读写的数据它不会同步等待，直接返回，
//		这样I/O通信线程就可以处理其他的链路，不需要同步等待这个链路可用。
		// 8.如果就绪的事件是读事件，则将客户端套接字通道中数据保存到接受到数据缓冲区，
		// 然后将数据缓冲区的数据块解码成字节数组，然后重新编码成字符串
		// 由于无法判断服务端发送来的消息码流大小，故预分配1mb的接受缓冲区用于读取应答消息
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		SocketChannel socketChannel = (SocketChannel) key.channel();
		try {
			int num = socketChannel.read(byteBuffer);
			if (num > 0) {
				byteBuffer.flip();
				byte[] bytes = new byte[byteBuffer.remaining()];
				byteBuffer.get(bytes);
				String currtentTime = new String(bytes, "UTF-8");
				System.out.println("server said: " + currtentTime);
				this.stop = true;
			} else if (num < 0) {
				// 链路关闭
				key.cancel();
				socketChannel.close();
			} else {
				// 正常忽略
			}
			// 如果存在“半包消息”在接受缓冲区RESET,继续读取后续的报文，将解码成功的消息封装成task,投寄到业务线程池中，进行业务逻辑编排
			handleHalfPackage(byteBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleHalfPackage(ByteBuffer byteBuffer) {
		while (byteBuffer.hasRemaining()) {
			byteBuffer.mark();
			Object message = decode(byteBuffer);
			if (message == null) {
				byteBuffer.reset();
				break;
			}
			messageList.add(message);

		}
		if (!byteBuffer.hasRemaining()) {
			byteBuffer.clear();
		} else {
			byteBuffer.compact();
		}
		for (Object message : messageList) {
			handleTask(message);
		}
	}

	private void handleTask(Object message) {
		System.out.println(message);
	}

	private Object decode(ByteBuffer byteBuffer) {
		byte[] bytes = new byte[byteBuffer.remaining()];
		byteBuffer.get(bytes);
		return bytes;
	}

	private void handleConnectable(SelectionKey key) {

		SocketChannel sockChannel = (SocketChannel) key.channel();
		try {
			System.out.println("连接事件就绪处理中...");
			if (sockChannel.finishConnect()) {

				sockChannel.register(selector, SelectionKey.OP_READ);
				handleWritable(sockChannel);
			} else {
				System.exit(1);// 连接失败，程序退出
			}
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doConnect() {
//		客户端发起的连接操作是异步的，可以通过在多路复用器上注册OP_CONNECT等待后续结果，
//		不需要像之前的客户端那样被同步阻塞
		try {
			// 5.如果连接成功，则在多路复用器上注册读事件
			// ，并向服务端请求时间服务
			// 否则 客户端套接字在多路复用器上注册
			if (socketChannel.connect(new InetSocketAddress(ip, port))) {
				System.out.println("客户端已连接上服务器（返回TCP握手应答消息后，多路复用器就可以轮询到这个客户端套接字通道处于连接就绪状态），直接注册读事件，并发送请求时间服务，这里没有调用到啊");
				socketChannel.register(selector, SelectionKey.OP_READ);
				handleWritable(socketChannel);
			} else {
				System.out.println("客户端未连接上服务器（服务器未返回TCP握手应答消息，不算连接失败，），先注册连接事件");
				socketChannel.register(selector, SelectionKey.OP_CONNECT);
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (socketChannel != null) {
				try {
					socketChannel.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private void handleWritable(SocketChannel sockChannel) {
		byte[] bytes = "query time".getBytes();
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
		byteBuffer.put(bytes);
		try {
			byteBuffer.flip();
			sockChannel.write(byteBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 因为是异步发送，可能存在“半包写”的问题，（当对方处理缓慢，那么缓冲区发送窗口就会不断缩小，直到为0，则写就会被阻塞，无法继续向缓冲区写入数据，这好像是阻塞模式）
		// 通过对数据缓冲区的检查，判断是否数据发送完毕
		if (!byteBuffer.hasRemaining()) {
			System.out.println("client write to server successfully");
		}
	}

}
