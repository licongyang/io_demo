package io.bio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
* @ClassName: TimeClient 
* @Description: BIO(Block io) 同步阻塞io客户端
* 请求服务器提供时间服务
* 客户端连接服务器位置信息获得套接字，向服务器发送查询时间服务并读取响应，随后关闭连接，释放资源
* @author lcy
* @date 2017年11月22日 上午10:20:34 
*  
 */
public class TimeClient {

	public static void main(String[] args) {
		//服务器默认端口
		int port = 8080;
		if(args != null && args.length > 0){
			try {
				port = Integer.parseInt(args[0]);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("获取服务器端口失败");
			}
		}
		//客户端套接字
		Socket socket = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		String result = null;
		try {
			socket = new Socket("127.0.0.1", port);
			//服务器响应
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println("current time");
			System.out.println("request current time ok");
			
			result = reader.readLine();
			System.out.println("server said:" + result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.close();
				writer = null;
			}
			
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				reader = null;
			}
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				socket = null;
			}
		}
		

	}

}
