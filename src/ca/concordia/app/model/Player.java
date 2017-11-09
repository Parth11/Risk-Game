package ca.concordia.app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import ca.concordia.app.controller.PhaseViewController;
import ca.concordia.app.model.GamePlayEvent.EventType;
import ca.concordia.app.service.ConsoleLoggerService;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.util.GameConstants;
import ca.concordia.app.util.GamePhase;
import ca.concordia.app.view.CardExchangeView;


/**
 * The Class Player which does Attack Reinforcement and Fortification. 
 * It will the Observable as it will notify based on the atate of the player
 *
 * @author Parth Nayak
 */
public class Player extends Observable {

	
	public String name;
	public int total_armies;
	public int reinforce_army_for_card =0;
	public String color;
	public GamePhase game_phase;
	public ArrayList<Card> cards_list= new ArrayList<>();
	public List<GamePlayEvent> event_log;
	public boolean card_flag = false;
	public boolean country_captured = false;
	public int reimburse_turn=1;

	/**
	 * Parameterized constructor Which sets the attributes of the player.
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		this.color = null;
		this.event_log = new ArrayList<>();
		this.addObserver(PhaseViewController.getInstance());
		this.addObserver(ConsoleLoggerService.getInstance(null));
	}
	
	/**
	 * Returns the reinforced army cards
	 * @return reinforce_army_for_card
	 */
	public int getReinforceArmyforCard() {
		return reinforce_army_for_card;
	}

	/**
	 * Sets the reinforce card
	 * @param reinforceArmyforCard
	 */
	public void setReinforceArmyforCard(int reinforceArmyforCard) {
		this.reinforce_army_for_card = reinforceArmyforCard;
	}
	
	/**
	 * returns the name of the player
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the player.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the total armies.
	 *
	 * @return the total armies
	 */
	public int getTotalArmies() {
		return total_armies;
	}
	
	/**
	 * Sets the total armies.
	 *
	 * @param totalArmies the new total armies
	 */
	public void setTotalArmies(int totalArmies) {
		this.total_armies = totalArmies;
	}
	
	/**
	 * Adds the army.
	 *
	 * @param n the n
	 */
	public void addArmy(int n) {
		this.total_armies+=n;
	}
	
	/**
	 * Sub army.
	 *
	 * @param n the n
	 */
	public void subArmy(int n) {
		this.total_armies-=n;
	}
	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	
	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * Returns the player cards that he has won.
	 * @return cards_list
	 */
	public ArrayList<Card> getCards() {
		return cards_list;
	}
	
	/**
	 * Adds the card in the list 
	 * @param card
	 */
	public void addCard(Card card) {
		cards_list.add(card);
	}
	
	
	/**
	 * Does the Reinforcement for the player
	 * @param country
	 * @param armiesWishToReinforce
	 */
	public void doReinforcement(Country country, int armiesWishToReinforce) {

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
	
	/**
	 * Initiates Battle between two players with their selected country
	 * @param attackerCountry
	 * @param defenderCountry
	 * @param attackResult
	 * @param defenceResult
	 */
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
	
	/**
	 * Does the Fortification for the Player.
	 * @param fromCountry
	 * @param toCountry
	 * @param armies
	 */
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
	/**
	 * Sets the current phase of the player.
	 * @param currentPhase
	 */
	public void setCurrentPhase(GamePhase currentPhase){
		this.game_phase = currentPhase;
	}
	
	/**
	 * Publishes the event and calls the <code>notifyObservers</> method
	 * @param gamePlayEvent
	 */
	public void publishGamePlayEvent(GamePlayEvent gamePlayEvent){
		this.event_log.add(gamePlayEvent);
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * return name in String format
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 *@return true or False 
	 */
	@Override
	public boolean equals(Object obj) {
		return this.name.equals(((Player)obj).getName());
	}
	
	/**
	 * Checks the eligibility for the attacking country 
	 * @return
	 */
	public boolean canAttack(){
		boolean result = GamePlayService.getInstance().getEligibleAttackingCountriesForPlayer(this).size()>0? true: false;
		return result;
	}
	
	/**
	 * Reimbursed Cards for getting army
	 * @param a
	 * @param i
	 * @param c
	 */
	public void reimburseCards(int a, int i, int c) {
		
		for(int j=0;j<a;j++){
			Card card = new Card(GameConstants.ARTILLERY);
			this.cards_list.remove(card);
			GamePlayService.getInstance().addCardsToDeck(GameConstants.ARTILLERY);
		}
		for(int j=0;j<i;j++){
			Card card = new Card(GameConstants.INFANTRY);
			this.cards_list.remove(card);
			GamePlayService.getInstance().addCardsToDeck(GameConstants.INFANTRY);
		}
		for(int j=0;j<c;j++){
			Card card = new Card(GameConstants.CAVALRY);
			this.cards_list.remove(card);
			GamePlayService.getInstance().addCardsToDeck(GameConstants.CAVALRY);
		}
		
		this.reinforce_army_for_card = reimburse_turn*5;
		reimburse_turn++;
		
		HashMap<String, Object> eventPayload = new HashMap<>();
		eventPayload.put("armies", this.reinforce_army_for_card);
		int[] array = {a,i,c};
		eventPayload.put("cards", Arrays.asList(array));
		GamePlayEvent gpe = new GamePlayEvent(EventType.CARD_EXCHANGE, eventPayload );
		this.publishGamePlayEvent(gpe);
	}
	
	/**
	 * Gives cards to the player if he win the round
	 */
	public void captureCards(){
		if(card_flag) {
			for(int i=0;i<5;i++){
				String playerCard = GamePlayService.getInstance().generateCard();
				Card card1 = new Card(playerCard);				
				cards_list.add(card1);
				
				HashMap<String, Object> eventPayload = new HashMap<>();
				eventPayload.put("player", this);
				eventPayload.put("card", card1);
				GamePlayEvent gpe = new GamePlayEvent(EventType.CARD_WIN, eventPayload );
				
				publishGamePlayEvent(gpe);
			}
		}
	}
}
