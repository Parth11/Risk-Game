package ca.concordia.app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicIconFactory;

import ca.concordia.app.controller.PhaseViewController;
import ca.concordia.app.model.GamePlayEvent.EventType;
import ca.concordia.app.service.ConsoleLoggerService;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.util.GameConstants;
import ca.concordia.app.util.GamePhase;

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
	public ArrayList<Card> cards_list;
	public List<GamePlayEvent> event_log;
	public boolean cardFlag = false;
	
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
		// temp assigning cards to a player
		Card card1 = new Card(GameConstants.ARTILLERY,5);
		Card card2 = new Card(GameConstants.INFANTRY,5);
		Card card3 = new Card(GameConstants.CAVALRY,5);
		cards_list = new ArrayList<>();
		cards_list.add(card1);
		cards_list.add(card2);
		cards_list.add(card3);
		return cards_list;
		
	}
	public void addCard(Card card) {
		cards_list.add(card);
	}
	
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
	
	
	public void doAttack(){		

		ConsoleLoggerService logger = ConsoleLoggerService.getInstance(null);
		
		logger.write("\n********** ATTACK PHASE BEGIN **********\n");
		
		setCurrentPhase(GamePhase.ATTACK);
		
		GamePlayService gamePlay= GamePlayService.getInstance();

		List<Country> countries = gamePlay.getCountriesConqueredBy(this);

		String conquredCountries="";
		for (Country c : countries)
			conquredCountries+= "" + c.getCountryName() + "(" + c.getNoOfArmy() + "), ";
			
		logger.write(getName()+" owns countries with armies: \n" +conquredCountries);
		
		logger.write("Please select attacker country from conquered countries list \n");
		
		List<Country> attackingCountries = new ArrayList<>();
		
		for(Country c:countries)
		{
			if(c.getNoOfArmy()>1)
			{
				List<Country> neighbourCountries=GameMap.getInstance().getNeighbourCountries(c);
				for(Country neighbour:neighbourCountries) {
					if(neighbour.getRuler()!=this) {
						attackingCountries.add(c);
						break;
					}
				}
			}
		}
		
		if(!attackingCountries.isEmpty())
		{
			Country attackerCountry = (Country) JOptionPane.showInputDialog(gamePlay.game_play_frame, "Select Attacker Country", "Input",
					JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(),
					attackingCountries.toArray(), null);
			logger.write(attackerCountry.getRuler().getName()+ " is attacking from: " + attackerCountry.getCountryName()+"("+attackerCountry.getNoOfArmy()+")");

			List<Country> neighboursOfAttackerCountry = GameMap.getInstance().getNeighbourCountries(attackerCountry);
			
			List<Country> defenderCountries = new ArrayList<>();
			for(Country neighbour:neighboursOfAttackerCountry) 
			{
				if(!neighbour.getRuler().getName().equalsIgnoreCase(getName())) 
				{
					logger.write("\n Neighbour:" + neighbour.getCountryName() + "("+neighbour.getNoOfArmy()+") : ruled by: " + neighbour.getRuler().getName());
					
					defenderCountries.add(neighbour);
				}
			}
			
			Country defenderCountry = (Country) JOptionPane.showInputDialog(gamePlay.game_play_frame, "Select Defender Country", "Input",
					JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), defenderCountries.toArray(), null);
		
			logger.write("\n"+attackerCountry.getCountryName()+ " attacks on " + defenderCountry.getCountryName() + " owned by " + defenderCountry.getRuler().getName());
			
			if(GamePlayService.getInstance().canWar(attackerCountry, defenderCountry)) 
			{
				DiceRoller attackDice = new DiceRoller(gamePlay.getAttackDiceLimit(attackerCountry));
				DiceRoller defenceDice = new DiceRoller(gamePlay.getDefenceDiceLimit(defenderCountry));
				logger.write("\n Attack by Dice : " + attackDice.no_of_dice+" Defence by Dice : " + defenceDice.no_of_dice);
				
				int[] attackResult= attackDice.rollAll();
				int[] defenceResult =defenceDice.rollAll();
				
				Arrays.sort(attackResult);
				List<Integer> attackResList = new ArrayList<Integer>();
				for (int i: attackResult)
				{
					attackResList.add(i);
				}
				Collections.reverse(attackResList);
				
				Arrays.sort(defenceResult);
				List<Integer> defendResList = new ArrayList<Integer>();
				for (int i: defenceResult)
				{
					defendResList.add(i);
				}
				Collections.reverse(defendResList);
				
				logger.write("Attack dice rolling : " +attackDice.toString() );
				logger.write("Defence dice rolling : " + defenceDice.toString() );
				
				int n = attackResult.length>defenceResult.length?defenceResult.length:attackResult.length;
				
				boolean isAttackerWon=false;
				for(int i = 0 ; i < n; i++) 
				{
					int attackResultInt = attackResList.get(i);
					int defenceResultInt =defendResList.get(i);
					
					if(attackResultInt > defenceResultInt) 
					{
						isAttackerWon=true;
						gamePlay.subArmies(defenderCountry.getRuler(), defenderCountry, 1);
					}
					else 
					{
						isAttackerWon=false;
						gamePlay.subArmies(attackerCountry.getRuler(), attackerCountry, 1);
					}
				}
				
				if(isAttackerWon)
					logger.write("\nAttacker win attack and Defender will lose the armies");
				else 
					logger.write("\nDefender win attack and attacker will lose the armies");
				
				
				defenderCountry.setRuler(attackerCountry.getRuler(), 0);
				// if defence country is completely defeated
				
				if(defenderCountry.getNoOfArmy() < 1) 
				{
					
					//1. Remove a country from defender's country list
					gamePlay.unmapPlayerToCountry(defenderCountry.getRuler(), defenderCountry);
					
					//2. Add defending country in attacker country list
					gamePlay.mapPlayerToCountry(attackerCountry.getRuler(), defenderCountry);
					
					//3. Check defender is eleminated from the game or not
					if(gamePlay.getCountriesConqueredBy(defenderCountry.getRuler()).size() == 0) {
						logger.write("\n"+defenderCountry.getRuler().getName()+" has no country left, player is eliminated from the game");
						//Remove this player from the player list
						gamePlay.getPlayers().remove(defenderCountry.getRuler());
					}
					
					Integer[] attackerArmies = new Integer[attackerCountry.getNoOfArmy()];

					for (int i = 0; i < attackerArmies.length; i++) {
						attackerArmies[i] = i + 1;
					}
					Integer armies = (Integer) JOptionPane.showInputDialog(gamePlay.game_play_frame, "Number of Armies to Move", "Input",
							JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), attackerArmies, attackerArmies[0]);
					
					
					gamePlay.moveArmyFromTo(attackerCountry.getRuler(), attackerCountry, defenderCountry, armies);
					
					cardFlag = true;
				}
				
				//still want to continue or what
				String[] selectionValues = { "Yes", "No" };
				String str = JOptionPane.showInputDialog(gamePlay.game_play_frame, "Do you want to continue attack", "Input",
						JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), selectionValues, "Yes").toString();
				if (str.equalsIgnoreCase("Yes")) {
					doAttack();
				}
				else {
					return;
				}
			}
		}
		logger.write("\n********** ATTACK PHASE ENDED **********");
		return;
	
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
			
		if(cardFlag) {
			//logic for adding card
			String playerCard = GamePlayService.getInstance().generateCard();
			Card card1 = new Card(playerCard,1);				
			cards_list = new ArrayList<>();
			cards_list.add(card1);
				
		}
			
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
	
}
