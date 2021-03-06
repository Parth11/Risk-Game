package ca.concordia.app.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import ca.concordia.app.component.MapEditorPanel;
import ca.concordia.app.model.Continent;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.util.ContinentColourMap;


/**
 * The Class MapEditorView generates the Map Editor view UI.
 *
 * @author Harvi
 */
public class MapEditorView extends JFrame implements IView {

	private static final long serialVersionUID = 1L;

	/** The window title. */
	private static String WINDOW_TITLE = "Create Map";

	/** The country name value. */
	public JTextField country_name_value;

	/** The map area. */
	public MapEditorPanel map_area;

	/** The country editor panel. */
	public JPanel country_editor_panel;

	/** The country name. */
	public JLabel country_name;

	/** The neighbours list. */
	public JList<String> neighbours_list;

	/** The save button. */
	public JButton save_button;

	/** The cancel button. */
	public JButton cancel_button;

	/** The next button. */
	public JButton next_button;

	/** The scroll pane. */
	private JScrollPane scroll_pane;

	/** The open dialog. */
	public JFileChooser open_dialog;
	
	/** The remove country button. */
	public JButton remove_country_button;

	/**
	 * Create the application.
	 */
	public MapEditorView() {
		getContentPane().setLayout(null);

		try {
			map_area = new MapEditorPanel();
			map_area.setBounds(12, 13, 1068, 813);
			map_area.map_area.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTitle("Map Editor View");
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

		scroll_pane = new JScrollPane();
		scroll_pane.setBounds(12, 147, 394, 382);
		country_editor_panel.add(scroll_pane);

		neighbours_list = new JList<String>();
		scroll_pane.setViewportView(neighbours_list);
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

		remove_country_button = new JButton("X");
		remove_country_button.setEnabled(true);
		remove_country_button.setBounds(359, 103, 45, 25);
		remove_country_button.setBackground(Color.RED);
		country_editor_panel.add(remove_country_button);
		
		this.setBounds(100, 100, 1550, 900);
		this.setTitle(WINDOW_TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Calls the setActionListener java method.
	 *
	 * @param actionListener the new action listener
	 */
	@Override
	public void setActionListener(ActionListener actionListener) {
		next_button.addActionListener(actionListener);
		save_button.addActionListener(actionListener);
		cancel_button.addActionListener(actionListener);
		remove_country_button.addActionListener(actionListener);
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.view.IView#setMouseListener(java.awt.event.MouseListener)
	 */
	/**
	 * sets the mouse listener 
	 * @param mouseListener mouse listener
	 */
	@Override
	public void setMouseListener(MouseListener mouseListener) {
		map_area.setMouseListener(mouseListener);
	}

	/**
	 * Repaint neighbours.
	 */
	public void repaintNeighbours() {
		GameMap gameMap = GameMap.getInstance();
		DefaultListModel<String> neighbours = new DefaultListModel<String>();

		for (Country c : gameMap.getCountries()) {
			neighbours.addElement(c.getCountryName());
		}
		neighbours_list = new JList<>(neighbours);
		scroll_pane.setViewportView(neighbours_list);
		neighbours_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		neighbours_list.setVisibleRowCount(8);
	}

	/**
	 * Connect neighbours.
	 *
	 * @param name the name
	 */
	public void connectNeighbours(String name) {
		map_area.map_area.connectNeighbours(name);

	}

	/**
	 * Paint loaded map.
	 */
	public void paintLoadedMap() {
		GameMap gameMap = GameMap.getInstance();
		
		ContinentColourMap.resetColors();

		for (Continent c : gameMap.getContinents()) {
			try {
				ContinentColourMap.setContinentColour(c.getContinentName());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		for (Country c : gameMap.getCountries()) {
			map_area.map_area.drawCountry(c.getCountryName(), c.getLocX(), c.getLocY(),
					ContinentColourMap.getContinentColour(c.getContinentName()));
			map_area.map_area.connectNeighbours(c.getCountryName());
		}

		repaintNeighbours();

	}
}
