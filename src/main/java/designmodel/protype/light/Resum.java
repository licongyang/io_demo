package designmodel.protype.light;

public class Resum implements Cloneable {
	
	private String name;
	private String sex;
	private String age;
	private WorkExperience workExperience;
	
	public Resum(){
		super();
		workExperience = new WorkExperience();
	}
	public void setPersonInfo(String name, String sex, String age) {
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public void setWorkExperience(String timeArea, String company) {
		workExperience.setTimeArea(timeArea);
		workExperience.setCompany(company);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	@Override
	public String toString(){
		return "个人信息：\n" + name + "\n" + sex + "\n" + age +  "\n" 
				+ "工作经验：\n"  + workExperience.getTimeArea() + "\n" + workExperience.getCompany() + "\n";
		
	}

}
