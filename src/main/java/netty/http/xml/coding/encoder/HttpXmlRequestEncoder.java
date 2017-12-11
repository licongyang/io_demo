package netty.http.xml.coding.encoder;

import java.net.InetAddress;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import netty.http.xml.coding.bean.HttpXmlRequest;

public class HttpXmlRequestEncoder extends AbstractHttpXmlEncoder<HttpXmlRequest> {
	
	/**
	 * 对封装请求消息的中间对象再次编码，将请求消息对象Order序列化成xml，并保存在字节数组结构ByteBuf对象中
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, HttpXmlRequest msg, List<Object> out) throws Exception {
//		调用父类方法，通过jibx将业务需要发送的pojo对象Order实例序列化成xml字符串，随后将它封装成Netty的ByteBuf
		
		ByteBuf buf = encode0(ctx, msg.getBody());
//		获取中间对象的请求消息，
		FullHttpRequest request = msg.getRequest();
//		如果业务侧自定义和定制了消息头，则使用业务侧设置的Http消息头，如果业务侧没有设置，则构造新的Http消息头
		if(request == null){
//			这里如果业务侧（HttpXmlClinetHandler）没有添加，则这里新增一个
//			起码要将请求消息结构对象buf保存到http传送的request中
			request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/do",  buf);
//			然后设置接受格式、接受编码、接受字符集、接受语言、连接
//			由于Http通信双方更关注消息体本身，这里采用了硬编码，要产品化，可以做成xml配置文件，允许业务自定义配置，以提升定制的灵活性
			HttpHeaders headers = request.headers();
//			169.254.132.46
			headers.set(HttpHeaderNames.HOST, InetAddress.getLocalHost().getHostAddress());
			headers.set(HttpHeaderNames.CONNECTION,HttpHeaderValues.CLOSE );
			headers.set(HttpHeaderNames.ACCEPT_ENCODING,HttpHeaderValues.GZIP.toString() + ","
					+ HttpHeaderValues.DEFLATE.toString());
			headers.set(HttpHeaderNames.ACCEPT_CHARSET,"ISO-8859-1,utf-8;q=0.7,*/*;q=0.7");
			headers.set(HttpHeaderNames.ACCEPT_LANGUAGE,"zh");
			headers.set(HttpHeaderNames.USER_AGENT,"Netty xml Http Client side");
			headers.set(HttpHeaderNames.ACCEPT,"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		}
//		设置消息头中消息体长度
//		由于请求消息消息体不为空，也没有使用Chunk方式，所以在Http消息头中设置消息体的长度Content-Length
		
		HttpHeaderUtil.setContentLength(request, buf.readableBytes());
		
//		完成消息体的Xml序列化后，将重新构造的Http请求消息加入到out中
		out.add(request);
		
	}

}
