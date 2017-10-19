package ca.concordia.app.view;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ca.concordia.app.model.GameMap;
import ca.concordia.app.service.MyLogger;
import lib.DbConverter;
import lib.Game;
import lib.model.Country;
import lib.model.Player;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

/**
 * 
 * @author Abhinav
 *
 */
public class NewGameFrame extends JFrame implements IView {
	public JTextField textField;
	public JButton btnSubmit, btnFortify;
	public JScrollPane scrollPane;
	public JTextArea textArea;
	public Game gameAPI;
	public MyLogger logger;
	
	public NewGameFrame() {
		
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(37, 459, 216, 26);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(296, 459, 117, 29);
		getContentPane().add(btnSubmit);
		
		btnFortify = new JButton("Fortify");
		btnFortify.setBounds(442, 459, 117, 29);
		getContentPane().add(btnFortify);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(97, 39, 593, 348);
		getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		textArea.setSize(scrollPane.getSize());
		scrollPane.setViewportView(textArea);
		logger = MyLogger.getInstance(textArea);
		
//		// using DB converter
		//DbConverter.convert(GameMap.getInstance(), lib.model.GameMap.getInstance());
		//DbConverter.print();
		
		initialize();
	}
	
	
	private void initialize() {
		this.setVisible(true);
		setBounds(100, 100, 881, 536);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void setActionListener(ActionListener actionListener) {
		btnSubmit.addActionListener(actionListener);
		btnFortify.addActionListener(actionListener);
		
	}

	
	public void setMouseListener(MouseListener mouseListener) {
		// TODO Auto-generated method stub
		
	}
	
}
