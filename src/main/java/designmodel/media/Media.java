package designmodel.media;

public class Media extends AbstractMedia {

	public Media(AbstractColleague colA, AbstractColleague colB) {
		super(colA, colB);
	}

	@Override
	public void affectB() {
		int num = colA.getNumber();
		colB.setNumber(num * 100);
	}

	@Override
	public void affectA() {
		int num = colB.getNumber();
		colA.setNumber(num / 100);
	}

}
