package designmodel.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxyStudy implements InvocationHandler {
	private Object object;
	public DynamicProxyStudy(Object o){
		this.object = o;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		preview();
		method.invoke(object, args);
		review();
		return null;
	}
	private void review() {
		System.out.println("review ...");
	}
	private void preview() {
		System.out.println("preview ...");
	}

}
