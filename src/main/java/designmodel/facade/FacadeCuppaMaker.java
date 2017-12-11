package designmodel.facade;
/**
 * 
* @ClassName: FacadeCuppaMaker 
* @Description: 外观模式属于结构型模式，Facade模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
*	外观模式的主要用途就是为子系统的复杂处理过程提供方便的调用方法，使得子系统更加容易被使用。
*	-->将复杂的过程包含在里面,提供一个简单的应用接口即可.
* @author lcy
* @date 2017年11月3日 下午5:15:15 
*  
 */
public class FacadeCuppaMaker implements FacadeIterator<TeaCup> {
	private boolean TeaBaglsSteeped;

	public FacadeCuppaMaker() {
		System.out.println("FacadeCuppaMaker ready...");
	}

	@Override
	public TeaCup makeACuppa() {
		TeaCup cup = new TeaCup();
		TeaBag teaBag = new TeaBag();
		Water water = new Water();
		cup.addFacadeTeaBag(teaBag);
		water.boilFacadeWater();
		cup.addFacadeWater();
		cup.steepTeaBag();
		return cup;
	}

}
