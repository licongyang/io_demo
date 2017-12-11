package netty.http.xml.coding.decoder;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import netty.http.xml.coding.bean.HttpXmlResponse;

public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<DefaultFullHttpResponse>{
	
	protected HttpXmlResponseDecoder(Class<?> clazz) {
		this(clazz, false);
	}
	
	public HttpXmlResponseDecoder(Class<?> clazz, boolean isPrint) {
		super(clazz, isPrint);
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, DefaultFullHttpResponse msg, List<Object> out) throws Exception {
//		通过DefaultFullHttpResponse 和Http应答消息反序列化后的POJO对象构造HttpXmlResponse
		HttpXmlResponse res = new HttpXmlResponse(msg, decode0(ctx, msg.content()));
//		添加到解码结果表中
		out.add(res);
	}

}
