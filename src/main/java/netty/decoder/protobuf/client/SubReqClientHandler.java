package netty.decoder.protobuf.client;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.decoder.protobuf.SubscribeReqProto;

public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		客户端连接服务端后调用此方法，向服务端请求服务
		for(int i = 0; i < 10; i++){
			ctx.write(subReq(i));
		}
		//将所有请求缓存后，再全部写入网络套接字通道
		ctx.flush();
	}

	private SubscribeReqProto.SubscribeReq subReq(int i) {
		 SubscribeReqProto.SubscribeReq.Builder builder =  SubscribeReqProto.SubscribeReq.newBuilder();
		 builder.setSubReqID(i);
		 builder.setProductName("Netty");
		 builder.setUserName("duran");
		 List<String> lists = new ArrayList<>();
		 lists.add("beijing");
		 lists.add("tianjin");
		 lists.add("shanghai");
		 lists.add("guangzhou");
		 builder.addAllAddress(lists);
		 return builder.build();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable caught) throws Exception {
		caught.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("client receive server response : [" + msg + "]");
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelRegistered();
	}
}
