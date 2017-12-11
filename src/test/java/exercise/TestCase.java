package exercise;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

public class TestCase {
	@Test
	public void copyJarTest(){
		String jarPath = "d:/test/test1.txt";
		String destPath ="d:/test/copy.txt";
		File jarFile = new File(jarPath);
		
		InputStream inStream = null;
		OutputStream outStream = null;
		
		try {
			inStream = new FileInputStream(jarFile);
			outStream = new FileOutputStream(destPath);
			byte[] b = new byte[2048];
			int length;
			while((length = inStream.read()) > 0){
				outStream.write(b, 0, length);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				inStream.close();
				outStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
}
