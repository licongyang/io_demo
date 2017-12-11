package designmodel.factory.staticfactory;

import designmodel.flyweight.BMWCar;
import designmodel.flyweight.Car;
import designmodel.flyweight.FordCar;

public class StaticFactory {
	private static Car car;
	
	public Car getCar(String name) {
		if ("BMW".equals(name)) {
			car = new BMWCar();
		}
		if ("FORD".equals(name)) {
			car = new FordCar();
		}
		return car;
	}
}
