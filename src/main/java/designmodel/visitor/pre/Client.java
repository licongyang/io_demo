package designmodel.visitor.pre;


/**
 * 
* @ClassName: Client 
* @Description: 元素类与访问者直接作用展示访问者模式面对的问题
* @author lcy
* @date 2017年11月13日 下午3:45:21 
*  
 */

public class Client {

	public static void main(String[] args) {
		VisitElement e = new VisitElement();
		e.doSomething();
		e.accept(new Visitor());
	}

}
