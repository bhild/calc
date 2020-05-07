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
		ArrayList<String> inT = format(tf1.getText());
		for (int i = 0; i < inT.size(); i++) {
			System.out.println(inT.get(i));
		}
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
}