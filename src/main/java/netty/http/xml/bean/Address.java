package netty.http.xml.bean;

public class Address {

//	First line of street information(required).
	private String street1;
//	Second line of street information(optional).
	private String street2;
	private String city;
//	State abbreviation(required).
	private String state;
//	Postal code (required).
	private String postCode;
	private String country;
	/** 
	* @return street1 
	*/
	public String getStreet1() {
		return street1;
	}
	/** 
	* @param street1 要设置的 street1 
	*/
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	/** 
	* @return street2 
	*/
	public String getStreet2() {
		return street2;
	}
	/** 
	* @param street2 要设置的 street2 
	*/
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	/** 
	* @return city 
	*/
	public String getCity() {
		return city;
	}
	/** 
	* @param city 要设置的 city 
	*/
	public void setCity(String city) {
		this.city = city;
	}
	/** 
	* @return state 
	*/
	public String getState() {
		return state;
	}
	/** 
	* @param state 要设置的 state 
	*/
	public void setState(String state) {
		this.state = state;
	}
	/** 
	* @return postCode 
	*/
	public String getPostCode() {
		return postCode;
	}
	/** 
	* @param postCode 要设置的 postCode 
	*/
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/** 
	* @return country 
	*/
	public String getCountry() {
		return country;
	}
	/** 
	* @param country 要设置的 country 
	*/
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString(){
		return "Address[street1=" + street1 + ",street2=" + street2
				+ ",city=" + city + ",state=" + state
				+ ",postCode=" + postCode + ",count=" + country + "]";
	}
}
