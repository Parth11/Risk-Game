package ca.concordia.app.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class MapEditorContinentView extends JFrame implements IView {

	private JTextField continent_name_value;
	public JList<String> continent_list,selected_country_list;
	public JList<String> available_country_list;
	public JButton add_button,save_button,next_button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapEditorContinentView window = new MapEditorContinentView();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MapEditorContinentView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 1300, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(347, 107, 245, 316);
		this.getContentPane().add(scrollPane);
		
		selected_country_list = new JList<String>();
		scrollPane.setViewportView(selected_country_list);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(684, 107, 245, 316);
		this.getContentPane().add(scrollPane_1);
		
		available_country_list = new JList<String>();
		scrollPane_1.setViewportView(available_country_list);
		
		add_button = new JButton("<<");
		add_button.setBounds(604, 213, 68, 50);
		this.getContentPane().add(add_button);
		
		JLabel lblContinentName = new JLabel("Continent Name");
		lblContinentName.setBounds(62, 30, 234, 25);
		this.getContentPane().add(lblContinentName);
		
		continent_name_value = new JTextField();
		continent_name_value.setBounds(347, 30, 245, 25);
		this.getContentPane().add(continent_name_value);
		continent_name_value.setColumns(10);
		
		save_button = new JButton("Save");
		save_button.setBounds(684, 467, 110, 25);
		this.getContentPane().add(save_button);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(62, 107, 234, 316);
		this.getContentPane().add(scrollPane_2);
		
		continent_list = new JList<String>();
		scrollPane_2.setViewportView(continent_list);
		
		next_button = new JButton("Next");
		next_button.setBounds(826, 467, 103, 25);
		this.getContentPane().add(next_button);
		
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMouseListener(MouseListener mouseListener) {
		// TODO Auto-generated method stub
		
	}

}
