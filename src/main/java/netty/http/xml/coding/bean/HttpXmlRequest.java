package netty.http.xml.coding.bean;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 
* @ClassName: HttpXmlRequest 
* @Description:包含两个成员变量，用于实现业务层和协议层解耦
* @author lcy
* @date 2017年12月6日 下午5:37:01 
*  
 */

public class HttpXmlRequest {
	
	
	private FullHttpRequest request;
	private Object body;
	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param request
	* @param body 
	*/
	public HttpXmlRequest(FullHttpRequest request, Object body) {
		super();
		this.request = request;
		this.body = body;
	}
	/** 
	* @return request 
	*/
	public final FullHttpRequest getRequest() {
		return request;
	}
	/** 
	* @param request 要设置的 request 
	*/
	public final void setRequest(FullHttpRequest request) {
		this.request = request;
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
