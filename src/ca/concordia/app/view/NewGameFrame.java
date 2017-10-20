package ca.concordia.app.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ca.concordia.app.service.MyLogger;
import lib.Game;

/**
 * 
 * @author Abhinav
 *
 */
public class NewGameFrame extends JFrame implements IView {
	
	public JTextField textField;
	public JButton btnNewButton, btnFortify;
	public JScrollPane scrollPane;
	public JTextArea textArea;
	public Game gameAPI;
	public MyLogger logger;
	
	public NewGameFrame() {
		
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(1000, 100, 216, 26);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Submit");
		btnNewButton.setBounds(1000, 250, 117, 29);
		getContentPane().add(btnNewButton);
		
		btnFortify = new JButton("Fortify");
		btnFortify.setBounds(1000, 400, 117, 29);
		getContentPane().add(btnFortify);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(150, 50, 800, 700);
		getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		logger = MyLogger.getInstance(textArea);
		
//		// using DB converter
		//DbConverter.convert(GameMap.getInstance(), lib.model.GameMap.getInstance());
		//DbConverter.print();
		
		initialize();
	}
	
	
	private void initialize() {
		this.setVisible(true);
		setBounds(100, 100, 1300, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	@Override
	public void setActionListener(ActionListener actionListener) {
		btnNewButton.addActionListener(actionListener);
		btnFortify.addActionListener(actionListener);
	}


	@Override
	public void setMouseListener(MouseListener mouseListener) {
		// TODO Auto-generated method stub
		
	}
}
