package designmodel.media;

public class ColleagueB extends AbstractColleague {

	@Override
	protected void setNumber(int i, AbstractMedia media) {
		this.setNumber(i);
		media.affectA();
	}

}
