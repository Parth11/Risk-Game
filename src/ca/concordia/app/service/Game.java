package ca.concordia.app.service;

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



/**
 * @author Parth Nayak
 * 
 * 
 */

public class Game {
	
	private static Game instance = null;
	
	private String mapPath=null;
	
	private int numberOfPlayers;
	
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
		if(instance==null)
			instance = new Game();
		return instance;
	}
	
	public void loadNewMap(String path) {
		instance.mapPath = path;
		instance.resetPlayersData();
		CreateMapService.getInstance().createMap(instance.mapPath);
	}
	
	public void resetGame() {
		numberOfPlayers = 0;
		turn = 0;
		players.clear();
		playerCountryMap.clear();
		mapPath = null;
		System.gc();
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
		addInitialArmiesUsingRR();
		
		return true;
	}
	
	private void addInitialArmiesUsingRR() {
		int j = 0;
		int playersLeftForAssign = numberOfPlayers;
		System.out.println("Hello");
		while (playersLeftForAssign > 0){
			if(players.get(j % numberOfPlayers).getTotalArmies() > 0)
			{
				List<Country> playerCountryList = getCountriesConqueredBy(players.get(j % numberOfPlayers));
				Country randomCountry = playerCountryList.get(new Random().nextInt(playerCountryList.size()));
				randomCountry.addArmies(1);
				players.get(j % numberOfPlayers).setTotalArmies(players.get(j % numberOfPlayers).getTotalArmies() - 1);			
			}
			else {
				playersLeftForAssign--;
			}
			j++;			
		}		
	}
	
	private void allocateCountriesToPlayers() {
		// allocate countries to the players in round-robin fashion
		int j = 0;
		for(Country c: gameMap.getCountries()) {
			Player p = players.get(j % numberOfPlayers);
			setNewCountryRuler(p,c,1);
			p.subArmy(1);
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
		int countArmy = 0;
		int countriesCounquered = getCountriesConqueredBy(p).size();
		if(countriesCounquered<=11 && countriesCounquered>0){
			countArmy = 3;
		}
		
		List<Continent> ruledContinents = getContinentsCounqueredBy(p);
		for(Continent c : ruledContinents)
			countArmy += c.getControlValue();
		
		return countArmy;
		
	}
	
	public List<Country> getCountriesConqueredBy(Player p) {
		return playerCountryMap.get(p);
	}
	
	public List<Continent> getContinentsCounqueredBy(Player p) {
		List<Continent> lst = new ArrayList<>();				
		for(Continent c : gameMap.getContinents()) {
			boolean isRuler = true;
			for(Country country :gameMap.getCountriesByContinent(c.getContinentName())) {
				if(!country.getRuler().equals(p)) {
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
		if(country.getNoOfArmy()!=0) 
			return false;
		country.setRuler(ruler, numberOfArmies);
		pcmPut(ruler, country);
		return true;
	}
	
	public void captureCountry(Player ruler, Country country, Country fromCountry, int numberOfArmies) {
		if(!setNewCountryRuler(ruler, fromCountry, numberOfArmies))
		{
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

	public boolean addArmies(Player p, Country c, int addAmount) {
		if(c.getNoOfArmy()==0 || playerCountryMap.get(p).contains(c)) {
			p.addArmy(addAmount);
			c.addArmies(addAmount);
			return true;
		}
		return false;
	}
	
	public boolean subArmies(Player p, Country c, int subAmount) {
		if((c.getNoOfArmy()==0 || playerCountryMap.get(p).contains(c)) && ((c.getNoOfArmy()-subAmount)>=0)) {
			p.subArmy(subAmount);
			c.removeArmies(subAmount);
			if(c.getNoOfArmy()==0)
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
	}
		catch(Exception e) {
			return false;
		}
	}
	
	public boolean isNeighbour(Country c1, Country c2) {
		return (gameMap.getNeighbourCountries(c1).contains(c2));
	}
	
	public boolean isConnected(Country c1, Country c2, Player p) {
		return isConnected(c1, c2, p, null);
	}
	
	private boolean isConnected(Country c1, Country c2, Player p, List<Country> unwanatedPair) {
		if(isNeighbour(c1, c2) && c1.getRuler().equals(c2.getRuler()))
			return true;
		
		if(unwanatedPair==null)
			unwanatedPair = new ArrayList<>();
		else if(unwanatedPair.contains(c1))
			return false;
		unwanatedPair.add(c1);
		
		for(Country c : gameMap.getNeighbourCountries(c1)) {
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
		return gameMap.getNeighbourCountries(fromCountry).contains(toCountry)		// should be neighbours
				&& fromCountry.getRuler()!=toCountry.getRuler() // shouldn't both countries belong to same player
				&& fromCountry.getNoOfArmy()>1		// attacker should have more than 1 army
				&& toCountry.getNoOfArmy()>0;		// defence should have atleast 1 army to protect the country
	}
	
	
	
	// Dies APIs

	public int getAttackDiceLimit(Player p, Country c) {
		if(playerCountryMap.get(p).contains(c) && c.getNoOfArmy()>1)
		{
			// max 3 dies, min (c.getNoOfArmy()-1) dies
			return c.getNoOfArmy()>3?3:c.getNoOfArmy()-1;
		}
		
		return -1;
	}
	
	public int getDefenceDiceLimit(Player p, Country c) {
		if(playerCountryMap.get(p).contains(c))
		{
			// max 2 dies, min 1 dies
			return c.getNoOfArmy()==1?1:2;
		}
		return -1;
	}
	
	public DiceRoller getAttackDiceRoller(Player p, Country c) {
		int n = getAttackDiceLimit(p, c);
		if(n==-1)
			return null;
		else
			return new DiceRoller(instance, n);
	}
	
	public DiceRoller getDefenceDiceRoller(Player p, Country c) {
		int n = getDefenceDiceLimit(p, c);
		if(n==-1)
			return null;
		else
			return new DiceRoller(instance, n);
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
	
	
	public Object[][] getGamePlayState(){
		
		Game game = Game.getInstance();
		List<Country> gameCountries = new ArrayList<Country>();
		for(Entry<Player,List<Country>> key : game.playerCountryMap.entrySet()){
			gameCountries.addAll(key.getValue());
		}
		Object[][] gamePlayState = new Object[gameCountries.size()][3];
		
		for(int i=0; i<gameCountries.size();i++){
			gamePlayState[i][0] = gameCountries.get(i).getCountryName();
			gamePlayState[i][1] = gameCountries.get(i).getNoOfArmy();
			gamePlayState[i][2] = gameCountries.get(i).getRuler().getName();
		}
		
		return gamePlayState;
	}
	
	public GameMap getMap(){
		return this.gameMap;
	}
}
