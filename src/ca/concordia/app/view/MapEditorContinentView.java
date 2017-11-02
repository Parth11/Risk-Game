package ca.concordia.app.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ca.concordia.app.model.Continent;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;

/**
 * The Class MapEditorContinentView.
 * @author Hardik
 */
public class MapEditorContinentView extends JFrame implements IView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The continent name value. */
	public JTextField continent_name_value;
	
	/** The selected country list. */
	public JList<String> continent_list, selected_country_list;
	
	/** The available country list. */
	public JList<String> available_country_list;

	/** The remove button. */
	public JButton add_button, save_button, next_button, remove_button;
	
	/** The available country pane. */
	public JScrollPane available_country_pane = new JScrollPane();
	
	/** The selected country pane. */
	public JScrollPane selected_country_pane = new JScrollPane();
	
	/** The continent pane. */
	public JScrollPane continent_pane = new JScrollPane();

	/** The game map. */
	public GameMap game_map = GameMap.getInstance();

	/** The select countries. */
	DefaultListModel<String> select_countries=new DefaultListModel<String>();
	
	/** The available countries. */
	DefaultListModel<String> available_countries=new DefaultListModel<String>();
	
	/** The continents. */
	DefaultListModel<String> continents=new DefaultListModel<String>();
	
	/** The control value. */
	public JTextField control_value;
	
	/** The save dialog. */
	public JFileChooser save_dialog;
	
	/** The cancel button. */
	public JButton cancel_button;
	
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
		selected_country_list.setVisibleRowCount(8);
		
		available_country_pane.setBounds(684, 107, 245, 316);
		this.getContentPane().add(available_country_pane);

		DefaultListModel<String> countries = new DefaultListModel<String>();
		for (Country c : game_map.getCountries()) {
			countries.addElement(c.getCountryName());
		}
		available_country_list = new JList<String>(countries);
		available_country_pane.setViewportView(available_country_list);
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
		continent_list.setVisibleRowCount(8);
		
		next_button = new JButton("Save Map");
		next_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		next_button.setBounds(826, 467, 103, 25);
		this.getContentPane().add(next_button);

		remove_button = new JButton(">>");
		remove_button.setBounds(604, 275, 68, 50);
		getContentPane().add(remove_button);
		

		JLabel lblControlValue = new JLabel("Control Value");
		lblControlValue.setBounds(684, 34, 91, 16);
		getContentPane().add(lblControlValue);
		
		control_value = new JTextField();
		control_value.setText("0");
		control_value.setBounds(787, 29, 142, 26);
		getContentPane().add(control_value);
		control_value.setColumns(10);
		
		cancel_button = new JButton("Cancel");
		cancel_button.setBounds(551, 467, 97, 25);
		getContentPane().add(cancel_button);
		
		JLabel lblListOfContinents = new JLabel("Continents");
		lblListOfContinents.setBounds(62, 79, 234, 16);
		getContentPane().add(lblListOfContinents);
		
		JLabel lblAddedCountries = new JLabel("Added Countries");
		lblAddedCountries.setBounds(347, 79, 245, 16);
		getContentPane().add(lblAddedCountries);
		
		JLabel lblAvailableCountries = new JLabel("Available Countries");
		lblAvailableCountries.setBounds(684, 79, 245, 16);
		getContentPane().add(lblAvailableCountries);

		save_dialog = new JFileChooser();
		
		repaintContinents();

	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.view.IView#setActionListener(java.awt.event.ActionListener)
	 */
	@Override
	public void setActionListener(ActionListener actionListener) {
		next_button.addActionListener(actionListener);
		save_button.addActionListener(actionListener);
		add_button.addActionListener(actionListener);
		remove_button.addActionListener(actionListener);
		cancel_button.addActionListener(actionListener);
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.view.IView#setMouseListener(java.awt.event.MouseListener)
	 */
	@Override
	public void setMouseListener(MouseListener mouseListener) {
		continent_list.addMouseListener(mouseListener);
	}

	/**
	 * Repaint continents.
	 */
	public void repaintContinents() {
		continents.removeAllElements();
		for (Continent c : game_map.getContinents()) {
			continents.addElement(c.getContinentName());
		}
		continent_list.setModel(continents);

		select_countries.removeAllElements();
		available_countries.removeAllElements();
		for (Country c : game_map.getCountries()) {
			if(c.getContinentName().isEmpty())
				available_countries.addElement(c.getCountryName());
		}
		available_country_list.setModel(available_countries);
	}
	
	/**
	 * Load continent.
	 *
	 * @param selected_continent the selected continent
	 */
	public void loadContinent(String selected_continent) {
		continent_name_value.setText(selected_continent);
		Continent continent= game_map.getContinentByName(selected_continent);
		control_value.setText(continent.getControlValue()+"");
		
		select_countries.removeAllElements();
		available_countries.removeAllElements();
		
		List<Country> countries_all = game_map.getCountries();
		for (Country c : countries_all) {
			if (c.getContinentName().equals(selected_continent)) {
				select_countries.addElement(c.getCountryName());
			} else if (c.getContinentName().isEmpty()) {
					available_countries.addElement(c.getCountryName());
			}
		}
		selected_country_list.setModel(select_countries);

		available_country_list.setModel(available_countries);
	}
	
	/**
	 * Repaint selected countries.
	 *
	 * @param selValue the sel value
	 */
	public void repaintSelectedCountries(String selValue) {
		select_countries.removeAllElements();
		available_countries.removeAllElements();
		for (Country c : game_map.getCountries()) {
			if(c.getContinentName().equals(selValue)) {
				select_countries.addElement(c.getCountryName());
			}
		}
		selected_country_list.setModel(select_countries);
	
		
		ArrayList<String> neighbours;
		
		for(int i = 0; i< select_countries.getSize();i++){
			Country c=game_map.getCountryByName(select_countries.get(i));
			neighbours=game_map.getTerritories().get(c);
			for (String s : neighbours) {
				if(game_map.getCountryByName(s).getContinentName().isEmpty())
					if(!available_countries.contains(s))
						available_countries.addElement(s);
			}
        }

		
		available_country_list.setModel(available_countries);
	}

	/**
	 * Repaint available countries.
	 *
	 * @param selValue the sel value
	 */
	public void repaintAvailableCountries(String selValue) {
		available_countries.removeAllElements();
		
		if(select_countries.getSize()==1) {
			for (Country c : game_map.getCountries()) {
				if(c.getContinentName().isEmpty()) {
					available_countries.addElement(c.getCountryName());
				}
			}
			
		}
		else {
			ArrayList<String> neighbours=new ArrayList<>();
			
			for(int i = 0; i< select_countries.getSize();i++){
				Country c=game_map.getCountryByName(select_countries.get(i));
				neighbours=game_map.getTerritories().get(c);
				for (String s : neighbours) {
					if(game_map.getCountryByName(s).getContinentName().isEmpty())
						if(!available_countries.contains(s))
							available_countries.addElement(s);
				}
	        }
			
		}
		available_country_list.setModel(available_countries);
		select_countries.removeElement(selValue);
		selected_country_list.setModel(select_countries);
	}
}
