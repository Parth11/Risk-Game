package ca.concordia.app.view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.LineBorder;

import ca.concordia.app.component.MapEditorPanel;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class MapEditorView extends JFrame implements IView{

	private static String WINDOW_TITLE = "Create Map";
	
	public JTextField country_name_value;

	public MapEditorPanel map_area;

	public JPanel country_editor_panel;

	public JLabel country_name;

	public JList<String> neighbours_list;

	public JButton save_button;

	public JButton cancel_button;

	public JButton next_button;

	private JScrollPane scrollPane;
	
	public JFileChooser open_dialog;

	/**
	 * Launch the application.
	 */
		/**
	 * Create the application.
	 * @throws IOException 
	 */
	public MapEditorView()  {
		getContentPane().setLayout(null);
		
		try {
			map_area = new MapEditorPanel();
			map_area.setBounds(12, 13, 1068, 813);
			map_area.mapArea.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		getContentPane().add(map_area);
		
		country_editor_panel = new JPanel();
		country_editor_panel.setBounds(1091, 101, 418, 653);
		getContentPane().add(country_editor_panel);
		country_editor_panel.setLayout(null);
		
		country_name = new JLabel("Country Name");
		country_name.setBounds(12, 13, 394, 32);
		country_editor_panel.add(country_name);
		
		country_name_value = new JTextField();
		country_name.setLabelFor(country_name_value);
		country_name_value.setBounds(12, 58, 394, 32);
		country_name_value.setColumns(10);
		country_editor_panel.add(country_name_value);
		
		
		JLabel lblSelectNeighbours = new JLabel("Select Neighbours");
		lblSelectNeighbours.setBounds(12, 103, 394, 32);
		country_editor_panel.add(lblSelectNeighbours);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 147, 394, 382);
		country_editor_panel.add(scrollPane);
		
		neighbours_list = new JList<String>();
		scrollPane.setViewportView(neighbours_list);
		neighbours_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		neighbours_list.setVisibleRowCount(8);
		
		save_button = new JButton("Save");
		save_button.setEnabled(false);
		save_button.setBounds(207, 553, 199, 25);
		country_editor_panel.add(save_button);
		
		cancel_button = new JButton("Cancel");
		cancel_button.setBounds(12, 553, 183, 25);
		country_editor_panel.add(cancel_button);
		
		next_button = new JButton("Next");
		next_button.setEnabled(false);
		next_button.setBounds(119, 611, 158, 25);
		country_editor_panel.add(next_button);
		
		this.setBounds(100, 100, 1550, 900);
		this.setTitle(WINDOW_TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		next_button.addActionListener(actionListener);
		save_button.addActionListener(actionListener);
		cancel_button.addActionListener(actionListener);
	}

	@Override
	public void setMouseListener(MouseListener mouseListener) {
		map_area.setMouseListener(mouseListener);
	}
	
	public void repaintNeighbours(){
		GameMap gameMap = GameMap.getInstance();
		DefaultListModel<String> neighbours = new DefaultListModel<String>();
		
		for(Country c : gameMap.getCountries()){
			neighbours.addElement(c.getCountryName());
		}
		neighbours_list = new JList<>(neighbours);
		scrollPane.setViewportView(neighbours_list);
		neighbours_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		neighbours_list.setVisibleRowCount(8);
	}

	public void connectNeighbours(String name) {
		map_area.mapArea.connectNeighbours(name);
		
	}

	public void paintLoadedMap() {
		GameMap gameMap = GameMap.getInstance();
		
		for(Country c : gameMap.getCountries()){
			map_area.mapArea.drawCountry(c.getCountryName(),c.getLocX(), c.getLocy());
			map_area.mapArea.connectNeighbours(c.getCountryName());
		}
		
		repaintNeighbours();
		
	}
}
