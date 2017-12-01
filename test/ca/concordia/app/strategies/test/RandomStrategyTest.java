package ca.concordia.app.strategies.test;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.service.MapService;
import ca.concordia.app.strategies.BenevolentStrategy;
import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.strategies.RandomStrategy;
import ca.concordia.app.util.MapValidationException;

public class RandomStrategyTest {

	private GamePlayService game_play;
	private GameMap game_map;
	private MapService map_service;
	private List<Player> players;
	private List<Country> country;
	private List<PlayerStrategy> strategies = new ArrayList<>();
	private PlayerStrategy strategy;
	
	@Before
	public void init() throws MapValidationException, URISyntaxException {

		map_service = MapService.getInstance();
		game_map = GameMap.getInstance();
		game_play = GamePlayService.getInstance();
		game_map = map_service.loadMap("maps/Europe.map");
		 
		strategies.add(new RandomStrategy());
		strategies.add(new RandomStrategy());
		    
		game_play.doStartupPhase(2,strategies);
		players = game_play.getPlayers();
		country = game_map.getCountries();

	}

	/**
	 * 	Testing the Reinforcement move for the random player
	 * 
	 * */
	@Test
	public void testcomputeReinforcementMove() {
		
		
		Map<String, Object> strategyAs = strategies.get(0).computeReinforcementMove(players.get(0));
		
		int beforeArmies = (int) strategyAs.get("beforeArmies");
		
		
		int afterArmies = (int) strategyAs.get("afterArmies");
		
		
		boolean b = afterArmies>beforeArmies;
		
		assertTrue(b);
		
	}
	
	/**
	 * 	Testing the fortify move for the random player
	 * 
	 * */
	@Test
	public void testcomputeFortifyMove() {
		Map<String, Object> strategyRs = strategies.get(0).computeFortifyMove(players.get(0));				
		
		int fromArmies = (int) strategyRs.get("fromBeforeArmies");		
		int afterArmies = (int) strategyRs.get("fromAfterArmies");
		int fortifiesArmies = (int) strategyRs.get("armies");
		
		assertEquals(fromArmies, afterArmies+fortifiesArmies);
		
		
	}


}
