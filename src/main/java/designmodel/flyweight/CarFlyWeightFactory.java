package designmodel.flyweight;

import java.util.HashMap;

public class CarFlyWeightFactory {
	private Car car;
	private HashMap<String, Car> map = new HashMap<>();

	public Car getCar(String name) {
		if(name.equals("BMW")){
			car = map.get(name);
			if(car == null){
				car = new BMWCar();
				map.put(name, car);
			}
		}
		if(name.equals("FORD")){
			car = map.get(name);
			if(car == null){
				map.put(name, car);
			}
		}
		return car;
	}
	
	public int getNumber(){
		return map.size();
	}

}
