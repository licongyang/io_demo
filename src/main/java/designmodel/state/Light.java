package designmodel.state;

public class Light {
	
	private Color color;
	
	public Light(){
		this.color = new RedColor(this);
	}
	
	

	/** 
	* @return color 
	*/
	public Color getColor() {
		return color;
	}



	/** 
	* @param color 要设置的 color 
	*/
	public void setColor(Color color) {
		this.color = color;
	}



	public void showColor() {
		color.show();
	}

}
