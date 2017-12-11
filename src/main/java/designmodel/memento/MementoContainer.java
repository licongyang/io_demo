package designmodel.memento;

import java.util.Vector;

public class MementoContainer<T> {
	
	private int current;
	
	private Vector<T> vector;
	
	public MementoContainer(){
		current = -1;
		vector = new Vector<>();
	}

	public void setMemento(T saveMemento) {
		current++;
		vector.addElement(saveMemento);
		
	}
	
	public T getMemento(){
		if(current >=0){
			T o =  vector.get(current);
			current--;
			return o;
		}
		return null;
	}

}
