package designmodel.proxy.staticproxy;

import designmodel.proxy.Origin;
import designmodel.proxy.StudyInterface;

public class ProxyStudy implements StudyInterface {
	private Origin student;
	public ProxyStudy(Origin student){
		this.student = student;
	}
	@Override
	public void study() {
		priview();
		student.study();
		review();
		
	}
	private void review() {
		System.out.println("priview ...");
	}
	private void priview() {
		System.out.println("review ...");
	}

}
