package netty.http.xml.coding.decoder;

import java.io.StringReader;
import java.nio.charset.Charset;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public abstract class AbstractHttpXmlDecoder<T> extends MessageToMessageDecoder<T> {
	
	private Class<?> clazz;
	private boolean isPrint;
	private IBindingFactory factory ;
	private StringReader reader ;
	private static final String CHARSET_NAME = "UTF-8";
	private static final Charset UTF_8   = Charset.forName(CHARSET_NAME);
	
	protected AbstractHttpXmlDecoder(Class<?> clazz){
		this(clazz,false);
	}
	
	protected AbstractHttpXmlDecoder(Class<?> clazz, boolean isPrint){
		this.clazz = clazz;
		this.isPrint = isPrint;
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(reader != null){
			reader.close();
			reader = null;
		}
	}

	protected Object decode0(ChannelHandlerContext ctx, ByteBuf buf ) throws Exception {
//		从Http的消息体中获取请求码流，然后通过jibx类库将xml转换成POJO对象
		factory = BindingDirectory.getFactory(clazz);
		String body = buf.toString(UTF_8);
		if(isPrint){
			System.out.println("The body is :" + body);
		}
		reader = new StringReader(body);
		IUnmarshallingContext context = factory.createUnmarshallingContext();
		Object result = context.unmarshalDocument(reader);
		reader.close();
		reader = null;
	//jibx将xml转成pojo
		return result;
	}
}
