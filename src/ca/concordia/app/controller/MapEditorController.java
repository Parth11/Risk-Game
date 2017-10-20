package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.service.MapService;
import ca.concordia.app.util.MapValidationException;
import ca.concordia.app.util.RiskExceptionHandler;
import ca.concordia.app.view.MapEditorView;


/**
 * The Class MapEditorController manages map editor frame.
 * 
 * @author Harvi and Hardik
 */
public class MapEditorController implements ActionListener, MouseListener{
	
	/** 
	 * creates an object of MapEditorView class.
	 */
	public MapEditorView map_editor_view;
	
	/**
	 *  Creates object of MapService class.
	 */
	MapService map_service;
	
	/** 
	 * creates object of GameMap
	 */
	GameMap game_map;
	
	/** The temp y. */
	int temp_x,temp_y;
	
	/**
	 * Instantiates a new map editor controller.
	 *
	 * @param edit the edit
	 */
	public MapEditorController(boolean edit) {
		
		game_map = GameMap.getInstance();
		
		map_service= MapService.getInstance();
		
		map_editor_view = new MapEditorView();
		map_editor_view.setActionListener(this);
		map_editor_view.setMouseListener(this);
		map_editor_view.setVisible(true);
		
		Thread.setDefaultUncaughtExceptionHandler(new RiskExceptionHandler(map_editor_view));
		
		if(edit){
			map_editor_view.open_dialog = new JFileChooser();
			
			int retVal = map_editor_view.open_dialog.showOpenDialog(map_editor_view);
			
			if(retVal == JFileChooser.APPROVE_OPTION){
				
				File file = map_editor_view.open_dialog.getSelectedFile();
				
				if(file.exists()){
					try {
						map_service.loadMap(file);
					} catch (MapValidationException e) {
						JOptionPane.showMessageDialog(map_editor_view, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
						return;
					}
					map_editor_view.paintLoadedMap();
				}
				
			}
			
		}
		
	}
	
	/**
	 * Reload map.
	 */
	private void reloadMap(){
		
		map_editor_view.dispose();
		map_editor_view = new MapEditorView();
		map_editor_view.setActionListener(this);
		map_editor_view.setMouseListener(this);
		map_editor_view.setVisible(true);
		
		int retVal = JOptionPane.showConfirmDialog(map_editor_view, "Reload Map?", "Confirm", JOptionPane.OK_OPTION);
		
		if(retVal == JOptionPane.OK_OPTION){
			map_editor_view.paintLoadedMap();
		}
		
	}

	/**
	 * Calls the mouseClicked method of java.
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(map_editor_view.map_area.map_area)){
			temp_x = e.getX();
			temp_y = e.getY();
			map_editor_view.map_area.map_area.drawCountry("",e.getX(),e.getY(),null);
			map_editor_view.save_button.setEnabled(true);
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
	 * calls the method actionPerformed for map editing.
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(map_editor_view.save_button)){
			
			Country c = new Country(map_editor_view.country_name_value.getText().trim(), 
					temp_x, temp_y, "");
			
			game_map.getCountries().add(c);
			
			List<String> selValues = map_editor_view.neighbours_list.getSelectedValuesList();
			
			
			@SuppressWarnings("unchecked")
			HashMap<Country, ArrayList<String>> territories = (HashMap<Country, ArrayList<String>>) game_map.getTerritories().clone(); 
			
			ArrayList<String> selectedNeighbours = new ArrayList<>(selValues);
			
			territories.put(c, selectedNeighbours);
			
			HashMap<String, List<String>> crossLinks = new HashMap<String,List<String>>();
			
			ArrayList<String> sNeighbours;
			
			for(String s : selectedNeighbours){
				sNeighbours = (ArrayList<String>) territories.get(game_map.getCountryByName(s));
				sNeighbours.add(c.getCountryName());
				crossLinks.put(s, sNeighbours);
			}
			
			game_map.setTerritories(territories);
			
			map_editor_view.repaintNeighbours();
			
			map_editor_view.connectNeighbours(c.getCountryName());
			
			map_editor_view.country_name_value.setText("");
			
			map_editor_view.save_button.setEnabled(false);
			
			map_editor_view.next_button.setEnabled(true);
		}
		else if(e.getSource().equals(map_editor_view.next_button)){
			
			new MapEditorContinentController();
			map_editor_view.dispose();
			
		}
		else if(e.getSource().equals(map_editor_view.cancel_button)){
			new MainController();
			map_editor_view.dispose();
		}
		else if(e.getSource().equals(map_editor_view.remove_country_button)){
			int retVal = JOptionPane.showConfirmDialog(map_editor_view, "Do you really want to delete this country?", "Confirm", JOptionPane.YES_NO_OPTION);
		
			if(retVal == JOptionPane.NO_OPTION){
				return;
			}
			else if(retVal == JOptionPane.YES_OPTION){
				String countryName = map_editor_view.neighbours_list.getSelectedValue();
				List<String> neighbours = GameMap.getInstance().getTerritories().get(GameMap.getInstance().getCountryByName(countryName));
				
				
				map_service.removeCountryFromMap(countryName);
				map_service.linkRemainingNeighbours(neighbours);
				reloadMap();
				map_editor_view.next_button.setEnabled(true);
			}
		}
	}

}
