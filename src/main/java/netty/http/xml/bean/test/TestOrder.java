package netty.http.xml.bean.test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import netty.http.xml.bean.Order;
import netty.http.xml.bean.OrderFactory;

public class TestOrder {
	private IBindingFactory factory;
	private StringWriter writer;
	private StringReader reader;
	private static final String CHARSET_NAME = "UTF-8";
	
	private Order decoder2Order(String body) {
		reader = new StringReader(body);
		Order result = null;
		try {
			IUnmarshallingContext context = factory.createUnmarshallingContext();
			result = (Order) context.unmarshalDocument(reader);
		} catch (JiBXException e) {
			e.printStackTrace();
		}
		return result;
		
	}

	private String encoder2Xml(Order order) {
		String result = null;
		try {
			factory = BindingDirectory.getFactory(Order.class);
			writer = new StringWriter();
			IMarshallingContext context = factory.createMarshallingContext();
			context.setIndent(2);
			context.marshalDocument(order, CHARSET_NAME, null, writer);
			result = writer.toString();
			writer.close();
			System.out.println(result.toString());
		} catch (JiBXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		TestOrder test  = new TestOrder();
		Order order = OrderFactory.create(123);
		String body = test.encoder2Xml(order);
		Order order2 = test.decoder2Order(body);
		System.out.println(order.equals(order2));
		System.out.println(order2);
	}

	

}
