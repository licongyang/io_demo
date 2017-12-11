package designmodel.media;

public class ColleagueA extends AbstractColleague {

	@Override
	protected void setNumber(int i, AbstractMedia media) {
		this.setNumber(i);
		media.affectB();
		
	}

}
