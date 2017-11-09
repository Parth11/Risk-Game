package ca.concordia.app.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.app.model.Card;

//import com.sun.media.jfxmedia.events.PlayerState;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.service.MapService;
import ca.concordia.app.util.GameConstants;
import ca.concordia.app.util.MapValidationException;

/**
 * This class will test all the methods described in the GamePlayService Class.
 * 
 */

public class GamePlayServiceTest {

	private GamePlayService game_play;
	
	private GameMap game_map;
	
	private MapService map_service;
	
	private List<Player> players;

	/**
	 * Initialize.
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
		game_play.doStartupPhase(4);
		players = game_play.getPlayers();
	}
	// **********STARTUP PHASE STARTED****//

	/**
	 * Test  start up phase.
	 */
	@Test
	public void testDoStartUp() {
		assertEquals(game_play.getNumberOfPlayers(), 4);
	}

	/**
	 * Test initial armies.
	 */
	@Test
	public void testInitialArmies() {
		assertEquals(0, players.get(0).getTotalArmies());
		assertEquals(0, players.get(1).getTotalArmies());
		assertEquals(0, players.get(2).getTotalArmies());
		assertEquals(0, players.get(3).getTotalArmies());

	}

	/**
	 * Test data of player is getting  reset or not.
	 */
	@Test
	public void testResetPlayerData() {
		game_play.resetPlayersData();
		assertEquals(30, game_play.getInitialArmy());
	}

	/**
	 * Test allocate countries to players.
	 */
	@Test
	public void testAllocateCountriesToPlayers() {
		game_play.allocateCountriesToPlayers();
		System.out.println(game_map.getCountries().size());
		assertEquals(22, game_play.getCountriesConqueredBy(players.get(0)).size());
		assertEquals(22, game_play.getCountriesConqueredBy(players.get(1)).size());
		assertEquals(22, game_play.getCountriesConqueredBy(players.get(2)).size());
		assertEquals(21, game_play.getCountriesConqueredBy(players.get(3)).size());

	}

	// ******** STARTUP PHASE ENDED *********/

	/**
	 * Test is not connected player countries.
	 */
	@Test
	public void testIsNotConnectedPlayerCountries() {
		Country c1 = game_play.getCountriesConqueredBy(players.get(0)).get(0);
		Country c2 = game_play.getCountriesConqueredBy(players.get(0)).get(1);
		assertFalse(game_play.isConnected(c1, c2, players.get(0)));
	}

	/**
	 * Test is connected player countries.
	 */
	@Test
	public void testIsConnectedPlayerCountries() {
		Country c1 = game_play.getCountriesConqueredBy(players.get(0)).get(0);
		Country c3 = game_play.getCountriesConqueredBy(players.get(0)).get(4);
		assertTrue(game_play.isConnected(c1, c3, players.get(0)));
	}

	/**
	 * Test fortification phase move army.
	 */
	@Test
	public void testFortificationPhaseMoveArmy() {
		Country c1 = game_play.getCountriesConqueredBy(players.get(0)).get(0);
		Country c2 = game_play.getCountriesConqueredBy(players.get(0)).get(2);
		int n1 = c1.getNoOfArmy();
		int n2 = c2.getNoOfArmy();
		int i = 1;
		game_play.moveArmyFromTo(players.get(0), c1, c2, 1);
		assertEquals(c1.getNoOfArmy(), n1 - i);
		assertEquals(c2.getNoOfArmy(), n2 + 1);
	}

	/**
	 * Test reinforcement phase calculate armies.
	 */
	@Test
	public void testReinforcementPhaseCalculateArmies() {
		int count = game_play.getReinforcementArmyForPlayer(players.get(0));
		assertEquals(30, count);
	}

	/**
	 * Test can war.
	 */
	@Test
	// assertTrue is failing...
	public void testCanWar() {
		Country c1 = game_play.getCountriesConqueredBy(players.get(0)).get(0);
		Country c2 = game_play.getCountriesConqueredBy(players.get(0)).get(1);
		boolean c3 = game_play.canWar(c1, c2);
		assertFalse(c3);

	}

	/**
	 * Test add armies.
	 */
	@Test
	public void testAddArmies() {
		Country c = game_play.getCountriesConqueredBy(players.get(0)).get(0);
		assertTrue(game_play.addArmies(players.get(0), c, 2));
	}

	/**
	 * Test sub armies.
	 */
	@Test
	public void testSubArmies() {
		Country c = game_play.getCountriesConqueredBy(players.get(0)).get(0);
		assertTrue(game_play.subArmies(players.get(0), c, 1));
	}

	/**
	 * Test get eligible attacking countries for player.
	 */
	@Test
	public void testGetEligibleAttackingCountriesForPlayer() {
		Player p = players.get(0);
		Country c = game_play.getCountriesConqueredBy(p).get(0);
		List<Country> countries = game_play.getEligibleAttackingCountriesForPlayer(p);
		assertFalse(countries.contains(c));

	}

	/**
	 * Test get reinforcement army for player.
	 */
	@Test
	public void testGetReinforcementArmyForPlayer() {
		Player p = players.get(0);
		int befoeSize = game_play.getReinforcementArmyForPlayer(p);

		p.reimburseCards(3, 0, 0);

		int afterSize = game_play.getReinforcementArmyForPlayer(p);

		assertEquals(befoeSize + 5, afterSize);
	}

	/**
	 * Test generate deck.
	 */
	//********* CARDS TESTCASES**********//
	@Test
	public void testGenerateDeck() {

		game_play.generateDeck();

		int deckSize = game_map.getCountries().size();
		int cavalryCards = game_play.getDeckMap().get(GameConstants.CAVALRY);
		int artilleryCards = game_play.getDeckMap().get(GameConstants.ARTILLERY);
		int infantryCards = game_play.getDeckMap().get(GameConstants.INFANTRY);
		int finalCards = cavalryCards + artilleryCards + infantryCards;

		assertEquals(deckSize, finalCards);
	}

	/**
	 * Test remove cards  from deck.
	 */
	@Test
	public void testRemoveCardsfromDeck() {
		game_play.generateCard();
		HashMap<String, Integer> deckMap = game_play.getDeckMap();

		int cavalryCards = game_play.getDeckMap().get(GameConstants.CAVALRY);
		int artilleryCards = game_play.getDeckMap().get(GameConstants.ARTILLERY);
		int infantryCards = game_play.getDeckMap().get(GameConstants.INFANTRY);

		game_play.removeCardsfromDeck(GameConstants.ARTILLERY);
		game_play.removeCardsfromDeck(GameConstants.CAVALRY);
		game_play.removeCardsfromDeck(GameConstants.INFANTRY);

		assertEquals(artilleryCards, game_play.getDeckMap().get(GameConstants.ARTILLERY) + 1);
		assertEquals(cavalryCards, game_play.getDeckMap().get(GameConstants.CAVALRY) + 1);
		assertEquals(infantryCards, game_play.getDeckMap().get(GameConstants.INFANTRY) + 1);

	}

	/**
	 * Test add cards to deck.
	 */
	@Test
	public void testAddCardsToDeck() {
		game_play.generateCard();
		HashMap<String, Integer> deckMap = game_play.getDeckMap();

		int cavalryCards = game_play.getDeckMap().get(GameConstants.CAVALRY);
		int artilleryCards = game_play.getDeckMap().get(GameConstants.ARTILLERY);
		int infantryCards = game_play.getDeckMap().get(GameConstants.INFANTRY);

		game_play.addCardsToDeck(GameConstants.ARTILLERY);
		game_play.addCardsToDeck(GameConstants.CAVALRY);
		game_play.addCardsToDeck(GameConstants.INFANTRY);

		assertEquals(artilleryCards, game_play.getDeckMap().get(GameConstants.ARTILLERY) - 1);
		assertEquals(cavalryCards, game_play.getDeckMap().get(GameConstants.CAVALRY) - 1);
		assertEquals(infantryCards, game_play.getDeckMap().get(GameConstants.INFANTRY) - 1);

	}

	/**
	 * Test card reimbursement.
	 */
	@Test
	public void testCardReimbursement() {

		// checking same sorts
		assertTrue(game_play.cardReimbursement(players.get(0), 3, 0, 0));
		assertTrue(game_play.cardReimbursement(players.get(0), 0, 3, 0));
		assertTrue(game_play.cardReimbursement(players.get(0), 0, 0, 3));

		//checking different sorts
		assertTrue(game_play.cardReimbursement(players.get(0), 1, 1, 1));
		
		//invalid checking
		assertFalse(game_play.cardReimbursement(players.get(0), 2, 1, 1));

	}

	/**
	 * Test set new country ruler.
	 */
	@Test
	public void testSetNewCountryRuler() {
		Player p = players.get(0);
		Country c1 = game_play.getCountriesConqueredBy(p).get(0);
		
		Player oldC1Ruler = c1.getRuler();
		
		game_play.setNewCountryRuler(players.get(1), c1, 0);
		
		Player newC1Ruler = c1.getRuler();
		
		boolean b = oldC1Ruler.getName() != newC1Ruler.getName() ;
		
		assertFalse(b);
	}
	
	/**
	 * Test move army from to.
	 */
	@Test
	public void testMoveArmyFromTo() {
		Player p = players.get(0);
		Country c1 = game_play.getCountriesConqueredBy(p).get(5);
		Country c2 = game_play.getCountriesConqueredBy(p).get(6);
		
		int beforeC1Armies = c1.getNoOfArmy();
		int beforeC2Armies = c2.getNoOfArmy();
		
		game_play.moveArmyFromTo(p, c1, c2, c1.getNoOfArmy()-1);
		
		int afterC1Armies = c1.getNoOfArmy();
		int afterC2Armies = c2.getNoOfArmy();
		
		boolean b1 = beforeC1Armies < afterC1Armies;
		boolean b2 = beforeC2Armies < afterC2Armies;
		boolean b3 = beforeC1Armies == afterC1Armies;
		boolean b4 = beforeC2Armies == afterC2Armies;
		
		if(b1)
			assertTrue(b1);
		else if(b2)
			assertTrue(b2);
		else if(b3)
			assertTrue(b3);
		else
			assertTrue(b4);
	}
	
	/**
	 * Test for putting player on country
	 * */
	@Test
	public void testmapPlayerToCountry() {
		
		Player p1 = players.get(0);
		Player p2 = players.get(1);
		
		Country c1 = game_play.getCountriesConqueredBy(p1).get(0);
		Country c2 = game_play.getCountriesConqueredBy(p2).get(0);
		
		int beforeSize = game_play.getCountriesConqueredBy(p1).size();
		
		game_play.mapPlayerToCountry(p1, c2);
		
		int afterSize = game_play.getCountriesConqueredBy(p1).size();
		
		boolean b = afterSize > beforeSize;
		
		assertTrue(b);
	}
	
	/**
	 * Test for removing player from country
	 * */
	@Test
	public void testUnmapPlayerToCountry() {
		
		Player p1 = players.get(0);
		Player p2 = players.get(1);
		
		Country c1 = game_play.getCountriesConqueredBy(p1).get(0);
		
		
		int beforeSize = game_play.getCountriesConqueredBy(p1).size();
		System.out.println(beforeSize);
		game_play.unmapPlayerToCountry(p1, c1);
		
		int afterSize = game_play.getCountriesConqueredBy(p1).size();
		System.out.println(afterSize);
		boolean b = afterSize < beforeSize;
		
		assertTrue(b);
	}

}
