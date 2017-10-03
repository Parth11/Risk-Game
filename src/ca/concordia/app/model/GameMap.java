/**
 * 
 */
package ca.concordia.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import ca.concordia.app.util.CountryComparator;

/**
 * @author harvi
 *
 */
public class GameMap {
	
	private static GameMap instance;
	
	private List<Country> countries;
	
	private List<Continent> continents;
	
	private TreeMap<Country, List<String>> territories;

	private GameMap(){
		this.countries = new ArrayList<Country>();
		this.continents = new ArrayList<Continent>();
		this.territories = new TreeMap<Country, List<String>>(new CountryComparator());
	}
	
	public static GameMap getInstance(){
		
		if(instance == null){
			instance = new GameMap();
		}
		
		return instance;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public List<Continent> getContinents() {
		return continents;
	}

	public void setContinents(List<Continent> continents) {
		this.continents = continents;
	}

	public TreeMap<Country, List<String>> getTerritories() {
		return territories;
	}

	public void setTerritories(TreeMap<Country, List<String>> territories) {
		this.territories = territories;
	}
	
}
