package designmodel.chainofresponsibility;

public class ResponsibilityHandler implements RequestHandler {
	
	private RequestHandler handler;

	public ResponsibilityHandler(RequestHandler handler) {
		System.out.println("init responsiblityhandler");
		this.handler = handler;	
	}

	@Override
	public void requestHandler(Boy boy) {
		if(boy.isHasResponsibility()){
			System.out.println("wa ,has responsibility , done");
		}else{
			System.out.println("no responsibility, look next");
			handler.requestHandler(boy);
		}
		
	}

}
