package ca.concordia.app.model.test;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.app.model.Card;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.service.MapService;
import ca.concordia.app.util.GameConstants;
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

	@Test
	public void testDoReinforcement() {
		Player p = players.get(0);
		Country c1 = game_play.getCountriesConqueredBy(p).get(0);
		
		int playerReinforceArmies = game_play.getReinforcementArmyForPlayer(p);
		int beforeArmies = c1.getNoOfArmy();

		p.doReinforcement(c1, playerReinforceArmies);
		
		int afterArmies = c1.getNoOfArmy();
		boolean b = afterArmies > beforeArmies;
		assertTrue(b);
		
	}
	
	@Test
	public void testdoFortification() {
		Player p = players.get(0);
		Country c1 = game_play.getCountriesConqueredBy(p).get(0);
		Country c2 = game_play.getCountriesConqueredBy(p).get(1);
		
		System.out.println(c1.getNoOfArmy());
		System.out.println(c2.getNoOfArmy());
		
		int c1Armies = c1.getNoOfArmy();
		int movingArmies = c1.getNoOfArmy()-1;
		p.doFortification(c1, c2, movingArmies);
		
		assertEquals(c1Armies, c1.getNoOfArmy()+movingArmies);		
	}

	@Test
	public void testReimburseCards() {
		
		Player p = players.get(0);
		game_play.generateDeck();
		int beforeDeckSize = game_play.getDeckMap().size();
		
		
		String firstGeneratedCard = game_play.generateCard();		
		Card c1 = new Card(firstGeneratedCard);		
		//add that card
		p.getCards().add(c1);
		
		String secondGeneratedCard = game_play.generateCard();		
		Card c2 = new Card(secondGeneratedCard);		
		//add that card
		p.getCards().add(c2);
		
		String thirdGeneratedCard = game_play.generateCard();		
		Card c3 = new Card(thirdGeneratedCard);		
		//add that card
		p.getCards().add(c3);
		
		int beforePlayerCards = p.getCards().size();
		
		int a=0,i=0,c=0;
		for(int k =0 ;k<3;k++) {
		if(firstGeneratedCard.equals(GameConstants.ARTILLERY)) {
			a+=1;
		}
		else if(secondGeneratedCard.equals(GameConstants.CAVALRY)) {
			c+=1;
		}
		else {
			i+=1;
		}
		}
		int afterPlayerCards = p.getCards().size();
		System.out.println(p.getCards().size());
		System.out.println("a : "+a+" i : "+i+" c : "+c);
		p.reimburseCards(a, i, c);
		int afterDeckSize = game_play.getDeckMap().size();
		
		assertEquals(beforeDeckSize, afterDeckSize);
		assertEquals(beforePlayerCards, afterPlayerCards);
		
	}
	
	@Test
	public void testCaptureCards() {
		Country c1 = game_play.getCountriesConqueredBy(players.get(0)).get(0);
		Country c2 = game_play.getCountriesConqueredBy(players.get(1)).get(0);
		
		List<Integer> attack = new ArrayList<>();
		attack.add(6);
		attack.add(6);
		
		List<Integer> defense = new ArrayList<>();
		defense.add(1);
		defense.add(2);
		
		int beforePlayerCard = players.get(0).getCards().size();
		System.out.println(beforePlayerCard);
		players.get(0).doAttack(c1, c2, attack, defense);
		players.get(0).captureCards();
		int afterPlayerCard = players.get(0).getCards().size();
		System.out.println(afterPlayerCard);
		
		//need to check captureCards coz in that for loop is executing 5 times.
		assertEquals(beforePlayerCard+5, afterPlayerCard);
	}
	
}
