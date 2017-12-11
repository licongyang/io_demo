package designmodel.template;

public abstract class AbstractHermerModel {

	// 是否能够按喇叭
	protected boolean isAlerm() {
		return false;
	}

	protected abstract void start();

	protected abstract void engineBoom();

	protected abstract void stop();

	protected abstract void alerm();

	public final void run() {
		
		this.start();
		this.engineBoom();
		if (isAlerm()) {
			this.alerm();
		}
		this.stop();
	}

}
