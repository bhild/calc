package calculators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class calculator003 implements ActionListener {
	JTextField tf1;
	JTextArea op;  
	String x="x";
	String pl="+";
	String min="-";
	calculator003(){  
		JFrame f=new JFrame();  
		tf1=new JTextField();  
		op=new JTextArea();
		op.setBounds(0,20,900, 455);
		tf1.setBounds(0,0,900,20);
		tf1.addActionListener(this);
		op.setEditable(false);
		f.add(op);
		f.add(tf1);
		f.setSize(900,445);
		f.setLayout(null);
		f.setVisible(true); 
	} 	
	@Override
	public void actionPerformed(ActionEvent e){  
		String inputR=tf1.getText();
		String[] iP=inputR.split(" ");
		ArrayList<String> fV=new ArrayList<String>();
		ArrayList<String> fN=new ArrayList<String>();
		for (int i=0; i < iP.length; i++) {
			if(iP[i].equals("=")) {
				for (int j=0; j < i; j++) {
					fV.add(iP[j]);
				}
				for (int j=i+1; j < iP.length; j++) {
					fN.add(iP[j]);					
				}
				break;
			}
		}
		xone(fV);
		xone(fN);
		perins(fV,fN);
		neg(fV);
		neg(fN);
		simplify(fV,fN);
		simplify(fN,fV);
		remove(fV);
		remove(fN);
		op.setText(fV + "=" + fN);			
		for (int i=0; i < fN.size(); i++) {
			if(fN.get(i).contains(x)) {
				if(fN.get(i).contains(min)) {
					fV.add(pl);
					fV.add(fN.get(i).replaceAll(min, ""));
					fN.remove(i);
				}
				else {
					fV.add(pl);
					fV.add(min+fN.get(i));
					fN.remove(i);
				}
			}
		}
		for (int i=0; i < fV.size(); i++) {
			if(!fV.get(i).contains("[^0-9|-]")&&!fV.get(i).contains(x)&&!fV.get(i).contains(pl)) {
				if(fV.get(i).contains(min)) {
					fN.add(pl);
					fN.add(fV.get(i).replaceAll(min, ""));
					fV.remove(i);
				}
				else {
					fN.add(pl);
					fN.add(min+fV.get(i));
					fV.remove(i);
				}
			}
		}
		simplify(fV,fN);
		simplify(fN,fV);
		remove(fV);
		remove(fN);
		fill(fV);
		fill(fN);
		if(fV.get(0).contains(min)) {
			fV.add(fV.get(0).replaceAll(min, ""));
			fV.remove(0);
			if(fN.get(0).contains(min)) {
				fN.add(fN.get(0).replaceAll(min, ""));
				fN.remove(0);
			} else if(!fN.get(0).contains(min)) {
				fN.add(min + fN.get(0));
				fN.remove(0);
			}
		}
		if(coolExponentFinder(fV.get(0))>1) {
			Double a=(double)1/coolExponentFinder(fV.get(0));
			fV.add(fV.get(0).substring(0,fV.get(0).indexOf(x)+1));
			fV.remove(0);
			fN.add(Math.pow(Double.parseDouble(fN.get(0)), a)+"");
			fN.remove(0);
		}
		if(fV.get(0).replace(x, "").contentEquals("")) {
			fV.add("1x");
			fV.remove(0);
		}
		op.setText(op.getText()+"\n"+fV + "=" + fN);
		if(Double.parseDouble(fV.get(0).replace(x, ""))==0.0 && Double.parseDouble(fN.get(0))==0.0) {
			op.setText(op.getText() + "\n Infinte soultions");			
		}else if(Double.parseDouble(fV.get(0).replace(x, ""))==0.0) {
			op.setText(op.getText() + "\n No soultions");			
		}else{
			Double a=Double.parseDouble(fN.get(0))/Double.parseDouble(fV.get(0).replace(x, ""));
			op.setText(op.getText() + "\n" + a);			
		}
	}
	public static void main(String[] args) {
		new calculator003();
	}
	private void neg(ArrayList<String> f) {
		for(int i=1; i<f.size();i++) {
			if(f.get(i-1).equals(min)) {
				if(f.get(i).contains(min)) {
					f.remove(i-1);
					f.add(i-1, pl);
					f.add(i+1,f.get(i).replaceAll(min, ""));
					f.remove(i);
				}else {
					f.remove(i-1);
					f.add(i-1, pl);
					f.add(i+1,min + f.get(i));
					f.remove(i);
				}
			}
			
		}
		remove(f);
	}
	private void fill(ArrayList<String> f) {
		if(f.size()==0) {
			f.add("0.0");
		}
	}
	private void remove(ArrayList<String> f) {
		for (int i=0; i < f.size(); i++) {
			if(i==0&&(f.get(i).equals(pl)||f.get(i).equals(min))) {
				f.remove(i);
			}
			if(i==f.size()-1 && (f.get(i).equals(pl)) || f.get(i).equals(min)) {
				f.remove(i);
			}else if (f.get(i).equals(pl) && (f.get(i+1).equals(pl) || f.get(i+1).equals(pl))) {				
				f.remove(i);
			}
		}
		try {
			if(f.get(f.size()-1).contains(pl) || f.get(f.size()-1).equals(min)) {
				f.remove(f.size()-1);
			}
		} catch (Exception e) {}
	}
	private void simplify(ArrayList<String> f,ArrayList<String> f2) {
		remove(f);
		multdiv(f,f2);
		remove(f);
		for (int i=0; i < f.size(); i++) {
			for (int j=i; j < f.size(); j++) {
				if(f.get(i).contains(x)) {
					if(f.get(j).contains(x) && i!=j) {
						plus(f, i, j,x);
					}
				}
				else if(!f.get(i).matches("[^0-9|-]")) {
					if(!f.get(j).matches("[^0-9]") && i!=j && !(f.get(j).contains(x))) {
						plus(f, i, j,"");
					}
				}
			}
		}
	}
	private void xone(ArrayList<String> fV) {
		for (int i= 0; i < fV.size(); i++) {
			if(fV.get(i).replace(x, "").contentEquals("")) {
				fV.add("1x");
				fV.remove(i);
			}			
		}
	}
	private void plus(ArrayList<String> f, int i, int j,String a) {
		double one=0;
		double two=0;
		try {
			one=Double.parseDouble(f.get(i).replaceAll("[a-zA-Z]", ""));
		} catch (Exception e2) {
			one=-1*Double.parseDouble(f.get(i).replaceAll("[^0-9]", ""));
		}
		try {
			two=Double.parseDouble(f.get(j).replaceAll("[a-zA-Z]", ""));
		} catch (Exception e2) {
			two=-1*Double.parseDouble(f.get(j).replaceAll("[^0-9]", ""));
		}
		f.remove(i);
		f.remove(j-1);
		f.add(i, one+two+a);
	}  
	private void multdiv(ArrayList<String> f,ArrayList<String> f2) {
		for(int i=1; i < f.size(); i++) {
			if(!f.get(i).contains(x)&&f.get(i).contains("^")) {
				f.add(i+1,Math.pow(Double.parseDouble(f.get(i).substring(0, f.get(i).indexOf("^"))), coolExponentFinder(f.get(i)))+"");
				f.remove(i);
			}
		}
		for (int i=1; i < f.size()-1; i++) {
			if(f.get(i).contentEquals("*")) {
				if(f.get(i-1).contains(x) && !f.get(i+1).contains(x) || !f.get(i-1).contains(x) && f.get(i+1).contains(x)) {
					f.add(i-1,xFin(f.get(i-1),f.get(i+1),x,"*"));
					r(f,i,3);
					i=0;
				}else if (f.get(i-1).equals(min)||!f.get(i-1).equals(pl)||!f.get(i+1).equals(min)||!f.get(i+1).equals(pl)) {
					f.add(i-1,xFin(f.get(i-1),f.get(i+1),"","*"));					
					r(f,i,3);
					i=0;
				}else if(f.get(i-1).contains(x) && f.get(i+1).contains(x)) {
					if(f.get(i-1).contains("^")) {
						
					}
				}
			}else if(f.get(i).contentEquals("/")) {
				if(f.get(i-1).contains(x) && f.get(i+1).contains(x)) {
					int a=coolExponentFinder(f.get(i-1)) - coolExponentFinder(f.get(i+1));
					if(a==0) {
						f.add(i-1,xFin(f.get(i-1),f.get(i+1),"","/"));
					}else if(a==1){
						f.add(i-1,xFin(f.get(i-1),f.get(i+1),"x","/"));						
					}else {						
						f.add(i-1,xFin(f.get(i-1),f.get(i+1),"x","/")+"^"+ a);						
					}
					r(f,i,3);
					i=0;
				}else if (f.get(i-1).equals(min)||!f.get(i-1).equals(pl)||!f.get(i+1).equals(min)||!f.get(i+1).equals(pl)) {
					f.add(i-1,xFin(f.get(i-1),f.get(i+1),"","/"));					
					r(f,i,3);
					i=0;
				}else if(!f.get(i-1).contains(x) && f.get(i+1).contains(x)) {
					f.add(i-1,xFin(f.get(i-1),f.get(i+1),"","/"));
					r(f,i,3);
					i=0;
					f2.add(0,1+x);
					f2.add(1,"(");
					f2.add(")");
				}
			}
		}
	}
	private void r(ArrayList<String> f,int i,int r) {
		for (int j=0; j < r; j++) {
			f.remove(i);			
		}
	}
	private String xFin(String one, String two, String a, String b) {
		if(one.contains("^")) {
			one=one.substring(0,one.indexOf(x));
		}
		if(two.contains("^")) {
			two=two.substring(0,two.indexOf(x));
		}
		if(one.replace(x, "").contentEquals("")) {
			one="1";
		}
		if(two.replace(x, "").contentEquals("")) {
			two="1";
		}
		Double a1=0.0;
		Double a2=0.0;
		try {
			a1=Double.parseDouble(one.replace(x,""));
		} catch (Exception e) {
			a1=-1*Double.parseDouble(one.replace(x,""));
		} try {
			a2=Double.parseDouble(two.replace(x,""));
		} catch (Exception e) {
			a2=-1*Double.parseDouble(two.replace(x,""));
		}
		if(b.equals("*")) {
			return a1 * a2 + a;
		}
		return a1 / a2 + a;
	}
	private void perins(ArrayList<String> f,ArrayList<String> f2) {
		ArrayList<String> t=new ArrayList<String>();
		for(int i=0; i < f.size();i++) {
			if(f.get(i).equals("(")) {
				f.remove(i);
				while(!f.get(i).equals(")")) {
					t.add(f.get(i));
					f.remove(i);	
					if(f.get(i).equals(")") == true) {
						break;
					}
				}
				f.remove(i);
				simplify(t,f2);
				if(i>=1) {
					for (int j=0; j < t.size(); j++) {
						try {
							if(t.get(i).contentEquals("*")) {
								if(t.get(i-1).contains(x) || t.get(i+1).contains(x)) {
									t.add(xFin(f.get(i-2),f.get(j),x,"*"));
								}else if (t.get(i-1).equals(min)||!t.get(i-1).equals(pl)||!t.get(i+1).equals(min)||!t.get(i+1).equals(pl)) {
									xFin(f.get(i-1),f.get(j),"","*");								
								}
							}else if(t.get(i).contentEquals("/")) {
								if(t.get(i-1).contains(x) || t.get(i+1).contains(x)) {
									xFin(f.get(i-1),f.get(j),x,"/");
								}else if (t.get(i-1).equals(min)||!t.get(i-1).equals(pl)||!t.get(i+1).equals(min)||!t.get(i+1).equals(pl)) {
									xFin(f.get(i-1),f.get(j),"","/");
								}	
							}
						} catch (Exception e) {}
					}
				}
				for (int j=0; j < t.size(); j++) {
					f.add(i+j,t.get(j));
				}
			}
		}
	}
	private static int coolExponentFinder(String a) {
		if(!a.contains("^")) {
			return 1;			
		}
		a=a.substring(a.indexOf('^')+1);
		return Integer.parseInt(a);
	}
}