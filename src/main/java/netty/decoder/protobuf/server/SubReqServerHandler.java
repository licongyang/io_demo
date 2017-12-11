package netty.decoder.protobuf.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.decoder.protobuf.SubscribeReqProto;
import netty.decoder.protobuf.SubscribeRespProto;

public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)msg;
		if("duran".equalsIgnoreCase(req.getUserName())){
			System.out.println("Server accept client subscribe req : [" + req.toString() + "]");
			ctx.writeAndFlush(resp(req.getSubReqID()));
		}
	}

	private SubscribeRespProto.SubscribeResp resp(int subReqID) {
		SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
		builder.setSubReqId(subReqID);
		builder.setRespCode(0);
		builder.setDesc("Netty book order succeed, 3 days later, send to zhe designed address");
		return builder.build();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
