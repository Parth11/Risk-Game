package ca.concordia.app.model.test;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.service.MapService;
import ca.concordia.app.strategies.AggressiveStrategy;
import ca.concordia.app.strategies.BenevolentStrategy;
import ca.concordia.app.strategies.CheaterStrategy;
import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.strategies.RandomStrategy;
import ca.concordia.app.util.MapValidationException;
// TODO: Auto-generated Javadoc

/**
 * The Class GameMapTest will check the methods of the GameMap Class.
 *
 * @author Shivangi
 */
public class GameMapTest {

	private GamePlayService game_play;
	
	private GameMap game_map;
	
	private MapService map_service;
	
	private List<Player> players;
	
	private List<Country> country;
	
	private List<PlayerStrategy> strategies = new ArrayList<>();
	
	/**
	 * Initialize
	 *
	 * @throws MapValidationException the map validation exception
	 * @throws URISyntaxException the URI syntax exception
	 */
	@Before
	public void init() throws MapValidationException, URISyntaxException {
		map_service = MapService.getInstance();
		game_map = GameMap.getInstance();
		game_play = GamePlayService.getInstance();
		game_map = map_service.loadMap("maps/Canada.map");
        strategies.add(new BenevolentStrategy());
	    strategies.add(new CheaterStrategy());
	    strategies.add(new AggressiveStrategy());
      	strategies.add(new RandomStrategy());
		game_play.doStartupPhase(4,strategies);
		players = game_play.getPlayers();
		country = game_map.getCountries();

	}
	
	/**
	 * Test get country neighbours as CSV.
	 */
	@Test
	public void testGetCountryNeighboursAsCSV() {
		Country c=game_map.getCountryByName("1");
	   String s = game_map.getCountryNeighboursAsCSV(c);
	   assertEquals("2,12",s);
	}
	
	/**
	 * Testget countries by continent.
	 */
	@Test
	public void testgetCountriesByContinent() {
		country = game_map.getCountriesByContinent("Quebec");
		assertEquals(8,country.size());
	}
}