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
import ca.concordia.app.util.MapValidationException;

public class PlayerTest {

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
		game_play.doStartupPhase(4);
		players = game_play.getPlayers();
		country = game_map.getCountries();

	}
	
	/**
	 * test that eligible attacking country of the player can attack or not 
	 */
	
	@Test
	public void testCanAttack(){
//		game_play.getEligibleAttackingCountriesForPlayer(players.get(0));
		Player p = players.get(0);
		boolean c = p.canAttack();
		assertTrue(c);
		
	}
	
	/**
	 * Test case to check whether attack is happening or not.
	 */
	@Test
	public void testDoAttack() {
		Country c1 = game_play.getCountriesConqueredBy(players.get(0)).get(0);
		Country c2 = game_play.getCountriesConqueredBy(players.get(1)).get(0);
		
		List<Integer> attack = new ArrayList<>();
		attack.add(6);
		attack.add(6);
		
		List<Integer> defense = new ArrayList<>();
		defense.add(1);
		defense.add(2);
		
		players.get(0).doAttack(c1, c2, attack, defense);
		
		assertTrue(players.get(0).card_flag);
		
	}
	
	
}
