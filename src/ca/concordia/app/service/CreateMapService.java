package ca.concordia.app.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ca.concordia.app.model.Continent;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;

public class CreateMapService {

	public void createMap(String path) {

	}

	public static void main(String[] args) {

		CreateMapService crm = new CreateMapService();
		crm.loadMap("Europe.map");
	}

	public GameMap loadMap(String path) {
		GameMap gameMap = null;
		try {
			gameMap = GameMap.getInstance();
			List<String> list = new ArrayList<>();

			try (BufferedReader br = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(path).toURI()))) {

				list = br.lines().collect(Collectors.toList());

			} catch (IOException e) {
				e.printStackTrace();
			}

			List<String> metaMap = list.subList(list.indexOf("[Map]") + 1, list.indexOf("[Continents]") - 1);

			List<String> metaContinents = list.subList(list.indexOf("[Continents]") + 1,
					list.indexOf("[Territories]") - 1);

			List<String> metaTerritories = list.subList(list.indexOf("[Territories]") + 1, list.size());

			parseContinents(metaContinents,gameMap);

			parseCountries(metaTerritories,gameMap);

			for(Country c : gameMap.getCountries()){
				System.out.println(c.getCountryName()+":"+gameMap.getTerritories().get(c));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return gameMap;
	}

	private void parseCountries(List<String> metaTerritories, GameMap gameMap) {
		List<Country> countries = new ArrayList<>();

		for (String c : metaTerritories) {
			
			if(c.isEmpty()){
				continue;
			}
			
			String[] metaData = c.split(",");
			//continents.add(new Continent(metaData[0].trim(), Integer.parseInt(metaData[1])));
			Country ctry = new Country(metaData[0].trim(), Integer.parseInt(metaData[1]), 
					Integer.parseInt(metaData[2]), metaData[3].trim());
			countries.add(ctry);
			List<String> t = Arrays.asList(Arrays.copyOfRange(metaData, 4, metaData.length));
			gameMap.getTerritories().put(ctry,t);
		}

		gameMap.setCountries(countries);
	}

	private void parseContinents(List<String> metaContinents, GameMap gameMap) {

		List<Continent> continents = new ArrayList<>();

		for (String c : metaContinents) {
			String[] metaData = c.split("=");
			continents.add(new Continent(metaData[0].trim(), Integer.parseInt(metaData[1])));
		}

		gameMap.setContinents(continents);
	}

}
