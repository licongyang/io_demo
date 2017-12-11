package designmodel.memento;


/**
 * 
* @ClassName: Client 
* @Description: 备忘录模式
* 意图是在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，这样以后就可以将对象恢复到原先保存的状态。
* 实例如下:
*	有一个对象Employee.除了属性外,还需要一个保存,还原状态的方法.
*	有一个对象Memento,用来记录Employee每一个时刻的状态,
*	CareTaker,用来保存,拿回Memento.需要一个保存,还原状态的方法.->需要一个指针,一个容器.
* @author lcy
* @date 2017年11月8日 下午4:39:03 
*  
 */

public class Client {

	public static void main(String[] args) {
		Employee employee = new Employee("lili",26);
		employee.show();
		MementoContainer<Memento> container = new MementoContainer<>();
		container.setMemento(employee.saveMemento());
		employee.setName("other");
		container.setMemento(employee.saveMemento());
		employee.show();
		employee.setAge(30);
		container.setMemento(employee.saveMemento());
		employee.show();
		employee.restoreMemento(container.getMemento());
		employee.show();
		employee.restoreMemento(container.getMemento());
		employee.show();
		employee.restoreMemento(container.getMemento());
		employee.show();
		
		
	}

}
