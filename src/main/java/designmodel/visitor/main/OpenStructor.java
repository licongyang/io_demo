package designmodel.visitor.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OpenStructor {

	public static List<AbstractElement> getElement() {
		List<AbstractElement> elements = new ArrayList<>();
		Random r = new Random();
		for(int i = 0; i < 10; i++){
			int a = r.nextInt(100);
			if(a < 50){
				elements.add(new ElementA());
			}else{
				elements.add(new ElementB());
			}
		}
		return elements;
	}

}
