package ca.concordia.app.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import ca.concordia.app.model.Continent;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;

import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class MapEditorContinentView extends JFrame implements IView {

	public JTextField continent_name_value;
	public JList<String> continent_list, selected_country_list;
	public JList<String> available_country_list;

	public JButton add_button, save_button, next_button, remove_button;
	public JScrollPane available_country_pane = new JScrollPane();
	public JScrollPane selected_country_pane = new JScrollPane();
	public JScrollPane continent_pane = new JScrollPane();

	public GameMap gameMap = GameMap.getInstance();

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

		selected_country_pane.setBounds(347, 107, 245, 316);
		this.getContentPane().add(selected_country_pane);

		selected_country_list = new JList<String>();
		selected_country_pane.setViewportView(selected_country_list);

		available_country_pane.setBounds(684, 107, 245, 316);
		this.getContentPane().add(available_country_pane);

		DefaultListModel<String> countries = new DefaultListModel<String>();
		for (Country c : gameMap.getCountries()) {
			countries.addElement(c.getCountryName());
		}
		available_country_list = new JList<String>(countries);
		available_country_pane.setViewportView(available_country_list);
		available_country_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		available_country_list.setVisibleRowCount(8);

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

		continent_pane.setBounds(62, 107, 234, 316);
		this.getContentPane().add(continent_pane);

		continent_list = new JList<String>();
		continent_pane.setViewportView(continent_list);

		next_button = new JButton("Next");
		next_button.setBounds(826, 467, 103, 25);
		this.getContentPane().add(next_button);

		remove_button = new JButton(">>");
		remove_button.setBounds(604, 275, 68, 50);
		getContentPane().add(remove_button);

	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		next_button.addActionListener(actionListener);
		save_button.addActionListener(actionListener);
		add_button.addActionListener(actionListener);
		remove_button.addActionListener(actionListener);
	}

	@Override
	public void setMouseListener(MouseListener mouseListener) {
		// TODO Auto-generated method stub
		continent_list.addMouseListener(mouseListener);

	}

	public void repaintContinents() {
		// TODO Auto-generated method stub
		DefaultListModel<String> continents = new DefaultListModel<String>();
		for (Continent c : gameMap.getContinents()) {
			continents.addElement(c.getContinentName());
		}
		continent_list = new JList<String>(continents);
		continent_pane.setViewportView(continent_list);
		continent_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		continent_list.setVisibleRowCount(8);

		selected_country_list = new JList<String>();
		selected_country_pane.setViewportView(selected_country_list);
	}

	public void repaintSelectedCountries(List<String> selValues) {
		// TODO Auto-generated method stub

		DefaultListModel<String> select_countries = new DefaultListModel<String>();
		for (String c : selValues) {
			select_countries.addElement(c);
		}
		selected_country_list = new JList<String>(select_countries);
		selected_country_pane.setViewportView(selected_country_list);
		selected_country_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selected_country_list.setVisibleRowCount(8);

		DefaultListModel<String> avail_countries = new DefaultListModel<String>();
		List<Country> countries_all = gameMap.getCountries();
		for (Country c : countries_all) {
			if (c.getContinentName().isEmpty()) {
				avail_countries.addElement(c.getCountryName());
			}
		}
		available_country_list = new JList<String>(avail_countries);
		available_country_pane.setViewportView(available_country_list);
		available_country_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		available_country_list.setVisibleRowCount(8);
	}

	public void loadContinent(String selected_continent) {
		// TODO Auto-generated method stub
		continent_name_value.setText(selected_continent);
		DefaultListModel<String> select_countries = new DefaultListModel<String>();
		DefaultListModel<String> available_countries = new DefaultListModel<String>();
		List<Country> countries_all = gameMap.getCountries();
		for (Country c : countries_all) {
			if (c.getContinentName().equals(selected_continent)) {
				select_countries.addElement(c.getCountryName());
			} else {
				available_countries.addElement(c.getCountryName());
			}
		}
		selected_country_list = new JList<String>(select_countries);
		selected_country_pane.setViewportView(selected_country_list);
		selected_country_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selected_country_list.setVisibleRowCount(8);

		available_country_list = new JList<String>(available_countries);
		available_country_pane.setViewportView(available_country_list);
		available_country_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		available_country_list.setVisibleRowCount(8);
	}

	public void repaintAvailableCountries(List<String> availValues) {
		// TODO Auto-generated method stub

		DefaultListModel<String> select_countries = new DefaultListModel<String>();
		for(int i = 0; i< selected_country_list.getModel().getSize();i++){
			select_countries.addElement(selected_country_list.getModel().getElementAt(i));
        }
		
		DefaultListModel<String> avail_countries = new DefaultListModel<String>();
		
		for(int i = 0; i< available_country_list.getModel().getSize();i++){
			avail_countries.addElement(available_country_list.getModel().getElementAt(i));
        }
		
		for (String c : availValues) {
			avail_countries.addElement(c);
			select_countries.removeElement(c);
		}
		available_country_list = new JList<String>(avail_countries);
		available_country_pane.setViewportView(available_country_list);
		available_country_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		available_country_list.setVisibleRowCount(8);
		
		selected_country_list = new JList<String>(select_countries);
		selected_country_pane.setViewportView(selected_country_list);
		selected_country_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selected_country_list.setVisibleRowCount(8);
	}
}
