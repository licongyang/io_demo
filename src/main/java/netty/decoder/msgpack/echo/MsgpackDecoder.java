package netty.decoder.msgpack.echo;

import java.util.List;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * 
* @ClassName: MsgpackDecoder 
* @Description: 解码器 将 缓冲区数据结构转换成POJO
* @author lcy
* @date 2017年11月28日 下午1:38:15 
*  
 */

public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
//		首先从数据包msg中获取需要解码的byte数组，
		final byte[] bytes;
		final int length = msg.readableBytes();
		bytes = new byte[length];
		msg.getBytes(msg.readerIndex(), bytes, 0, length);
		MessagePack msgpack = new MessagePack();
//		然后调用MessagePack的read方法将其反序列化为Object对象
//		并解码后的对象加入到解码列表中
		out.add(msgpack.read(bytes));
	}

}
