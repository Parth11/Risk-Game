
/**
 * 
 */
package ca.concordia.app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
		Continent c = new Continent(continentName,0,null);
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
	
	public List<Country> getCountriesByContinent(String continentName) {
		List<Country> continentCountry= new ArrayList<>();
		for(Country c: getCountries()){
			if(c.getContinentName().equals(continentName)){
				continentCountry.add(c);
			}
		}
		return continentCountry;
	}
	
	public List<Country> getNeighbourCountries(Country country){
		List<Country> neighbourCountry= new ArrayList<>();
		for(String neighbourC: territories.get(country)){
			neighbourCountry.add(getCountryByName(neighbourC));
		}
		return neighbourCountry;
	}
	
//	//new-- final initialization
//	private void initCountryNetwork() {
//		for(Entry<Country, List<String>> set: tempTerritories.entrySet()) {
//			List<Country> countries = new ArrayList<>();
//			for(String s : set.getValue()) {
//				Country country = getCountry(s);
//				if(country!=null)
//					countries.add(country);
//			}
//			territories.put(set.getKey(), countries);
//		}
//		tempTerritories = null;
//	}
//	//new--For neighbouring territories
//	public void addNeighbour(Country c, List<String> neighbours) {
//		tempTerritories.put(c, neighbours);
//	}
}
