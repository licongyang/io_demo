package designmodel.adapter;

/**
 * 
* @ClassName: Adapter 
* @Description: 适配器模式的意图是将一个已存在的类/接口进行复用,将其转换/具体化成客户希望的另外的一个类/接口。
* 复用类，通过修改被复用类的方法参数复用其它类
* @author lcy
* @date 2017年11月3日 下午5:22:55 
*  
 */
public class Adapter implements TargetInterface{
	private Adaptee pt;
	
	public Adapter(){
		pt = new Adaptee();
	}
	@Override
	public long get2Power(long exp) {
		return pt.getPower(2, exp);
	}

}
