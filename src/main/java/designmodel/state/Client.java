package designmodel.state;

/**
 * 
* @ClassName: Client 
* @Description: 状态模式
* 适用于内部状态，不断循环变化，每种状态对应一种行为
* 结构包括：一个对象 + 对象的内部属性（属性接口 + 具体属性）
* 一个对象： 包含 属性接口 、属性接口的set \get ,以及初始化 和状态显示(属性接口调用显示状态的行为)
* 属性接口：包含一个可执行方法
* 具体属性：属性接口的实现
* @author lcy
* @date 2017年11月6日 上午11:17:22 
*  
 */

public class Client {

	public static void main(String[] args) {
		Light line = new Light();
		line.showColor();
		line.showColor();
		line.showColor();
		line.showColor();

	}

}
