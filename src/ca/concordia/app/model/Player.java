package ca.concordia.app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import ca.concordia.app.controller.PhaseViewController;
import ca.concordia.app.model.GamePlayEvent.EventType;
import ca.concordia.app.service.ConsoleLoggerService;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.util.GamePhase;
import ca.concordia.app.view.CardExchangeView;

/**
 * @author Parth Nayak
 * 
 * 
 */
public class Player extends Observable {

	public String name;
	public int total_armies;
	public int reinforceArmyforCard =0;
	public String color;
	public GamePhase game_phase;
	public ArrayList<Card> cards_list= new ArrayList<>();
	public List<GamePlayEvent> event_log;
	public boolean card_flag = false;
	public boolean country_captured = false;
	
	public int reimburse_turn=1;

	public Player(String name) {
		this.name = name;
		this.color = null;
		this.event_log = new ArrayList<>();
		this.addObserver(PhaseViewController.getInstance());
		this.addObserver(ConsoleLoggerService.getInstance(null));
	}
	
	public int getReinforceArmyforCard() {
		return reinforceArmyforCard;
	}

	public void setReinforceArmyforCard(int reinforceArmyforCard) {
		this.reinforceArmyforCard = reinforceArmyforCard;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalArmies() {
		return total_armies;
	}
	public void setTotalArmies(int totalArmies) {
		this.total_armies = totalArmies;
	}
	
	public void addArmy(int n) {
		this.total_armies+=n;
	}
	public void subArmy(int n) {
		this.total_armies-=n;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public ArrayList<Card> getCards() {
		return cards_list;
	}
	
	public void addCard(Card card) {
		cards_list.add(card);
	}
	
	public void doReinforcement(Country country, int armiesWishToReinforce) {
		
		if(GamePlayService.getInstance().checkPlayerCardsIsGreater())
		{
			CardExchangeView cardView = new CardExchangeView();
		}

		ConsoleLoggerService logger = ConsoleLoggerService.getInstance(null);

		logger.write("These are your countries with current armies present in it : \n"
				+ GamePlayService.getInstance().printCountryAllocationToConsole(this));
		logger.write("Please select the country in which you want to reinforce the army");

		
		country.addArmies(armiesWishToReinforce);
		
		HashMap<String, Object> eventPayload = new HashMap<>();
		eventPayload.put("reinforcedCountry", country.getCountryName());
		eventPayload.put("reinforceArmy", armiesWishToReinforce);
		GamePlayEvent gpe = new GamePlayEvent(EventType.REFINFORCE_COUNTRY, eventPayload);
		this.publishGamePlayEvent(gpe);
		
	}
	
	
	public void doAttack(Country attackerCountry, Country defenderCountry, List<Integer> attackResult,
			List<Integer> defenceResult) {

		ConsoleLoggerService logger = ConsoleLoggerService.getInstance(null);

		logger.write("\n********** ATTACK PHASE BEGIN **********\n");

		GamePlayService gamePlay = GamePlayService.getInstance();

		logger.write(attackerCountry.getRuler().getName() + " is attacking from: " + attackerCountry.getCountryName()
				+ "(" + attackerCountry.getNoOfArmy() + ")");

		logger.write("\n" + attackerCountry.getCountryName() + " attacks on " + defenderCountry.getCountryName()
				+ " owned by " + defenderCountry.getRuler().getName());

		int n = attackResult.size() > defenceResult.size() ? defenceResult.size() : attackResult.size();

		List<String> outcomes = new ArrayList<String>();
		
		boolean isAttackerWon = false;
		for (int i = 0; i < n; i++) {
			int attackResultInt = attackResult.get(i);
			int defenceResultInt = defenceResult.get(i);

			if (attackResultInt > defenceResultInt) {
				isAttackerWon = true;
				gamePlay.subArmies(defenderCountry.getRuler(), defenderCountry, 1);
				outcomes.add("WIN");
			} else {
				isAttackerWon = false;
				gamePlay.subArmies(attackerCountry.getRuler(), attackerCountry, 1);
				outcomes.add("LOSS");
			}
		}

		if (isAttackerWon)
			logger.write("\nAttacker win attack and Defender will lose the armies");
		else
			logger.write("\nDefender win attack and attacker will lose the armies");
		
		HashMap<String, Object> eventPayload = new HashMap<>();
		eventPayload.put("attackingCountry", attackerCountry.getCountryName());
		eventPayload.put("defendingCountry", defenderCountry.getCountryName());
		eventPayload.put("attackThrows", attackResult);
		eventPayload.put("defenceThrows", defenceResult);
		eventPayload.put("attackWin", outcomes);
		GamePlayEvent gpe = new GamePlayEvent(EventType.ATTACK_COUNTRY, eventPayload );
		publishGamePlayEvent(gpe);

		if (defenderCountry.getNoOfArmy() < 1) {

			// 1. Remove a country from defender's country list
			gamePlay.unmapPlayerToCountry(defenderCountry.getRuler(), defenderCountry);

			// 2. Add defending country in attacker country list
			gamePlay.mapPlayerToCountry(attackerCountry.getRuler(), defenderCountry);

			// 3. Check defender is eleminated from the game or not
			if (gamePlay.getCountriesConqueredBy(defenderCountry.getRuler()).size() == 0) {
				logger.write("\n" + defenderCountry.getRuler().getName()
						+ " has no country left, player is eliminated from the game");
				// Remove this player from the player list
				gamePlay.getPlayers().remove(defenderCountry.getRuler());
			}
			
			
			defenderCountry.setRuler(attackerCountry.getRuler(), 0);

			card_flag = true;
			
			country_captured = true;

		}

	}
	
	public void doFortification(Country fromCountry, Country toCountry, Integer armies) {

		ConsoleLoggerService logger = ConsoleLoggerService.getInstance(null);

		GamePlayService.getInstance().moveArmyFromTo(this, fromCountry, toCountry, armies);

		HashMap<String, Object> eventPayload = new HashMap<>();
		eventPayload.put("fromCountry", fromCountry.getCountryName());
		eventPayload.put("toCountry", toCountry.getCountryName());
		eventPayload.put("armies", armies);
		GamePlayEvent gpe = new GamePlayEvent(EventType.FORTIFY_COUNTRY, eventPayload);
		this.publishGamePlayEvent(gpe);
		logger.write(this.name + " has completed fortification");
			
		logger.write(this.name + " has completed fortification");
			
	}
	
	public void setCurrentPhase(GamePhase currentPhase){
		this.game_phase = currentPhase;
	}
	
	public void publishGamePlayEvent(GamePlayEvent gamePlayEvent){
		this.event_log.add(gamePlayEvent);
		this.setChanged();
		this.notifyObservers();
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.name.equals(((Player)obj).getName());
	}
	
	public boolean canAttack(){
		boolean result = GamePlayService.getInstance().getEligibleAttackingCountriesForPlayer(this).size()>0? true: false;
		return result;
	}
	
	public void reimburseCards() {
		int noOfReemburseArmy= reimburse_turn*5;
		GamePlayService gamePlay=GamePlayService.getInstance();
		List<Card> cardsEmburse=getCards();
		for(Card c:cardsEmburse) {
			gamePlay.addCardsToDeck(c.getCard_type());
			cards_list.remove(c);
		}
	
		reimburse_turn++;
		total_armies+=noOfReemburseArmy;
		
	}
	
	public void captureCards(){
		if(card_flag) {
			String playerCard = GamePlayService.getInstance().generateCard();
			Card card1 = new Card(playerCard,1);				
			cards_list.add(card1);
			
			HashMap<String, Object> eventPayload = new HashMap<>();
			eventPayload.put("player", this);
			eventPayload.put("card", card1);
			GamePlayEvent gpe = new GamePlayEvent(EventType.CARD_WIN, eventPayload );
			
			publishGamePlayEvent(gpe);
		}
	}
}
