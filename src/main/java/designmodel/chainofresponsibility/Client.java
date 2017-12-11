package designmodel.chainofresponsibility;

/**
 * 
* @ClassName: Client 
* @Description: 责任链
* 为了避免请求的发送者和接收者之间的耦合关系，使多个接受对象都有机会处理请求。
* 将这些对象连成一条链，并沿着这条链传递该请求，直到有一个对象处理它为止。
* 要沿着链转发请求，并保证接受者为隐式的，每个链上的对象都有一致的处理请求和访问链上后继者的接口
* (即如下实例中,在自己方法中再调用一次相同的方法)。
* 
* 如何实例使请求沿着链在各接受对象中传递,当没被第一个接受对象接受时,会传递给第二个对象,若被第一个对象接受了,则不传递下去:
*	1.各具体的接受对象采用这样的构造方法:
*	public CarHandler(Handler handler) { this.handler = handler; }
*	2.各具体的接受对象实现接口的方法handleRequest()中.在调用时,若被接受,则执行true的内容,若不被接受,则执行false的内容,并继续调用再调用handleRequest()方法.
*	3.在最后的测试类中,生成具体的handler时,用多层包含的形式.这样,在调用了上一层car的方法后,会调用house的相应方法,最后再调用ResponsibilityHandler的方法.
*有点类似状态模式(内部状态映射某种欣伟，且这个状态值可以不断循环) -- 责任链模式（接受者和请求者的关系，请求的多个要求对象形成链，请求在链上逐一验证接受者，直至接受者满足请求（必须有个满足））
* @author lcy
* @date 2017年11月8日 下午3:23:58 
*  
 */

public class Client {

	public static void main(String[] args) {
		//被接受者
		Boy boy = new Boy(false, false, true);
//		Boy boy = new Boy(false, false, false);//最后responsibilityhandler（这是个空值）请求男孩会报错
		//责任链
		RequestHandler handler = new CarHandler(new HouseHandler(new ResponsibilityHandler(null)));
		
		//请求
		handler.requestHandler(boy);
	}

}
