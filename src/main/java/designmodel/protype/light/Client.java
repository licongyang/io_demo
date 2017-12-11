package designmodel.protype.light;

/**
 * 
* @ClassName: Client 
* @Description: 原型模式
* 对于实现Cloneable接口的clone方法的类对象，可以通过重写克隆方法，实现对象的拷贝。
* 重写时，只是简单的调用父类（Object）的clone方法，则会简单的复制类对象的基本类型变量（八种基本类型和String）.
* 如果被拷贝的类对象中有引用或者集合、数组、map等，需要特殊处理复制，完成深克隆。
* 注意：使用原型模式复制对象不会调用类的构造方法。
* 因为对象的复制是通过调用Object类的clone方法来完成的，它直接在内存中复制数据，因此不会调用到类的构造方法。
* 不但构造方法中的代码不会执行，甚至连访问权限都对原型模式无效。还记得单例模式吗？
* 单例模式中，只要将构造方法的访问权限设置为private型，就可以实现单例。
* 但是clone方法直接无视构造方法的权限，所以，单例模式与原型模式是冲突的，在使用时要特别注意。
* Prototype类需要具备以下两个条件：
实现Cloneable接口。
	在java语言有一个Cloneable接口，它的作用只有一个，
	就是在运行时通知虚拟机可以安全地在实现了此接口的类上使用clone方法。
	在java虚拟机中，只有实现了这个接口的类才可以被拷贝，否则在运行时会抛出CloneNotSupportedException异常。
重写Object类中的clone方法。
	Java中，所有类的父类都是Object类，Object类中有一个clone方法，作用是返回对象的一个拷贝，
	但是其作用域protected类型的，一般的类无法调用，因此，Prototype类需要将clone方法的作用域修改为public类型。
* @author lcy
* @date 2017年11月13日 上午9:31:21 
*  
 */

public class Client {
	public static void main(String[] args) throws CloneNotSupportedException {
		
	Resum resum = new Resum();
	resum.setPersonInfo("lili","男", "26");
	resum.setWorkExperience("2年", "pinan");
	Resum copy = (Resum) resum.clone();
	copy.setWorkExperience("3", "damao");
	System.out.println(resum);
	System.out.println(copy);
	System.out.println("\u7cfb\u5217:60\u7cfb\u5217");
	
	}
}
