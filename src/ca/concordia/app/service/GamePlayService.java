package ca.concordia.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicIconFactory;

import ca.concordia.app.model.Continent;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.DiceRoller;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.view.NewGamePlayView;

/**
 * This is GamePlayService class through which game is going to play.
 * It also contains startup, reinforcement and fortification phases.
 * @author Parth Nayak
 */
public class GamePlayService {

	/** The instance. 
	*
	*  Creaing an instance of GamePlayService and initialize it with null.
	*/
	private static GamePlayService instance = null;

	/** The number of players. */
	private int number_of_players;

	/** Creating an object of GameMap class. */
	private GameMap game_map;

	/** This is list of players. */
	private List<Player> players;

	/** This player country map in which key is Player and value is Country. */
	private Map<Player, List<Country>> player_country_map;

	/** The turn for the player. */
	private int turn = 0;

	/** An instance of the logger service to write to gameplay console */
	private ConsoleLoggerService logger;

	/**
	 * Instantiates a new game play service.
	 */
	private GamePlayService() {
		game_map = GameMap.getInstance();
		players = new ArrayList<>();
		player_country_map = new HashMap<>();
		logger = ConsoleLoggerService.getInstance(null);
	}

	/**
	 * Gets the instance single of GamePlayService class.
	 *
	 * @return single instance of GamePlayService
	 */
	public static GamePlayService getInstance() {
		if (instance == null)
			instance = new GamePlayService();
		return instance;
	}

	/**
	 * Resetting players data.
	 * 
	 */
	private void resetPlayersData() {
		turn = 0;
		for (Player p : players) {
			p.setTotalArmies(getInitialArmy());
		}
	}

	/**
	 * Getting the initial army as per the number of players.
	 *
	 * @return the initial army
	 */
	public int getInitialArmy() {
		switch (number_of_players) {
		case 2:
			return 40;
		case 3:
			return 35;
		case 4:
			return 30;
		case 5:
			return 25;
		case 6:
			return 20;
		default:
			return 10;
		}
	}

	/**
	 * This is startup phase. 
	 * As per the number of players it will generate the data, initializing players data, 
	 * allocating countries to players and it will add aremies using round-robin fashion.
	 * 
	 * @param numberOfPlayers the number of players
	 * @return true, if successful
	 */
	public boolean doStartupPhase(int numberOfPlayers) {
		if (game_map.getCountries().isEmpty())
			return false;

		this.number_of_players = numberOfPlayers;

		for (int i = 1; i <= numberOfPlayers; i++) {
			players.add(new Player("Player " + i));
		}

		resetPlayersData();

		allocateCountriesToPlayers();

		addInitialArmiesUsingRoundRobin();

		return true;
	}

	/**
	 * Adding the initial armies using Roun-Robin fashion.
	 */
	private void addInitialArmiesUsingRoundRobin() {
		int j = 0;
		int playersLeftForAssign = number_of_players;
		while (playersLeftForAssign > 0) {
			if (players.get(j % number_of_players).getTotalArmies() > 0) {
				List<Country> playerCountryList = getCountriesConqueredBy(players.get(j % number_of_players));
				Country randomCountry = playerCountryList.get(new Random().nextInt(playerCountryList.size()));
				randomCountry.addArmies(1);
				players.get(j % number_of_players)
						.setTotalArmies(players.get(j % number_of_players).getTotalArmies() - 1);
			} else {
				playersLeftForAssign--;
			}
			j++;
		}
	}

	/**
	 * Allocating countries to players.
	 */
	private void allocateCountriesToPlayers() {
		int j = 0;
		for (Country c : game_map.getCountries()) {
			Player p = players.get(j % number_of_players);
			setNewCountryRuler(p, c, 1);
			p.subArmy(1);
			j++;
		}
	}

	/**
	 * Geting the number of players.
	 *
	 * @return the number of players
	 */
	public int getNumberOfPlayers() {
		return number_of_players;
	}

	/**
	 * Getting the players.
	 *
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Getting the current turn player.
	 *
	 * @return the current turn player
	 */
	public Player getCurrentTurnPlayer() {
		return players.get(turn);
	}

	/**
	 * Changing turn to next player.
	 *
	 * @return the player
	 */
	public Player changeTurnToNextPlayer() {
		turn = (turn + 1) % number_of_players;
		return getCurrentTurnPlayer();
	}

	/**
	 * Getting the reinforcement army for player.
	 *
	 * @param p the p
	 * @return the reinforcement army for player
	 */
	public int getReinforcementArmyForPlayer(Player p) {

		int countArmy = 0;
		int countriesCounquered = getCountriesConqueredBy(p).size();
		if (countriesCounquered <= 11 && countriesCounquered > 0) {
			countArmy = 3;
		} else {
			countArmy = countriesCounquered / 3;
		}

		List<Continent> ruledContinents = getContinentsCounqueredBy(p);
		for (Continent c : ruledContinents)
			countArmy += c.getControlValue();

		return countArmy;

	}

	/**
	 * Geting the countries conquered by.
	 *
	 * @param p the p
	 * @return the countries conquered by
	 */
	public List<Country> getCountriesConqueredBy(Player p) {
		return player_country_map.get(p);
	}

	/**
	 * Getting the continents counquered by.
	 *
	 * @param p the p
	 * @return the continents counquered by
	 */
	public List<Continent> getContinentsCounqueredBy(Player p) {
		List<Continent> lst = new ArrayList<>();
		for (Continent c : game_map.getContinents()) {
			boolean isRuler = true;
			for (Country country : game_map.getCountriesByContinent(c.getContinentName())) {
				if (!country.getRuler().equals(p)) {
					isRuler = false;
					break;
				}

			}
			if (isRuler)
				lst.add(c);
		}

		return lst;
	}

	/**
	 * Setting the new country ruler.
	 *
	 * @param ruler the ruler
	 * @param country the country
	 * @param numberOfArmies the number of armies
	 * @return true, if successful
	 */
	public boolean setNewCountryRuler(Player ruler, Country country, int numberOfArmies) {
		if (country.getNoOfArmy() != 0)
			return false;
		country.setRuler(ruler, numberOfArmies);
		mapPlayerToCountry(ruler, country);
		return true;
	}

	/**
	 * Capturing country.
	 *
	 * @param ruler the ruler
	 * @param country the country
	 * @param fromCountry the from country
	 * @param numberOfArmies the number of armies
	 */
	public void captureCountry(Player ruler, Country country, Country fromCountry, int numberOfArmies) {
		if (!setNewCountryRuler(ruler, fromCountry, numberOfArmies)) {
			Player defeatedRuler = country.getRuler();
			unmapPlayerToCountry(defeatedRuler, country);
			defeatedRuler.subArmy(country.getNoOfArmy());

			mapPlayerToCountry(ruler, country);
			country.setRuler(ruler, numberOfArmies);
			fromCountry.removeArmies(numberOfArmies);
		}
	}

	/**
	 * Adding the armies.
	 *
	 * @param p the p
	 * @param c the c
	 * @param addAmount the add amount
	 * @return true, if successful
	 */
	public boolean addArmies(Player p, Country c, int addAmount) {
		if (c.getNoOfArmy() == 0 || player_country_map.get(p).contains(c)) {
			p.addArmy(addAmount);
			c.addArmies(addAmount);
			return true;
		}
		return false;
	}

	/**
	 * The subtract armies method.
	 *
	 * @param p the p
	 * @param c the c
	 * @param subAmount the sub amount
	 * @return true, if successful
	 */
	public boolean subArmies(Player p, Country c, int subAmount) {
		if ((c.getNoOfArmy() == 0 || player_country_map.get(p).contains(c)) && ((c.getNoOfArmy() - subAmount) >= 0)) {
			p.subArmy(subAmount);
			c.removeArmies(subAmount);
			if (c.getNoOfArmy() == 0)
				c.setRuler(null, 0);
			return true;
		}
		return false;
	}

	/**
	 * Moving army from user selected country to destination country.
	 *
	 * @param p the p
	 * @param fromCountry the from country
	 * @param toCountry the to country
	 * @param noOfArmy the no of army
	 * @return true, if successful
	 */
	public void moveArmyFromTo(Player p, Country fromCountry, Country toCountry, int noOfArmy) {
			fromCountry.removeArmies(noOfArmy);
			toCountry.addArmies(noOfArmy);
	}

	/**
	 * Checks if it is neighbour.
	 *
	 * @param c1 the c 1
	 * @param c2 the c 2
	 * @return true, if is neighbour
	 */
	public boolean isNeighbour(Country c1, Country c2) {
		return (game_map.getNeighbourCountries(c1).contains(c2));
	}

	/**
	 * Checks if it is connected.
	 *
	 * @param c1 the c 1
	 * @param c2 the c 2
	 * @param p the p
	 * @return true, if is connected
	 */
	public boolean isConnected(Country c1, Country c2, Player p) {
		return isConnected(c1, c2, p, null);
	}

	/**
	 * Checks if it is connected.
	 *
	 * @param c1 the c 1
	 * @param c2 the c 2
	 * @return true, if is connected
	 */
	public boolean isConnected(Country c1, Country c2) {
		return isConnected(c1, c2, new ArrayList<Country>());
	}

	/**
	 * Checks if it is connected.
	 *
	 * @param c1 the c 1
	 * @param c2 the c 2
	 * @param p the p
	 * @param unwantedPair the unwanted pair
	 * @return true, if is connected
	 */
	private boolean isConnected(Country c1, Country c2, Player p, List<Country> unwantedPair) {
		if (isNeighbour(c1, c2) && c1.getRuler().equals(c2.getRuler()))
			return true;

		if (unwantedPair == null)
			unwantedPair = new ArrayList<>();
		else if (unwantedPair.contains(c1))
			return false;
		unwantedPair.add(c1);

		for (Country c : game_map.getNeighbourCountries(c1)) {
			if (!unwantedPair.contains(c) && isConnected(c, c2, p, unwantedPair))
				return true;
		}

		return false;
	}

	/**
	 * Checks if it is connected.
	 *
	 * @param c1 the c 1
	 * @param c2 the c 2
	 * @param unwantedPair the unwanted pair
	 * @return true, if is connected
	 */
	private boolean isConnected(Country c1, Country c2, List<Country> unwantedPair) {
		if (isNeighbour(c1, c2))
			return true;

		if (unwantedPair == null)
			unwantedPair = new ArrayList<>();
		else if (unwantedPair.contains(c1))
			return false;
		unwantedPair.add(c1);

		for (Country c : game_map.getNeighbourCountries(c1)) {
			if (!unwantedPair.contains(c) && isConnected(c, c2, unwantedPair))
				return true;
		}

		return false;
	}

	/**
	 * Who winning after attacking.
	 *
	 * @param attackResult the attack result
	 * @param defenceResult the defence result
	 * @return the int
	 */
	public int whoWins(int[] attackResult, int[] defenceResult) {
		Arrays.sort(attackResult);
		Arrays.sort(defenceResult);
		int n = attackResult.length > defenceResult.length ? defenceResult.length : attackResult.length;

		int warResult = 0;
		for (int i = 0; i < n; i++) {
			if (attackResult[i] > defenceResult[i])
				warResult++;
			else
				warResult--;
		}

		return warResult;
	}

	/**
	 * Can able to war or not.
	 *
	 * @param fromCountry the from country
	 * @param toCountry the to country
	 * @return true, if successful
	 */
	public boolean canWar(Country fromCountry, Country toCountry) {
		return game_map.getNeighbourCountries(fromCountry).contains(toCountry)
				&& fromCountry.getRuler() != toCountry.getRuler() && fromCountry.getNoOfArmy() > 1
				&& toCountry.getNoOfArmy() > 0;
	}

	/**
	 * Getting the attack dice limit.
	 *
	 * @param p the p
	 * @param c the c
	 * @return the attack dice limit
	 */
	public int getAttackDiceLimit(Player p, Country c) {
		if (player_country_map.get(p).contains(c) && c.getNoOfArmy() > 1) {
			return c.getNoOfArmy() > 3 ? 3 : c.getNoOfArmy() - 1;
		}

		return -1;
	}

	/**
	 * Getting the defence dice limit.
	 *
	 * @param p the p
	 * @param c the c
	 * @return the defence dice limit
	 */
	public int getDefenceDiceLimit(Player p, Country c) {
		if (player_country_map.get(p).contains(c)) {
			// max 2 dies, min 1 dies
			return c.getNoOfArmy() == 1 ? 1 : 2;
		}
		return -1;
	}

	/**
	 * Getting the attack dice roller.
	 *
	 * @param p the p
	 * @param c the c
	 * @return the attack dice roller
	 */
	public DiceRoller getAttackDiceRoller(Player p, Country c) {
		int n = getAttackDiceLimit(p, c);
		if (n == -1)
			return null;
		else
			return new DiceRoller(instance, n);
	}

	/**
	 * Getting the defence dice roller.
	 *
	 * @param p the p
	 * @param c the c
	 * @return the defence dice roller
	 */
	public DiceRoller getDefenceDiceRoller(Player p, Country c) {
		int n = getDefenceDiceLimit(p, c);
		if (n == -1)
			return null;
		else
			return new DiceRoller(instance, n);
	}

	/**
	 * put a player on a country
	 *
	 * @param p the player
	 * @param c the country
	 */
	private void mapPlayerToCountry(Player p, Country c) {
		List<Country> cList = player_country_map.get(p);
		if (cList == null) {
			cList = new ArrayList<>();
			player_country_map.put(p, cList);
		}
		cList.add(c);
	}

	/**
	 * Remove a player from country when defeated
	 *
	 * @param p the player
	 * @param c the country
	 */
	private void unmapPlayerToCountry(Player p, Country c) {
		List<Country> cList = player_country_map.get(p);
		if (cList != null) {
			cList.remove(c);
		}
	}

	/**
	 * Getting the game play state.
	 *
	 * @return the game play state
	 */
	public Object[][] getGamePlayState() {

		GamePlayService game = GamePlayService.getInstance();
		List<Country> gameCountries = new ArrayList<Country>();
		for (Entry<Player, List<Country>> key : game.player_country_map.entrySet()) {
			gameCountries.addAll(key.getValue());
		}
		Object[][] gamePlayState = new Object[gameCountries.size()][3];

		for (int i = 0; i < gameCountries.size(); i++) {
			gamePlayState[i][0] = gameCountries.get(i).getCountryName();
			gamePlayState[i][1] = gameCountries.get(i).getNoOfArmy();
			gamePlayState[i][2] = gameCountries.get(i).getRuler().getName();
		}

		return gamePlayState;
	}

	/**
	 * Getting the map.
	 *
	 * @return the map
	 */
	public GameMap getMap() {
		return this.game_map;
	}

	/**
	 * Initiates the game play.
	 *
	 */
	public void doPlayGame(NewGamePlayView gamePlayView) {

		logger = ConsoleLoggerService.getInstance(gamePlayView.console);

		logger.write("Startup Phase Completed");
		logger.write("Number of Players : " + players.size());

		int j = 0;
		while (true) {
			Player player = players.get(j % players.size());
			logger.write("*** "+player.name+ " Turn Start ***");
			doReinforcementPhase(player, gamePlayView);
			doAttackPhase(player);
			doFortificationPhase(player, gamePlayView);
			logger.write("*** "+player.name+ " Turn End ***");
			j++;
		}
	}

	//run the reinforcement phase of a player's turn
	private void doReinforcementPhase(Player player, NewGamePlayView gamePlayView) {

		logger.write("Do you wish to enter Reinforcement phase?");

		String[] options = { "Yes", "No" };

		String str = JOptionPane.showInputDialog(gamePlayView, "Enter Reinforcemet Phase?", "Input",
				JOptionPane.OK_OPTION, BasicIconFactory.getMenuArrowIcon(), options, "Yes").toString();

		if (str.equalsIgnoreCase("Yes")) {
			int numberOfArmies = getReinforcementArmyForPlayer(player);
			logger.write(player.name + " gets " + numberOfArmies + " armies");
			logger.write("These are your countries with current armies present in it : \n"
					+ printCountryAllocationToConsole(player));
			while (numberOfArmies > 0) {
				logger.write("Please select the country in which you want to reinforce the army");

				Country country = (Country) JOptionPane.showInputDialog(gamePlayView, "Select Country", "Input",
						JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(),
						getCountriesConqueredBy(player).toArray(), null);

				logger.write("How many armies you wish to reinforce between 1 - " + numberOfArmies);
				Integer[] selectOptions = new Integer[numberOfArmies];
				for (int i = 0; i < numberOfArmies; i++) {
					selectOptions[i] = i + 1;
				}
				Integer armiesWishToReinforce = (Integer) JOptionPane.showInputDialog(gamePlayView, "Number of Armies",
						"Input", JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions,
						selectOptions[0]);
				country.addArmies(armiesWishToReinforce);
				numberOfArmies = numberOfArmies - armiesWishToReinforce;
				logger.write("You are now left with "+numberOfArmies+" armies");
			}
			if (numberOfArmies == 0) {
				logger.write(
						"You have successfully placed all the armies into the countries you selected. Moving to the next phase.");
				return;
			}

		} else {
			return;
		}

	}
	
	//run the attack phase of a player's turn - TODO
	private void doAttackPhase(Player player) {
		logger.write("Skipping the attack phase for now");
		return;

	}

	//run the fortification phase of a player's turn
	private void doFortificationPhase(Player player, NewGamePlayView gamePlayView) {

		logger.write("Fortification Phase");
		logger.write("Do you wish to enter Fortification phase?");
		String[] selectionValues = { "Yes", "No" };
		String str = JOptionPane.showInputDialog(gamePlayView, "Enter Fortification Phase?", "Input",
				JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), selectionValues, "Yes").toString();
		if (str.equalsIgnoreCase("Yes")) {

			logger.write("These are your countries with current armies present in it : "
					+ printCountryAllocationToConsole(player));
			logger.write("Please select the country from which you want to take armies");
			List<Country> selectOptions = getCountriesConqueredBy(player);
			
			boolean inputCaptured = false;
			
			Country fromCountry;
			
			do{
				fromCountry = (Country) JOptionPane.showInputDialog(gamePlayView, "Select Country", "Input",
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
				if (!c.equals(fromCountry)) {
					toCountryOptions.add(c);
				}
			}
			
			inputCaptured = false;
			Country toCountry;
			
			do{
				toCountry = (Country) JOptionPane.showInputDialog(gamePlayView, "Select Country", "Input",
						JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), toCountryOptions.toArray(), null);

				boolean areBothCountriesConnected = isConnected(fromCountry, toCountry, player);
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
			Integer armies = (Integer) JOptionPane.showInputDialog(gamePlayView, "Number of Armies to Move", "Input",
					JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), optionArmies, 1);
			
			moveArmyFromTo(player, fromCountry, toCountry, armies);
			
			logger.write(player.name + " has completed fortification");
			
		} else {
			return;
		}
	}
	
	//print the player country allocation during gameplay
	private String printCountryAllocationToConsole(Player player) {

		String s = player.name + " - [ ";
		List<Country> countries = getCountriesConqueredBy(player);
		for (Country c : countries)
			s += "" + c.getCountryName() + "(" + c.getNoOfArmy() + "), ";
		s += "]\n";
		return s;
	}

}