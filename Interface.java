import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Interface implements ActionListener{
	JTextField tf1;
	JTextArea op;  
	InputFormater i = new InputFormater();
	Interface(){  
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
	public void actionPerformed(ActionEvent e) {
		String varSide = i.varSide(tf1.getText());
		String numSide = i.numSide(tf1.getText());
		//System.out.println(varSide+"\n"+numSide);
		op.setText(i.simp(varSide)+'='+i.simp(numSide));
		
	} 	
	public static void main(String[] args) {
		new Interface();
	}
}
