package calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;




public class Calc implements ActionListener {
	JTextField tf1;
	JTextArea op;  
	String x="x";
	String pl="+";
	String min="-";
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
		ArrayList<String> varS = format(tf1.getText().split("=")[0]);
		ArrayList<String> intS = new ArrayList<String>();
		try {
			intS = format(tf1.getText().split("=")[1]);
		} catch (Exception e2) {
			intS.add("0");
		}
		varS = doPlusMin(doMultDiv(varS));
		intS = doPlusMin(doMultDiv(intS));
	}
	public static void main(String[] args) {
		new Calc();
	}
	public ArrayList<String> format(String in){
		String coolIn = "";
		for (int i = 0; i < in.length(); i++) {
			coolIn+=((Character.toString(in.charAt(i)).matches("[^0-9a-z]"))?" "+Character.toString(in.charAt(i))+" ":Character.toString(in.charAt(i)));
		}
		String[] inT = coolIn.split(" ");
		ArrayList<String> out = new ArrayList<String>();
		for (String i : inT) {
			out.add(i);
		}
		return out;
	}
	public ArrayList<String> doMultDiv(ArrayList<String> in){
		ArrayList<String> out = in;
		for (int i = 0; i < out.size(); i++) {
			if (out.get(i).matches("[*/]")) {
				if (i!=0&&i+1!=out.size()) {
					double temp = Double.parseDouble(out.get(i-1));
					double temp2 = Double.parseDouble(out.get(i+1));
					if (in.get(i).contentEquals("*")) {
						out.remove(i+1);
						out.remove(i);
						out.remove(i-1);
						try {
							out.add(i, (temp*temp2)+"");
						} catch (Exception e) {
							out.add((temp*temp2)+"");
						}
					}else if (in.get(i).contentEquals("/")) {
						out.remove(i+1);
						out.remove(i);
						out.remove(i-1);
						try {
							out.add(i, (temp/temp2)+"");
						} catch (Exception e) {
							out.add((temp/temp2)+"");
						}
					}
				}
			}
		}
		return out;
	}
	public ArrayList<String> doPlusMin(ArrayList<String> in){
		ArrayList<String> out = in;
		for (int i = 0; i < out.size(); i++) {
			if (out.get(i).matches("[+-]")) {
				if (i!=0&&i+1!=out.size()) {
					double temp = Double.parseDouble(out.get(i-1));
					double temp2 = Double.parseDouble(out.get(i+1));
					if (in.get(i).contentEquals("+")) {
						out.remove(i+1);
						out.remove(i);
						out.remove(i-1);
						try {
							out.add(i, (temp+temp2)+"");
						} catch (Exception e) {
							out.add((temp+temp2)+"");
						}
					}else if (in.get(i).contentEquals("-")) {
						out.remove(i+1);
						out.remove(i);
						out.remove(i-1);
						try {
							out.add(i, (temp-temp2)+"");
						} catch (Exception e) {
							out.add((temp-temp2)+"");
						}
					}
				}
			}
		}
		return out;
	}
}
