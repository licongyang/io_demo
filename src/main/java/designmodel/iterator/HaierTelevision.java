package designmodel.iterator;

import java.util.Vector;

public class HaierTelevision implements Television<Item> {
	private Vector<Item> vector;
	public HaierTelevision(){
		vector = new Vector<>();
		vector.add(new Item("type 1"));
		vector.add(new Item("type 2"));
	}
	
	@Override
	public Iterator<Item> createIterator() {
		return new IteratorImpl<Item>(vector);
	}

}
