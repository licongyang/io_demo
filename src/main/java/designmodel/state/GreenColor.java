package designmodel.state;


public class GreenColor implements Color {
	private Light light;
	
	public GreenColor(Light light){
		this.light = light;
	}
	@Override
	public void show() {
		System.out.println("the color is green, the car can run");
		System.out.println("do otherthing");
		light.setColor(new YellowColor(light));
	}

}
