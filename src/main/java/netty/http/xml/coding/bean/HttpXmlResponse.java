package netty.http.xml.coding.bean;

import io.netty.handler.codec.http.FullHttpResponse;

public class HttpXmlResponse {
	
	private FullHttpResponse response;
	//业务需要发送的应答POJO对象
	private Object body;
	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param response
	* @param body 
	*/
	public HttpXmlResponse(FullHttpResponse response, Object body) {
		this.response = response;
		this.body = body;
	}
	/** 
	* @return response 
	*/
	public final FullHttpResponse getResponse() {
		return response;
	}
	/** 
	* @param response 要设置的 response 
	*/
	public final void setResponse(FullHttpResponse response) {
		this.response = response;
	}
	/** 
	* @return body 
	*/
	public final Object getBody() {
		return body;
	}
	/** 
	* @param body 要设置的 body 
	*/
	public final void setBody(Object body) {
		this.body = body;
	}
	
	

}
