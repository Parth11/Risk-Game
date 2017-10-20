package ca.concordia.app.service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicIconFactory;

import java.util.Random;

import ca.concordia.app.controller.MainController;
import ca.concordia.app.model.Continent;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.DiceRoller;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.util.MapValidationException;
import ca.concordia.app.view.NewGamePlayView;

/**
 * @author Parth Nayak
 * @author Hardik Fumakiya
 * 
 */

public class GamePlayService {

	private static GamePlayService instance = null;

	private String map_path = null;

	private int number_of_players;

	private GameMap game_map;

	private List<Player> players;

	private Map<Player, List<Country>> player_country_map;

	private int turn = 0;
	
	private ConsoleLoggerService logger;

	private GamePlayService() {
		game_map = GameMap.getInstance();
		players = new ArrayList<>();
		player_country_map = new HashMap<>();
		logger = ConsoleLoggerService.getInstance(null);
	}

	public static GamePlayService getInstance() {
		if (instance == null)
			instance = new GamePlayService();
		return instance;
	}


	private void resetPlayersData() {
		turn = 0;
		for (Player p : players) {
			p.setTotalArmies(getInitialArmy());
		}
	}

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

	private void addInitialArmiesUsingRoundRobin() {
		int j = 0;
		int playersLeftForAssign = number_of_players;
		while (playersLeftForAssign > 0) {
			if (players.get(j % number_of_players).getTotalArmies() > 0) {
				List<Country> playerCountryList = getCountriesConqueredBy(players.get(j % number_of_players));
				Country randomCountry = playerCountryList.get(new Random().nextInt(playerCountryList.size()));
				randomCountry.addArmies(1);
				players.get(j % number_of_players).setTotalArmies(players.get(j % number_of_players).getTotalArmies() - 1);
			} else {
				playersLeftForAssign--;
			}
			j++;
		}
	}

	private void allocateCountriesToPlayers() {
		int j = 0;
		for (Country c : game_map.getCountries()) {
			Player p = players.get(j % number_of_players);
			setNewCountryRuler(p, c, 1);
			p.subArmy(1);
			j++;
		}
	}

	public int getNumberOfPlayers() {
		return number_of_players;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Player getCurrentTurnPlayer() {
		return players.get(turn);
	}

	public Player changeTurnToNextPlayer() {
		turn = (turn + 1) % number_of_players;
		return getCurrentTurnPlayer();
	}

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

	public List<Country> getCountriesConqueredBy(Player p) {
		return player_country_map.get(p);
	}

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

	public boolean setNewCountryRuler(Player ruler, Country country, int numberOfArmies) {
		if (country.getNoOfArmy() != 0)
			return false;
		country.setRuler(ruler, numberOfArmies);
		mapPlayerToCountry(ruler, country);
		return true;
	}

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

	public boolean addArmies(Player p, Country c, int addAmount) {
		if (c.getNoOfArmy() == 0 || player_country_map.get(p).contains(c)) {
			p.addArmy(addAmount);
			c.addArmies(addAmount);
			return true;
		}
		return false;
	}

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

	public boolean moveArmyFromTo(Player p, Country fromCountry, Country toCountry, int noOfArmy) {
		try {

			fromCountry.removeArmies(noOfArmy);
			toCountry.addArmies(noOfArmy);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isNeighbour(Country c1, Country c2) {
		return (game_map.getNeighbourCountries(c1).contains(c2));
	}

	public boolean isConnected(Country c1, Country c2, Player p) {
		return isConnected(c1, c2, p, null);
	}

	public boolean isConnected(Country c1, Country c2) {
		return isConnected(c1, c2, new ArrayList<Country>());
	}

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

	/*
	 * @return warResult if warResult is a positive integer, means attacker wins
	 * if it is negative, means defender wins 0 means draw
	 * 
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

	public boolean canWar(Country fromCountry, Country toCountry) {
		return game_map.getNeighbourCountries(fromCountry).contains(toCountry)
				&& fromCountry.getRuler() != toCountry.getRuler()
				&& fromCountry.getNoOfArmy() > 1 
				&& toCountry.getNoOfArmy() > 0; 
	}

	public int getAttackDiceLimit(Player p, Country c) {
		if (player_country_map.get(p).contains(c) && c.getNoOfArmy() > 1) {
			return c.getNoOfArmy() > 3 ? 3 : c.getNoOfArmy() - 1;
		}

		return -1;
	}

	public int getDefenceDiceLimit(Player p, Country c) {
		if (player_country_map.get(p).contains(c)) {
			// max 2 dies, min 1 dies
			return c.getNoOfArmy() == 1 ? 1 : 2;
		}
		return -1;
	}

	public DiceRoller getAttackDiceRoller(Player p, Country c) {
		int n = getAttackDiceLimit(p, c);
		if (n == -1)
			return null;
		else
			return new DiceRoller(instance, n);
	}

	public DiceRoller getDefenceDiceRoller(Player p, Country c) {
		int n = getDefenceDiceLimit(p, c);
		if (n == -1)
			return null;
		else
			return new DiceRoller(instance, n);
	}

	private void mapPlayerToCountry(Player p, Country c) {
		List<Country> cList = player_country_map.get(p);
		if (cList == null) {
			cList = new ArrayList<>();
			player_country_map.put(p, cList);
		}
		cList.add(c);
	}

	private void unmapPlayerToCountry(Player p, Country c) {
		List<Country> cList = player_country_map.get(p);
		if (cList != null) {
			cList.remove(c);
		}
	}

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

	public GameMap getMap() {
		return this.game_map;
	}


	public void doPlayGame(NewGamePlayView gamePlayView) {
		
		logger = ConsoleLoggerService.getInstance(gamePlayView.console);
		
		logger.write("Game phase starts");
		logger.write("Number of Players : " + players.size());
		

		int j = 0;
		while (true) {
			Player player = players.get(j % players.size());
			doReinforcementPhase(player,gamePlayView);
			doAttackPhase(player);
			doFortificationPhase(player,gamePlayView);
			j++;
		}
	}

	private void doReinforcementPhase(Player player, NewGamePlayView gamePlayView) {
		logger.write("Do you wish to enter Reinforcement phase?");

		String[] options = { "Yes", "No" };

		String str = JOptionPane.showInputDialog(gamePlayView, "Enter Reinforcemet Phase?", "Input",
				JOptionPane.OK_OPTION, null, options, "Yes").toString();

		if (str.equalsIgnoreCase("Yes")) {
			int numberOfArmies = getReinforcementArmyForPlayer(player);
			logger.write("You get " + numberOfArmies);
			logger.write("These are your countries with current armies present in it : "
					+ printCountryAllocationToConsole(player));
			while (numberOfArmies > 0) {
				logger.write("Please type the exact name of the country in which you want to reinforce the army");

				Country country = (Country) JOptionPane.showInputDialog(gamePlayView, "Select Country",
						"Input", JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(),
						getCountriesConqueredBy(player).toArray(), null);

				logger.write("How many armies you wish to reinforce between 1 - " + numberOfArmies);
				Integer[] selectOptions = new Integer[numberOfArmies];
				for(int i=0;i<numberOfArmies;i++){
					selectOptions[i] = i+1;
				}
				Integer armiesWishToReinforce = (Integer) JOptionPane.showInputDialog(gamePlayView, "Number of Armies","Input",JOptionPane.YES_OPTION,BasicIconFactory.getMenuArrowIcon(),selectOptions,selectOptions[0]);
				country.addArmies(armiesWishToReinforce);
				numberOfArmies = numberOfArmies - armiesWishToReinforce;
			}
			if (numberOfArmies == 0) {
				logger.write(
						"You have successfully placed all the armies into the countries you selected. Moving to attack phase.");
				return;
			}

		} else {
			return;
		}

	}

	private void doAttackPhase(Player player) {
		logger.write("Skipping the attack phase for BUILD 1, just for now");
		return;

	}

	private void doFortificationPhase(Player player, NewGamePlayView gamePlayView) {

		logger.write("Fortification Phase");
		logger.write("Do you wish to enter Fortification phase?");
		String[] selectionValues = { "Yes", "No" };
		String str = JOptionPane.showInputDialog(gamePlayView, "Enter Fortification Phase?", "Input",
				JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), selectionValues, "Yes").toString();
		if (str.equalsIgnoreCase("Yes")) {

			logger.write("These are your countries with current armies present in it : "
					+ printCountryAllocationToConsole(player));
			logger.write("Please enter the country name from which you want to take armies");
			List<Country> selectOptions = getCountriesConqueredBy(player);
			Country fromCountry = (Country) JOptionPane.showInputDialog(gamePlayView, "Select Country",
					"Input", JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions.toArray(),
					null);

			if (fromCountry.getNoOfArmy() == 1) {
				logger.write("Please leave atleast one army behind, so it can defend your country from an attack.");
				logger.write("Please select again ::  ");
				doFortificationPhase(player,gamePlayView);
			}
			logger.write("Please select the country to which you want to add armies :");

			List<Country> toCountryOptions = new ArrayList<Country>();
			for (Country c : selectOptions) {
				if (!c.equals(fromCountry)) {
					toCountryOptions.add(c);
				}
			}
			Country toCountry = (Country) JOptionPane.showInputDialog(gamePlayView, "Select Country",
					"Input", JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), toCountryOptions.toArray(),
					null);

			logger.write("Let's check if there is a direct path from " + fromCountry + " to " + toCountry
					+ ", in which, between countries(if any) are ruled by you");
			boolean areBothCountriesConnected = isConnected(fromCountry, toCountry, player);
			if (areBothCountriesConnected) {
				logger.write("Great ! Both are connected");
			} else {
				logger.write("Not connected ! Select Again");
				doFortificationPhase(player,gamePlayView);
			}
			logger.write("Now please select the number of armies from " + fromCountry.getCountryName()
					+ ", which has : " + fromCountry.getNoOfArmy());
			Integer[] optionArmies = new Integer[fromCountry.getNoOfArmy() - 1];

			for (int i = 0; i < optionArmies.length; i++) {
				optionArmies[i] = i + 1;
			}
			Integer armies = (Integer) JOptionPane.showInputDialog(gamePlayView, "Number of Armies to Move",
					"Input", JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), optionArmies, 1);
			boolean isFortifyComplete = moveArmyFromTo(player, fromCountry, toCountry, armies);
			if (isFortifyComplete)
				return;
			else
				doFortificationPhase(player,gamePlayView);

		} else {
			gamePlayView.dispose();
			new MainController();
		}
	}
	
	private String printCountryAllocationToConsole(Player player) {
		
		String s = player.name + " - [ ";
		List<Country> countries = getCountriesConqueredBy(player);
		for (Country c : countries)
			s += "" + c.getCountryName() + "(" + c.getNoOfArmy() + "), ";
		s += "]\n";
		logger.write(s);
		return s;
	}

}
