package lib.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import lib.model.Continent;
import lib.model.Country;
import lib.model.GameMap;

/**
 * @author harvi
 * @modified by Parth Nayak
 * 
 */
public class CreateMapService {
	
	private static CreateMapService crm=null;
	
	private static final String MapLabel = "[Map]", ContinentLabel = "[Continents]", TerritoryLabel = "[Territories]";
	
	public static CreateMapService getInstance() {
		if(crm==null)
			crm = new CreateMapService();
		return crm;
	}
	
	private CreateMapService() {
		
	}

	/*public void createDemoMap(GameMap gameMap) {
		//loadMap(gameMap, path);
		loadMap(gameMap, "Europe.map");
	}*/

	public void loadMap(GameMap gameMap, String path) {
		try {
			gameMap.getCountries().clear();
			gameMap.getContinents().clear();
			gameMap.getTerritories().clear();
			
			// Storage for file content
			List<String> list = new ArrayList<>();

			// read map data as "list-of-strings"
			try (BufferedReader br = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(path).toURI()))) {

				// fetch whole file in a stream & collect it into a list of strings
				list = br.lines().collect(Collectors.toList());

			} catch (IOException e) {
				e.printStackTrace();
			}

			// get map-meta-data as list-of-strings
			List<String> metaMap = list.subList(list.indexOf(MapLabel) + 1, list.indexOf(ContinentLabel) - 1);

			// get continents as list-of-strings
			List<String> metaContinents = list.subList(list.indexOf(ContinentLabel) + 1, list.indexOf(TerritoryLabel) - 1);

			// get territories as list-of-strings
			List<String> metaTerritories = list.subList(list.indexOf(TerritoryLabel) + 1, list.size());

			// parse continents-list
			parseContinents(metaContinents,gameMap);

			// parse territories-list
			parseCountries(metaTerritories,gameMap);

			// print
			for(Country c : gameMap.getCountries()){
				System.out.print(c.getName()+"( belongs to '"+c.getContinent().getName()+"') : [");
				
				if(gameMap.getTerritories().get(c)!=null)
					for(Country e : gameMap.getTerritories().get(c)) 
						if(e!=null)
							System.out.print(e.getName()+", ");
				
				System.out.println("]\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseCountries(List<String> metaTerritories, GameMap gameMap) {
		List<Country> countries = new ArrayList<>();

		for (String c : metaTerritories) {
			
			if(c.isEmpty()){
				continue;
			}
			
			String[] metaData = c.split(",");
			
			//continents.add(new Continent(metaData[0].trim(), Integer.parseInt(metaData[1])));
			Continent continent = gameMap.getContinent(metaData[3].trim());
			Country ctry = new Country(metaData[0].trim(), Integer.parseInt(metaData[1]), Integer.parseInt(metaData[2]), continent, null, 0);
			
			countries.add(ctry);
			
			List<String> t = Arrays.asList(Arrays.copyOfRange(metaData, 4, metaData.length));
			
			gameMap.addNeighbour(ctry,t);
		}

		gameMap.setCountries(countries);
	}

	private void parseContinents(List<String> metaContinents, GameMap gameMap) {

		List<Continent> continents = new ArrayList<>();

		for (String c : metaContinents) {
			String[] metaData = c.split("=");
			continents.add(new Continent(metaData[0].trim(), Integer.parseInt(metaData[1]), null));
		}

		gameMap.setContinents(continents);
	}

}
