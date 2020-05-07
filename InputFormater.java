public class InputFormater {
	Mathinator m = new Mathinator();
	public String varSide(String inputR){
		String out = "";
		for(int i=0;i<inputR.length();i++) {
			if(inputR.charAt(i) == '=') {
				for(int j=0;j<=i-1;j++) {
					if(inputR.charAt(j)!=' ') {
						out+=inputR.charAt(j);						
					}
				}
			}
		}
		return out;
	}
	public String numSide(String inputR){
		String out = "";
		for(int i=0;i<inputR.length();i++) {
			if(inputR.charAt(i) == '=') {
				for(int j=i+1;j<inputR.length();j++) {
					if(inputR.charAt(j)!=' ') {
						out+=inputR.charAt(j);						
					}
				}
			}
		}
		return out;
	}
	//public String swap(String inputA,String inputB) {}
	public String simp(String in) {
		in=m.mult(in);
		in=m.div(in);
		in=m.plus(in);
		in=m.min(in);
		return in;
	}
}
