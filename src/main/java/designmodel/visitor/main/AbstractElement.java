package designmodel.visitor.main;

public abstract class AbstractElement {
	
	public abstract void doSomething();
	
	public abstract void accept(AbstractVisitor visitor);
	
}
