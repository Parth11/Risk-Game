package ca.concordia.app.strategies.test;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.service.MapService;
import ca.concordia.app.strategies.AggressiveStrategy;
import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.strategies.StrategyFactory;
import ca.concordia.app.util.MapValidationException;

public class AggressiveStrategyTest {
	
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
		 
		strategies.add(new AggressiveStrategy());
		strategies.add(new AggressiveStrategy());
		    
		game_play.doStartupPhase(2,strategies);
		players = game_play.getPlayers();
		country = game_map.getCountries();

	}

	@Test
	public void testcomputeReinforcementMove() {
		
		System.out.println(strategies.get(0).getName());
		
		Map<String, Object> strategyAs = strategies.get(0).computeReinforcementMove(players.get(0));
		
		int beforeArmies = (int) strategyAs.get("beforeArmies");
		
		System.out.println(beforeArmies);
		
		int afterArmies = (int) strategyAs.get("afterArmies");
		
		System.out.println(afterArmies);
		
		boolean b = afterArmies>beforeArmies;
		
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
		strategy.computeReinforcementMove(players.get(0));		
		
		Country strongestCountry = game_play.getStrongestCountry(players.get(0));
		strongestCountry.setNoOfArmy(25);
		
		Country defendingCountry = GamePlayService.getInstance().getEligibleAttackableCountries(strongestCountry).get(0);
		defendingCountry.setNoOfArmy(1);
		
		
	}

}
