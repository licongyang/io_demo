package designmodel.singleton;

/**
 * 
 * @ClassName: LazyInitializationHoldClassSingleton 
 * @Description: 延迟加载并且线程安全的单例（懒汉模式？）
 *               延迟加载:通过类级内部类（带static）中实例化，保证只有调用类级内部类时才会触发实例化
 *               线程安全：通过在类级内部类中给外部类变量带static属性，保证只初始化一次，从而达到线程安全
 * @author lcy
 * @date 2017年11月2日 下午2:08:41    
 */
public class LazyInitializationHoldClassSingleton {
	public String name = "kaka";
	// 私有化构造方法，防止外部调用
	private LazyInitializationHoldClassSingleton() {
	};

	// 通过private防止外部调用，通过static属性保证可以有类static变量
	private static class InnerHoldClass {
		static {
			System.out.println("InnerHoldClass init");
		}
		
		// 通过static保证初始化一次
		private static LazyInitializationHoldClassSingleton instance = new LazyInitializationHoldClassSingleton();
//		private static String hello(){
//			return "hello";
//		}
	}

	public static LazyInitializationHoldClassSingleton getInstance() {
		return InnerHoldClass.instance;
	}
	
	public static void hello(){
		System.out.println("hello outword");
	}

	public static void main(String[] args) {
		//没有调用内部类，就不会触发内部类的初始化
		LazyInitializationHoldClassSingleton.hello();
//		System.out.println(LazyInitializationHoldClassSingleton.InnerHoldClass.hello());
		
		System.out.println(LazyInitializationHoldClassSingleton.InnerHoldClass.instance);
		//内部类只会初始化一次
		System.out.println(LazyInitializationHoldClassSingleton.InnerHoldClass.instance);
	}

}
