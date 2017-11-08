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
import javax.swing.undo.CannotRedoException;

import ca.concordia.app.controller.PhaseViewController;
import ca.concordia.app.model.GamePlayEvent.EventType;
import ca.concordia.app.service.ConsoleLoggerService;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.util.GameConstants;
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
	public boolean cardFlag = false;
	
	public Player(String name) {
		this.name = name;
		this.color = null;
		this.event_log = new ArrayList<>();
		this.addObserver(PhaseViewController.getInstance());
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
//		Card card1 = new Card(GameConstants.ARTILLERY,5);
//		Card card2 = new Card(GameConstants.INFANTRY,5);
//		Card card3 = new Card(GameConstants.CAVALRY,5);
//		cards_list = new ArrayList<>();
//		cards_list.add(card1);
//		cards_list.add(card2);
//		cards_list.add(card3);
		return cards_list;
		
	}
	public void addCard(Card card) {
		cards_list.add(card);
	}
	
	public void doReinforcement(){
		
		
		
		
		if(GamePlayService.getInstance().checkPlayerCardsIsGreater()){
			CardExchangeView cardView = new CardExchangeView();
		}

		ConsoleLoggerService logger = ConsoleLoggerService.getInstance(null);
	
			setCurrentPhase(GamePhase.REINFORCEMENT);
			
			int numberOfArmies = GamePlayService.getInstance().getReinforcementArmyForPlayer(this);
			
			this.setTotalArmies(numberOfArmies);
			
			logger.write(this.name + " gets " + numberOfArmies + " armies");
			
			HashMap<String, Object> eventPayload = new HashMap<>();
			eventPayload.put("reinforcementArmies", numberOfArmies);
			GamePlayEvent gpe = new GamePlayEvent(EventType.REINFORCE_ARMY_ALLOCATION, eventPayload );
			
			this.publishGamePlayEvent(gpe);
			
			logger.write("These are your countries with current armies present in it : \n"+ GamePlayService.getInstance().printCountryAllocationToConsole(this));
			while (numberOfArmies > 0) {
				logger.write("Please select the country in which you want to reinforce the army");

				Country country = (Country) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Select Country", "Input",
						JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(),
						GamePlayService.getInstance().getCountriesConqueredBy(this).toArray(), null);

				logger.write("How many armies you wish to reinforce between 1 - " + numberOfArmies);
				Integer[] selectOptions = new Integer[numberOfArmies];
				for (int i = 0; i < numberOfArmies; i++) {
					selectOptions[i] = i + 1;
				}
				Integer armiesWishToReinforce = (Integer) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Number of Armies",
						"Input", JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions,
						selectOptions[0]);
				country.addArmies(armiesWishToReinforce);
				numberOfArmies = numberOfArmies - armiesWishToReinforce;
				logger.write("You are now left with "+numberOfArmies+" armies");
				
				this.setTotalArmies(numberOfArmies);
				
				eventPayload = new HashMap<>();
				eventPayload.put("reinforcedCountry", country.getCountryName());
				eventPayload.put("reinforceArmy", armiesWishToReinforce);
				gpe = new GamePlayEvent(EventType.REFINFORCE_COUNTRY, eventPayload);
				this.publishGamePlayEvent(gpe);
			}
			if (numberOfArmies == 0) {
				logger.write("You have successfully placed all the armies into the countries you selected. Moving to the next phase.");
				return;
			}

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
				
				Integer[] a = new Integer[attackDice.no_of_dice];
				
				for(int i = 0 ; i<attackDice.no_of_dice; i++){
					a[i] = i+1;
				}
				
				
				
				
				Integer b = (Integer) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Select the number of Dice you Want to Roll",
						"Input", JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), a,a[0]);
				
				
				int[] attackResult= attackDice.rollAll();
				int[] defenceResult =defenceDice.rollAll();
				
				
				Arrays.sort(attackResult);
				List<Integer> attackResList = new ArrayList<Integer>();
				for (int i: attackResult)
				{
					attackResList.add(i);
				}
				Collections.reverse(attackResList);
				System.out.println(attackResList);
				
				Arrays.sort(defenceResult);
				List<Integer> defendResList = new ArrayList<Integer>();
				for (int i: defenceResult)
				{
					defendResList.add(i);
				}
				Collections.reverse(defendResList);
				System.out.println(defendResList);
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
				
				
				//defenderCountry.setRuler(attackerCountry.getRuler(), 0);
				
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
					
					defenderCountry.setRuler(attackerCountry.getRuler(), 0);
					
					Integer[] attackerArmies = new Integer[attackerCountry.getNoOfArmy()];

					for (int i = 0; i < attackerArmies.length; i++) {
						attackerArmies[i] = i + 1;
					}
					Integer armies = (Integer) JOptionPane.showInputDialog(gamePlay.game_play_frame, "Number of Armies to Move", "Input",
							JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), attackerArmies, attackerArmies[0]);
					
					//based on the attacker dice it will move armies into defender country
					if(armies >= attackDice.no_of_dice) {

						gamePlay.moveArmyFromTo(attackerCountry.getRuler(), attackerCountry, defenderCountry, armies);

						}

						else {

						logger.write("You have to select greater than equal to dice you rolled which is " + attackDice.no_of_dice);

						}
					
					
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
	
	public void doFortification(){

		ConsoleLoggerService logger = ConsoleLoggerService.getInstance(null);
		
		logger.write("\n********** FORTIFICATION PHASE BEGIN **********");
		logger.write("Do you wish to enter Fortification phase?");
		String[] selectionValues = { "Yes", "No" };
		String str = JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Enter Fortification Phase?", "Input",
				JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), selectionValues, "Yes").toString();
		if (str.equalsIgnoreCase("Yes")) {

			this.setCurrentPhase(GamePhase.FORTIFICATION);
			
			logger.write("These are your countries with current armies present in it : "
					+ GamePlayService.getInstance().printCountryAllocationToConsole(this));
			logger.write("Please select the country from which you want to take armies");
			List<Country> selectOptions = GamePlayService.getInstance().getCountriesConqueredBy(this);
			
			boolean inputCaptured = false;
			
			Country fromCountry;
			
			do{
				fromCountry = (Country) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Select Country", "Input",
						JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions.toArray(), null);

				if (fromCountry.getNoOfArmy() == 1) {
					logger.write("Please leave atleast one army behind, so it can defend your country from an attack.");
					logger.write("Please select again");
				}
				else{
					inputCaptured = true;
				}
			}while(!inputCaptured);
			
			
			logger.write("Please select the country to which you want to add armies :");

			List<Country> toCountryOptions = new ArrayList<Country>();
			for (Country c : selectOptions) {
				if (!c.equals(fromCountry) && GamePlayService.getInstance().isConnected(fromCountry, c,this)) {
					toCountryOptions.add(c);
				}
			}
			
			inputCaptured = false;
			Country toCountry;
			
			do{
				toCountry = (Country) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Select Country", "Input",
						JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), toCountryOptions.toArray(), null);

				boolean areBothCountriesConnected = GamePlayService.getInstance().isConnected(fromCountry, toCountry, this);
				if (!areBothCountriesConnected) {
					logger.write("Not connected ! Select Again");
				}
				else{
					inputCaptured = true;
				}
			}while(!inputCaptured);
			
			logger.write("Please select the number of armies from " + fromCountry.getCountryName() + ", which has : "
					+ fromCountry.getNoOfArmy() + " armies to move to : " + toCountry.getCountryName());
			Integer[] optionArmies = new Integer[fromCountry.getNoOfArmy() - 1];

			for (int i = 0; i < optionArmies.length; i++) {
				optionArmies[i] = i + 1;
			}
			Integer armies = (Integer) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Number of Armies to Move", "Input",
					JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), optionArmies, 1);
			
			GamePlayService.getInstance().moveArmyFromTo(this, fromCountry, toCountry, armies);
			
			if(cardFlag) {
				//logic for adding card
				String playerCard = GamePlayService.getInstance().generateCard();
				Card card1 = new Card(playerCard,1);								
				cards_list.add(card1);
				GamePlayService.getInstance().removeCardsfromDeck(card1.getCard_type());
				cardFlag=false;
				
			}
			
			HashMap<String,Object> eventPayload = new HashMap<>();
			eventPayload.put("fromCountry", fromCountry.getCountryName());
			eventPayload.put("toCountry", toCountry.getCountryName());
			eventPayload.put("armies", armies);
			GamePlayEvent gpe = new GamePlayEvent(EventType.FORTIFY_COUNTRY, eventPayload);
			this.publishGamePlayEvent(gpe);
			logger.write(this.name + " has completed fortification");
			
		} else {
			if(cardFlag) {
				//logic for adding card
				String playerCard = GamePlayService.getInstance().generateCard();
				Card card1 = new Card(playerCard,1);
				
				cards_list.add(card1);
				GamePlayService.getInstance().removeCardsfromDeck(card1.getCard_type());
				cardFlag=false;
				
			}
			logger.write("********** FORTIFICATION PHASE ENDED **********");
			return;
		}
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
