package lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import lib.callbacks.OnWarCallBacks;
import lib.model.Continent;
import lib.model.Country;
import lib.model.DiceRoller;
import lib.model.GameMap;
import lib.model.Player;
import lib.service.CreateMapService;


/**
 * @author Parth Nayak
 * 
 * 
 */

public class Game {
	private static Game game = null;
	private String mapPath=null;
	
	private int numberOfPlayers=0;
	
	private GameMap gameMap;
	
	private List<Player> players;
	
	private Map<Player, List<Country>> playerCountryMap;
	
	private int turn = 0;
	
	private Game() {
		gameMap = GameMap.getInstance();
		players = new ArrayList<>();
		playerCountryMap = new HashMap<>();
	}
	
	// map APIs
	
	public static Game getInstance() {
		if(game==null)
			game = new Game();
		return game;
	}
	
	public void loadNewMap(String path) {
		game.mapPath = path;
		game.resetPlayersData();
		CreateMapService.getInstance().loadMap(game.gameMap, game.mapPath);
	}
	
	public void resetGame() {
		numberOfPlayers = 0;
		turn = 0;
		players.clear();
		playerCountryMap.clear();
		mapPath = null;
		System.gc();
	}
	
	public List<Continent> getContinents() {
		return gameMap.getContinents();
	}
	
	public List<Country> getCountries() {
		return gameMap.getCountries();
	}
	
	public List<Country> getCountriesInContinent(Continent continent) {
		return gameMap.getCountriesInContinent(continent);
	}
	
	public Continent getContinent(String continent) {
		return gameMap.getContinent(continent);
	}

	public Country getCountry(String country) {
		return gameMap.getCountry(country);
	}
	
	public List<Country> getNeighbours(Country country) {
		return gameMap.getNeighbours(country);
	}
	
	// player APIs
	
	private void resetPlayersData() {
		turn = 0;
		for(Player p : players) {
			//p.addCard(null);
			p.setTotalArmies(getInitialArmy());
		}
	}
	
	public int getInitialArmy() {
		switch (numberOfPlayers) {
		    case 2: return 40;
		    case 3: return 35;
		    case 4: return 30;
		    case 5: return 25;
		    case 6: return 20;
		    default: return 10;
	    }
	}
	
	public boolean setPlayers(int numberOfPlayers) {
		if(gameMap.getCountries().isEmpty())
			return false;
		
		this.numberOfPlayers = numberOfPlayers;
		
		// generating players
		for (int i=0; i < numberOfPlayers; i++) {
			players.add(new Player("Player" + i));    
		}
		
		// init players data
		resetPlayersData();
		
		// allocate countries to players
		allocateCountriesToPlayers();
		
		// add initial army using round-robin fashion
		addInitialArmies();
		
		return true;
	}
	
	private void allocateCountriesToPlayers() {
		// allocate countries to the players in round-robin fashion
		int j = 0;
		for(Country c: gameMap.getCountries()) {
			Player p = players.get(j % numberOfPlayers);
			setNewCountryRuler(p,c,1);
			j++;
		}
	}
	
	private void addInitialArmies() {
		for(Player p : getPlayers()) {
			List<Country> cList = getCountriesConqueredBy(p);
			for(int i=0; i<getInitialArmy(); i++) {
				int index = i % cList.size();
				Country putArmyAt = cList.get(index);
				addArmies(p, putArmyAt, 1);
			}
		}
	}
	
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	// Game Edit APIs
	
	// ...
	// under construction
	// ...
	
	// Game APIs
	
	public Player getCurrentTurnPlayer() {
		return players.get(turn);
	}
	
	public Player changeTurnToNextPlayer() {
		turn = (turn+1) % numberOfPlayers;
		return getCurrentTurnPlayer();
	}
	
	
	//Change the whole mathematical calculation and add Cards Logics
	public int getReinforcementArmyForPlayer(Player p) {
		int playerCountries = getCountriesConqueredBy(p).size();
		int countArmy = 0;
		if(playerCountries <= 1 && playerCountries <= 11) {
			countArmy = 3;
		}
		
		List<Continent> ruledContinents = getContinentsCounqueredBy(p);
		for(Continent c : ruledContinents)
			countArmy += c.getControlValue();
		
		if(countArmy > 3)
			return countArmy;
		else
			return 3;
		
	}
	
	public List<Country> getCountriesConqueredBy(Player p) {
		return playerCountryMap.get(p);
	}
	
	public List<Continent> getContinentsCounqueredBy(Player p) {
		List<Continent> lst = new ArrayList<>();				
		for(Continent c : getContinents()) {
			boolean isRuler = true;
			for(Country country : c.getCountriesList()) {
				if(!country.getRulerPlayer().equals(p)) {
					isRuler = false;
					break;
				}
					
			}
			if(isRuler)
				lst.add(c);
		}
		
		return lst;
	}
	
	public boolean setNewCountryRuler(Player ruler, Country country, int numberOfArmies) {
		if(country.getNoOfArmies()!=0) 
			return false;
		country.setPlayer(ruler, numberOfArmies);
		pcmPut(ruler, country);
		return true;
	}
	
	public void captureCountry(Player ruler, Country country, Country fromCountry, int numberOfArmies) {
		if(!setNewCountryRuler(ruler, fromCountry, numberOfArmies))
		{
			// remove defeated ruler from the country
			Player defeatedRuler = country.getRulerPlayer();
			pcmRemove(defeatedRuler, country);
			defeatedRuler.subArmy(country.getNoOfArmies());
			country.setPlayer(null, 0);
			
			// add new ruler
			pcmPut(ruler, country);
			country.setPlayer(ruler, numberOfArmies);
			fromCountry.subtractArmy(numberOfArmies);
		}
	}

	public boolean addArmies(Player p, Country c, int addAmount) {
		if(c.getNoOfArmies()==0 || playerCountryMap.get(p).contains(c)) {
			p.addArmy(addAmount);
			c.addArmy(addAmount);
			return true;
		}
		return false;
	}
	
	public boolean subArmies(Player p, Country c, int subAmount) {
		if((c.getNoOfArmies()==0 || playerCountryMap.get(p).contains(c)) && ((c.getNoOfArmies()-subAmount)>=0)) {
			p.subArmy(subAmount);
			c.subtractArmy(subAmount);
			if(c.getNoOfArmies()==0)
				c.setPlayer(null, 0);
			return true;
		}
		return false;
	}
	
	
	public boolean moveArmyFromTo(Player p, Country fromCountry, Country toCountry, int noOfArmy) {
		if(playerCountryMap.get(p).contains(fromCountry) && (playerCountryMap.get(p).contains(toCountry)) && (fromCountry.getNoOfArmies()-noOfArmy)>=1 && isConnected(fromCountry, toCountry, p)) {
			fromCountry.subtractArmy(noOfArmy);
			toCountry.addArmy(noOfArmy);
			return true;
		}
		return false;
	}
	
	public boolean isNeighbour(Country c1, Country c2) {
		return (getNeighbours(c1).contains(c2));
	}
	
	public boolean isConnected(Country c1, Country c2, Player p) {
		return isConnected(c1, c2, p, null);
	}
	
	private boolean isConnected(Country c1, Country c2, Player p, List<Country> unwanatedPair) {
		if(isNeighbour(c1, c2) && c1.getRulerPlayer().equals(c2.getRulerPlayer()))
			return true;
		
		if(unwanatedPair==null)
			unwanatedPair = new ArrayList<>();
		else if(unwanatedPair.contains(c1))
			return false;
		unwanatedPair.add(c1);
		
		for(Country c : getNeighbours(c1)) {
			if(!unwanatedPair.contains(c) && isConnected(c, c2, p, unwanatedPair))
				return true;
		}
		
		return false;
	}
	
	/*
	 * @return warResult
	 * if warResult is a positive integer, means attacker wins
	 * if it is negative, means defender wins
	 * 0 means draw
	 * 
	 * */
	public int whoWins(int[] attackResult, int[] defenceResult) {
		Arrays.sort(attackResult);
		Arrays.sort(defenceResult);
		int n = attackResult.length>defenceResult.length?defenceResult.length:attackResult.length;
		
		int warResult = 0;
		for(int i=0; i<n; i++) {
			if(attackResult[i]>defenceResult[i])
				warResult++;
			else
				warResult--;
		}
		
		return warResult;
	}
	
	public boolean canWar(Country fromCountry, Country toCountry) {
		return gameMap.getNeighbours(fromCountry).contains(toCountry)		// should be neighbours
				&& fromCountry.getRulerPlayer()!=toCountry.getRulerPlayer() // shouldn't both countries belong to same player
				&& fromCountry.getNoOfArmies()>1		// attacker should have more than 1 army
				&& toCountry.getNoOfArmies()>0;		// defence should have atleast 1 army to protect the country
	}
	
	/*public void war(Player attackPlayer, Country fromCountry, Player defencePlayer, Country toCountry, int[] attackResult, int[] defenceResult) {
		// can war
		if(!canWar(fromCountry, toCountry))
			return;
		
		// if yes, then start war
		Arrays.sort(attackResult);
		Arrays.sort(defenceResult);
		int n = attackResult.length>defenceResult.length?defenceResult.length:attackResult.length;
		
		for(int i=0; i<n; i++) {
			if(attackResult[i]>defenceResult[i]) {
				toCountry.subtractArmy(1);
				defencePlayer.subArmy(1);
			} else {
				fromCountry.subtractArmy(1);
				attackPlayer.subArmy(1);
			}
		}
		
		// if defence country is completely defeated
		if(toCountry.getNoOfArmies()<=0) {
			toCountry.setPlayer(null, 0);
			pcmRemove(defencePlayer, toCountry);
			
			// now attack player must move army
			// ...
		}
	}*/
	
	public void war(OnWarCallBacks onWarCallBacks) {
		
		// get Data
		Player attackPlayer = onWarCallBacks.getAttackPlayer(), defencePlayer = onWarCallBacks.getDefencePlayer();
		Country fromCountry = onWarCallBacks.getFromCountry(), toCountry = onWarCallBacks.getToCountry();
		
		// can war
		if(!canWar(fromCountry, toCountry)) {
			onWarCallBacks.onWarStartFailure();
			return;
		}
		
		onWarCallBacks.beforeWarStart();
		
		DiceRoller attackDice = new DiceRoller(game, getAttackDiceLimit(attackPlayer, fromCountry));
		DiceRoller defenceDice = new DiceRoller(game, getDefenceDiceLimit(defencePlayer, toCountry));
		
		attackDice.rollAll();
		defenceDice.rollAll();
		
		int[] attackResult = attackDice.getResults();
		int[] defenceResult = defenceDice.getResults();
		
		// if yes, then start war
		Arrays.sort(attackResult);
		Arrays.sort(defenceResult);
		int n = attackResult.length>defenceResult.length?defenceResult.length:attackResult.length;
		
		int[] storeAttackResult = new int[n], storeDefenceResult = new int[n];
		//int whoWins = 0;
		for(int i=0; i<n; i++) {
			storeAttackResult[i] = attackResult[i];
			storeAttackResult[i] = defenceResult[i];
			if(attackResult[i]>defenceResult[i]) {
				toCountry.subtractArmy(1);
				defencePlayer.subArmy(1);
				onWarCallBacks.onAttackerWin((i+1), attackResult[i],defenceResult[i]);
				//whoWins++;
			} else {
				fromCountry.subtractArmy(1);
				attackPlayer.subArmy(1);
				onWarCallBacks.onDefenderWin((i+1), attackResult[i],defenceResult[i]);
				//whoWins--;
			}
		}
		onWarCallBacks.setAttackDiceResult(storeAttackResult);
		onWarCallBacks.setDefenceDiceResult(storeDefenceResult);
		
		onWarCallBacks.onWarFinished();
		
		// if defence country is completely defeated
		if(toCountry.getNoOfArmies()<=0) {
			toCountry.setPlayer(null, 0);
			pcmRemove(defencePlayer, toCountry);
			
			onWarCallBacks.onCountryCaptured();
			// now attack player must move army
			// ...
		}
		else {
			onWarCallBacks.setCanReWar(true);
			onWarCallBacks.askForReWar();
		}
	}

	// Dies APIs

	public int getAttackDiceLimit(Player p, Country c) {
		if(playerCountryMap.get(p).contains(c) && c.getNoOfArmies()>1)
		{
			// max 3 dies, min (c.getNoOfArmies()-1) dies
			return c.getNoOfArmies()>3?3:c.getNoOfArmies()-1;
		}
		
		return -1;
	}
	
	public int getDefenceDiceLimit(Player p, Country c) {
		if(playerCountryMap.get(p).contains(c))
		{
			// max 2 dies, min 1 dies
			return c.getNoOfArmies()==1?1:2;
		}
		return -1;
	}
	
	public DiceRoller getAttackDiceRoller(Player p, Country c) {
		int n = getAttackDiceLimit(p, c);
		if(n==-1)
			return null;
		else
			return new DiceRoller(game, n);
	}
	
	public DiceRoller getDefenceDiceRoller(Player p, Country c) {
		int n = getDefenceDiceLimit(p, c);
		if(n==-1)
			return null;
		else
			return new DiceRoller(game, n);
	}
	
	// PlayerCountryMap calls
	
	private void pcmPut(Player p, Country c) {
		List<Country> cList = playerCountryMap.get(p);
		if(cList==null) {
			cList = new ArrayList<>();
			playerCountryMap.put(p, cList);
		}
		cList.add(c);
	}
	
	private void pcmRemove(Player p, Country c) {
		List<Country> cList = playerCountryMap.get(p);
		if(cList!=null) {
			cList.remove(c);
		}
	}
	
	// CallBacks
	
	public interface ResultInterface {
		void onSuccess();
		void onFailure();
	}
	
	public static class Console {
		private static Console console=null;
		private static BufferedReader br;
		
		private Console() {
			 br = new BufferedReader(new InputStreamReader(System.in));
		}
		
		public static Console getInstance() {
			if(console==null) 
				console = new Console();
			return console;
		}
	}
}
