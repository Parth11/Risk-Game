package ca.concordia.app.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import ca.concordia.app.component.MapEditorPanel;
import ca.concordia.app.service.Game;
import ca.concordia.app.service.MyLogger;
import javax.swing.JPanel;
import javax.swing.JComboBox;

/**
 * 
 * @author Abhinav
 *
 */
public class NewGamePlayView extends JFrame implements IView {
	
	public JTextField textField;
	public JButton btnNewButton, btnFortify;
	public JScrollPane scrollPane;
	public JTextArea textArea;
	public Game gameAPI;
	public MyLogger logger;
	private MapEditorPanel map_panel;
	
	public NewGamePlayView() {
		
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(960, 68, 256, 36);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Submit");
		btnNewButton.setBounds(1099, 120, 117, 36);
		getContentPane().add(btnNewButton);
		
		btnFortify = new JButton("Fortify");
		btnFortify.setBounds(1099, 407, 117, 29);
		getContentPane().add(btnFortify);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 563, 935, 265);
		getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		logger = MyLogger.getInstance(textArea);
		
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setToolTipText("select country");
		comboBox.setBounds(960, 16, 256, 36);
		getContentPane().add(comboBox);
		
		try {
			map_panel = new MapEditorPanel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		map_panel.setBounds(15, 16, 930, 531);
		getContentPane().add(map_panel);
		
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
