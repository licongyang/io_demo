package netty.decoder.msgpack.echo;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
* @ClassName: MsgpackEncoder 
* @Description: 编码器
* Netty集成MessagePack编解码器
* @author lcy
* @date 2017年11月28日 上午11:47:32 
*  
 */

public class MsgpackEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		MessagePack msgpack = new MessagePack();
		//负责将POJO对象编码为字节数组，然后写入ByteBuffer
		byte[] bytes = msgpack.write(msg);
		out.writeBytes(bytes);
	}

}
