package designmodel.observer;

public class Student implements Observer {
	
	private String teacherPhone;
	private String name;
	private Teacher teacher;
	
	public Student(String name, Teacher t){
		this.name = name;
		this.teacher = t;
	}
	
	public void show(){
		System.out.println("name:" + name + ",teacher's phone:" + teacherPhone);
	}
	
	@Override
	public void update() {
		teacherPhone = teacher.getPhone();

	}

}
