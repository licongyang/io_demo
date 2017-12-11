package netty.decoder.msgpack.echo;

import org.msgpack.annotation.Message;

//用MessagePack编解码，POJO类上一定要加上这个注解，否则，服务器接受不到请求信息
@Message
public class UserInfo  {

	
	private int userId;
	
	private String userName;

	/** 
	* @return userId 
	*/
	public int getUserId() {
		return userId;
	}

	/** 
	* @param userId 要设置的 userId 
	 * @return 
	*/
	public UserInfo setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	/** 
	* @return userName 
	*/
	public String getUserName() {
		return userName;
	}

	/** 
	* @param userName 要设置的 userName 
	 * @return 
	*/
	public UserInfo setUserName(String userName) {
		this.userName = userName;
		return this;
	}
	
	@Override
	public String toString(){
		return "UserInfo[id=" + userId + ",name=" + userName + "]";
	}
	
	

}
