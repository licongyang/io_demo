package Initialization;

public class ConstantClass {
	static {
		System.out.println("ConstantClass init");
	}
	//final修饰的static变量在编译器就把变量放入常量池
	public static final int value = 123;
}
