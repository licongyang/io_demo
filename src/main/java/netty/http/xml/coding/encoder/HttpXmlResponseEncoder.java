package 
netty.http.xml.coding.encoder;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import netty.http.xml.coding.bean.HttpXmlResponse;

public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse>{

	@Override
	protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> out) throws Exception {
//		获取响应的内容
//		调用父类的方法，通过jibx将pojo转换成xml,并封装到字节数组结构对象中
		ByteBuf buf = encode0(ctx, msg.getBody());
		
		//		将http响应对象HttpResponse放到响应队列中
		FullHttpResponse response = msg.getResponse();
//		如果response在业务侧（HttpXmlServerHandler未进行添加内容），则这里进行简单必要内容添加
//		如构建一个响应对象
		
		if(response == null){
			response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
		}else{
//			如果业务侧已经构建了Http应答消息，则利用业务已有应答消息重新复制一个新的Http应答消息
//			无法重用业务侧自定义的Http应答消息的主要原因是：
//			是netty的DefaultFullHttpResponse没有提供动态设置消息体content的接口，只能在第一次构建的时候设置内容
			response = new DefaultFullHttpResponse(msg.getResponse().protocolVersion(), msg.getResponse().status(), buf); 
		}
//		设置响应对象的内容类型
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/xml");
//		设置响应对象的内容长度
		HttpHeaderUtil.setContentLength(response, buf.readableBytes());
//		将响应对象放入编码结果表out中，由后续Netty的Http编码类进行二次编码
		out.add(response);
	}

}
