package ca.concordia.app.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ca.concordia.app.model.GameMap;

public class CreateMapService {
	
	public void createMap(String path){
		
	}

	public static void main(String[] args) {

		CreateMapService crm = new CreateMapService();
		crm.loadMap("Europe.map");
	}
	public  GameMap loadMap(String path) {
		GameMap gameMap=null;
		try 
		{
		  gameMap= GameMap.getInstance();
			List<String> list = new ArrayList<>();

			try (BufferedReader br = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(path).toURI()))) {

				list = br.lines().collect(Collectors.toList());

			} catch (IOException e) {
				e.printStackTrace();
			}

			
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return gameMap;
	}
	
}
