package netty.http.xml.coding.encoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.http.xml.bean.OrderFactory;
import netty.http.xml.coding.bean.HttpXmlRequest;
import netty.http.xml.coding.bean.HttpXmlResponse;

public class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse> {
	/**
	 * 对于上层业务层，构造订购请求消息后，以Http+xml协议将消息发送给服务端，
	 * 如果要实现对业务零侵入或者尽可能少的侵入，协议层和应用层应该解耦
	 * 
	 * 考虑到http+xml协议栈需要一定的定制扩展能力，例如通过http消息头携带业务自定义字段，
	 * 所以应该允许业务利用Netty的http协议栈接口自行构造私有的HTTP协议头
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//客户端连接服务端，建立http连接后，调用此方法，
//		客户端向服务端发送请求消息，这里请求消息（订单对象被封装成包括http消息头和订单对象的中间对象HttpXmlRequest）
//		将中间对象写入网络通道以备再次编码
		HttpXmlRequest request = new HttpXmlRequest(null, OrderFactory.create(123));
		ctx.writeAndFlush(request);
	
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
//		服务器响应信息response
	System.out.println("The client receive response of http head is :" + msg.getResponse().headers().names());	
//		服务器响应信息body
	System.out.println("The client receive response of http body is " + msg.getBody());
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}

}
