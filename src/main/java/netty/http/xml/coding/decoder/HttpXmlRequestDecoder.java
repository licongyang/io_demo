package netty.http.xml.coding.decoder;

import java.util.List;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import netty.http.xml.coding.bean.HttpXmlRequest;

/**
 * 
* @ClassName: HttpXmlRequestDecoder 
* @Description: Http服务端接受到Http+xml请求消息后，需要从http消息体中获取请求码流，
* 通过jibx框架对它进行反序列化，得到请求POJO对象，然后进行结果封装，回调到业务handler对象，业务得到的是解码后的POJO对象和Http请求头
* @author lcy
* @date 2017年12月6日 下午5:39:02 
*  
 */
public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest> {
	
	protected HttpXmlRequestDecoder(Class<?> clazz) {
		this(clazz, false);
	}
	//有两个参数，分别为需要解码的对象的类型信息和是否打印Http消息体码流的码流开关，码流开关默认关闭
	public HttpXmlRequestDecoder(Class<?> clazz, boolean isPrint) {
		super(clazz, isPrint);
	}

	@Override
	public void decode(ChannelHandlerContext ctx, FullHttpRequest request, List<Object> outs) throws Exception{
//		判断是否需要进行解码，如传过来的解码结果是否正确，不对，再对消息体二次解码已经没有意义，直接返回
		if(request.decoderResult().isFailure()){
			sendError(ctx,HttpResponseStatus.BAD_REQUEST);
			return;
		}
		//这里利用父类方法，对传送过来的FullHttpRequest请求内容（xml）转换成pojo
		HttpXmlRequest  httpXmlRequest = new HttpXmlRequest(request, decode0(ctx, request.content()));
		//通过FullHttpRequest 和反序列化后的Order对象构造HttpXmlRequest实例，将它添加到解码结果列表中
		outs.add(httpXmlRequest);
	}

	private void sendError(ChannelHandlerContext ctx, HttpResponseStatus badRequest) {
//		这里没有考虑异常封装和处理，一般采用统一的异常处理机制
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,badRequest,
				Unpooled.copiedBuffer("Failture:" + badRequest.toString() + "\r\n", CharsetUtil.UTF_8 ));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
}
