package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.Game;
import ca.concordia.app.service.MyLogger;
import ca.concordia.app.view.NewGamePlayView;

public class NewGamePhaseController implements ActionListener,MouseListener{
	
	NewGamePlayView new_game_phase_selector;
	
	Game gameApi = Game.getInstance();
	private static String currentPhase = "";
	int numPlayers;
	List<Player> playerList = gameApi.getPlayers();
	//get logger
	MyLogger logger;
	
	public NewGamePhaseController(int numPlayers, MyLogger logger) {
		// TODO Auto-generated constructor stub
		new_game_phase_selector = new NewGamePlayView();
		this.numPlayers=numPlayers;
		this.logger=logger;
		new_game_phase_selector.setActionListener(this);
		new_game_phase_selector.setMouseListener(this);
		new_game_phase_selector.setVisible(true);
		init();
		
	}
	
	private void init() {
		// TODO Auto-generated method stub
		logger.write("Game phase starts");
		logger.write(""+playerList.size());
		startPlayingGame();
		
		
	}

	public void startPlayingGame() {
		int j = 0;
		// TODO Auto-generated method stub
		while(true) {
			Player player = playerList.get(j % numPlayers);
			enterReinforcementPhase(player);
			enterAttackPhase(player);
			enterFortificationPhase(player);
			j++;
		}
		
		
	}

	private void enterFortificationPhase(Player player) {
		// TODO Auto-generated method stub
		
		logger.write("Fortification Phase");
		logger.write("Do you wish to enter Fortification phase?");
		String str = new_game_phase_selector.textInputField.getText();
		if(str.equalsIgnoreCase("yes")) {
		
		logger.write("These are your countries with current armies present in it : " + printPlayerCountriesAndArmy(player));
		logger.write("Please enter the country name from which you want to take armies");
		String inputFromCountryName = new_game_phase_selector.textInputField.getText();
		Country fromCountry = gameApi.getMap().getCountryByName(inputFromCountryName);
		if(fromCountry.getNoOfArmy() == 1) {
			logger.write("Please leave atleast one army behind, so it can defend your country from an attack.");
			logger.write("Please select again ::  ");			
			enterFortificationPhase(player);
		}
		logger.write("Please select the country to which you want to add armies :");
		String inputToCountryName = new_game_phase_selector.textInputField.getText();
		Country toCountry = gameApi.getMap().getCountryByName(inputToCountryName);
		logger.write("Let's check if there is a direct path from "+fromCountry+" to "+toCountry + ", in which, between countries(if any) are ruled by you");
		boolean areBothCountriesConnected = gameApi.isConnected(fromCountry, toCountry, player);
		if(areBothCountriesConnected) {
			logger.write("Great ! Both are connected");
		}
		else {
			logger.write("Not connected ! Select Again");
			enterFortificationPhase(player);
		}			
			logger.write("Now please select the number of armies from "+ fromCountry.getCountryName() + ", which has : "+fromCountry.getNoOfArmy());
			int armies = validInputOfArmies(fromCountry);
			boolean isFortifyComplete = gameApi.moveArmyFromTo(player, fromCountry, toCountry, armies);
			if(isFortifyComplete) return;
			else enterFortificationPhase(player);	
	
		}
		else
		{
			return ;
		}
	}
	
	private int validInputOfArmies(Country fromCountry) {
		int count = Integer.parseInt(new_game_phase_selector.textInputField.getText());
		if(count == fromCountry.getNoOfArmy() || count > fromCountry.getNoOfArmy()) {
			logger.write("Please leave atleast one army behind, so it can defend your country from an attack.");
			validInputOfArmies(fromCountry);
		}
		return count;
		
		// TODO Auto-generated method stub
		
	}

	private String printPlayerCountriesAndArmy(Player player) {
		// TODO Auto-generated method stub
		String s = player.name + " - [ ";				
		// get countries of the player + no. of armies assigned
		List<Country> countries = gameApi.getCountriesConqueredBy(player);
		for(Country c : countries)
			s += "" + c.getCountryName()+ "(" + c.getNoOfArmy() + "), ";				
		s += "]\n";			
		// write each info to logger files
		logger.write(s);
		return s;
	}

	private void enterAttackPhase(Player player) {
		// TODO Auto-generated method stub
		logger.write("Skipping the attack phase for BUILD 1, just for now");
		return;
		
	}

	private void enterReinforcementPhase(Player player) {
		logger.write("Do you wish to enter Reinforcement phase?");
		String str = new_game_phase_selector.textInputField.getText();
		if(str.equalsIgnoreCase("yes")) {			
			int numberOfArmies = gameApi.getReinforcementArmyForPlayer(player);
			logger.write("You get "+ numberOfArmies);
			logger.write("These are your countries with current armies present in it : "+ printPlayerCountriesAndArmy(player));
				while (numberOfArmies > 0) {
					logger.write("Please type the exact name of the country in which you want to reinforce the army");
					String inputCountryName = new_game_phase_selector.textInputField.getText();
					Country country = gameApi.getMap().getCountryByName(inputCountryName);
					logger.write("How many armies you wish to reinforce between 0 - "+numberOfArmies);
					int armiesWishToReinforce = Integer.parseInt(new_game_phase_selector.textInputField.getText());
					country.addArmies(armiesWishToReinforce);
					numberOfArmies = numberOfArmies - armiesWishToReinforce;					
				}
				if(numberOfArmies == 0) {
					logger.write("You have successfully placed all the armies into the countries you selected. Moving to attack phase.");
					return;
				}
			
		}
		else {
			return;
		}
		
		
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource().equals(new_game_phase_selector.btnSubmit)){
			
			if(new_game_phase_selector.textInputField.getText().equalsIgnoreCase("yes")) {
				
			}
			else if(new_game_phase_selector.textInputField.getText()!=null) {
				
			}
			
			
		}
		else if(e.getSource().equals(new_game_phase_selector.btnFortify)){
			
		}
		else if(e.getSource().equals(new_game_phase_selector.textInputField)){
		
			/*Game gameApi = Game.getInstance();
			playerList = gameApi.getPlayers();
			
			//using DB converter
			DbConverter.convert(GameMap.getInstance(), lib.model.GameMap.getInstance());
			DbConverter.print();
			
			logger.write("Reinforcement Phase Started:-\n----------------------\n");
			reinforcementPhase(numPlayers,gameApi, logger);
			logger.write("Reinforcement Phase Ended:-\n----------------------\n");*/
		}
		
	}

}
