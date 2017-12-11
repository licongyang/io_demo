package designmodel.observer;

public interface Subjector {
	public void attache(Observer o);
	public void remove(Observer o);
	public void notice();
}
