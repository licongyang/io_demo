package netty.decoder.msgpack.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	
	private int sendNumber;
	
	public EchoClientHandler(int sendNumber){
		this.sendNumber = sendNumber;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		这里没有考虑TCP粘包拆包问题
		UserInfo[] infos = getuserInfos();
		for(UserInfo userInfo : infos){
			ctx.write(userInfo);
		}
		//这样服务端只能接受一个对象
		ctx.flush();
		//这样服务器可以接受全部的对象
//		连接后触发这个方法，这时客户端要将请求信息写入网络套接字的通道（flush），以便服务端接受
//		ctx.writeAndFlush(getuserInfos());
	}

	private UserInfo[] getuserInfos() {
		UserInfo[] infos = new UserInfo[sendNumber];
		UserInfo info = null;
		for(int i = 0; i < sendNumber; i++){
			info = new UserInfo();
			info.setUserId(i).setUserName("ABCDEFG-->" + i);
			infos[i] = info;
		}
		
		return infos;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("client receive the msgpack message :" + msg);
//		下面这个会造成客户端接受服务端响应后，重新请求，然后接受响应，再请求的循环
//		ctx.write(msg);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
