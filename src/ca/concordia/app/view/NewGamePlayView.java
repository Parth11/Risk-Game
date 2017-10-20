package ca.concordia.app.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ca.concordia.app.service.Game;
import ca.concordia.app.service.MyLogger;

/**
 * 
 * @author Abhinav
 *
 */
public class NewGamePlayView extends JFrame implements IView {
	
	public JTextField textInputField;
	public JButton btnSubmit, btnFortify;
	public JScrollPane scrollPane;
	public JTextArea textAreaConsole;
	public Game gameAPI;
	public MyLogger logger;
	
	public NewGamePlayView() {
		
		getContentPane().setLayout(null);
		
		textInputField = new JTextField();
		textInputField.setBounds(1000, 100, 216, 26);
		getContentPane().add(textInputField);
		textInputField.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(1000, 250, 117, 29);
		getContentPane().add(btnSubmit);
		
		btnFortify = new JButton("Fortify");
		btnFortify.setBounds(1000, 400, 117, 29);
		getContentPane().add(btnFortify);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(150, 50, 800, 700);
		getContentPane().add(scrollPane);
		
		textAreaConsole = new JTextArea();
		scrollPane.setViewportView(textAreaConsole);
		textAreaConsole.setForeground(Color.WHITE);
		textAreaConsole.setBackground(Color.BLACK);
		logger = MyLogger.getInstance(textAreaConsole);
		
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
		btnSubmit.addActionListener(actionListener);
		btnFortify.addActionListener(actionListener);
	}


	@Override
	public void setMouseListener(MouseListener mouseListener) {
		// TODO Auto-generated method stub
		
	}
}
