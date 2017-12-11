package designmodel.media;

public abstract class AbstractColleague {
		
	private int number;
	
	
	

	/** 
	* @return number 
	*/
	public int getNumber() {
		return number;
	}




	/** 
	* @param number 要设置的 number 
	*/
	public void setNumber(int number) {
		this.number = number;
	}




	protected abstract void setNumber(int i, AbstractMedia media);

}
