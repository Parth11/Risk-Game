package ca.concordia.app.model.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.model.SavedGame;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.service.MapService;
import ca.concordia.app.strategies.AggressiveStrategy;
import ca.concordia.app.strategies.BenevolentStrategy;
import ca.concordia.app.strategies.CheaterStrategy;
import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.strategies.RandomStrategy;
import ca.concordia.app.util.GsonUtil;
import ca.concordia.app.util.MapValidationException;



public class TestSaveGame {
	
private GamePlayService game_play;
	
	private GameMap game_map;
	
	private MapService map_service;
	
	private SavedGame savedGame;
	


	@Before
	public void init() throws MapValidationException, URISyntaxException {
		map_service = MapService.getInstance();
		game_map = GameMap.getInstance();
		game_play = GamePlayService.getInstance();
		
		
	    
	}
	
	@Test
	public void testSaveGame() throws FileNotFoundException
	{
		
		File f = new File("res/TestSaving");
		FileReader fr = new FileReader(f);
		Gson gson = GsonUtil.getGSONInstance();
		SavedGame savedGame = gson.fromJson(fr, SavedGame.class);
		game_play.restoreSavedData(savedGame);
		assertEquals(savedGame.getNumber_of_players(),2);
	}

}
