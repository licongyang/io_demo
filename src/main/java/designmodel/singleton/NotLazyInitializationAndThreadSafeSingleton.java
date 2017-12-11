package designmodel.singleton;

	/**
	 * 
	* @ClassName: NotLazyInitializationAndThreadSafe 
	* @Description: 线程安全但加载时实例化的单例演示（饿汉单例模式）
	* 1.线程安全，在static变量上jvm默认是执行同步处理的，且只会在类加载时初始化一次
	* 2.非延迟加载，在static类变量初始化时就实例化了
	* @author lcy
	* @date 2017年11月2日 下午1:34:18 
	*  
	 */

public class NotLazyInitializationAndThreadSafeSingleton {
//	私有化构造方法，防止外部实例化
	private NotLazyInitializationAndThreadSafeSingleton(){}
//	定义static的类变量并实例化
	private static NotLazyInitializationAndThreadSafeSingleton instance = new NotLazyInitializationAndThreadSafeSingleton();
//	static定义类方法获取类实例
	public static NotLazyInitializationAndThreadSafeSingleton getInstance(){
		return instance;
	}
	
	public static void hello(){
		System.out.println("hello!");
	}
	public static void main(String[] args) {
		NotLazyInitializationAndThreadSafeSingleton.hello();
		//单例类在加载后就实例化了
		//designmodel.singleton.NotLazyInitializationAndThreadSafe@15db9742
		System.out.println(NotLazyInitializationAndThreadSafeSingleton.instance);
	}
}
