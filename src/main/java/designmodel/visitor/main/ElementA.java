package designmodel.visitor.main;

public class ElementA extends AbstractElement {


	@Override
	public void doSomething() {
		System.out.println("do some businesss A");
		
	}

	@Override
	public void accept(AbstractVisitor visitor) {
		visitor.visit(this);
		
	}

}
