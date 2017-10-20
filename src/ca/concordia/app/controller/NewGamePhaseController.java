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


/**
 * The Class NewGamePhaseController manages the starting of the game.
 * 
 * @author Parth
 */
public class NewGamePhaseController implements ActionListener,MouseListener{
	
	/**
	 * Create the object of NewGamePlayView class.
	 */
	NewGamePlayView new_game_phase_selector;
	
	/** Creates the object of the GamePlayService */
	GamePlayService gameApi = GamePlayService.getInstance();
	
	/** The num players. */
	int numPlayers;
	
	/** The player list. */
	List<Player> playerList = gameApi.getPlayers();
	
	/** The logger. */
	ConsoleLoggerService logger;
	
	/**
	 * Instantiates a new new game phase controller.
	 */
	public NewGamePhaseController() {
		new_game_phase_selector = new NewGamePlayView();
		this.numPlayers=GamePlayService.getInstance().getNumberOfPlayers();
		this.logger=ConsoleLoggerService.getInstance(null);
		new_game_phase_selector.setActionListener(this);
		new_game_phase_selector.setMouseListener(this);
		new_game_phase_selector.setVisible(true);
		init();
		
	}
	
	/**
	 * Initializes the start
	 */
	private void init() {
		logger.write("Game phase starts");
		logger.write(""+playerList.size());
		startPlayingGame();
		
		
	}

	/**
	 * Start playing game.
	 */
	public void startPlayingGame() {
		int j = 0;
		while(true) {
			Player player = playerList.get(j % numPlayers);
			enterReinforcementPhase(player);
			enterAttackPhase(player);
			enterFortificationPhase(player);
			j++;
		}
	}

	/**
	 * Enter fortification phase.
	 *
	 * @param player the player
	 */
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
	
	/**
	 * Prints the player countries and army.
	 *
	 * @param player the player
	 * @return the string
	 */
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

	/**
	 * Enter attack phase.
	 *
	 * @param player the player
	 */
	private void enterAttackPhase(Player player) {
		// TODO Auto-generated method stub
		logger.write("Skipping the attack phase for BUILD 1, just for now");
		return;
		
	}

	/**
	 * Enter reinforcement phase.
	 *
	 * @param player the player
	 */
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

	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
