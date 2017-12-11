package netty.http.xml.coding.encoder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import netty.http.xml.bean.Address;
import netty.http.xml.bean.Order;
import netty.http.xml.coding.bean.HttpXmlRequest;
import netty.http.xml.coding.bean.HttpXmlResponse;

public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {

	@Override
	protected void messageReceived(final ChannelHandlerContext ctx, HttpXmlRequest msg) throws Exception {
		//通过上一个解码器获取到客户端请求消息的中间对象msg
//		请求httprequest
		HttpRequest request = msg.getRequest();
//		请求消息
		Order order = (Order) msg.getBody();
		System.out.println("Http server receive request: " + order);
//		对请求消息进行服务处理
		dobusiness(order);
		//输出响应HttpXmlResponse
		ChannelFuture future = ctx.writeAndFlush(new HttpXmlResponse(null, order));
		//如果客户端请求socket不是要求一直保持，由服务端先关闭http连接
		if(!HttpHeaderUtil.isKeepAlive(request)){
			future.addListener(new GenericFutureListener<Future<? super Void>>() {

				@Override
				public void operationComplete(Future<? super Void> future) throws Exception {
					ctx.close();
				}
			});
		}
		
		
	}
	
	private void dobusiness(Order order) {
		order.getCustomer().setName("lili");
		Address address = order.getBillTo();
		address.setCity("北京");
		order.setBillTo(address);
		order.setShipTo(address);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if(ctx.channel().isActive()){
			sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void sendError(ChannelHandlerContext ctx, HttpResponseStatus internalServerError) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, internalServerError,
				Unpooled.copiedBuffer("失败：" + internalServerError.toString(), CharsetUtil.UTF_8));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

}
