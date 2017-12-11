package designmodel.memento;

public class Memento {
	private String name;
	private int age;

	public Memento(String name, int age) {
		this.name = name;
		this.age = age;
	}

	/**
	 *  
	 * 
	 * @return name 
	 */
	public String getName() {
		return name;
	}

	/**
	 *  
	 * 
	 * @param name
	 *            要设置的 name 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *  
	 * 
	 * @return age 
	 */
	public int getAge() {
		return age;
	}

	/**
	 *  
	 * 
	 * @param age
	 *            要设置的 age 
	 */
	public void setAge(int age) {
		this.age = age;
	}

}
