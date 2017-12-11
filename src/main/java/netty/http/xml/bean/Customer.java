package netty.http.xml.bean;

public class Customer {
	
	private long customerNumber;
	
	private String name;
	
	private String telephone;

	/** 
	* @return customerNumber 
	*/
	public long getCustomerNumber() {
		return customerNumber;
	}

	/** 
	* @param customerNumber 要设置的 customerNumber 
	*/
	public void setCustomerNumber(long customerNumber) {
		this.customerNumber = customerNumber;
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
		name = name;
	}

	/** 
	* @return telephone 
	*/
	public String getTelephone() {
		return telephone;
	}

	/** 
	* @param telephone 要设置的 telephone 
	*/
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Override
	public String toString(){
		return "Customer[CustomerNumber=" + customerNumber + ",name=" + name + ",telephone=" + telephone + "]" ;  
	}
	
	

}
