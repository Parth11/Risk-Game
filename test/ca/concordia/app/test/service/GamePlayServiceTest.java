package ca.concordia.app.test.service;

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
		game_map = map_service.loadMap("maps/Canada.map");
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
	public void testIsNotConnectedPlayerCountries() {
		Country c1 = game_play.getCountriesConqueredBy(players.get(0)).get(0);
		Country c2 = game_play.getCountriesConqueredBy(players.get(0)).get(1);
		assertFalse(game_play.isConnected(c1, c2, players.get(0)));
	}
	
	@Test
	public void testIsConnectedPlayerCountries() {
		Country c1 = game_play.getCountriesConqueredBy(players.get(0)).get(0);
		Country c3 = game_play.getCountriesConqueredBy(players.get(0)).get(4);
		assertTrue(game_play.isConnected(c1, c3, players.get(0)));
	}
	
	@Test
	public void testFortificationPhaseMoveArmy() {
		Country c1 = game_play.getCountriesConqueredBy(players.get(0)).get(0);
		Country c2 = game_play.getCountriesConqueredBy(players.get(0)).get(2);
		int n1 = c1.getNoOfArmy();
		int n2 = c2.getNoOfArmy();
		int i = 1;
		game_play.moveArmyFromTo(players.get(0), c1, c2, 1);
		assertEquals(c1.getNoOfArmy(), n1-i);
		assertEquals(c2.getNoOfArmy(), n2+1);
	}
	
	@Test
	public void testReinforcementPhaseCalculateArmies() {
		int count = game_play.getReinforcementArmyForPlayer(players.get(0));
		assertEquals(23, count);
	}

}
