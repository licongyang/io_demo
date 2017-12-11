package designmodel.memento;

public class Employee {
	
	private String name;
	private int age;
	

	public Employee(String s, int a) {
		this.name = s;
		this.age = a;
	}
	
	
	/** 
	* @return name 
	*/
	public String getName() {
		return name;
	}


	/** 
	* @param name 要设置的 name 
	*/
	public void setName(String name) {
		this.name = name;
	}


	/** 
	* @return age 
	*/
	public int getAge() {
		return age;
	}


	/** 
	* @param age 要设置的 age 
	*/
	public void setAge(int age) {
		this.age = age;
	}


	public void show() {
		System.out.println("-------------------");
		System.out.println("name:" + name);
		System.out.println("age:" + age);
		System.out.println("-------------------");
	}

	public Memento saveMemento() {
		Memento memento = new Memento(name,age);
		return memento;
	}
	
	public void restoreMemento(Memento memento){
		this.name = memento.getName();
		this.age = memento.getAge();
	}

}
