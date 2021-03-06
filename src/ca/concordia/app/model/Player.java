package ca.concordia.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import ca.concordia.app.controller.PhaseViewController;
import ca.concordia.app.model.GamePlayEvent.EventType;
import ca.concordia.app.service.ConsoleLoggerService;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.util.GameConstants;
import ca.concordia.app.util.GamePhase;


/**
 * The Class Player which does Attack Reinforcement and Fortification. 
 * It will the Observable as it will notify based on the atate of the player
 *
 * @author harvi
 */
public class Player extends Observable implements Serializable{

	private static final long serialVersionUID = 9179169374855185777L;
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
	public PlayerStrategy strategy;
	
	/**
	 * Parameterized constructor Which sets the attributes of the player.
	 * @param name the player name
	 * @param strategy players strategy
	 */
	public Player(String name, PlayerStrategy strategy) {
		this.name = name;
		this.color = null;
		this.event_log = new ArrayList<>();
		this.addObserver(PhaseViewController.getInstance());
		this.addObserver(ConsoleLoggerService.getInstance(null));
		this.strategy = strategy;
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
	 * @param reinforceArmyforCard the cards
	 */
	public void setReinforceArmyforCard(int reinforceArmyforCard) {
		this.reinforce_army_for_card = reinforceArmyforCard;
	}
	
	/**
	 * returns the name of the player
	 * @return name the name of the card
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
	 * @param card the card variable
	 */
	public void addCard(Card card) {
		cards_list.add(card);
	}
	
	/**
	 * This method strategize's the reinforcement
	 */
	public void strategizeReinforcement(){
		
		Map<String,Object> strategyRs = strategy.computeReinforcementMove(this);
		
	}
	
	/**
	 * Does the Reinforcement for the player
	 * @param country the country to reinforce
	 * @param armiesWishToReinforce nuber of armies
	 */
	public void doReinforcement(Country country, int armiesWishToReinforce) {

		country.addArmies(armiesWishToReinforce);
		
		HashMap<String, Object> eventPayload = new HashMap<>();
		eventPayload.put("reinforcedCountry", country.getCountryName());
		eventPayload.put("reinforceArmy", armiesWishToReinforce);
		GamePlayEvent gpe = new GamePlayEvent(EventType.REFINFORCE_COUNTRY, eventPayload);
		this.publishGamePlayEvent(gpe);
		
	}
	
	/**
	 * This method strategize's attack
	 */
	public void strategizeAttack(){
			strategy.computeAttackMove(this);
	}
	
	/**
	 * Initiates Battle between two players with their selected country
	 * @param attackerCountry the attacking country
	 * @param defenderCountry the defending country
	 * @param attackResult the attack result
	 * @param defenceResult the defense result
	 */
	public void doAttack(Country attackerCountry, Country defenderCountry, List<Integer> attackResult,
			List<Integer> defenceResult) {

		GamePlayService gamePlay = GamePlayService.getInstance();

		int n = attackResult.size() > defenceResult.size() ? defenceResult.size() : attackResult.size();

		List<String> outcomes = new ArrayList<String>();
		
		for (int i = 0; i < n; i++) {
			int attackResultInt = attackResult.get(i);
			int defenceResultInt = defenceResult.get(i);

			if (attackResultInt > defenceResultInt) {
				gamePlay.subArmies(defenderCountry.getRuler(), defenderCountry, 1);
				outcomes.add("WIN");
			} else {
				gamePlay.subArmies(attackerCountry.getRuler(), attackerCountry, 1);
				outcomes.add("LOSS");
			}
		}

		
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
				// Remove this player from the player list
				gamePlay.knockPlayerOut(defenderCountry.getRuler());
			}
			
			
			defenderCountry.setRuler(attackerCountry.getRuler(), 0);

			card_flag = true;
			
			country_captured = true;

		}

	}
	
	/**
	 * This method strategize's fortification
	 */
	public void strategizeFortification(){
		Map<String,Object> strategyRs = strategy.computeFortifyMove(this);
	
//		if(strategyRs!=null){
//			doFortification((Country) strategyRs.get("from"),
//					(Country) strategyRs.get("to"), 
//						(Integer) strategyRs.get("armies"));
//		}
//		else{
//			ConsoleLoggerService.getInstance(null).write(this.getName()+
//					" -> does not have eligible countries to fortify -> Skipping Fortification \n");
//		}
		
	}	
	/**
	 * Does the Fortification for the Player.
	 * @param fromCountry country to move armies from
	 * @param toCountry country to put armies in
	 * @param armies the number of armies
	 */
	public void doFortification(Country fromCountry, Country toCountry, Integer armies) {

		GamePlayService.getInstance().moveArmyFromTo(this, fromCountry, toCountry, armies);

		HashMap<String, Object> eventPayload = new HashMap<>();
		eventPayload.put("fromCountry", fromCountry.getCountryName());
		eventPayload.put("toCountry", toCountry.getCountryName());
		eventPayload.put("armies", armies);
		GamePlayEvent gpe = new GamePlayEvent(EventType.FORTIFY_COUNTRY, eventPayload);
		this.publishGamePlayEvent(gpe);
	}
	/**
	 * Sets the current phase of the player.
	 * @param currentPhase the phase
	 */
	public void setCurrentPhase(GamePhase currentPhase){
		this.game_phase = currentPhase;
	}
	
	/**
	 * Publishes the event and calls the notifyObservers method
	 * @param gamePlayEvent the event
	 */
	public void publishGamePlayEvent(GamePlayEvent gamePlayEvent){
		this.event_log.add(gamePlayEvent);
		this.setChanged();
		this.notifyObservers();
		GamePlayService.getInstance().logGameEvent(this, gamePlayEvent);
	}
	
	/**
	 * return name in String format
	 * @return name
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * Compare player name
	 *@return true or False 
	 */
	@Override
	public boolean equals(Object obj) {
		return this.name.equals(((Player)obj).getName());
	}
	
	/**
	 * hash code for the name
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	/**
	 * Checks the eligibility for the attacking country 
	 * @return true or false
	 */
	public boolean canAttack(){
		return GamePlayService.getInstance().getEligibleAttackingCountriesForPlayer(this).size()>0? true: false;
	}
	
	/**
	 * Reimbursed Cards for getting army
	 * @param a card type
	 * @param i card type
	 * @param c card type
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
		
		this.reinforce_army_for_card += reimburse_turn*5;
		reimburse_turn++;
		
		HashMap<String, Object> eventPayload = new HashMap<>();
		eventPayload.put("armies", this.reinforce_army_for_card);
		eventPayload.put("cards", new String("["+a+","+i+","+c+"]"));
		GamePlayEvent gpe = new GamePlayEvent(EventType.CARD_EXCHANGE, eventPayload);
		this.publishGamePlayEvent(gpe);
	}
	
	/**
	 * Gives cards to the player if he win the round
	 */
	public void captureCards(){
		if(card_flag) {
			//for(int i=0;i<5;i++){
				String playerCard = GamePlayService.getInstance().generateCard();
				Card card1 = new Card(playerCard);				
				cards_list.add(card1);
				
				HashMap<String, Object> eventPayload = new HashMap<>();
				eventPayload.put("player", this);
				eventPayload.put("card", card1);
				GamePlayEvent gpe = new GamePlayEvent(EventType.CARD_WIN, eventPayload );
				
				publishGamePlayEvent(gpe);
			//}
		}
	}
}
