package io.bio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * 
* @ClassName: TimeServerHandler 
* @Description: 服务器端接受客户端连接请求后，处理客户端请求信息并根据请求指令返回时间信息或错误信息
* @author lcy
* @date 2017年11月22日 上午9:59:11 
*  
 */

public class TimeServerHandler implements Runnable {
	
	private Socket socket;
	
	public TimeServerHandler(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			//从客户端套接字中获取请求信息
			reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			//服务端响应信息打印到控制台并返回客户端的套接字
			writer = new PrintWriter(this.socket.getOutputStream(), true);
			String requestOrder = null;
			String response = null;
			while(true){
				requestOrder = reader.readLine();
				if(requestOrder == null){
					break;
				}
				System.out.println("client request order :" + requestOrder);
				response = "current time".equalsIgnoreCase(requestOrder) ? new Date(System.currentTimeMillis()).toString() : "bad order!";
				writer.println(response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{//关闭输入流、输出流及网络套接字
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(writer != null){
				writer.close();
			}
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				socket = null;
			}
		}
	}

}
