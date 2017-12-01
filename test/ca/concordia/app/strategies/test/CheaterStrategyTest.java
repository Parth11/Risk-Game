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
import ca.concordia.app.strategies.CheaterStrategy;
import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.strategies.RandomStrategy;
import ca.concordia.app.util.MapValidationException;

public class CheaterStrategyTest {

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
		 
		strategies.add(new CheaterStrategy());
		strategies.add(new CheaterStrategy());
		    
		game_play.doStartupPhase(2,strategies);
		players = game_play.getPlayers();
		country = game_map.getCountries();

	}

	@Test
	public void testcomputeReinforcementMove() {
		
		
		Map<String, Object> strategyAs = strategies.get(0).computeReinforcementMove(players.get(0));
		
		int beforeArmies = (int) strategyAs.get("beforeArmies");
		
		
		int afterArmies = (int) strategyAs.get("afterArmies");
		
		
		boolean b = afterArmies == (2*beforeArmies);
		
		assertTrue(b);
		
	}
	
	@Test
	public void testcomputeAttackMove() {
		strategy.computeReinforcementMove(players.get(0));		
		
		Country strongestCountry = game_play.getStrongestCountry(players.get(0));
		strongestCountry.setNoOfArmy(25);
		
		Country defendingCountry = GamePlayService.getInstance().getEligibleAttackableCountries(strongestCountry).get(0);
		defendingCountry.setNoOfArmy(1);
		
		
	}
	
	@Test
	public void testcomputeFortifyMove() {
		Map<String, Object> strategyAs = strategies.get(0).computeFortifyMove(players.get(0));
		
		int beforeArmies = (int) strategyAs.get("fromBeforeArmies");
				
		int afterArmies = (int) strategyAs.get("fromAfterArmies");
				
		boolean b = afterArmies == (2*beforeArmies);
		
		assertTrue(b);
		
		
	}


}
