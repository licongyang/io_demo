package netty.decoder.serializable2bytebuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class PerformTestUserInfo {

	public static void main(String[] args) {
		UserInfo user = new UserInfo();
		user.setUserId(100).setUserName("duran");
		long startTime = System.currentTimeMillis();
		new Thread(() -> {
			ByteArrayOutputStream byteArrayOutputStream = null;
			ObjectOutputStream objectOutputStream = null;
			try {
				for (int i = 0; i < 1000000; i++) {
					byteArrayOutputStream = new ByteArrayOutputStream();
					objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
					objectOutputStream.writeObject(user);
					byte[] result = byteArrayOutputStream.toByteArray();
				}
				long currentTime = System.currentTimeMillis();
				System.out.println("java 序列化对象一百万次耗时：" + (currentTime - startTime) + "/ms");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
		
		new Thread(()->{
			for(int i = 0; i < 1000000; i++){
				encode(user);
			}
			long currentTime = System.currentTimeMillis();
			System.out.println("基于ButeBuffer对对象编码一百万次，耗时" + (currentTime - startTime) + "/ms");
		}).start();
	}
	
	private static byte[] encode(UserInfo user) {
		//预分配一个1M空间的缓冲区
		ByteBuffer byteBuf = ByteBuffer.allocate(1024);
		byte[] bname = user.getUserName().getBytes();
//		Relative put method for writing an int value  (optional operation). 
//		Writes four bytes containing the given int value, in the current byte order, 
//		into this buffer at the current position, and then increments the position by four.
		byteBuf.putInt(bname.length);
		byteBuf.put(bname);
		byteBuf.putInt(user.getUserId());
		byteBuf.flip();
		bname = null;
		byte[] result = new byte[byteBuf.remaining()];
		byteBuf.get(result);
		
		return result;
	}

}
