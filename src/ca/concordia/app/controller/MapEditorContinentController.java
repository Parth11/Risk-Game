
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ca.concordia.app.model.Continent;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.service.MapService;
import ca.concordia.app.util.ContinentColourMap;
import ca.concordia.app.view.MapEditorContinentView;

/**
 * The Class MapEditorContinentController.
 *
 * @author harvi
 */
public class MapEditorContinentController implements ActionListener, MouseListener {

	/** The map continent view. */
	MapEditorContinentView map_continent_view;
	
	/** The game map. */
	GameMap game_map;

	/**
	 * Instantiates a new map editor continent controller.
	 */
	public MapEditorContinentController() {

		game_map = GameMap.getInstance();

		map_continent_view = new MapEditorContinentView();
		map_continent_view.setActionListener(this);
		map_continent_view.setMouseListener(this);
		map_continent_view.setVisible(true);
	}

	/**
	 * Calls the mouseClicked method of java which responds to the user input.
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(map_continent_view.continent_list)) {
			int index = map_continent_view.continent_list.locationToIndex(e.getPoint());
			if (index >= 0) {
				String selected_continent = map_continent_view.continent_list.getModel().getElementAt(index);
				map_continent_view.loadContinent(selected_continent);
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}


	/**
	 * Calls the actionPerformed method of java.
	 * Access the overall functionality of MapEditorController.java
	 * 
	 * @param e
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(map_continent_view.save_button)) {
			String continent_name = map_continent_view.continent_name_value.getText().trim();
			int controlValue = Integer.parseInt(map_continent_view.control_value.getText().trim());

			if (controlValue <= 0) {
				JOptionPane.showMessageDialog(map_continent_view, "Control Value must be a positive integer", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (game_map.getContinentByName(continent_name) == null) {
				try {
					ContinentColourMap.setContinentColour(continent_name);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(map_continent_view, e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				Continent continent = new Continent(continent_name,
						Integer.parseInt(map_continent_view.control_value.getText()),
						ContinentColourMap.getContinentColour(continent_name));
				game_map.getContinents().add(continent);

			}
			map_continent_view.repaintContinents();

		} else if (e.getSource().equals(map_continent_view.cancel_button)) {
			new MainController();
			map_continent_view.dispose();
		} else if (e.getSource().equals(map_continent_view.add_button)) {
			String selValue = map_continent_view.available_country_list.getSelectedValue();
			String continent = map_continent_view.continent_name_value.getText();

			game_map.getCountryByName(selValue).setContinentName(continent);

			map_continent_view.repaintSelectedCountries(continent);
		} else if (e.getSource().equals(map_continent_view.remove_button)) {
			String selValue = map_continent_view.selected_country_list.getSelectedValue();

			game_map.getCountryByName(selValue).setContinentName("");

			map_continent_view.repaintAvailableCountries(selValue);
		} else if (e.getSource().equals(map_continent_view.next_button)) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Map Files", "map");
			map_continent_view.save_dialog.setFileFilter(filter);
			map_continent_view.save_dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int retVal = map_continent_view.save_dialog.showSaveDialog(map_continent_view);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				File file = map_continent_view.save_dialog.getSelectedFile();
				MapService.getInstance().createMap(file.getAbsolutePath());
				map_continent_view.dispose();
				new MainController();
			}
		}
	}

}
