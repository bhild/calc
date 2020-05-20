package calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings({ "unchecked", "serial" ,"static-access"})
public class Calc extends JFrame implements ActionListener {
	public static void main(String[] args) {
		new Calc();
	}
	JTextField tf1;
	JTextArea op;  
	String inputVar = "";
	Calc(){  
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
		ArrayList<String> varS = format(tf1.getText().split("=")[0]);
		ArrayList<String> intS = new ArrayList<String>();
		try {
			intS = format(tf1.getText().split("=")[1]);
		} catch (Exception e2) {
			intS.add("0");
		}
		addNewEquation(varS, intS);
		while (varS.contains("*")||varS.contains("/")||intS.contains("*")||intS.contains("/")) {
			doMultDiv(varS);
			doMultDiv(intS);
			addNewEquation(varS, intS);
		}
		ArrayList<String> varS2 = new ArrayList<String>();
		ArrayList<String> intS2 = new ArrayList<String>();
		while ((varS.contains("+")||varS.contains("-")||intS.contains("+")||intS.contains("-"))&&(!varS2.equals(varS)||!intS2.equals(intS))) {
			varS2 = (ArrayList<String>) varS.clone();
			intS2 = (ArrayList<String>) intS.clone();
			doPlusMin(varS);
			doPlusMin(intS);
			addNewEquation(varS, intS);
		}
		String[] iso = isolateTerms(varS,intS);
		varS = format(iso[0]);
		intS = format(iso[1]);
		addNewEquation(varS, intS);
	}
	public ArrayList<String> format(String in){
		try {
			inputVar = Character.toString(in.replaceAll("[^a-z]", "").charAt(0));
		} catch (Exception e) {
			inputVar = "x";
		}
		String coolIn = "";
		for (int i = 0; i < in.length(); i++) {
			coolIn+=((Character.toString(in.charAt(i)).matches("[^0-9a-z. ]"))?" "+Character.toString(in.charAt(i))+" ":Character.toString(in.charAt(i)));
		}
		String[] inT = coolIn.split(" ");
		ArrayList<String> out = new ArrayList<String>();
		for (String i : inT) {
			out.add((!i.matches("^[a-z]$"))?i:"1"+i);
		}
		return out;
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
		for (String s:varS) {
			if(s.matches("[0-9.]*"+inputVar)) {
				vars+=Double.parseDouble(s.replaceAll("[^0-9.]", ""));
			}else if(s.matches("[0-9.]*")) {
				constants-=Double.parseDouble(s.replaceAll("[^0-9.]", ""));
			}
		}
		for (String s:intS) {
			if(s.matches("[0-9.]*"+inputVar)) {
				vars-=Double.parseDouble(s.replaceAll("[^0-9.]", ""));
			}else if(s.matches("[0-9.]*")) {
					constants+=Double.parseDouble(s.replaceAll("[^0-9.]", ""));
			}
		}
		
		out[0]= vars + inputVar;
		out[1]=constants+"";
		return out;
	}
}
