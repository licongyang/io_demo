package designmodel.proxy.dynamicproxy;

import java.lang.reflect.Proxy;

import designmodel.proxy.Origin;
import designmodel.proxy.StudyInterface;

public class Client {
	public static void main(String[] args) {
		Origin student = new Origin();
		StudyInterface proxy = (StudyInterface) Proxy.newProxyInstance(student.getClass().getClassLoader(), student.getClass().getInterfaces(), new DynamicProxyStudy(student));
		proxy.study();
	}
}
