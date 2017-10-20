package ca.concordia.app.test.service;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.service.MapService;
import ca.concordia.app.util.MapValidationException;

public class GamePlayServiceTest {

	private GamePlayService game_play;
	private GameMap game_map;
	private MapService map_service;
	private List<Player> players;
	
	@Before
	public void init() throws MapValidationException, URISyntaxException {
		map_service = MapService.getInstance();
		game_map = GameMap.getInstance();
		game_play = GamePlayService.getInstance();
		game_map = map_service.loadMap("Canada.map");
		game_play.doStartupPhase(4);
		players = game_play.getPlayers();
	}

	@Test
	public void testInitialArmies() {
		assertEquals(0, players.get(0).getTotalArmies());
		assertEquals(0, players.get(1).getTotalArmies());
		assertEquals(0, players.get(2).getTotalArmies());
		assertEquals(0, players.get(3).getTotalArmies());
	}
	
	@Test
	public void testIsConnectedCountries() {
		
		
	}

}
