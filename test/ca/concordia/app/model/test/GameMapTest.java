package ca.concordia.app.model.test;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.service.MapService;
import ca.concordia.app.util.MapValidationException;

public class GameMapTest {

	private GamePlayService game_play;
	private GameMap game_map;
	private MapService map_service;
	private List<Player> players;
	private List<Country> country;
	
	@Before
	public void init() throws MapValidationException, URISyntaxException {
		map_service = MapService.getInstance();
		game_map = GameMap.getInstance();
		game_play = GamePlayService.getInstance();
		game_map = map_service.loadMap("maps/Canada.map");
		game_play.doStartupPhase(4,null);
		players = game_play.getPlayers();
		country = game_map.getCountries();

	}
	
	@Test
	public void testGetCountryNeighboursAsCSV() {
		Country c=game_map.getCountryByName("1");
	   String s = game_map.getCountryNeighboursAsCSV(c);
	   System.out.println(s);
	   assertEquals("2,12",s);
	}
	
}