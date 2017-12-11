package designmodel.observer;

import java.util.Vector;

public class Teacher implements Subjector {
	private String phone;
	private Vector<Observer> students;
	
	public Teacher(){
		this.phone = "";
		this.students = new Vector<>();
	}
	
	@Override
	public void attache(Observer o) {
		students.add(o);
	}

	@Override
	public void remove(Observer o) {
		students.remove(o);
	}

	@Override
	public void notice() {
		for(int i = 0; i < students.size(); i++){
			students.get(i).update();
		}
	}

	/** 
	* @return phone 
	*/
	public String getPhone() {
		return phone;
	}

	/** 
	* @param phone 要设置的 phone 
	*/
	public void setPhone(String phone) {
		this.phone = phone;
		notice();
	}
	
	

}
