package designmodel.singleton;

/**
 * 
* @ClassName: LazyInitializationAndNotThreadSafeSingleton 
* @Description: 延迟加载但不是线程安全的单例演示（懒汉模式）
* 延迟加载：类加载时static成员都没有实例化，而是在后续调用static类方法处理时实例化类变量
* 通过判断是否实例为空，来保证只实例化一次
* 线程不安全：存在多个线程调用getInstance()方法时，instance变量都为空情况，重复实例化，造成类实例不唯一，
* 解决方法可以加同步控制字段 Synchronized
* @author lcy
* @date 2017年11月2日 下午1:46:38 
*  
 */

public class LazyInitializationAndNotThreadSafeSingleton {
	//私有化构造方法，防止外部实例化
	private LazyInitializationAndNotThreadSafeSingleton(){};
	private static LazyInitializationAndNotThreadSafeSingleton instance;
	public static LazyInitializationAndNotThreadSafeSingleton getInstance(){
		if(instance == null){
			instance = new LazyInitializationAndNotThreadSafeSingleton();
		}
		return instance;
	}
	
}
