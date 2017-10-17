package lib.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author harvi
 * @modified by Parth Nayak
 * 
 */

public class GameMap {
	private static GameMap instance;
	
	private List<Country> countries;
	
	private List<Continent> continents;
	
	private TreeMap<Country, List<Country>> territories;	// neighbours
	
	private TreeMap<Country, List<String>> tempTerritories;
	
	private boolean MAP_LOADED = false;

	private GameMap(){
	this.countries = new ArrayList<>();
		this.continents = new ArrayList<>();
		//this.territories = new TreeMap<Country, List<Country>>(new CountryComparator());
		this.territories = new TreeMap<Country, List<Country>>();
		this.tempTerritories = new TreeMap<Country, List<String>>();
	}
	
	public static GameMap getInstance(){
		if(instance == null)
			instance = new GameMap();
		return instance;
	}
	
	public boolean isMapLoaded() {
		return MAP_LOADED;
	}

	
	//to get countries value
	public Country getCountry(String countryName) {
		for(Country c : countries)
			if(c.getName().equals(countryName))
				return c;
		return null;
	}
	
	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
		initCountryNetwork();
		MAP_LOADED = true;
	}
	
	public List<Country> getCountriesInContinent(Continent c) {
		return c.getCountriesList();
	}
	
	
	//to get continent value
	public Continent getContinent(String continentName) {
		for(Continent c : continents)
			if(c.getName().equals(continentName))
				return c;
		return null;
	}
	
	public List<Continent> getContinents() {
		return continents;
	}

	public void setContinents(List<Continent> continents) {
		this.continents = continents;
	}

	
	//For neighbouring territories
	public void addNeighbour(Country c, List<String> neighbours) {
		tempTerritories.put(c, neighbours);
	}
	
	public List<Country> getNeighbours(Country c) {
		return territories.get(c);
	}
	
	public TreeMap<Country, List<Country>> getTerritories() {
		return territories;
	}

	public void setTerritories(TreeMap<Country, List<Country>> territories) {
		this.territories = territories;
	}
	
	// final initialization
	private void initCountryNetwork() {
		for(Entry<Country, List<String>> set: tempTerritories.entrySet()) {
			List<Country> countries = new ArrayList<>();
			for(String s : set.getValue()) {
				Country country = getCountry(s);
				if(country!=null)
					countries.add(country);
			}
			territories.put(set.getKey(), countries);
		}
		tempTerritories = null;
	}

}
