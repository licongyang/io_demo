package designmodel.protype.deep;

public class Client {
	public static void main(String[] args) throws CloneNotSupportedException {
		
	Resum resum = new Resum();
	resum.setPersonInfo("lili","男", "26");
	resum.setWorkExperience("2年", "pinan");
	Resum copy = (Resum) resum.clone();
	copy.setWorkExperience("3", "damao");
	System.out.println(resum);
	System.out.println(copy);
	
	}
}
