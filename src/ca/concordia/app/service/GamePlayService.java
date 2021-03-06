package ca.concordia.app.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ca.concordia.app.controller.PhaseViewController;
import ca.concordia.app.model.Card;
import ca.concordia.app.model.Continent;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.DiceRoller;
import ca.concordia.app.model.GameLogEvent;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.GamePlayEvent;
import ca.concordia.app.model.GamePlayEvent.EventType;
import ca.concordia.app.model.GamePlayEvent.GameMode;
import ca.concordia.app.model.Player;
import ca.concordia.app.model.SavedGame;
import ca.concordia.app.model.TournamentConfiguration;
import ca.concordia.app.model.TournamentResult;
import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.util.GameConstants;
import ca.concordia.app.util.GamePhase;
import ca.concordia.app.util.MapValidationException;


/**
 * This is GamePlayService class through which game is going to play.
 * It also contains startup, reinforcement and fortification phases.
 * @author Parth Nayak
 */
public class GamePlayService {

	private GameMode game_mode=GameMode.SINGLE_GAME;
	
	private static GamePlayService instance = null;
	
	private HashMap<String,Integer> deckMap;
	
	private int number_of_players;

	private GameMap game_map;

	private List<Player> players;

	private Map<Player, List<Country>> player_country_map;

	private int turn = 0;
	
	/**
	 * this method will get turn 
	 * @return turn
	 */
	public int getTurn() {
		return turn;
	}
	

	private ConsoleLoggerService logger;
	
	public List<GameLogEvent> game_log;
	
	/**
	 * Constructor of the GamePlaySrvice
	 */
	private GamePlayService() {
		game_map = GameMap.getInstance();
		players = new ArrayList<>();
		player_country_map = new HashMap<>();
		game_log = new ArrayList<GameLogEvent>();
		logger = ConsoleLoggerService.getInstance(null);
		generateDeck();
		
	}
	
	/**
	 * get the game mode 
	 * @return game_mode
	 */
	public GameMode getGameMode() {
		return game_mode;
	}

	
	/**
	 * sets the game mode
	 * @param game_mode game mode
	 */
	public void setGameMode(GameMode game_mode) {
		this.game_mode = game_mode;
	}



	/**
	 * Auto Generates Deck of Cards 
	 */
	public void generateDeck() {
		int totalCards = game_map.getCountries().size();
		int cardsDividedByType = totalCards / 3;
		deckMap= new HashMap<>();
		
		int difference = totalCards - (cardsDividedByType * 3);
		
		deckMap.put(GameConstants.INFANTRY, cardsDividedByType);
		deckMap.put(GameConstants.CAVALRY, cardsDividedByType);
		deckMap.put(GameConstants.ARTILLERY, cardsDividedByType);
		
		// setting up the cards for the scenario like 
		if((cardsDividedByType * 3) < totalCards)
		{
			String [] cardType = {GameConstants.ARTILLERY, GameConstants.CAVALRY,GameConstants.INFANTRY};
			while(difference>0) {
				Random randomeCardType = new Random();
				int result=randomeCardType.nextInt(2);
				String cardName =cardType[result];
				int numOfcards=deckMap.get(cardName);
				deckMap.put(cardName, numOfcards + 1);
				difference--;
			}
		}
	}
	
	/**
	* returns the deck of cards
	* @return deckMap
	*/
	public HashMap<String, Integer> getDeckMap() {
		return deckMap;
	}

	/**
	 * sets the Deck map
	* @param deckMap sets the deck of cards
	*/
	public void setDeckMap(HashMap<String, Integer> deckMap) {
		this.deckMap = deckMap;
	}

	/**
	 * Removes the cards for the master deck
	 * @param cardType string card
	 */
	public void removeCardsfromDeck(String cardType )
	{
			int value = deckMap.get(cardType);
			deckMap.put(cardType,value-1);								
	}
	
	/**
	 * Adds the cards to the master deck
	 * @param cardType string card type
	 */
	public void addCardsToDeck(String cardType )
	{
			int value = deckMap.get(cardType);
			deckMap.put(cardType,value+1);								
	}
	
	/**
	 * Criteria for card reimbursement
	 * @param player the player	
	 * @param a integer a
	 * @param i integer i
	 * @param c integer c
	 * @return true or false
	 */
	public boolean cardReimbursement(Player player, int a, int i, int c) {
		boolean flag = false;
		
		if(a==3 && i==0 && c==0){
			flag = true;
		}
		else if(i==3 && a==0 && c==0){
			flag = true;
		}
		else if(c==3 && a==0 && i==0){
			flag = true;
		}
		else if(a==1 && i==1 && c==1){
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * Randomly generates cards Strings
	 * @return cardName
	 */
	public String generateCard() {
		
		String [] cardType = {GameConstants.ARTILLERY, GameConstants.CAVALRY,GameConstants.INFANTRY};
		
		Random randomeCardType = new Random();
		int result = randomeCardType.nextInt(2);
		String cardName = cardType[result];
		return cardName;
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
	 * Resets players data.
	 */
	public void resetPlayersData() {
		turn = 0;
		for (Player p : players) {
			p.setCurrentPhase(GamePhase.STARTUP);
			p.setTotalArmies(getInitialArmy());
			HashMap<String, Object> payload = new HashMap<>();
			payload.put("initialArmies", p.getTotalArmies());
			GamePlayEvent gpe = new GamePlayEvent(EventType.START_ARMY_ALLOCATION, payload);
			p.publishGamePlayEvent(gpe);
		//	logger.write(p.getName()+" -> Receives -> "+p.getTotalArmies()+" armies\n");
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
	 * allocating countries to players and it will add armies using round-robin fashion.
	 * 
	 * @param numberOfPlayers the number of players
	 * @param strategies strategies of game 
	 */
	public void doStartupPhase(int numberOfPlayers, List<? extends PlayerStrategy> strategies) {
		
		logger = ConsoleLoggerService.getInstance(null);
		
		this.number_of_players = numberOfPlayers;

		logger.write("********** START UP PHASE BEGIN **********\n");
		logger.write("Game starts with "+numberOfPlayers+" players\n");
		
		for (int i = 1; i <= numberOfPlayers; i++) {
			players.add(new Player("Player " + i,strategies.get(i-1)));
		}

		resetPlayersData();

		allocateCountriesToPlayers();

		addInitialArmiesUsingRoundRobin();

		logger.write("********** START UP PHASE END **********\n");
		
	}

	/**
	 * Adding the initial Armies in a round robin fashion.
	 */
	private void addInitialArmiesUsingRoundRobin() {
		int j = 0;
		int playersLeftForAssign = number_of_players;
		while (playersLeftForAssign > 0) {
			if (players.get(j % number_of_players).getTotalArmies() > 0) {
				Player p = players.get(j%number_of_players);
				List<Country> playerCountryList = getCountriesConqueredBy(p);
				Country randomCountry = playerCountryList.get(new Random().nextInt(playerCountryList.size()));
				randomCountry.addArmies(1);
				p.setTotalArmies(p.getTotalArmies() - 1);
				HashMap<String, Object> eventPayload = new HashMap<>();
				eventPayload.put("countryName", randomCountry.getCountryName());
				GamePlayEvent gpe = new GamePlayEvent(EventType.START_ARMY_COUNTRY, eventPayload );
				p.publishGamePlayEvent(gpe);
			} else {
				playersLeftForAssign--;
			}
			j++;
		}
	}
	
	/**
	 * Allocating countries to the players
	 */
	public void allocateCountriesToPlayers() {
		int j = 0;
		for (Country c : game_map.getCountries()) {
			Player p = players.get(j % number_of_players);
			setNewCountryRuler(p, c, 1);
			p.subArmy(1);
			HashMap<String, Object> eventPayload = new HashMap<>();
			eventPayload.put("countryName", c.getCountryName());
			GamePlayEvent gpe = new GamePlayEvent(EventType.START_COUNTRY, eventPayload );
			p.publishGamePlayEvent(gpe);
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
		
		if(p.reinforce_army_for_card>0) {
			countArmy += p.reinforce_army_for_card;
			p.reinforce_army_for_card = 0;
		}
		

		List<Continent> ruledContinents = getContinentsCounqueredBy(p);
		for (Continent c : ruledContinents){
			ConsoleLoggerService.getInstance(null).write(p.getName()+" -> is the ruler of the continent ->"+c.getContinentName()+
					" -> and gets additional armies -> "+c.getControlValue()+"\n");
			countArmy += c.getControlValue();
		}
		return countArmy;

	}

	/**
	 * To Show the card exchange view
	 * @param p the player
	 * @return true or false
	 */
	public boolean showCardExchangeView(Player p) {
		int infantary=0;
		int cavallry =0;
		int artillry=0;
		
		if(p.getCards()!=null) 
		{
			for(Card card : p.getCards()) 
			{
				String cardType=card.getCardType();
				if(cardType.equalsIgnoreCase(GameConstants.INFANTRY)) {
					infantary++;
				}
				if(cardType.equalsIgnoreCase(GameConstants.CAVALRY)) {
					cavallry++;
				}
				if(cardType.equalsIgnoreCase(GameConstants.ARTILLERY)) {
					artillry++;
				}
			
				if((infantary==1 && cavallry==1 && artillry==1) || infantary==3 || cavallry==3 || artillry==3) 
				{
					return true;
				}
			}
		
		}
		return false;
	}
	
	/**
	 * Check if the player has 5 cards
	 * @return True or False
	 */
	public boolean checkPlayerCardsIsGreater(){
		
	    if(GamePlayService.getInstance().getCurrentTurnPlayer().getCards().size() == 5){
			return true;
		}
		return false;
		
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
//			if (c.getNoOfArmy() == 0)
//				c.setRuler(null, 0);
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
		return game_map.getNeighbourCountries(fromCountry).contains(toCountry) //should be neighbours
				&& fromCountry.getRuler() != toCountry.getRuler() // shouldn't both countries belong to same player
				&& fromCountry.getNoOfArmy() > 1 // attacker should have more than 1 army
				&& toCountry.getNoOfArmy() > 0; // defence should have atleast 1 army to protect the country
	}

	/**
	 * Getting the attack dice limit.
	 *
	 * @param c the country
	 * @return number of army 
	 */
	public int getAttackDiceLimit(Country c) {
		if (player_country_map.get(c.getRuler()).contains(c) && c.getNoOfArmy() > 1) {
			return c.getNoOfArmy() > 3 ? 3 : c.getNoOfArmy() - 1;
		}

		return -1;
	}

	/**
	 * Getting the defence dice limit.
	 *
	 * @param c the country
	 * @return the defence dice limit
	 */
	public int getDefenceDiceLimit(Country c) {
		if (player_country_map.get(c.getRuler()).contains(c)) {
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
		int n = getAttackDiceLimit(c);
		if (n == -1)
			return null;
		else
			return new DiceRoller( n);
	}

	/**
	 * Getting the defence dice roller.
	 *
	 * @param p the p
	 * @param c the c
	 * @return the defence dice roller
	 */
	public DiceRoller getDefenceDiceRoller(Player p, Country c) {
		int n = getDefenceDiceLimit(c);
		if (n == -1)
			return null;
		else
			return new DiceRoller( n);
	}

	/**
	 * put a player on a country
	 *
	 * @param p the player
	 * @param c the country
	 */
	public void mapPlayerToCountry(Player p, Country c) {
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
	public void unmapPlayerToCountry(Player p, Country c) {
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
	 * @param player the Player
	 * @return s string country allocation
	 */
	public String printCountryAllocationToConsole(Player player) {

		String s = "\n";
		List<Country> countries = getCountriesConqueredBy(player);
		
		for (Country c : countries)
			s += "" + c.getCountryName() + "(" + c.getNoOfArmy() + "), ";
		s += " \n";
		return s;
	}
	
	/**
	 * Check if the attacking country is a neighbor and of the current player
	 * @param p the player
	 * @return The Attacking List
	 */
	public List<Country> getEligibleAttackingCountriesForPlayer(Player p){
		Set<Country> attackingCountries = new HashSet<>();
		List<Country> countries = getCountriesConqueredBy(p);
		for(Country c : countries){
			if(c.getNoOfArmy()>1){
				List<Country> neighbourCountries = game_map.getNeighbourCountries(c);
				for(Country n : neighbourCountries){
					if(!n.getRuler().equals(p)){
						attackingCountries.add(c);
					}
				}
			}
		}
		return new ArrayList<>(attackingCountries);
	}
	
	/**
	 * Check if the attacked country is a neighbor and of differnt ruler
	 * @param c country
	 * @return List of Attackable countries
	 */
	public List<Country> getEligibleAttackableCountries(Country c){
		List<Country> neighboursOfAttackerCountry = game_map.getNeighbourCountries(c);
		
		List<Country> defenderCountries = new ArrayList<>();
		for(Country neighbour:neighboursOfAttackerCountry) 
		{
			if(!neighbour.getRuler().equals(c.getRuler())) 
			{
				defenderCountries.add(neighbour);
			}
		}
		return defenderCountries;
	}
	
	/**
	 * Check if the game has ended or not
	 * @return true or false
	 */
	public boolean isThisTheEnd(){
		if(getPlayers().size()==1 ){
			return true;
		}
		return false;
	}

	public void knockPlayerOut(Player ruler) {
		this.getPlayers().remove(ruler);
		this.number_of_players--;
		this.turn--;
		
		HashMap<String, Object> eventPayload = new HashMap<>();
		eventPayload.put("deadPlayer", ruler.getName());
		GamePlayEvent gpe = new GamePlayEvent(EventType.PLAYER_DEAD, eventPayload );

		ruler.publishGamePlayEvent(gpe);
	}
	
	/**
	 *  Get the strongest country for the player
	 *  @param p player
	 *  @return strongestCountry return the strongest country
	 * 
	 * */
	public Country getStrongestCountry(Player p) {

		List<Country> playerCountries = GamePlayService.getInstance().getCountriesConqueredBy(p);

		Country strongestCountry = null;
		
		int maxArmy = 0;
		
		for (Country country : playerCountries) {
			
			int playerArmy = country.getNoOfArmy();
			
			if (playerArmy > maxArmy) {
				maxArmy = playerArmy;
				strongestCountry = country;
			}
		}

		return strongestCountry;
}
	
	/**
	 *  Get the weakest country for the player
	 *  @param p the Player
	 *  @return weakestCountry return the weakest country
	 * 
	 * */
	public Country getWeakestCountry(Player p) {

		List<Country> playerCountries = GamePlayService.getInstance().getCountriesConqueredBy(p);

		Country weakestCountry = null;
		
		int minArmy = Integer.MAX_VALUE;
		
		for (Country country : playerCountries) {
			
			int playerArmy = country.getNoOfArmy();
			
			if (playerArmy < minArmy) {
				minArmy = playerArmy;
				weakestCountry = country;
			}
		}

		return weakestCountry;
	}
		
	/**
	 * this method copy the save data
	 * @param savedGame Saved Game
	 */
	public void copySaveData(SavedGame savedGame){
		savedGame.setDeckMap(deckMap);
		savedGame.setNumber_of_players(number_of_players);
		savedGame.setPlayer_country_map(player_country_map);
		savedGame.setPlayers(players);
		savedGame.setTurn(turn);
		savedGame.setCurrent_player(getCurrentTurnPlayer());
		savedGame.setGame_log(game_log);
	}
	
	/**
	 * Restore the saved data
	 * @param savedGame Saved Game 
	 */
	public void restoreSavedData(SavedGame savedGame){
		deckMap = savedGame.getDeckMap();
		number_of_players = savedGame.getNumber_of_players();
		player_country_map = savedGame.getPlayer_country_map();
		players = savedGame.getPlayers();
		turn = savedGame.getTurn();
		game_map = GameMap.getInstance();
		game_log = savedGame.getGame_log();
		
		for(Player p : players){
			p.addObserver(PhaseViewController.getInstance());
			p.addObserver(ConsoleLoggerService.getInstance(null));
		}
		
		ConsoleLoggerService cls = ConsoleLoggerService.getInstance(null);
		for(GameLogEvent gle : game_log){
			cls.updateConsoleForEvent(gle.getPlayer(), gle.getGame_event());
		}
	}
	
	
	/**
	 * declares the draw
	 */
	public void declareDraw() {
		logger.write("Game reached max turns="+TournamentConfiguration.getInstance().getMax_turns()+" . Game is draw now.");
	}

	/**
	 * Declares the Win
	 */
	public void declareWin() {
		logger.write("Game won by "+getCurrentTurnPlayer().getName());
	}

	/**
	 * write the text
	 * @param text String
	 */
	public void write(String text) {
		logger.write(text);
	}
	
	/**
	 * This method will add tournament map
	 * @param mapFile Map File
	 * @throws MapValidationException validate map
	 */
	public void addTournamentMap(File mapFile) throws MapValidationException{
			MapService.getInstance().loadMap(mapFile);
			MapService.getInstance().resetMap();
			TournamentConfiguration.getInstance().getTournament_maps().add(mapFile);
	}
	
	/**
	 * This method will display the result
	 */
	public void displayResults() {

	}
	/**
	 * This method log the game event
	 * @param player Player
	 * @param event game play event
	 */
	public void logGameEvent(Player player, GamePlayEvent event){
		GameLogEvent gle = new GameLogEvent(player, event);
		game_log.add(gle);
	}
	
	public void resetGame(){
		instance = null;
	}
	
}