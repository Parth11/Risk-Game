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
 * @author harvi
 *
 */
public class SavedGame implements Serializable {

	/**
	 * 
	 */
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
	
	public void saveThisGame(){
		for(Player p :GamePlayService.getInstance().getPlayers()){
			p.deleteObservers();
		}
		GameMap.getInstance().copySavedData(this);
		GamePlayService.getInstance().copySaveData(this);
		Gson gson = GsonUtil.getGSONInstance();
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File("D:\\saves\\"+System.currentTimeMillis()+".json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		gson.toJson(this, fw);
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public List<Continent> getContinents() {
		return continents;
	}

	public void setContinents(List<Continent> continents) {
		this.continents = continents;
	}

	public HashMap<Country, ArrayList<String>> getTerritories() {
		return territories;
	}

	public void setTerritories(HashMap<Country, ArrayList<String>> territories) {
		this.territories = territories;
	}

	public HashMap<String, Integer> getDeckMap() {
		return deckMap;
	}

	public void setDeckMap(HashMap<String, Integer> deckMap) {
		this.deckMap = deckMap;
	}

	public int getNumber_of_players() {
		return number_of_players;
	}

	public void setNumber_of_players(int number_of_players) {
		this.number_of_players = number_of_players;
	}

	public int getMaxTurns() {
		return maxTurns;
	}

	public void setMaxTurns(int maxTurns) {
		this.maxTurns = maxTurns;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Map<Player, List<Country>> getPlayer_country_map() {
		return player_country_map;
	}

	public void setPlayer_country_map(Map<Player, List<Country>> player_country_map) {
		this.player_country_map = player_country_map;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public Player getCurrent_player() {
		return current_player;
	}

	public void setCurrent_player(Player current_player) {
		this.current_player = current_player;
	}
	
}
