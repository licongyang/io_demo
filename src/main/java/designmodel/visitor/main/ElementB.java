package designmodel.visitor.main;

public class ElementB extends AbstractElement {


	@Override
	public void doSomething() {
		System.out.println("do some businesss B");
		
	}

	@Override
	public void accept(AbstractVisitor visitor) {
		visitor.visit(this);
		
	}

}
