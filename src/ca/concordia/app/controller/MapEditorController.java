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
import ca.concordia.app.service.CreateMapService;
import ca.concordia.app.util.MapValidationException;
import ca.concordia.app.util.RiskExceptionHandler;
import ca.concordia.app.view.MapEditorView;

public class MapEditorController implements ActionListener, MouseListener{
	
	public MapEditorView map_editor_view;
	
	GameMap gameMap;
	
	int tempX,tempY;
	
	public MapEditorController(boolean edit) {
		
		
		gameMap = GameMap.getInstance();
		
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
						CreateMapService.loadMap(file);
					} catch (MapValidationException e) {
						JOptionPane.showMessageDialog(map_editor_view, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
						return;
					}
					map_editor_view.paintLoadedMap();
				}
				
			}
			
		}
		
	}
	
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

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println(e);
		if(e.getSource().equals(map_editor_view.map_area.mapArea)){
			tempX = e.getX();
			tempY = e.getY();
			map_editor_view.map_area.mapArea.drawCountry("",e.getX(),e.getY(),null);
			map_editor_view.save_button.setEnabled(true);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(map_editor_view.save_button)){
			
			Country c = new Country(map_editor_view.country_name_value.getText().trim(), 
					tempX, tempY, "");
			
			gameMap.getCountries().add(c);
			
			List<String> selValues = map_editor_view.neighbours_list.getSelectedValuesList();
			
			
			@SuppressWarnings("unchecked")
			HashMap<Country, ArrayList<String>> territories = (HashMap<Country, ArrayList<String>>) gameMap.getTerritories().clone(); 
			
			ArrayList<String> selectedNeighbours = new ArrayList<>(selValues);
			
			territories.put(c, selectedNeighbours);
			
			HashMap<String, List<String>> crossLinks = new HashMap<String,List<String>>();
			
			ArrayList<String> sNeighbours;
			
			for(String s : selectedNeighbours){
				sNeighbours = (ArrayList<String>) territories.get(gameMap.getCountryByName(s));
				sNeighbours.add(c.getCountryName());
				crossLinks.put(s, sNeighbours);
			}
			
			gameMap.setTerritories(territories);
			
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
				CreateMapService.removeCountryFromMap(countryName);
				CreateMapService.linkRemainingNeighbours(neighbours);
				reloadMap();
				map_editor_view.next_button.setEnabled(true);
			}
		}
	}

}
