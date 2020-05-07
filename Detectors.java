public class Detectors {
	public boolean isOperator(char in) {
		if(in=='+'||in=='-'||in=='/'||in=='*') {
			return true;
		}
		return false;
	}
}
