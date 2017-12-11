package designmodel.visitor.main;

public class Visitor implements AbstractVisitor{

	@Override
	public void visit(AbstractElement element) {
//		System.out.println("init");
		element.doSomething();
//		System.out.println("end");
	}

}
