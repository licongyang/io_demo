package designmodel.template;

public class ConcreteHermerModel extends AbstractHermerModel {
	
	private boolean alermFlag = true;
	
	public void setAlermFlag(boolean flag){
		this.alermFlag = flag;
	}
	
	@Override
	public boolean isAlerm(){
		return alermFlag;
	}
	
	@Override
	protected void start() {
		System.out.println("start...");
	}

	@Override
	protected void engineBoom() {
		System.out.println("engineBoom...");
	}

	@Override
	protected void stop() {
		System.out.println("stop...");
	}

	@Override
	protected void alerm() {
		System.out.println("alerm...");
	}

}
