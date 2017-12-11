package designmodel.observer;

import java.util.Vector;

public class Client {

	public static void main(String[] args) {
		Vector<Observer> students = new Vector<>();
		Teacher teacher = new Teacher();
		for(int i = 0; i < 10; i++){
			Student stu = new Student("name" + i, teacher);
			students.add(stu);
			teacher.attache(stu);
		}
		teacher.setPhone("15346");
		for(int i = 0; i < 10; i++){
			((Student)students.get(i)).show();
		}
		teacher.setPhone("27891");
		for(int i = 0; i < 10; i++){
			((Student)students.get(i)).show();
		}
		
		
	}

}
