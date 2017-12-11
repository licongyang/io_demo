package designmodel.iterator;

public interface Iterator<T> {
	public T first();
	public T next();
	public T current();
	public boolean isDone();
}
