package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicIconFactory;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.service.ConsoleLoggerService;
import ca.concordia.app.view.NewGamePlayView;

public class NewGamePhaseController implements ActionListener,MouseListener{
	
	NewGamePlayView new_game_phase_selector;
	
	GamePlayService gameApi = GamePlayService.getInstance();
	private static String currentPhase = "";
	int numPlayers;
	List<Player> playerList = gameApi.getPlayers();
	//get logger
	ConsoleLoggerService logger;
	
	public NewGamePhaseController(int numPlayers, ConsoleLoggerService logger) {
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
		String[] selectionValues = {"Yes","No"};
		String str = JOptionPane.showInputDialog(new_game_phase_selector, "Enter Fortification Phase?", "Input", JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), selectionValues, "Yes").toString();
		if(str.equalsIgnoreCase("Yes")) {
		
		logger.write("These are your countries with current armies present in it : " + printPlayerCountriesAndArmy(player));
		logger.write("Please enter the country name from which you want to take armies");
		List<Country> selectOptions = gameApi.getCountriesConqueredBy(player);
		Country fromCountry = (Country) JOptionPane.showInputDialog(new_game_phase_selector, "Select Country", "Input", JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(),selectOptions.toArray(),null);
		
		if(fromCountry.getNoOfArmy() == 1) {
			logger.write("Please leave atleast one army behind, so it can defend your country from an attack.");
			logger.write("Please select again ::  ");			
			enterFortificationPhase(player);
		}
		logger.write("Please select the country to which you want to add armies :");
		
		List<Country> toCountryOptions = new ArrayList<Country>();
		for(Country c : selectOptions){
			if(!c.equals(fromCountry)){
				toCountryOptions.add(c);
			}
		}
		Country toCountry = (Country) JOptionPane.showInputDialog(new_game_phase_selector, "Select Country", "Input", JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), toCountryOptions.toArray(),null);
		
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
			Integer[] optionArmies = new Integer[fromCountry.getNoOfArmy()-1];
			
			for(int i = 0;i<optionArmies.length;i++){
				optionArmies[i] = i+1;
			}
			Integer armies = (Integer) JOptionPane.showInputDialog(new_game_phase_selector, "Number of Armies to Move", 
					"Input", JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), optionArmies, 1);
			boolean isFortifyComplete = gameApi.moveArmyFromTo(player, fromCountry, toCountry, armies);
			if(isFortifyComplete) return;
			else enterFortificationPhase(player);	
	
		}
		else
		{
			new_game_phase_selector.dispose();
			new MainController();
		}
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
		
		String[] options = {"Yes","No"};

		String str = JOptionPane.showInputDialog(new_game_phase_selector, "Enter Reinforcemet Phase?", "Input", JOptionPane.OK_OPTION, null,options, "Yes").toString();
		
		if(str.equalsIgnoreCase("Yes")) {			
			int numberOfArmies = gameApi.getReinforcementArmyForPlayer(player);
			logger.write("You get "+ numberOfArmies);
			logger.write("These are your countries with current armies present in it : "+ printPlayerCountriesAndArmy(player));
				while (numberOfArmies > 0) {
					logger.write("Please type the exact name of the country in which you want to reinforce the army");
					
					Country country = (Country) JOptionPane.showInputDialog(new_game_phase_selector, "Select Country", "Input", JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), gameApi.getCountriesConqueredBy(player).toArray(),null);
					
					//Country country = gameApi.getMap().getCountryByName(inputCountryName);
					logger.write("How many armies you wish to reinforce between 0 - "+numberOfArmies);
					int armiesWishToReinforce = Integer.parseInt(JOptionPane.showInputDialog(new_game_phase_selector, "Number of Armies"));
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
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
