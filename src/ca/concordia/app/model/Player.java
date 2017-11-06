package ca.concordia.app.model;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	public void doReinforcement(){

		ConsoleLoggerService logger = ConsoleLoggerService.getInstance(null);
	
			setCurrentPhase(GamePhase.REINFORCEMENT);
			
			int numberOfArmies = GamePlayService.getInstance().getReinforcementArmyForPlayer(this);
			
			this.setTotalArmies(numberOfArmies);
			
			logger.write(this.name + " gets " + numberOfArmies + " armies");
			
			HashMap<String, Object> eventPayload = new HashMap<>();
			eventPayload.put("reinforcementArmies", numberOfArmies);
			GamePlayEvent gpe = new GamePlayEvent(EventType.REINFORCE_ARMY_ALLOCATION, eventPayload );
			
			this.publishGamePlayEvent(gpe);
			
			logger.write("These are your countries with current armies present in it : \n"
					+ GamePlayService.getInstance().printCountryAllocationToConsole(this));
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
		this.setCurrentPhase(GamePhase.ATTACK);
		
		ConsoleLoggerService logger = ConsoleLoggerService.getInstance(null);
		
		logger.write("\n********** ATTACK PHASE BEGIN **********");
		
		logger.write("These are your countries with current armies present in it : \n" + GamePlayService.getInstance().printCountryAllocationToConsole(this));
		
		logger.write("Please select attacker country from your conquered countries list");

		Country attackerCountry = (Country) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Select Attacker Country", "Input",
				JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(),
				GamePlayService.getInstance().getCountriesConqueredBy(this).toArray(), null);

		
		logger.write("Attacker Country : " + attackerCountry.getCountryName());
		

		Player attackPlayer = attackerCountry.getRuler();
		logger.write("Attack Player : " + attackPlayer.getName());
		
		List<Country> neighboursOfAttackerCountry = GameMap.getInstance().getNeighbourCountries(attackerCountry);
		
		for(int i = 0 ; i < neighboursOfAttackerCountry.size(); i++) {
			logger.write("neighbours Of AttackerCountry : " + neighboursOfAttackerCountry.get(i) + "Ruler : " + neighboursOfAttackerCountry.get(i).getRuler().getName().toString());
			
		}
		
		List<Country> listOfDefenderCountries = new ArrayList<>();
		Player tempPlayer;
		for(int i = 0 ; i < neighboursOfAttackerCountry.size(); i++) {
			tempPlayer = neighboursOfAttackerCountry.get(i).getRuler();

			if(tempPlayer.getName().equalsIgnoreCase(attackPlayer.getName())) {

				continue;
				
			}
			else {
				listOfDefenderCountries.add(neighboursOfAttackerCountry.get(i));
				
			}
		}
		
		Country defenderCountry = (Country) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Select Defender Country", "Input",
				JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), listOfDefenderCountries.toArray(), null);
		
		Player defencePlayer = defenderCountry.getRuler();
		
		logger.write("Attacker Country is " + attackerCountry.getCountryName().toString() + "\n Defending Country is " + defenderCountry.getCountryName().toString() + "and Defence player is " + defencePlayer.getName().toString());
		
		if(!GamePlayService.getInstance().canWar(attackerCountry, defenderCountry)) {
			return;
		}
		
		DiceRoller attackDice = new DiceRoller(GamePlayService.getInstance(), GamePlayService.getInstance().getAttackDiceLimit(attackPlayer, attackerCountry));
		DiceRoller defenceDice = new DiceRoller(GamePlayService.getInstance(), GamePlayService.getInstance().getAttackDiceLimit(defencePlayer, defenderCountry));
		
		logger.write("Attack Dice" + attackDice.no_of_dice);
		logger.write("Defence Dice" + defenceDice.no_of_dice);
		
		attackDice.rollAll();
		defenceDice.rollAll();
		
		int[] attackResult = attackDice.getResults();
		int [] defenceResult = defenceDice.getResults();
		
		Arrays.sort(attackResult);
		Arrays.sort(defenceResult);
		
		int n = attackResult.length>defenceResult.length?defenceResult.length:attackResult.length;
		
		int[] storeAttackResult = new int[n], storeDefenceResult = new int[n];
		
		for(int i = 0 ; i < n; i++) {
			storeAttackResult[i] = attackResult[i];
			storeDefenceResult[i] = defenceResult[i];
			
			if(attackResult[i] > defenceResult[i]) {
				GamePlayService.getInstance().subArmies(defencePlayer, defenderCountry, 1);
			}
			else {
				GamePlayService.getInstance().subArmies(attackPlayer, attackerCountry, 1);
			}
		}
		
		// if defence country is completely defeated
		if(defenderCountry.getNoOfArmy()<=0) {
			defenderCountry.setRuler(null, 0);
			GamePlayService.getInstance().unmapPlayerToCountry(defencePlayer, defenderCountry);
			GamePlayService.getInstance().mapPlayerToCountry(attackPlayer, attackerCountry);
			
			
			Integer[] attackerArmies = new Integer[attackerCountry.getNoOfArmy()];

			for (int i = 0; i < attackerArmies.length; i++) {
				attackerArmies[i] = i + 1;
			}
			Integer armies = (Integer) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Number of Armies to Move", "Input",
					JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), attackerArmies, attackerArmies[0]);
			
			
			GamePlayService.getInstance().moveArmyFromTo(attackPlayer, attackerCountry, defenderCountry, armies);
			
			cardFlag = true;
		}
		
		//still want to continue or what
		String[] selectionValues = { "Yes", "No" };
		String str = JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Do you want to continue attack", "Input",
				JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), selectionValues, "Yes").toString();
		if (str.equalsIgnoreCase("Yes")) {
			doAttack();
		}
		else {
			return;
		}
		
		
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
				cards_list = new ArrayList<>();
				cards_list.add(card1);
				
			}
			
			HashMap<String,Object> eventPayload = new HashMap<>();
			eventPayload.put("fromCountry", fromCountry.getCountryName());
			eventPayload.put("toCountry", toCountry.getCountryName());
			eventPayload.put("armies", armies);
			GamePlayEvent gpe = new GamePlayEvent(EventType.FORTIFY_COUNTRY, eventPayload);
			this.publishGamePlayEvent(gpe);
			logger.write(this.name + " has completed fortification");
			
		} else {
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
