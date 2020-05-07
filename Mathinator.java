public class Mathinator {
	Detectors d = new Detectors();
	public String mult(String in) {
		String out = "";
		int s = 0;
		String a1 = "";
		String a2="";
		for(int i =0;i<in.length();i++) {
			if(in.charAt(i)=='*') {
				for(int j =i-1;j>=0;j--) {
					if(d.isOperator(in.charAt(j))) {
						break;
					}
					a1+= in.charAt(j);
					in=in.substring(0,j)+in.substring(j+1);
					i--;
				}
				a1=reverter(a1);
				for(int j=i+1;j<in.length();j+=0) {
					if(d.isOperator(in.charAt(j))) {
						break;
					}
					a2+=in.charAt(j);
					in=in.substring(j);
					i--;
				}
				double a = Double.parseDouble(a1);
				double b = Double.parseDouble(a2);
				out = (a*b)+"";
				in = in.substring(0,s)+out+in.substring(s+1);
				if(i<0) {
					i=0;
				}
				System.out.println(in);
			}
		}
		return in;
	}
	public String div(String in) {
		String out = "";
		int s = 0;
		String a1 = "";
		String a2="";
		for(int i =0;i<in.length();i++) {
			if(in.charAt(i)=='/') {
				for(int j =i-1;j>=0;j--) {
					if(d.isOperator(in.charAt(j))) {
						break;
					}
					a1+= in.charAt(j);
					in=in.substring(0,j)+in.substring(j+1);
					i--;
				}
				a1=reverter(a1);
				for(int j=i+1;j<in.length();j+=0) {
					if(d.isOperator(in.charAt(j))) {
						break;
					}
					a2+=in.charAt(j);
					in=in.substring(j);
					i--;
				}
				double a = Double.parseDouble(a1);
				double b = Double.parseDouble(a2);
				out = (a/b)+"";
				in = in.substring(0,s)+out+in.substring(s+1);
				if(i<0) {
					i=0;
				}
				System.out.println(in);
			}
		}
		return in;
	}
	public String plus(String in) {
		String out = "";
		int s = 0;
		String a1 = "";
		String a2="";
		for(int i =0;i<in.length();i++) {
			if(in.charAt(i)=='+') {
				for(int j =i-1;j>=0;j--) {
					if(d.isOperator(in.charAt(j))) {
						break;
					}
					a1+= in.charAt(j);
					in=in.substring(0,j)+in.substring(j+1);
					i--;
				}
				a1=reverter(a1);
				for(int j=i+1;j<in.length();j+=0) {
					if(d.isOperator(in.charAt(j))) {
						break;
					}
					a2+=in.charAt(j);
					in=in.substring(j);
					i--;
				}
				double a = Double.parseDouble(a1);
				double b = Double.parseDouble(a2);
				out = (a+b)+"";
				in = in.substring(0,s)+out+in.substring(s+1);
				if(i<0) {
					i=0;
				}
				System.out.println(in);
			}
		}
		return in;
	}
	public String min(String in) {
		String out = "";
		int s = 0;
		String a1 = "";
		String a2="";
		for(int i =0;i<in.length();i++) {
			if(in.charAt(i)=='-') {
				for(int j =i-1;j>=0;j--) {
					if(d.isOperator(in.charAt(j))) {
						break;
					}
					a1+= in.charAt(j);
					in=in.substring(0,j)+in.substring(j+1);
					i--;
				}
				a1=reverter(a1);
				for(int j=i+1;j<in.length();j+=0) {
					if(d.isOperator(in.charAt(j))) {
						break;
					}
					a2+=in.charAt(j);
					in=in.substring(j);
					i--;
				}
				double a = Double.parseDouble(a1);
				double b = Double.parseDouble(a2);
				out = (a-b)+"";
				in = in.substring(0,s)+out+in.substring(s+1);
				if(i<0) {
					i=0;
				}
				System.out.println(in);
			}
		}
		return in;
	}
	private String reverter(String in) {
		String out = "";
		for(int i = in.length()-1;i>=0;i--) {
			out+=in.charAt(i);
		}
		return out;
	}
}
