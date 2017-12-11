package designmodel.factory.staticfactory;

import designmodel.flyweight.Car;

/**
 * 
 * @ClassName: Client 
 * @Description: 静态工厂 延迟加载，需要时new出需要的实例
 * @author lcy
 * @date 2017年11月8日 下午3:05:14    
 */

public class Client {
	public static void main(String[] args) {

		StaticFactory factory = new StaticFactory();
		Car car = factory.getCar("BMW");
		System.out.println(car.showCarName());
		Car car1 = factory.getCar("BMW");
		System.out.println(car1.showCarName());
		if (car == car1) {
			System.out.println("同一部车");
		} else {
			System.out.println("不是同一部车");
		}
	}
}
