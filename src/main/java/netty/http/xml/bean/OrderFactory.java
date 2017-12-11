package netty.http.xml.bean;

public class OrderFactory {

	public static Order create(int i) {
		Order o = new Order();
		o.setOrderNumber(i);
		Customer customer = new Customer();
		customer.setCustomerNumber(i);
		customer.setName("duran");
		customer.setTelephone("183");
		o.setCustomer(customer);
		Address billTo = new Address();
		billTo.setStreet1("龙眠大道");
		billTo.setCity("南京市");
		billTo.setState("江苏省");
		billTo.setPostCode("123321");
		billTo.setCountry("中国");
		o.setBillTo(billTo);
		o.setShipping(Shipping.INTERNATIONAL_MAIL);
		o.setShipTo(billTo);
		o.setTotal((float) 9999.999);
		return o;
	}

}
