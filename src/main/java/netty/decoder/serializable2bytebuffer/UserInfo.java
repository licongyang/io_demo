package netty.decoder.serializable2bytebuffer;

import java.io.Serializable;

public class UserInfo implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = 1L;
	
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
