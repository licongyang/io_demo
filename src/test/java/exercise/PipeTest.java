package exercise;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

import org.junit.Test;

public class PipeTest {
	
	Pipe pipe = null;
//	要向管道写数据，需要访问sink通道
	@Test
	public void testSink(){
		
		//准备缓冲区，并写入数据及写出数据初始化（flip）
		String str = "this is a log";
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.put(str.getBytes());
		buf.flip();
		
		//初始化Pipe
		try {
			pipe = Pipe.open();
			Pipe.SinkChannel sinkChannel = pipe.sink();
			while(buf.hasRemaining()){
				sinkChannel.write(buf);
			}
			//缓存区即使写完数据到channel后，position==limit（与位置无关，应该是跟内容有关）,照样可以将缓冲区的内容输出出来
			System.out.println(new String(buf.array(), "utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	从读取管道的数据，需要访问source通道
	@Test
	public void testSource(){
		
	}
}
