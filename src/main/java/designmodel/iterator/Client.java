package designmodel.iterator;

public class Client {

	public static void main(String[] args) {
		Television<Item> tv = new HaierTelevision();
		Iterator<Item> it = tv.createIterator();
		while(!it.isDone()){
			System.out.println(it.next().getName());
		}
	}

}
