package designmodel.chainofresponsibility;

public class HouseHandler implements RequestHandler {
	
	private RequestHandler handler;

	public HouseHandler(RequestHandler handler) {
		System.out.println("init househandler");
		this.handler = handler;
	}

	@Override
	public void requestHandler(Boy boy) {
		if(boy.isHasHouse()){
			System.out.println("wa ,has houses , done");
		}else{
			System.out.println("no house, look next");
			handler.requestHandler(boy);
		}
	}

}
