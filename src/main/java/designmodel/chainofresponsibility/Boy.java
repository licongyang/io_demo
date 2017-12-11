package designmodel.chainofresponsibility;

public class Boy {
	private boolean hasCar;
	private boolean hasHouse;
	private boolean hasResponsibility;

	public Boy(boolean car, boolean house, boolean responsibility) {
		this.hasCar = car;
		this.hasHouse = house;
		this.hasResponsibility = responsibility;
	}

	/** 
	* @return hasCar 
	*/
	public boolean isHasCar() {
		return hasCar;
	}

	/** 
	* @param hasCar 要设置的 hasCar 
	*/
	public void setHasCar(boolean hasCar) {
		this.hasCar = hasCar;
	}

	/** 
	* @return hasHouse 
	*/
	public boolean isHasHouse() {
		return hasHouse;
	}

	/** 
	* @param hasHouse 要设置的 hasHouse 
	*/
	public void setHasHouse(boolean hasHouse) {
		this.hasHouse = hasHouse;
	}

	/** 
	* @return hasResponsibility 
	*/
	public boolean isHasResponsibility() {
		return hasResponsibility;
	}

	/** 
	* @param hasResponsibility 要设置的 hasResponsibility 
	*/
	public void setHasResponsibility(boolean hasResponsibility) {
		this.hasResponsibility = hasResponsibility;
	}
	
	

}
