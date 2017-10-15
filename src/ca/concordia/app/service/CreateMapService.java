package ca.concordia.app.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
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
import ca.concordia.app.util.MapEditorConstants;


public class CreateMapService {

	public void createMap(String savePath) {
		
		GameMap gameMap = GameMap.getInstance();
		
		List<String> lines = new ArrayList<String>();
		
		lines.add(MapEditorConstants.MAP_HEADER_CONST);
		lines.add("author="+MapEditorConstants.MAP_AUTHOR);
		lines.add("warn=yes");
		lines.add("image=noname.bmp");
		lines.add("wrap=no");
		lines.add("scroll=horizontal");
		lines.add("");
		lines.add(MapEditorConstants.CONTINENT_HEADER_CONST);
		
		for(Continent c : gameMap.getContinents()){
		
			lines.add(c.getContinentName()+"="+c.getControlValue());
		}
		
		lines.add("");
		
		lines.add(MapEditorConstants.TERRITORY_HEADER_CONST);
		
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
										append(",").append(cn.getLocy()).
											append(",").append(cn.getContinentName()).
												append(",").append(gameMap.getCountryNeighboursAsCSV(cn));
				
				lines.add(sb.toString());
			
		}
		
		Path path = Paths.get(savePath);
		
		try {
			Files.write(path, lines,StandardOpenOption.CREATE,StandardOpenOption.WRITE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		CreateMapService crm = new CreateMapService();
		GameMap gm = crm.loadMap("Europe.map");
		crm.createMap("C:\\Users\\harvi\\Desktop\\test\\mine.map");
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

			extractFileInformation(gameMap, list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return gameMap;
	}

	private void extractFileInformation(GameMap gameMap, List<String> list) {
		
		List<String> metaContinents = list.subList(list.indexOf("[Continents]") + 1,
				list.indexOf("[Territories]") - 1);

		List<String> metaTerritories = list.subList(list.indexOf("[Territories]") + 1, list.size());

		parseContinents(metaContinents,gameMap);

		parseCountries(metaTerritories,gameMap);

		for(Country c : gameMap.getCountries()){
			System.out.println(c.getCountryName()+":"+gameMap.getTerritories().get(c));
		}
	}
	
	public GameMap loadMap(File mapFile){
		GameMap gameMap = null;
		try {
			gameMap = GameMap.getInstance();
			List<String> list = new ArrayList<>();

			try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {

				list = br.lines().collect(Collectors.toList());

			} catch (IOException e) {
				e.printStackTrace();
			}

			extractFileInformation(gameMap, list);
			
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
			ArrayList<String> t = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(metaData, 4, metaData.length)));
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
