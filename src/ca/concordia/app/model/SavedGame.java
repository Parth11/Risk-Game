/**
 * 
 */
package ca.concordia.app.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.util.GsonUtil;

/**
 * This class handles the saved game
 * @author harvi
 *
 */
public class SavedGame implements Serializable {

	private static final long serialVersionUID = -8451620048455126517L;

	private List<Country> countries;
	
	private List<Continent> continents;

	private HashMap<Country, ArrayList<String>> territories;
	
	private HashMap<String,Integer> deckMap;
	
	private int number_of_players;
	
	private int maxTurns=0;
	
	private List<Player> players;

	private Map<Player, List<Country>> player_country_map;

	private int turn = 0;
	
	private Player current_player;
	
	private List<GameLogEvent> game_log;
	
	/**
	 * This method will save the game
	 * @param saveFile save file
	 */
	public void saveThisGame(File saveFile){
		for(Player p :GamePlayService.getInstance().getPlayers()){
			p.deleteObservers();
		}
		GameMap.getInstance().copySavedData(this);
		GamePlayService.getInstance().copySaveData(this);
		Gson gson = GsonUtil.getGSONInstance();
		FileWriter fw = null;
		try {
			fw = new FileWriter(saveFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			gson.toJson(this, fw);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * get the countries
	 * @return countries
	 */
	public List<Country> getCountries() {
		return countries;
	}
	
	/**
	 * sets the countries
	 * @param countries list 
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	
	/**
	 * 	gets the continents
	 * @return continents
	 */
	public List<Continent> getContinents() {
		return continents;
	}
	
	/**
	 * set the continents 
	 * @param continents the continent
	 */
	public void setContinents(List<Continent> continents) {
		this.continents = continents;
	}
	
	/**
	 * hashmap to get territories
	 * @return territories the territory
	 */
	public HashMap<Country, ArrayList<String>> getTerritories() {
		return territories;
	}
	
	/**
	 * sets the territories
	 * @param territories the territory
	 */
	public void setTerritories(HashMap<Country, ArrayList<String>> territories) {
		this.territories = territories;
	}

	/**
	 * gets the deck map 
	 * @return deckMap 
	 */
	public HashMap<String, Integer> getDeckMap() {
		return deckMap;
	}
	
	/**
	 * set the deckMap
	 * @param deckMap the deck 
	 */
	public void setDeckMap(HashMap<String, Integer> deckMap) {
		this.deckMap = deckMap;
	}
	
	/**
	 * get the number of players
	 * @return number_of_players
	 */
	public int getNumber_of_players() {
		return number_of_players;
	}
	
	/**
	 * set number of players 
	 * @param number_of_players the players
	 */
	public void setNumber_of_players(int number_of_players) {
		this.number_of_players = number_of_players;
	}
	
	/**
	 * get max turns
	 * @return maxTurns
	 */
	public int getMaxTurns() {
		return maxTurns;
	}
	
	/**
	 * set max turns
	 * @param maxTurns the maximum turn
	 */
	public void setMaxTurns(int maxTurns) {
		this.maxTurns = maxTurns;
	}
	
	/**
	 * get the players
	 * @return players
	 */
	public List<Player> getPlayers() {
		return players;
	}
	
	/**
	 * set the players
	 * @param players the list of players
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	/**
	 * gets player country map
	 * @return player_country_map
	 */
	public Map<Player, List<Country>> getPlayer_country_map() {
		return player_country_map;
	}
	
	/**
	 * sets the player country map
	 * @param player_country_map player map
	 */
	public void setPlayer_country_map(Map<Player, List<Country>> player_country_map) {
		this.player_country_map = player_country_map;
	}
	
	/**
	 * get the turn
	 * @return turn
	 */
	public int getTurn() {
		return turn;
	}
	
	/**
	 * set the turn
	 * @param turn the number of turns
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	/**
	 * gets current player
	 * @return the current player.
	 */
	public Player getCurrent_player() {
		return current_player;
	}
	
	/**
	 * Set the current player
	 * @param current_player variable of type player
	 */
	public void setCurrent_player(Player current_player) {
		this.current_player = current_player;
	}
	
	/**
	 * get the game log
	 * @return game_log the game log
	 */
	public List<GameLogEvent> getGame_log() {
		return game_log;
	}
	
	/**
	 * sets the game log
	 * @param game_log the game log
	 */
	public void setGame_log(List<GameLogEvent> game_log) {
		this.game_log = game_log;
	}
	
}
