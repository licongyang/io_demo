package shiro;

public class ReFormAuthenticationFilter 
//extends FormAuthenticationFilter 
{
	public static void main(String[] args) {
		String s = "http://wuliu.damaov40.cn/wuliu/damao/logout;Jession=33-0add";
		System.out.println(s.replaceAll("/logout.*", "/login"));
	}
}
