package designmodel.media;

/**
 * 
* @ClassName: Client 
* @Description: 中介者模式
* 同事者类对象间相互影响依赖，为了降低对象间耦合度，通过中介者封装对象间影响关系，将网状关系变成星状关系
* 意图： 一般来说，同事类之间的关系是比较复杂的，多个同事类之间互相关联时，他们之间的关系会呈现为复杂的网状结构，这是一种过度耦合的架构，即不利于类的复用，也不稳定
* 中介者模式的结构
*      中介者模式又称为调停者模式，共分为3部分：
*	抽象中介者：定义好同事类对象到中介者对象的接口，用于各个同事类之间的通信。一般包括一个或几个抽象的事件方法，并由子类去实现。
*	中介者实现类：从抽象中介者继承而来，实现抽象中介者中定义的事件方法。从一个同事类接收消息，然后通过消息影响其他同时类。
*	同事类：如果一个对象会影响其他的对象，同时也会被其他对象影响，那么这两个对象称为同事类。在类图中，同事类只有一个，这其实是现实的省略，在实际应用中，同事类一般由多个组成，他们之间相互影响，相互依赖。同事类越多，关系越复杂。并且，同事类也可以表现为继承了同一个抽象类的一组实现组成。在中介者模式中，同事类之间必须通过中介者才能进行消息传递。
*	例子来说明一下什么是同事类：有两个类A和B，类中各有一个数字，并且要保证类B中的数字永远是类A中数字的100倍。也就是说，当修改类A的数时，将这个数字乘以100赋给类B，而修改类B时，要将数除以100赋给类A。类A类B互相影响，就称为同事类
* @author lcy
* @date 2017年11月13日 下午1:53:01 
*  
 */
public class Client {

	public static void main(String[] args) {
		AbstractColleague colA = new ColleagueA();
		AbstractColleague colB = new ColleagueB();
		
		AbstractMedia media = new Media(colA, colB);
		
		System.out.println("======通过设置A影响B");
		colA.setNumber(100, media);
		System.out.println("A的值：" + colA.getNumber());
		System.out.println("B的值：" + colB.getNumber());
		
		System.out.println("=====通过设置B影响A");
		colB.setNumber(900,media);
		System.out.println("A的值：" + colA.getNumber());
		System.out.println("B的值：" + colB.getNumber());
		
	}

}
