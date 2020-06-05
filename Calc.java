package calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings({ "unchecked", "serial" ,"static-access"})
public class Calc23 extends JFrame implements ActionListener {
	public static void main(String[] args) {
		new Calc23();
	}
	JTextField tf1;
	JTextArea op;  
	String inputVar = "";
	Calc23(){  
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
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
	} 	
	@Override
	public void actionPerformed(ActionEvent e){  
		op.setText("");
		parens(tf1.getText().split("=")[0]);
		ArrayList<String> varS = format(parens(tf1.getText().split("=")[0]));
		ArrayList<String> intS = new ArrayList<String>();
		try {
			inputVar = Character.toString(tf1.getText().replaceAll("[^a-z]", "").charAt(0));
		} catch (Exception e1) {
			inputVar = "x";
		}
		try {
			intS = format(parens(tf1.getText().split("=")[1]));
		} catch (Exception e2) {
			intS.add("0");
		}
		addNewEquation(varS, intS);
		op.setText(op.getText()+"Multiply\n");
		while (varS.contains("*")||varS.contains("/")||intS.contains("*")||intS.contains("/")) {
			doMultDiv(varS);
			doMultDiv(intS);
			addNewEquation(varS, intS);
		}
		op.setText(op.getText()+"Add like terms\n");
		ArrayList<String> varS2 = new ArrayList<String>();
		ArrayList<String> intS2 = new ArrayList<String>();
		while ((varS.contains("+")||varS.contains("-")||intS.contains("+")||intS.contains("-"))&&(!varS2.equals(varS)||!intS2.equals(intS))) {
			varS2 = (ArrayList<String>) varS.clone();
			intS2 = (ArrayList<String>) intS.clone();
			doPlusMin(varS);
			doPlusMin(intS);
			addNewEquation(varS, intS);
		}
		op.setText(op.getText()+"Isolate the varibles\n");
		String[] iso = isolateTerms(varS,intS);
		varS = format(iso[0]);
		intS = format(iso[1]);
		addNewEquation(varS, intS);
		op.setText(op.getText()+"divide by the coefficient\n");
		divideByCo(varS, intS);
		addNewEquation(varS, intS);
	}
	public void doMultDiv(ArrayList<String> in){
		ArrayList<String> out = in;
		for (int i = 0; i < out.size(); i++) {
			if (out.get(i).matches("[*/]")) {
				if (i!=0&&i+1!=out.size()) {
					double temp = Double.parseDouble(out.get(i-1).replace(inputVar, ""));
					double temp2 = Double.parseDouble(out.get(i+1).replace(inputVar, ""));
					boolean cont = containsX(out.get(i-1)+out.get(i+1));
					if (in.get(i).contentEquals("*")) {
						out.remove(i+1);
						out.remove(i);
						out.remove(i-1);
						try {
							out.add(i-1, (cont)?(temp*temp2)+inputVar:(temp*temp2)+"");
						} catch (Exception e) {
							out.add((cont)?(temp*temp2)+inputVar:(temp*temp2)+"");
						}
					}else if (in.get(i).contentEquals("/")) {
						out.remove(i+1);
						out.remove(i);
						out.remove(i-1);
						try {
							out.add(i-1, (cont)?(temp/temp2)+inputVar:(temp/temp2)+"");
						} catch (Exception e) {
							out.add((cont)?(temp/temp2)+inputVar:(temp/temp2)+"");
						}
					}
				}
			}
		}
		in =  out;
	}
	public void doPlusMin(ArrayList<String> in){
		ArrayList<String> out = in;
		for (int i = 0; i < out.size(); i++) {
			if (out.get(i).matches("[+-]")) {
				if (i!=0&&i+1!=out.size()) {
					double temp = Double.parseDouble(out.get(i-1).replace(inputVar, ""));
					double temp2 = Double.parseDouble(out.get(i+1).replace(inputVar, ""));
					boolean x1 = containsX(out.get(i-1));
					boolean x2 = containsX(out.get(i+1));
					if (in.get(i).contentEquals("+")&&x1==x2) {
						out.remove(i+1);
						out.remove(i);
						out.remove(i-1);
						try {
							out.add(i-1, (x1)?(temp+temp2)+inputVar:(temp+temp2)+"");
						} catch (Exception e) {
							out.add((x1)?(temp+temp2)+inputVar:(temp+temp2)+"");
						}
					}else if (in.get(i).contentEquals("-")&&x1==x2) {
						out.remove(i+1);
						out.remove(i);
						out.remove(i-1);
						try {
							out.add(i-1, (x1)?(temp-temp2)+inputVar:(temp-temp2)+"");
						} catch (Exception e) {
							out.add((x1)?(temp-temp2)+inputVar:(temp-temp2)+"");
						}
					}
				}
			}
		}
		in =  out;
	}
	public void updateOutPutText(String in) {
		op.setText(op.getText()+in);
	}
	public void addNewEquation(ArrayList<String> varS,ArrayList<String> intS) {
		for (int i = 0; i < varS.size(); i++) {
			updateOutPutText(varS.get(i));
		}
		updateOutPutText("=");
		for (int i = 0; i < intS.size(); i++) {
			updateOutPutText(intS.get(i));
		}
		updateOutPutText("\n");
	}
	public boolean containsX(String s) {
		return s.contains(inputVar);
	}
	public String[] isolateTerms(ArrayList<String> varS,ArrayList<String> intS) {
		String[] out = new String[2];
		double vars = 0;
		double constants = 0;
		boolean lastIsNeg = false;
		for (String s:varS) {
			if(s.matches("[-]?[0-9.]*"+inputVar)) {
				vars+=(lastIsNeg)?-Double.parseDouble(s.replaceAll("[^0-9.-]", "")):Double.parseDouble(s.replaceAll("[^0-9.-]", ""));
			}else if(s.matches("^[-]?[0-9.]+$")) {
				constants-=(lastIsNeg)?-Double.parseDouble(s):Double.parseDouble(s);
			}
			if(s.matches("-")) {
				lastIsNeg=true;
			}else {
				lastIsNeg=false;
			}
		}
		for (String s:intS) {
			if(s.matches("[-]?[0-9.]*"+inputVar)) {
				vars-=(lastIsNeg)?-Double.parseDouble(s.replaceAll("[^0-9.-]", "")):Double.parseDouble(s.replaceAll("[^0-9.-]", ""));
			}else if(s.matches("^[-]?[0-9.]+$")) {
					constants+=(lastIsNeg)?-Double.parseDouble(s):Double.parseDouble(s);
			}
		}
		
		out[0]= vars + inputVar;
		out[1]=constants+"";
		return out;
	}
	public void divideByCo(ArrayList<String> varS,ArrayList<String> intS) {
		String s = varS.get(0).replace(inputVar, "");
		double a = (!s.matches("-"))? Double.parseDouble(s):Double.parseDouble(varS.get(1).replace(inputVar, ""));
		double b = (!intS.get(0).equals("-"))?Double.parseDouble(intS.get(0)):Double.parseDouble(intS.get(1));
		varS.set(0, inputVar);
		intS.set(0, (b/a)+"");
	}
	public String parens(String in) {
		ArrayList<String> out = new ArrayList<String>();
		String[] inArr = in.replaceAll("[(]", "(_").split("[()]");
		if(inArr.length==1) {
			return in;
		}
		for(int i = 1;i<inArr.length;i++) {
			if(inArr[i-1].matches("[^_].*[*]")) {
				if(inArr[i].matches("[_].*")) {
					int store = 0;
					for (int j = inArr[i-1].length()-1; j > 0; j--) {
						if(Character.toString(inArr[i-1].charAt(j)).matches("[+-]")) {
							store = j+1;
							break;
							}
					}
					double temp = Double.parseDouble(inArr[i-1].substring(store).replaceAll("[^0-9.-]", ""));
					String tempOut = "";
					for(String j: format(inArr[i])) {
						if(!j.equals("+")&&!j.equals("-")&&!j.equals("/")&&!j.equals("_")&&!j.equals("*")) {
							tempOut+=(!j.replaceAll("[a-zA-z-]", "").equals(""))?Double.parseDouble(j.replaceAll("[a-zA-z-]", ""))*temp:temp;
							if(j.contains("x")) {
								tempOut+="x";
							}
						}else if(!j.equals("_")) {
							tempOut+=j;
						}
					}
					out.add(inArr[i-1].substring(0,store));
					out.add(tempOut);
				}
			}else {
				if(i==1) {
					out.add(inArr[i-1]);
				}
				if(inArr[i].contains("_")) {
					inArr[i] = inArr[i].replaceAll("_", "");
					ArrayList<String> temp = format(inArr[i]);
					while (temp.contains("*")||temp.contains("/")) {
						doMultDiv(temp);
					}
					ArrayList<String> varS2 = new ArrayList<String>();
					while ((temp.contains("+")||temp.contains("-"))&&!varS2.equals(temp)) {
						varS2 = (ArrayList<String>) temp.clone();
						doPlusMin(temp);
						doPlusMin(temp);
					}
					String out2 = "";
					for(String j:temp) {
						out2+=j;
					}
					out.add(out2);
				}else {
					out.add(inArr[i]);
				}
			}
		}	
		in = "";
		for(String i:out) {
			in+=i;
		}
		return in;
	}
	public ArrayList<String> format(String in){
		String coolIn = "";
		for (int i = 0; i < in.length(); i++) {
			String a = Character.toString(in.charAt(i));
			coolIn+=(!(a.matches("[^0-9a-z. ]"))||i==0)?a:" "+a+" ";
		}
		String[] inT = coolIn.split(" ");
		ArrayList<String> out = new ArrayList<String>();
		for (String i : inT) {
			out.add(!(i.matches("^[-]?[a-z]$"))?i:(i.contains("-"))?"-1"+i.replace("-", ""):"1"+i);
		}
		return out;
	}
}
