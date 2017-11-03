package ca.concordia.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicIconFactory;

import ca.concordia.app.controller.PhaseViewController;
import ca.concordia.app.service.ConsoleLoggerService;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.util.GamePhase;

/**
 * @author Parth Nayak
 * 
 * 
 */
public class Player extends Observable {

	public String name;
	public int total_armies;
	public String color;
	public ArrayList<Card> cards_list;
	public GamePhase game_phase;
	
	public Player(String name) {
		this.name = name;
		this.color = null;
		this.cards_list = new ArrayList<>();
		this.addObserver(PhaseViewController.getInstance());
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
	
	public void doReinforcement(){

		ConsoleLoggerService logger = ConsoleLoggerService.getInstance(null);
		
//		logger.write("Do you wish to enter Reinforcement phase?");
//
//		String[] options = { "Yes", "No" };

//		String str = JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Enter Reinforcemet Phase?", "Input",
//				JOptionPane.OK_OPTION, BasicIconFactory.getMenuArrowIcon(), options, "Yes").toString();
		
			setCurrentPhase(GamePhase.REINFORCEMENT);
			
			int numberOfArmies = GamePlayService.getInstance().getReinforcementArmyForPlayer(this);
			logger.write(this.name + " gets " + numberOfArmies + " armies");
			logger.write("These are your countries with current armies present in it : \n"
					+ GamePlayService.getInstance().printCountryAllocationToConsole(this));
			while (numberOfArmies > 0) {
				logger.write("Please select the country in which you want to reinforce the army");

				Country country = (Country) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Select Country", "Input",
						JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(),
						GamePlayService.getInstance().getCountriesConqueredBy(this).toArray(), null);

				logger.write("How many armies you wish to reinforce between 1 - " + numberOfArmies);
				Integer[] selectOptions = new Integer[numberOfArmies];
				for (int i = 0; i < numberOfArmies; i++) {
					selectOptions[i] = i + 1;
				}
				Integer armiesWishToReinforce = (Integer) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Number of Armies",
						"Input", JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions,
						selectOptions[0]);
				country.addArmies(armiesWishToReinforce);
				numberOfArmies = numberOfArmies - armiesWishToReinforce;
				logger.write("You are now left with "+numberOfArmies+" armies");
				setChanged();
				notifyObservers();
			}
			if (numberOfArmies == 0) {
				logger.write(
						"You have successfully placed all the armies into the countries you selected. Moving to the next phase.");
				return;
			

		}

	}
	
	public void doAttack(){
		ConsoleLoggerService.getInstance(null).write("Skipping the attack phase for now");
		return;
	}
	
	public void doFortification(){

		ConsoleLoggerService logger = ConsoleLoggerService.getInstance(null);
		
		logger.write("Fortification Phase");
		logger.write("Do you wish to enter Fortification phase?");
		String[] selectionValues = { "Yes", "No" };
		String str = JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Enter Fortification Phase?", "Input",
				JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), selectionValues, "Yes").toString();
		if (str.equalsIgnoreCase("Yes")) {

			logger.write("These are your countries with current armies present in it : "
					+ GamePlayService.getInstance().printCountryAllocationToConsole(this));
			logger.write("Please select the country from which you want to take armies");
			List<Country> selectOptions = GamePlayService.getInstance().getCountriesConqueredBy(this);
			
			boolean inputCaptured = false;
			
			Country fromCountry;
			
			do{
				fromCountry = (Country) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Select Country", "Input",
						JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions.toArray(), null);

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
				if (!c.equals(fromCountry) && GamePlayService.getInstance().isConnected(fromCountry, c)) {
					toCountryOptions.add(c);
				}
			}
			
			inputCaptured = false;
			Country toCountry;
			
			do{
				toCountry = (Country) JOptionPane.showInputDialog(GamePlayService.getInstance().game_play_frame, "Select Country", "Input",
						JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), toCountryOptions.toArray(), null);

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
					JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), optionArmies, 1);
			
			GamePlayService.getInstance().moveArmyFromTo(this, fromCountry, toCountry, armies);
			
			logger.write(this.name + " has completed fortification");
			
		} else {
			return;
		}
	}
	
	public void setCurrentPhase(GamePhase currentPhase){
		this.game_phase = currentPhase;
	}
	
}
