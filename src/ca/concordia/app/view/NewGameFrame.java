package ca.concordia.app.view;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class NewGameFrame extends JFrame {
	private JTextField textField;
	public NewGameFrame() {
		
		getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		textArea.setBounds(69, 30, 429, 173);
		getContentPane().add(textArea);
		
		textField = new JTextField();
		textField.setBounds(107, 273, 216, 26);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.setBounds(403, 273, 117, 29);
		getContentPane().add(btnNewButton);
		
		JButton btnFortify = new JButton("Fortify");
		btnFortify.setBounds(403, 336, 117, 29);
		getContentPane().add(btnFortify);
		
		initialize();
	}
	
	
	private void initialize() {
		this.setVisible(true);
		setBounds(100, 100, 881, 536);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
