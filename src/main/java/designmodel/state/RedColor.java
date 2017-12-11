package designmodel.state;

public class RedColor implements Color {
	private Light light;
	
	public RedColor(Light light) {
		this.light = light;
	}

	@Override
	public void show() {
		System.out.println("the color is red, the car must stop");
		System.out.println("do otherthing");
		light.setColor(new GreenColor(light));

	}

}
