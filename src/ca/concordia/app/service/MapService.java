package ca.concordia.app.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ca.concordia.app.model.Continent;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.util.CountryComparator;
import ca.concordia.app.util.GameConstants;
import ca.concordia.app.util.MapValidationException;
import ca.concordia.app.util.RiskExceptionHandler;

/**
 * The Class MapService.
 */
public class MapService {

	/** The instance. */
	public static MapService instance;
	
	public ArrayList<String> tournamentMaps= new ArrayList<>();
	
	/**
	 * Gets the single instance of MapService.
	 *
	 * @return single instance of MapService
	 */
	public static MapService getInstance() {
		if(instance==null)
			instance= new MapService();
		return instance;
	}
	
	/**
	 * Creates the map.
	 *
	 * @param savePath the save path
	 */
	public void createMap(String savePath) {
		
		GameMap gameMap = GameMap.getInstance();
		
		List<String> lines = new ArrayList<String>();
		
		lines.add(GameConstants.MAP_HEADER_CONST);
		lines.add("author="+GameConstants.MAP_AUTHOR);
		lines.add("warn=yes");
		lines.add("image=noname.bmp");
		lines.add("wrap=no");
		lines.add("scroll=horizontal");
		lines.add("");
		lines.add(GameConstants.CONTINENT_HEADER_CONST);
		
		for(Continent c : gameMap.getContinents()){
		
			lines.add(c.getContinentName()+"="+c.getControlValue());
		}
		
		lines.add("");
		
		lines.add(GameConstants.TERRITORY_HEADER_CONST);
		
		List<Country> countries = gameMap.getCountries();
		
		Collections.sort(countries, new CountryComparator());
		
		String previous = null;
		
		for(Country cn : countries){
			if(countries.indexOf(cn)==0){
				previous = cn.getContinentName();
			}
			else if(!previous.equals(cn.getContinentName())){
				previous = cn.getContinentName();
				lines.add("");
			}
			
				StringBuffer sb = new StringBuffer(cn.getCountryName()).
									append(",").append(cn.getLocX()).
										append(",").append(cn.getLocY()).
											append(",").append(cn.getContinentName()).
												append(",").append(gameMap.getCountryNeighboursAsCSV(cn));
				
				lines.add(sb.toString());
			
		}
		
		Path path = Paths.get(savePath);
		
		try {
			Files.write(path, lines,StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Load map.
	 *
	 * @param path the path
	 * @return the game map
	 * @throws MapValidationException the map validation exception
	 * @throws URISyntaxException the URI syntax exception
	 */
	public GameMap loadMap(String path) throws MapValidationException, URISyntaxException {
		GameMap gameMap = null;
		gameMap = GameMap.getInstance();
		List<String> list = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(path).toURI()))) {

			list = br.lines().collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}

		extractFileInformation(gameMap, list);

		return gameMap;
	}

	/**
	 * Extract file information.
	 *
	 * @param gameMap the game map
	 * @param list the list
	 * @throws MapValidationException the map validation exception
	 */
	private void extractFileInformation(GameMap gameMap, List<String> list) throws MapValidationException {
		
		if(list.indexOf(GameConstants.CONTINENT_HEADER_CONST)<0){
			throw new MapValidationException("Map does not declare continents");
		}
		
		if(list.indexOf(GameConstants.TERRITORY_HEADER_CONST)<0){
			throw new MapValidationException("Map does not declare territory");
		}
		
		
		List<String> metaContinents = list.subList(list.indexOf(GameConstants.CONTINENT_HEADER_CONST) + 1,
				list.indexOf(GameConstants.TERRITORY_HEADER_CONST) - 1);

		
		List<String> metaTerritories = list.subList(list.indexOf(GameConstants.TERRITORY_HEADER_CONST) + 1, list.size());

		if(metaContinents.size()==0) {
			throw new MapValidationException("Map does not have any continents");
		}
		parseContinents(metaContinents,gameMap);
		
		if(metaTerritories.size()==0) {
			throw new MapValidationException("Map does not have any territory");
		}
		parseCountries(metaTerritories,gameMap);

		isTraversable();
	}
	
	/**
	 * Checks if is traversable.
	 *
	 * @throws MapValidationException the map validation exception
	 */
	protected void isTraversable() throws MapValidationException{

		GamePlayService game = GamePlayService.getInstance();
		for(Country c1 : game.getMap().getCountries()){
			for(Country c2 : game.getMap().getCountries()){
				if(!c1.equals(c2)){
					if(game.isConnected(c1, c2)==false){
						throw new MapValidationException(c1.getCountryName()+" and "+c2.getCountryName()+" are disconnected. Invalid Map");
					}
				}
			}
		}
	}

	/**
	 * Load map.
	 *
	 * @param mapFile the map file
	 * @return the game map
	 * @throws MapValidationException the map validation exception
	 */
	public GameMap loadMap(File mapFile) throws MapValidationException{
		GameMap gameMap = GameMap.getInstance();
		List<String> list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {

				list = br.lines().collect(Collectors.toList());

		} catch (IOException e) {
				e.printStackTrace();
		}

		extractFileInformation(gameMap, list);
		validateContinentNeighbours(gameMap);
			
		return gameMap;
	}

	private void validateContinentNeighbours(GameMap gameMap) throws MapValidationException{
		// TODO Auto-generated method stub
		List<Continent> continents=gameMap.getContinents();
		int neighbourCount=0;
		for(Continent continent: continents){
			List<Country> countryList=gameMap.getCountriesByContinent(continent.getContinentName());
			for(Country c: countryList){
				List<Country> neighbours=gameMap.getNeighbourCountries(c);
				neighbourCount=0;
				for(Country neighbour:neighbours){
					if(neighbour.getContinentName().equals(continent.getContinentName())){
						neighbourCount++;
					}
				}
			}
			if(neighbourCount==0)
				throw new MapValidationException("Disconnected Countries belongs to same Continent");
		}
	}

	/**
	 * Parses the countries.
	 *
	 * @param metaTerritories the meta territories
	 * @param gameMap the game map
	 * @throws MapValidationException the map validation exception
	 */
	private void parseCountries(List<String> metaTerritories, GameMap gameMap) throws MapValidationException{
		List<Country> countries = new ArrayList<>();

		for (String c : metaTerritories) 
		{
			
			if(c.isEmpty()){
				continue;
			}
			
			String[] metaData = c.split(",");
			if(metaData.length<5) {
				throw new MapValidationException("Map does not contain valid country");
			}
			int country_x=Integer.parseInt(metaData[1].trim());
			int country_y=Integer.parseInt(metaData[2].trim());
			String continent = metaData[3].trim();
			if(country_x<0 || country_y<0) {
				throw new MapValidationException("Map does not contain valid (x,y) coordinates");
			}
			if(metaData[0].trim().isEmpty() || continent.isEmpty()||metaData[4].trim().isEmpty())
			{
				throw new MapValidationException("Map does not contain valid country values");
			}
			if(gameMap.getContinentByName(continent)==null){
				throw new MapValidationException(continent+" is not a valid continent");
			}
			
			Country ctry = new Country(metaData[0].trim(), country_x,	country_y, continent);
			countries.add(ctry);
			ArrayList<String> t = new ArrayList<String>();
			for(String x : Arrays.asList(Arrays.copyOfRange(metaData, 4, metaData.length))){
				t.add(x.trim());
			}
			gameMap.getTerritories().put(ctry,t);
		}

		gameMap.setCountries(countries);
		
		validateTerriotoriesAsValidCountries();
		
	}

	/**
	 * Parses the continents.
	 *
	 * @param metaContinents the meta continents
	 * @param gameMap the game map
	 * @throws MapValidationException the map validation exception
	 */
	private void parseContinents(List<String> metaContinents, GameMap gameMap) throws MapValidationException{

		List<Continent> continents = new ArrayList<>();

		for (String c : metaContinents) {
			
			if(c.isEmpty()){
				continue;
			}
			
			String[] metaData = c.split("=");
			if(metaData.length<2) {
				throw new MapValidationException("Map does not contain valid continent");
			}
			int control_value=Integer.parseInt(metaData[1]);
			if(control_value<0)
			{	
				throw new MapValidationException("Map does not contain valid control value");
			}
			continents.add(new Continent(metaData[0].trim(), control_value,null));
		}

		gameMap.setContinents(continents);
	}
	
	/**
	 * Validate terriotories as valid countries.
	 *
	 * @throws MapValidationException the map validation exception
	 */
	private void validateTerriotoriesAsValidCountries() throws MapValidationException{
		GameMap gameMap = GameMap.getInstance();
		
		for(Country c : gameMap.getCountries()){
			for(String s : gameMap.getTerritories().get(c)){
				Country cTerritory = gameMap.getCountryByName(s);
				if(cTerritory==null){
					throw new MapValidationException(s+" is not a valid country");
				}
			}
		}
		
	}
	
	/**
	 * Removes the country from map.
	 *
	 * @param countryName the country name
	 */
	public void removeCountryFromMap(String countryName){
		GameMap gameMap = GameMap.getInstance();
		Country c = gameMap.getCountryByName(countryName);
		
		if(c!=null){
		
			if(gameMap.getCountries().remove(c)){
				for(Country cn : gameMap.getTerritories().keySet()){
					gameMap.getTerritories().get(cn).remove(c.getCountryName());
				}
			}
			gameMap.getTerritories().remove(c);
			
		}
		
	}

	/**
	 * Link remaining neighbours.
	 *
	 * @param neighbours the neighbours
	 */
	public void linkRemainingNeighbours(List<String> neighbours) {
		
		GameMap gameMap = GameMap.getInstance();
		
		for(String c1 : neighbours){
			Country c = gameMap.getCountryByName(c1);
			for(String c2 : neighbours){
				if(!c1.equals(c2)){
					if(!gameMap.getTerritories().get(c).contains(c2)){
						gameMap.getTerritories().get(c).add(c2);
					}
				}
			}
		}
		
	}

	public void addTournamentMap(String absolutePath){
		// TODO Auto-generated method stub
			tournamentMaps.add(absolutePath);
	}

}
