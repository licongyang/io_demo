package io.nio.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class NioTest {

	public static void main(String[] args) {
		
		//Java.nio.charset.Charset处理了字符转换问题。它通过构造CharsetEncoder和CharsetDecoder将字符序列转换成字节和逆转换
//		Charset charset =Charset.forName("UTF-8");
//		CharsetDecoder decoder = charset.newDecoder();
		RandomAccessFile aFile = null;
		ByteBuffer byteBuffer = ByteBuffer.allocate(48);
//		CharBuffer charBuffer = CharBuffer.allocate(48);
		try {
			aFile = new RandomAccessFile("src/main/resources/data/nio-data.txt", "rw");
			FileChannel inChannel = aFile.getChannel();
			int bytesRead = inChannel.read(byteBuffer);
			while (bytesRead != -1) {
				
				System.out.println("Read " + bytesRead);
				byteBuffer.flip();
//				decoder.decode(byteBuffer, charBuffer,false);
//				charBuffer.flip();
//				System.out.println(charBuffer);
				while (byteBuffer.hasRemaining()) {
					System.out.print((char) byteBuffer.get());
//					System.out.println(charBuffer);
				}
				
				byteBuffer.clear();
//				charBuffer.clear();
				bytesRead = inChannel.read(byteBuffer);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(aFile != null){
					aFile.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

}
