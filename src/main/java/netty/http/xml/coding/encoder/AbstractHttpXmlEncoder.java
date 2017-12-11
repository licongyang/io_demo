package netty.http.xml.coding.encoder;


import java.io.StringWriter;
import java.nio.charset.Charset;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public abstract class AbstractHttpXmlEncoder<T> extends MessageToMessageEncoder<T> {
	
	final static String CHARSET_NAME = "UTF-8";
	final static Charset UTF_8 = Charset.forName(CHARSET_NAME);
	IBindingFactory factory = null;
	StringWriter writer = null;
	

	protected ByteBuf encode0(ChannelHandlerContext ctx, Object msg) throws Exception {
		String xmlString = null;
		writer = new StringWriter();
		factory = BindingDirectory.getFactory(msg.getClass());
		IMarshallingContext context = factory.createMarshallingContext();
		context.setIndent(2);
		context.marshalDocument(msg, CHARSET_NAME, null, writer);
		xmlString = writer.toString();
		writer.close();
		writer = null;
		ByteBuf buf = Unpooled.copiedBuffer(xmlString, UTF_8);
		return buf;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(writer != null){
			writer.close();
			writer = null;
		}
	}

}
