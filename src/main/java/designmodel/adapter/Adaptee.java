package designmodel.adapter;

//被复用的类
public class Adaptee{
	public long getPower(long base, long exp){
		long result = 1;
		for(int i = 0; i < exp; i++){
			result *= base;
		}
		//tijiao
		return result;
	}
}