package netty.http.xml.bean;

public class Order {
	private long orderNumber;
	private Customer customer;
	
//	Billing address information.
	private Address billTo;
	
	private Shipping shipping;
	
//	Shipping address information. If missing, the billing address is also
//	used as the shipping address.
	private Address shipTo;
	
	private Float total;

	/** 
	* @return orderNumber 
	*/
	public long getOrderNumber() {
		return orderNumber;
	}

	/** 
	* @param orderNumber 要设置的 orderNumber 
	*/
	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	/** 
	* @return customer 
	*/
	public Customer getCustomer() {
		return customer;
	}

	/** 
	* @param customer 要设置的 customer 
	*/
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/** 
	* @return billTo 
	*/
	public Address getBillTo() {
		return billTo;
	}

	/** 
	* @param billTo 要设置的 billTo 
	*/
	public void setBillTo(Address billTo) {
		this.billTo = billTo;
	}

	/** 
	* @return shipping 
	*/
	public Shipping getShipping() {
		return shipping;
	}

	/** 
	* @param shipping 要设置的 shipping 
	*/
	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	/** 
	* @return shipTo 
	*/
	public Address getShipTo() {
		return shipTo;
	}

	/** 
	* @param shipTo 要设置的 shipTo 
	*/
	public void setShipTo(Address shipTo) {
		this.shipTo = shipTo;
	}

	/** 
	* @return total 
	*/
	public Float getTotal() {
		return total;
	}

	/** 
	* @param total 要设置的 total 
	*/
	public void setTotal(Float total) {
		this.total = total;
	}

	@Override
	public String toString(){
		return "Order [orderNumber=" + orderNumber + ", customer=" + customer  
		        + ", billTo=" + billTo + ", shipping=" + shipping.toString()  
		        + ", shipTo=" + shipTo + ", total=" + total + "]";  
		    }  ;
	

}
