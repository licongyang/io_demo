package designmodel.media;

public abstract class AbstractMedia {
	
	protected AbstractColleague colA;
	protected AbstractColleague colB;
	
	public AbstractMedia(AbstractColleague a, AbstractColleague b){
		this.colA = a;
		this.colB = b;
	}

	public abstract void affectB();

	public abstract void affectA() ;

}
