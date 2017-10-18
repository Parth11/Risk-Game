package lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import java.util.Map.Entry;

import ca.concordia.app.model.Continent;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;

/**
 * @author AbhinavSingh
 * @author parthnayak 
 * 
 */

public class DbConverter {
	public static void convert(GameMap gm, lib.model.GameMap gameMap) {
		// 1. converting continents
		List<Continent> continents = gm.getContinents();
		List<lib.model.Continent> myContinents = new ArrayList<>();
		for(Continent c : continents) {
			lib.model.Continent con = new lib.model.Continent(c.getContinentName(), c.getControlValue(), c.getColor().toString());
			myContinents.add(con);
		}
		gameMap.setContinents(myContinents);
		
		// 2. converting neighbors
		HashMap<Country, ArrayList<String>> neighbours = gm.getTerritories();
		for(Entry<Country, ArrayList<String>> e : neighbours.entrySet()) {
			lib.model.Country c = new lib.model.Country(e.getKey().getCountryName(), e.getKey().getLocX(), e.getKey().getLocy(), gameMap.getContinent(e.getKey().getContinentName()), null, 0);
			gameMap.addNeighbour(c, e.getValue());
		}
		
		// 3. converting countries
		List<Country> countries = gm.getCountries();
		List<lib.model.Country> myCountries = new ArrayList<>();
		for(Country c : countries) {
			lib.model.Country con = new lib.model.Country(c.getCountryName(), c.getLocX(), c.getLocy(), gameMap.getContinent(c.getContinentName()), null, 0);
			myCountries.add(con);
		}
		gameMap.setCountries(myCountries);
	}
	
	public static void print() {
		lib.model.GameMap gameMap = lib.model.GameMap.getInstance();
		for(lib.model.Country c : gameMap.getCountries()){
			System.out.print(c.getName()+"( belongs to '"+c.getContinent().getName()+"') : [");
			
			if(gameMap.getTerritories().get(c)!=null)
				for(lib.model.Country e : gameMap.getTerritories().get(c)) 
					if(e!=null)
						System.out.print(e.getName()+", ");
			
			System.out.println("]\n");
		}
	}

}
