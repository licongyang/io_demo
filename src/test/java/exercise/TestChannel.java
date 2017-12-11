package exercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class TestChannel {
	@Test
//	数据从一个channel读取到多个buffer中
//	Scattering Reads在移动下一个buffer前，必须填满当前的buffer，
//	这也意味着它不适用于动态消息(译者注：消息大小不固定)。
//	换句话说，如果存在消息头和消息体，消息头必须完成填充（例如 128byte），Scattering Reads才能正常工作。 

	public void testScatter(){
		RandomAccessFile randomAccessFile;
		try {
			randomAccessFile = new RandomAccessFile("src/main/resources/data/nio-data.txt", "rw");
			FileChannel channel = randomAccessFile.getChannel();
			System.out.println("file size:" + channel.size());
			ByteBuffer header = ByteBuffer.allocate(128);
			ByteBuffer body   = ByteBuffer.allocate(1024);
			
			ByteBuffer[] bufferArray = { header, body };
			
			channel.read(bufferArray);
			System.out.println(header.position());
			System.out.println(body.position());
			header.flip();
			body.flip();
			System.out.println("header:");
			while(header.hasRemaining()){
				System.out.print((char)header.get());
			}
			System.out.println("body:");
			while(body.hasRemaining()){
				System.out.print((char)body.get());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGather() throws IOException{
		ByteBuffer header = ByteBuffer.allocate(8);
		ByteBuffer body = ByteBuffer.allocate(12);
		header.putInt(1);
		header.putInt(2);
		body.putInt(11);
		body.putInt(12);
		body.putInt(13);
		header.flip();
		body.flip();
		System.out.println("header postion:" + header.position());
		System.out.println("header limit:" + header.limit());
		ByteBuffer[] buffers = {header, body};
		System.out.println(buffers.toString());
		
		File file = new File("src/main/resources/data/gather.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		FileOutputStream outStream = null;
		FileChannel fileChannel = null;
		try {
			outStream = new FileOutputStream(file);
			fileChannel = outStream.getChannel();
			fileChannel.write(buffers);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTransFrom(){
		RandomAccessFile fromFile;
		try {
			fromFile = new RandomAccessFile("src/main/resources/data/fromFile.txt", "rw");
			FileChannel      fromChannel = fromFile.getChannel();
			RandomAccessFile toFile = new RandomAccessFile("src/main/resources/data/toFile.txt", "rw");
			FileChannel      toChannel = toFile.getChannel();
			
			long position = 0;
			long count = fromChannel.size();
			
			toChannel.transferFrom(fromChannel,  position, count);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
