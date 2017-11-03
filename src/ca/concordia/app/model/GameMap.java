
package ca.concordia.app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * This Class parses the map file and creates countries territories continents and neighboring countries
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
	
	/**
	 * Singleton 
	 * @return the instance of gameMap class
	 */
	public static GameMap getInstance(){
		
		if(instance == null){
			instance = new GameMap();
		}
		
		return instance;
	}
	
	/**
	 * 
	 * @return the country list
	 */
	public List<Country> getCountries() {
		return countries;
	}
	/**
	 * sets the countries which are parsed the the countries list.
	 * @param countries
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	/**
	 * 
	 * @return the continent list.
	 */
	public List<Continent> getContinents() {
		return continents;
	}
	
	/**
	 * Sets the continents which are parsed the the continents list.
	 * @param continents
	 */
	public void setContinents(List<Continent> continents) {
		this.continents = continents;
	}
	
	/**
	 * 
	 * @return The territories list
	 */
	public HashMap<Country, ArrayList<String>> getTerritories() {
		return territories;
	}

	/**
	 * Sets the territories which are parsed the the continents list.
	 * @param territories 
	 */
	public void setTerritories(HashMap<Country, ArrayList<String>> territories) {
		this.territories = territories;
	}
	
	/**
	 * 
	 * @param countryName
	 * @return the country by name
	 */
	public Country getCountryByName(String countryName){
		Country c = new Country(countryName, 0, 0, "");
		if(this.countries.indexOf(c)>=0){
			return this.countries.get(this.countries.indexOf(c));
		}
		return null;
	}
	/**
	 * 
	 * @param continentName
	 * @return Continent by name
	 */
	public Continent getContinentByName(String continentName) {
		// TODO Auto-generated method stub
		Continent c = new Continent(continentName,0,null);
		if(this.continents.indexOf(c)>=0){
			return this.continents.get(this.continents.indexOf(c));
		}
		return null;
	}
	
	/**
	 * 
	 * @param c
	 * @return Country neighbor as per the file loaded.
	 */
	public String getCountryNeighboursAsCSV(Country c){
		StringBuffer sb = new StringBuffer();
		for(String s : GameMap.getInstance().getTerritories().get(c)){
			sb.append(s).append(",");
		}
		return sb.toString().substring(0, sb.length()-1);
	}
	
	/**
	 * 
	 * @param continentName
	 * @return Countries in the continent
	 */
	public List<Country> getCountriesByContinent(String continentName) {
		List<Country> continentCountry= new ArrayList<>();
		for(Country c: getCountries()){
			if(c.getContinentName().equals(continentName)){
				continentCountry.add(c);
			}
		}
		return continentCountry;
	}
	
	/**
	 * 
	 * @param country
	 * @return Neighboring countries
	 */
	public List<Country> getNeighbourCountries(Country country){
		List<Country> neighbourCountry= new ArrayList<>();
		for(String neighbourC: territories.get(country)){
			neighbourCountry.add(getCountryByName(neighbourC));
		}
		return neighbourCountry;
	}
}
