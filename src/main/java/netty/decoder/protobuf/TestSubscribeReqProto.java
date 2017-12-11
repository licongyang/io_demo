package netty.decoder.protobuf;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
* @ClassName: TestSubscribeReqProto 
* @Description: 根据proto自动生成代码，进行对象的编码解码 
* @author lcy
* @date 2017年11月29日 下午1:39:32 
*  
 */
public class TestSubscribeReqProto {

	public static SubscribeReqProto.SubscribeReq createSubscribeReq(){
		SubscribeReqProto.SubscribeReq.Builder builder
		= SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(1);
		builder.setUserName("duran");
		builder.setProductName("Netty");
		List<String> address = new ArrayList<>();
		address.add("beijing");
		address.add("tianjin");
		builder.addAllAddress(address);
		return builder.build();
	}
	
	private static byte[] encode(SubscribeReqProto.SubscribeReq req){
		return req.toByteArray();
	}
	
	private static SubscribeReqProto.SubscribeReq decode(byte[] bytes) throws InvalidProtocolBufferException{
		return SubscribeReqProto.SubscribeReq.parseFrom(bytes);
	}
	public static void main(String[] args) throws InvalidProtocolBufferException {
//		1.生成请求消息对象
		SubscribeReqProto.SubscribeReq req = createSubscribeReq();
		System.out.println("before encode: " + req.toString());
//		2.将生成的请求消息对象编码和解码得到新对象
		SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
		System.out.println("after decode:" + req2);
//		3.判断两对象是否相同
		System.out.println("Assert equal:" + req2.equals(req));
	}

}
