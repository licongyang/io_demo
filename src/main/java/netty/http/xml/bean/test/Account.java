package netty.http.xml.bean.test;

public class Account {
    private int id;
    private String name;
    private String email;
    private String address;
    private Birthday birthday;
    //getter、setter'
    

    @Override
    public String toString() {
        return this.id + "#" + this.name + "#" + this.email + "#" + this.address + "#" + this.birthday;
    }


	/** 
	* @return id 
	*/
	public int getId() {
		return id;
	}


	/** 
	* @param id 要设置的 id 
	*/
	public void setId(int id) {
		this.id = id;
	}


	/** 
	* @return name 
	*/
	public String getName() {
		return name;
	}


	/** 
	* @param name 要设置的 name 
	*/
	public void setName(String name) {
		this.name = name;
	}


	/** 
	* @return email 
	*/
	public String getEmail() {
		return email;
	}


	/** 
	* @param email 要设置的 email 
	*/
	public void setEmail(String email) {
		this.email = email;
	}


	/** 
	* @return address 
	*/
	public String getAddress() {
		return address;
	}


	/** 
	* @param address 要设置的 address 
	*/
	public void setAddress(String address) {
		this.address = address;
	}


	/** 
	* @return birthday 
	*/
	public Birthday getBirthday() {
		return birthday;
	}


	/** 
	* @param birthday 要设置的 birthday 
	*/
	public void setBirthday(Birthday birthday) {
		this.birthday = birthday;
	}
}
