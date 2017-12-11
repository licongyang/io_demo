package netty.http.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private String url ;
	public HttpFileServerHandler(String url) {
		this.url = url;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
//		Please keep in mind that this method will be renamed to messageReceived(ChannelHandlerContext, I) in 5.0. Is called for each message of type I
		//		当服务器接收到消息时，会自动触发 messageReceived方法。该方法首先对URL进行判断，并只接受GET请求。
//		该方法首先对Http请求消息的解码结果进行判断，如果解码失败，返回400，
		if(!request.decoderResult().isSuccess()){
			sendError(ctx,HttpResponseStatus.BAD_REQUEST);
			return;
		}
//		只接受GET请求，否则返回405
		if(request.method() != HttpMethod.GET ){
			sendError(ctx,HttpResponseStatus.METHOD_NOT_ALLOWED);
			return;
		}
//		通过RandomAccessFile类打开文件，并构造响应。
		//1.获取客户端请求的路径
		final String uri = request.uri();
		final String path = sanitizeUri(uri);
//		URL进行判断
		if(path == null){
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}
		File file = new File(path);
		if(file.isHidden() || !file.exists()){
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			return;
		}
		if(file.isDirectory()){
//			如果文件是目录，则发送目录的链接给客户端浏览器
			if(uri.endsWith("/")){
				sendListing(ctx, file);
			}else{
				sendRedirect(ctx, uri + "/");
			}
			return;
		}
		//如果用户点击超链接直接打开或者下载文件
		if(!file.isFile()){
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}
		//2.RandomAccessFile以只读的形式打开
		RandomAccessFile randomAccessFile =  null;
		try {
			randomAccessFile = new RandomAccessFile(file, "r");
		}catch(FileNotFoundException exception){
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			return;
		}
		long fileLength = randomAccessFile.length();
//		3.构造成功的Http应答消息
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
//		setContentLength(response, fileLength);
		HttpHeaderUtil.setContentLength(response, fileLength);
//		在消息头中设置内容类型
		setContentTypeHeader(response, file);
//		判断是否socket连接一直保持
		if(HttpHeaderUtil.isKeepAlive(request)){
//			如果socket连接一直保持，则在应答信息头中设置连接保持socket通讯
			response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		}
//		发送应答消息到缓冲区
		ctx.write(response);
//		4.利用ChunkedFile对象将文件写到缓冲区，并存到http应答消息中，然后释放缓冲区，并刷新到客户端套接字通道
		ChannelFuture sendFileFuture = null;
		sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 8192), ctx.newProgressivePromise());
		sendFileFuture.addListener(new  ChannelProgressiveFutureListener() {
			
			@Override
			public void operationComplete(ChannelProgressiveFuture future) throws Exception {
//				如果发送完成调用此方法
				System.out.println("Transfer complete.");
			}
			
			@Override
			public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
//				Invoked when the operation has progressed.
//				progress
//					the progress of the operation so far (cumulative)
//				total
//					the number that signifies the end of the operation when progress reaches at it.
//				-1 if the end of operation is unknown.
				if(total < 0){
					System.err.println("Transfer progress: " + progress);
				}else{
					System.err.println("Transfer progress: " + progress + "/" + total);
				}
			}
		});
//		如果使用Chunked编码，最后需要发送一个编码结束的空消息体，，将LastHttpContent的EMPTY_LAST_CONTENT发送到缓冲区
//		，标识所有的应答消息已经发送完成
//		同时调用flusth方法将之前在发送缓冲区的消息刷新到SocketChannel中发送到对方
		ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		if(!HttpHeaderUtil.isKeepAlive(request)){
//			使用Keep-Alive，可以减少HTTP连接建立的次数，在HTTP1.1中该选项是默认开启的。
//			在使用Keep-Alive的情况下，当Server处理了Client的请求且生成一个response后，在response的头部添加Connection: Keep-Alive选项，把response返回给client，此时Socket连接并不会关闭。
//			【若没有Keep-Alive，一次HTTP请求响应之后，本次Socket连接就关闭了】
//			由于连接还没有关闭，当client再发送另一个请求时，就会重用这个Socket连接，直至其中一方drops the connection.
//			如果是非保持socket连接的，最后一包消息发送完成之后，服务器要主动关闭连接
			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}
	@Override 
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable caught) throws Exception{
		caught.printStackTrace();
		if(ctx.channel().isActive()){
			sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private static final Pattern INSECURE_URL = Pattern.compile(".*[<>&\"].*");
	private String sanitizeUri(String uri) {
		try {
//		首先对uri进行解码，使用UTF-8字符集
			uri = URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			try {
				uri = URLDecoder.decode(uri, "ISO-8859-1");
			} catch (Exception e2) {
				throw new Error();
			}
			e.printStackTrace();
		}
//		解码成功后对uri进行合法性检查
//		如果uri与允许的的uri一致或者是其子目录，则校验通过
		if(!uri.startsWith(url)){
			return null;
		}
		if(!uri.startsWith("/")){
			return null;
		}
//		将硬编码的文件分隔符替换成本地操作系统的文件分隔符
		uri = uri.replace('/', File.separatorChar);
//		对新的uri做二次合法性检查，失败返回空
		if(uri.contains(File.separator + '.' )
				|| uri.contains('.' + File.separator )
						|| uri.startsWith(".")
						|| uri.endsWith(".")
						|| INSECURE_URL.matcher(uri).matches()){
			return null;
		}
//		通过检查返回返回当前运行程序所在工程目录 + uri构造的绝对路径
		return System.getProperty("user.dir") + File.separator + uri;
	}
	
	private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");
	
	private static void sendListing(ChannelHandlerContext ctx, File dir){
//		首先创建成功的Http响应消息
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
//		response.headers().set("CONNECT_TYPE", "texxt/html;charset=UTF-8");
//		随后设置消息头的类型
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");

//		构造响应消息体，由于要将响应结果展示在浏览器上，所以采用html的格式
		String dirPath = dir.getPath();
		StringBuilder buf = new StringBuilder();
		
		buf.append("<!DOCTYPE html> \r\n");
		buf.append("<html><head><title>");
		buf.append(dirPath);
		buf.append("目录:");
		buf.append("</title></head><body>\r\n");
		
		buf.append("<h3>");
		buf.append(dirPath).append("目录：");
		buf.append("</h3>\r\n");
		buf.append("<ul>");
		buf.append("<li>链接： <a href=\"../\">..</a></li>\r\n");
		for(File f : dir.listFiles()){
			if(f.isHidden() || !f.exists()){
				continue;
			}
			String name = f.getName();
			if(!ALLOWED_FILE_NAME.matcher(name).matches()){
				continue;
			}
			buf.append("<li>链接： <a href=\"");
			buf.append(name);
			buf.append("\">");
			buf.append(name);
			buf.append("</a></li>\r\n");
		}
		
		buf.append("</ul></body></html>\r\n");
//		分配对应消息的缓冲对象
		ByteBuf buffer = Unpooled.copiedBuffer(buf, CharsetUtil.UTF_8);
//		将缓冲区中的响应消息存放到http应答消息中
		response.content().writeBytes(buffer);
//		释放缓冲区
		buffer.release();
//		将应答消息发送到缓冲区并刷新到SocketChannel
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	private static void sendRedirect(ChannelHandlerContext ctx, String newUri){
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
		//response.headers().set("LOCATIN", newUri);
		response.headers().set(HttpHeaderNames.LOCATION, newUri);
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status
				,Unpooled.copiedBuffer("Failure:" + status.toString() + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	private static void setContentTypeHeader(HttpResponse response, File file){
		MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
		response.headers().set(HttpHeaderNames.CONTENT_TYPE,mimetypesFileTypeMap.getContentType(file.getPath()));
	}

}
