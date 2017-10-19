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
	
	public NewGameFrame(int finalPlayers) {
		
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(107, 273, 216, 26);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Submit");
		btnNewButton.setBounds(403, 273, 117, 29);
		getContentPane().add(btnNewButton);
		
		btnFortify = new JButton("Fortify");
		btnFortify.setBounds(403, 336, 117, 29);
		getContentPane().add(btnFortify);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(97, 39, 392, 141);
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
}
