package netty.decoder.serializable2bytebuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * 
* @ClassName: TestUserInfo 
* @Description: 本示例主要说明jdk自带的序列化码流大
* 这里是进行编解码框架的比较，
* 针对java序列化是根据对象输入ObjectInputStream\输出流ObjectOutputStream，实现将java对象转换成字节数组进行存储或者网络传输
* 判断一个优秀的编解码框架有以下五点：
* 1.跨语言，支持多种语言
* 2.码流大小，码流越大，需要的带宽越大，占用的存储空间越大
* 3.性能
* 4.类库是否小巧，api使用是否方便
* 5.手工开发工作量和难度
* @author lcy
* @date 2017年11月28日 上午9:18:13 
*  
 */

public class TestUserInfo {

	public static void main(String[] args) throws IOException {
		//1.java对象
		UserInfo user = new UserInfo();
		user.setUserId(100).setUserName("duran");
//		2.java序列化成字节数组
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//		3.输出序列化后的字节数组长度
		objectOutputStream.writeObject(user);
		objectOutputStream.flush();
		objectOutputStream.close();
		byte[] b = byteArrayOutputStream.toByteArray();
		System.out.println("java 序列化后字节数组长度：" + b.length );
//		4.用基于ByteBuffer的通用二进制编解码技术对java对象进行编码后字节数组长度
		int num = encode(user).length;
		System.out.println("基于ByteBuffer编解码技术，对象对应的字节数组长度：" + num);
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
