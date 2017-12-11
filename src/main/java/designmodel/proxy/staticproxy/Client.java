package designmodel.proxy.staticproxy;

import designmodel.proxy.Origin;
import designmodel.proxy.StudyInterface;

/**
 * 
* @ClassName: Client 
* @Description: 代理可以对原有逻辑添加其它逻辑，生成新的逻辑
* 静态代理: 
*-->一个原类与一个代理类要一一对应。
*-->两者都实现共同的接口或继承相同的抽象类；
*-->只是在代理类中,实例化原类，在原类方法的前后添加新的逻辑。 
* @author lcy
* @date 2017年11月3日 下午5:47:57 
*  
 */
public class Client {

	public static void main(String[] args) {
		Origin student = new Origin();
		StudyInterface proxy = new ProxyStudy(student);
		proxy.study();
		
		
	}

}
