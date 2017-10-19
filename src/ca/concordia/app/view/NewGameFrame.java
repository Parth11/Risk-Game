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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import java.awt.Dimension;

/**
 * 
 * @author Abhinav
 *
 */
public class NewGameFrame extends JFrame {
	private JTextField textField;
	private JButton btnNewButton, btnFortify;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private Game gameAPI;
	private MyLogger logger;
	
	public NewGameFrame() {
		
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(167, 345, 216, 26);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Submit");
		btnNewButton.setBounds(442, 345, 117, 29);
		getContentPane().add(btnNewButton);
		
		btnFortify = new JButton("Fortify");
		btnFortify.setBounds(442, 415, 117, 29);
		getContentPane().add(btnFortify);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(143, 24, 477, 197);
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
		setBounds(100, 100, 881, 536);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
