
/**
 * 
 */
package ca.concordia.app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

import ca.concordia.app.util.CountryComparator;

/**
 * @author harvi
 *
 */
public class GameMap {
	
	private static GameMap instance;
	
	private List<Country> countries;
	
	private List<Continent> continents;
	
	private HashMap<Country, ArrayList<String>> territories;

	private GameMap(){
		this.countries = new ArrayList<Country>();
		this.continents = new ArrayList<Continent>();
		this.territories = new HashMap<Country, ArrayList<String>>();
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

	public HashMap<Country, ArrayList<String>> getTerritories() {
		return territories;
	}

	public void setTerritories(HashMap<Country, ArrayList<String>> territories) {
		this.territories = territories;
	}
	
	public Country getCountryByName(String countryName){
		Country c = new Country(countryName, 0, 0, "");
		if(this.countries.indexOf(c)>=0){
			return this.countries.get(this.countries.indexOf(c));
		}
		return null;
	}

	public Continent getContinentByName(String continentName) {
		// TODO Auto-generated method stub
		Continent c = new Continent(continentName,0);
		if(this.continents.indexOf(c)>=0){
			return this.continents.get(this.continents.indexOf(c));
		}
		return null;
	}
	
	public String getCountryNeighboursAsCSV(Country c){
		StringBuffer sb = new StringBuffer();
		for(String s : GameMap.getInstance().getTerritories().get(c)){
			sb.append(s).append(",");
		}
		return sb.toString().substring(0, sb.length()-1);
	}
}
