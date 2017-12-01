package ca.concordia.app.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.app.controller.TournamentController;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.model.TournamentConfiguration;
import ca.concordia.app.model.TournamentResult;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.service.MapService;
import ca.concordia.app.strategies.BenevolentStrategy;
import ca.concordia.app.strategies.CheaterStrategy;
import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.util.MapValidationException;



public class TournamentModeTest {
	
private GamePlayService game_play;
	
	private GameMap game_map;
	
	private MapService map_service;
	
	private List<Player> players;
	
	private List<Country> country;
	
	private List<PlayerStrategy> strategies = new ArrayList<>();
	
	private TournamentConfiguration tournament;
	
	private TournamentController tournamentcontroller;
	

	@Before
	public void init() throws MapValidationException, URISyntaxException {
		map_service = MapService.getInstance();
		game_map = GameMap.getInstance();
		game_play = GamePlayService.getInstance();
		game_map = map_service.loadMap("maps/Canada.map");
        strategies.add(new BenevolentStrategy());
	   strategies.add(new CheaterStrategy());
	   tournament = TournamentConfiguration.getInstance();
		
		
		

	}
	
	@Test public void testTournament() throws MapValidationException
	{
		File f = new File("C:\\Users\\harvi\\Desktop\\Europe.map");
		tournament.addMap(f);
		tournament.setMax_turns(10);
		tournament.setNum_games(10);
		tournament.setNum_players(2);
		tournament.setStrategies(strategies);
		new TournamentController();
		
		assertTrue(TournamentResult.getInstance().end);
		
		assertEquals(TournamentResult.getInstance().results.keySet().size(),1);
		
	}

}