package ca.concordia.app.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ca.concordia.app.component.MapEditorPanel;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.Game;
import ca.concordia.app.service.MyLogger;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollBar;

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
	private JTable table;
	public JComboBox country_combo;
	
	public NewGamePlayView() {
		
		getContentPane().setLayout(null);
		
		gameAPI = Game.getInstance();
		
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
		scrollPane.setBounds(15, 449, 935, 379);
		getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		logger = MyLogger.getInstance(textArea);
		
		
		
		country_combo = new JComboBox();
		country_combo.setToolTipText("select country");
		country_combo.setBounds(960, 16, 256, 36);
		getContentPane().add(country_combo);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(15, 431, 935, -392);
		getContentPane().add(scrollPane_1);
		
		
		
		
		Object[][] A  = gameAPI.getGamePlayState();
		Object[] B = {"country name","number of armies","owner"};
		
		
		DefaultTableModel dataModel = new DefaultTableModel(A, B);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(15, 16, 930, 420);
		getContentPane().add(scrollPane_2);
		
		table = new JTable(A.length, 3);
		scrollPane_2.setViewportView(table);
		table.setModel(dataModel);
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
