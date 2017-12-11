package designmodel.iterator;

import java.util.Vector;

public class IteratorImpl<T> implements Iterator<T> {
	private int current = 0;
	Vector<T> vector;
	
	public IteratorImpl(Vector<T> v){
		this.vector = v;
	}

	@Override
	public T first() {
		current = 0;
		return vector.get(current);
	}

	@Override
	public T next() {
		current++;
		return vector.get(current);
	}

	@Override
	public T current() {
		return vector.get(current);
	}

	@Override
	public boolean isDone() {
		return current >= vector.size() - 1;
	}

}
