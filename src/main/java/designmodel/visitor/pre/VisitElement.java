package designmodel.visitor.pre;

public class VisitElement {
	public void doSomething(){
		System.out.println("do some business");
	}
	public void accept(Visitor visitor){
		visitor.visit(this);
	}
}
