package designmodel.flyweight;

/**
 * 
* @ClassName: Client 
* @Description: 享元模式
* 利用对象存储池实现创建对象共享
* 一个系统中如果有多个相同的对象，那么只共享一份就可以了，不必每个都去实例化一个对象
* 减少内存占用，提高程序效率
* 新建对象时:
* 先到hashtable中进行获取-->判断取得对象是否为空-->若是,则新建此对象,且放回hashtable -->若存在,则共享原来的对象
* @author lcy
* @date 2017年11月8日 下午1:49:04 
*  
 */

public class Client {

	public static void main(String[] args) {
		CarFlyWeightFactory factory = new CarFlyWeightFactory();
		Car c1 = factory.getCar("BMW");
		System.out.println(c1.showCarName());
		Car c2 = factory.getCar("BMW");
		System.out.println(c2.showCarName());
		if(c1 == c2){
			System.out.println("取出的是同一部车");
		}else{
			System.out.println("不是同一部车");
		}
		System.out.println(factory.getNumber());
	}

}
