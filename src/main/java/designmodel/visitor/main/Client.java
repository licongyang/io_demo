package designmodel.visitor.main;

import java.util.List;

/**
 * 
* @ClassName: Client 
* @Description: 访问者模式
* 意图：对于对象中存在着一些与本对象不相干或者相对弱的操作，为了避免这些操作污染这个对象，可以使用访问者模式将这些操作封装到访问者中去
* 容器类中某种数据结构包含不同的元素，这些元素有不同的业务逻辑，通过增加访问者依赖来统一的访问不同元素的业务逻辑
* 访问者模式的优点
*	符合单一职责原则：凡是适用访问者模式的场景中，元素类中需要封装在访问者中的操作必定是与元素类本身关系不大且是易变的操作，使用访问者模式一方面符合单一职责原则，另一方面，因为被封装的操作通常来说都是易变的，所以当发生变化时，就可以在不改变元素类本身的前提下，实现对变化部分的扩展。
*	扩展性良好：元素类可以通过接受不同的访问者来实现对不同操作的扩展。
*   相对于pre介绍的访问者模式，这个抽象度更高，
*   开闭原则中的里氏替换（父类出现的地方都可以替换成子类）、依赖倒置（高层模块不应该依赖底层模块）、
*   接口隔离、单一职责、
*   迪米特原则（只与直接朋友（成员变量、方法参数、方法返回值）通信）
* @author lcy
* @date 2017年11月13日 下午3:51:57 
*  
 */

public class Client {

	public static void main(String[] args) {
//		从容器类取出某种结构的所有元素
		List<AbstractElement> elements = OpenStructor.getElement();
		for(AbstractElement e : elements){
			e.accept(new Visitor());
		}
 	}

}
