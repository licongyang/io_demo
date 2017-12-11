package Initialization;
/**
 * 
* @ClassName: NotInitialization2 
* @Description: 此处不会引起SuperClass的初始化，但是却触发了【[Ltest.SuperClass】的初始化
* 这不是一个合法的类名称，它是由虚拟机自动生成的，直接继承于Object的子类，
* 创建动作由字节码指令newarray触发,此时数组越界检查也会伴随数组对象的所有调用过程，越界检查并不是封装在数组元素访问的类中，
* 而是封装在数组访问的xaload,xastore字节码指令中
* @author lcy
* @date 2017年11月2日 下午2:36:55 
*  
 */
public class NotInitialization2 {

	public static void main(String[] args) {
		SuperClass[] arr = new SuperClass[10];
		System.out.println(arr.toString());
	}

}
