/**
 * 
 */
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.concordia.app.model.Continent;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.view.MapEditorContinentView;

/**
 * @author harvi
 *
 */
public class MapEditorContinentController implements ActionListener,MouseListener {
	
	MapEditorContinentView map_continent_view;
	GameMap gameMap;
	
	public MapEditorContinentController() {

		gameMap=GameMap.getInstance();
		
		map_continent_view = new MapEditorContinentView();
		map_continent_view.setActionListener(this);
		map_continent_view.setMouseListener(this);
		map_continent_view.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("new :"+e);
		if(e.getSource().equals(map_continent_view.continent_list))
		{
			int index = map_continent_view.continent_list.locationToIndex(e.getPoint());
	          if (index >= 0) {
	            String selected_continent = map_continent_view.continent_list.getModel().getElementAt(index);
	            map_continent_view.loadContinent(selected_continent);
	          }
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(map_continent_view.save_button)){
			Continent continent = new Continent(map_continent_view.continent_name_value.getText().trim(),0);
			gameMap.getContinents().add(continent);
			
			map_continent_view.repaintContinents();
			
		}
		else if(e.getSource().equals(map_continent_view.add_button))
		{	
			List<String> selValues = map_continent_view.available_country_list.getSelectedValuesList();
			
			HashMap<Country, ArrayList<String>> territories= gameMap.getTerritories();
			
			ArrayList<String> sNeighbours = null;
			List<String> neighbour_all= new ArrayList<>();
			for(String c:selValues) {
				gameMap.getCountryByName(c).setContinentName(map_continent_view.continent_name_value.getText());
				sNeighbours = (ArrayList<String>) territories.get(gameMap.getCountryByName(c));
				for(String neighbour: sNeighbours) {
					neighbour_all.add(neighbour);
					gameMap.getCountryByName(neighbour).setContinentName(map_continent_view.continent_name_value.getText());
				}
				
			}
			
			selValues.addAll(neighbour_all);
			
			map_continent_view.repaintSelectedCountries(selValues);
		}
		else if(e.getSource().equals(map_continent_view.remove_button)){
			List<String> selValues = map_continent_view.selected_country_list.getSelectedValuesList();
			
			HashMap<Country, ArrayList<String>> territories= gameMap.getTerritories();
			
			ArrayList<String> sNeighbours = null;
			List<String> neighbour_all= new ArrayList<>();
			for(String c:selValues) {
				gameMap.getCountryByName(c).setContinentName("");
				sNeighbours = (ArrayList<String>) territories.get(gameMap.getCountryByName(c));
				for(String neighbour: sNeighbours) {
					neighbour_all.add(neighbour);
					gameMap.getCountryByName(neighbour).setContinentName("");
				}
			}
			
			selValues.addAll(neighbour_all);
			
			map_continent_view.repaintAvailableCountries(selValues);
		}
		else if(e.getSource().equals(map_continent_view.next_button)){
			
		}
	}

}
