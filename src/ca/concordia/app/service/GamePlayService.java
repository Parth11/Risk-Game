package ca.concordia.app.service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import ca.concordia.app.model.Continent;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.DiceRoller;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.util.MapValidationException;


/**
 * The Class GamePlayService.
 *
 * @author Parth Nayak
 */

public class GamePlayService {
	
	/** The instance. */
	/*
	 * Type Here
	 */
	private static GamePlayService instance = null;

	/** The map path. */
	private String mapPath = null;

	/** The number of players. */
	private int numberOfPlayers;

	/** The game map. */
	private GameMap gameMap;

	/** The players. */
	private List<Player> players;

	/** The player country map. */
	private Map<Player, List<Country>> playerCountryMap;

	/** The turn. */
	private int turn = 0;

	/**
	 * Instantiates a new game play service.
	 */
	private GamePlayService() {
		gameMap = GameMap.getInstance();
		players = new ArrayList<>();
		playerCountryMap = new HashMap<>();
	}

	// map APIs

	/**
	 * Gets the single instance of GamePlayService.
	 *
	 * @return single instance of GamePlayService
	 */
	public static GamePlayService getInstance() {
		if (instance == null)
			instance = new GamePlayService();
		return instance;
	}

	/**
	 * Load new map.
	 *
	 * @param path the path
	 * @throws MapValidationException the map validation exception
	 * @throws URISyntaxException the URI syntax exception
	 */
	public void loadNewMap(String path) throws MapValidationException, URISyntaxException {
		instance.mapPath = path;
		instance.resetPlayersData();
		MapService.getInstance().loadMap(instance.mapPath);
	}

	/**
	 * Reset game.
	 */
	public void resetGame() {
		numberOfPlayers = 0;
		turn = 0;
		players.clear();
		playerCountryMap.clear();
		mapPath = null;
		System.gc();
	}

	// player APIs

	/**
	 * Reset players data.
	 */
	private void resetPlayersData() {
		turn = 0;
		for (Player p : players) {
			// p.addCard(null);
			p.setTotalArmies(getInitialArmy());
		}
	}

	/**
	 * Gets the initial army.
	 *
	 * @return the initial army
	 */
	public int getInitialArmy() {
		switch (numberOfPlayers) {
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
	 * Do startup phase.
	 *
	 * @param numberOfPlayers the number of players
	 * @return true, if successful
	 */
	public boolean doStartupPhase(int numberOfPlayers) {
		if (gameMap.getCountries().isEmpty())
			return false;

		this.numberOfPlayers = numberOfPlayers;

		// generating players
		for (int i = 1; i < numberOfPlayers; i++) {
			players.add(new Player("Player " + i));
		}

		// init players data
		resetPlayersData();

		// allocate countries to players
		allocateCountriesToPlayers();

		// add initial army using round-robin fashion
		addInitialArmiesUsingRR();

		return true;
	}

	/**
	 * Adds the initial armies using RR.
	 */
	private void addInitialArmiesUsingRR() {
		int j = 0;
		int playersLeftForAssign = numberOfPlayers;
		while (playersLeftForAssign > 0) {
			if (players.get(j % numberOfPlayers).getTotalArmies() > 0) {
				List<Country> playerCountryList = getCountriesConqueredBy(players.get(j % numberOfPlayers));
				Country randomCountry = playerCountryList.get(new Random().nextInt(playerCountryList.size()));
				randomCountry.addArmies(1);
				players.get(j % numberOfPlayers).setTotalArmies(players.get(j % numberOfPlayers).getTotalArmies() - 1);
			} else {
				playersLeftForAssign--;
			}
			j++;
		}
	}

	/**
	 * Allocate countries to players.
	 */
	private void allocateCountriesToPlayers() {
		// allocate countries to the players in round-robin fashion
		int j = 0;
		for (Country c : gameMap.getCountries()) {
			Player p = players.get(j % numberOfPlayers);
			setNewCountryRuler(p, c, 1);
			p.subArmy(1);
			j++;
		}
	}

	/**
	 * Adds the initial armies.
	 */
	private void addInitialArmies() {
		for (Player p : getPlayers()) {
			List<Country> cList = getCountriesConqueredBy(p);
			for (int i = 0; i < getInitialArmy(); i++) {
				int index = i % cList.size();
				Country putArmyAt = cList.get(index);
				addArmies(p, putArmyAt, 1);
			}
		}
	}

	/**
	 * Gets the number of players.
	 *
	 * @return the number of players
	 */
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	// Game Edit APIs

	// ...
	// under construction
	// ...

	// Game APIs

	/**
	 * Gets the current turn player.
	 *
	 * @return the current turn player
	 */
	public Player getCurrentTurnPlayer() {
		return players.get(turn);
	}

	/**
	 * Change turn to next player.
	 *
	 * @return the player
	 */
	public Player changeTurnToNextPlayer() {
		turn = (turn + 1) % numberOfPlayers;
		return getCurrentTurnPlayer();
	}

	/**
	 * Gets the reinforcement army for player.
	 *
	 * @param p the p
	 * @return the reinforcement army for player
	 */
	// Change the whole mathematical calculation and add Cards Logics
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
	 * Gets the countries conquered by.
	 *
	 * @param p the p
	 * @return the countries conquered by
	 */
	public List<Country> getCountriesConqueredBy(Player p) {
		return playerCountryMap.get(p);
	}

	/**
	 * Gets the continents counquered by.
	 *
	 * @param p the p
	 * @return the continents counquered by
	 */
	public List<Continent> getContinentsCounqueredBy(Player p) {
		List<Continent> lst = new ArrayList<>();
		for (Continent c : gameMap.getContinents()) {
			boolean isRuler = true;
			for (Country country : gameMap.getCountriesByContinent(c.getContinentName())) {
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
	 * Sets the new country ruler.
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
		pcmPut(ruler, country);
		return true;
	}

	/**
	 * Capture country.
	 *
	 * @param ruler the ruler
	 * @param country the country
	 * @param fromCountry the from country
	 * @param numberOfArmies the number of armies
	 */
	public void captureCountry(Player ruler, Country country, Country fromCountry, int numberOfArmies) {
		if (!setNewCountryRuler(ruler, fromCountry, numberOfArmies)) {
			// remove defeated ruler from the country
			Player defeatedRuler = country.getRuler();
			pcmRemove(defeatedRuler, country);
			defeatedRuler.subArmy(country.getNoOfArmy());

			// add new ruler
			pcmPut(ruler, country);
			country.setRuler(ruler, numberOfArmies);
			fromCountry.removeArmies(numberOfArmies);
		}
	}

	/**
	 * Adds the armies.
	 *
	 * @param p the p
	 * @param c the c
	 * @param addAmount the add amount
	 * @return true, if successful
	 */
	public boolean addArmies(Player p, Country c, int addAmount) {
		if (c.getNoOfArmy() == 0 || playerCountryMap.get(p).contains(c)) {
			p.addArmy(addAmount);
			c.addArmies(addAmount);
			return true;
		}
		return false;
	}

	/**
	 * Sub armies.
	 *
	 * @param p the p
	 * @param c the c
	 * @param subAmount the sub amount
	 * @return true, if successful
	 */
	public boolean subArmies(Player p, Country c, int subAmount) {
		if ((c.getNoOfArmy() == 0 || playerCountryMap.get(p).contains(c)) && ((c.getNoOfArmy() - subAmount) >= 0)) {
			p.subArmy(subAmount);
			c.removeArmies(subAmount);
			if (c.getNoOfArmy() == 0)
				c.setRuler(null, 0);
			return true;
		}
		return false;
	}

	/**
	 * Move army from to.
	 *
	 * @param p the p
	 * @param fromCountry the from country
	 * @param toCountry the to country
	 * @param noOfArmy the no of army
	 * @return true, if successful
	 */
	public boolean moveArmyFromTo(Player p, Country fromCountry, Country toCountry, int noOfArmy) {
		try {

			fromCountry.removeArmies(noOfArmy);
			toCountry.addArmies(noOfArmy);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks if is neighbour.
	 *
	 * @param c1 the c 1
	 * @param c2 the c 2
	 * @return true, if is neighbour
	 */
	public boolean isNeighbour(Country c1, Country c2) {
		return (gameMap.getNeighbourCountries(c1).contains(c2));
	}

	/**
	 * Checks if is connected.
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
	 * Checks if is connected.
	 *
	 * @param c1 the c 1
	 * @param c2 the c 2
	 * @return true, if is connected
	 */
	public boolean isConnected(Country c1, Country c2) {
		return isConnected(c1, c2, new ArrayList<Country>());
	}

	/**
	 * Checks if is connected.
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

		for (Country c : gameMap.getNeighbourCountries(c1)) {
			if (!unwantedPair.contains(c) && isConnected(c, c2, p, unwantedPair))
				return true;
		}

		return false;
	}

	/**
	 * Checks if is connected.
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

		for (Country c : gameMap.getNeighbourCountries(c1)) {
			if (!unwantedPair.contains(c) && isConnected(c, c2, unwantedPair))
				return true;
		}

		return false;
	}

	/**
	 * Who wins.
	 *
	 * @param attackResult the attack result
	 * @param defenceResult the defence result
	 * @return the int
	 */
	/*
	 * @return warResult if warResult is a positive integer, means attacker wins if
	 * it is negative, means defender wins 0 means draw
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

	/**
	 * Can war.
	 *
	 * @param fromCountry the from country
	 * @param toCountry the to country
	 * @return true, if successful
	 */
	public boolean canWar(Country fromCountry, Country toCountry) {
		return gameMap.getNeighbourCountries(fromCountry).contains(toCountry) // should be neighbours
				&& fromCountry.getRuler() != toCountry.getRuler() // shouldn't both countries belong to same player
				&& fromCountry.getNoOfArmy() > 1 // attacker should have more than 1 army
				&& toCountry.getNoOfArmy() > 0; // defence should have atleast 1 army to protect the country
	}

	// Dies APIs

	/**
	 * Gets the attack dice limit.
	 *
	 * @param p the p
	 * @param c the c
	 * @return the attack dice limit
	 */
	public int getAttackDiceLimit(Player p, Country c) {
		if (playerCountryMap.get(p).contains(c) && c.getNoOfArmy() > 1) {
			// max 3 dies, min (c.getNoOfArmy()-1) dies
			return c.getNoOfArmy() > 3 ? 3 : c.getNoOfArmy() - 1;
		}

		return -1;
	}

	/**
	 * Gets the defence dice limit.
	 *
	 * @param p the p
	 * @param c the c
	 * @return the defence dice limit
	 */
	public int getDefenceDiceLimit(Player p, Country c) {
		if (playerCountryMap.get(p).contains(c)) {
			// max 2 dies, min 1 dies
			return c.getNoOfArmy() == 1 ? 1 : 2;
		}
		return -1;
	}

	/**
	 * Gets the attack dice roller.
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
	 * Gets the defence dice roller.
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
	 * Pcm put.
	 *
	 * @param p the p
	 * @param c the c
	 */
	private void pcmPut(Player p, Country c) {
		List<Country> cList = playerCountryMap.get(p);
		if (cList == null) {
			cList = new ArrayList<>();
			playerCountryMap.put(p, cList);
		}
		cList.add(c);
	}

	/**
	 * Pcm remove.
	 *
	 * @param p the p
	 * @param c the c
	 */
	private void pcmRemove(Player p, Country c) {
		List<Country> cList = playerCountryMap.get(p);
		if (cList != null) {
			cList.remove(c);
		}
	}

	/**
	 * Gets the game play state.
	 *
	 * @return the game play state
	 */
	public Object[][] getGamePlayState() {

		GamePlayService game = GamePlayService.getInstance();
		List<Country> gameCountries = new ArrayList<Country>();
		for (Entry<Player, List<Country>> key : game.playerCountryMap.entrySet()) {
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
	 * Gets the map.
	 *
	 * @return the map
	 */
	public GameMap getMap() {
		return this.gameMap;
	}

	/**
	 * Do startup phase.
	 *
	 * @param numberOfPlayers the number of players
	 * @param logger the logger
	 */
	public void doStartupPhase(int numberOfPlayers, ConsoleLoggerService logger) {

		doStartupPhase(numberOfPlayers);

		List<Player> players = getPlayers();
		for (Player p : players) {
			String s = p.name + " - [ ";

			List<Country> countries = getCountriesConqueredBy(p);
			for (Country c : countries)
				s += "" + c.getCountryName() + "(" + c.getNoOfArmy() + "), ";

			s += "]\n";

			logger.write(s);
		}

	}
}
