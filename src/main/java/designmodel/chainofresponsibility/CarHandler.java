package designmodel.chainofresponsibility;

public class CarHandler implements RequestHandler {
	
	private RequestHandler handler;

	public CarHandler(RequestHandler handler) {
		System.out.println("init carhandler");
		this.handler = handler;
	}

	@Override
	public void requestHandler(Boy boy) {
		if(boy.isHasCar()){
			System.out.println("wa, has cars");
		}else{
			System.out.println("no car , look next!");
			handler.requestHandler(boy);
		}
	}

}
