package designmodel.state;


public class YellowColor implements Color {
	private Light light ;
	
	public YellowColor(Light light){
		this.light = light;
	}
	@Override
	public void show() {
		System.out.println("the color is yellow, the car is ready");
		System.out.println("do otherthing");
		light.setColor(new RedColor(light));
	}

}
