package ca.concordia.app.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ca.concordia.app.strategies.PlayerStrategy;

/**
 * This class configures the tournament mode
 * @author Harvi
 *
 */
public class TournamentConfiguration {
	
	private static TournamentConfiguration instance = null;
	
	private TournamentConfiguration(){

	}
	
	/**
	 * gets the instance
	 * @return instance
	 */
	public static TournamentConfiguration getInstance(){
		if(instance==null){
			instance = new TournamentConfiguration();
		}
		return instance;
	}
	
	private int num_players;
	
	private List<? extends PlayerStrategy> strategies = new ArrayList<>();
	
	private int max_turns;
	
	private int num_games;
	
	private List<File> tournament_maps = new ArrayList<>();
	
	/**
	 * get number of player
	 * @return num_player
	 */
	public int getNum_players() {
		return num_players;
	}
	
	/**
	 * sets the number of player
	 * @param num_players the number of players
	 */
	public void setNum_players(int num_players) {
		this.num_players = num_players;
	}
	
	/**
	 * gets the strategies
	 * @return strategies
	 */
	public List<? extends PlayerStrategy> getStrategies() {
		return strategies;
	}
	
	/**
	 * set the strategies
	 * @param strategies list of strategy
	 */
	public void setStrategies(List<? extends PlayerStrategy> strategies) {
		this.strategies = strategies;
	}
	
	/**
	 * get the max turn
	 * @return max_turns the maximum turn
	 */
	public int getMax_turns() {
		return max_turns;
	}
	
	/**
	 * set the max turn 
	 * @param max_turns  the maximum turn
	 */
	public void setMax_turns(int max_turns) {
		this.max_turns = max_turns;
	}
	
	/**
	 * get the number of games
	 * @return num_games 
	 */
	public int getNum_games() {
		return num_games;
	}
	
	/**
	 * set the number of games
	 * @param num_games the number of games
	 */
	public void setNum_games(int num_games) {
		this.num_games = num_games;
	}
	
	/**
	 * gets the  tournament map
	 * @return tournament_map
	 */
	public List<File> getTournament_maps() {
		return tournament_maps;
	}
	
	/**
	 * set the tournament map
	 * @param tournament_maps the list
	 */
	public void setTournament_maps(List<File> tournament_maps) {
		this.tournament_maps = tournament_maps;
	}
	
	/**
	 * add the map file
	 * @param mapFile the map file
	 */
	public void addMap(File mapFile){
		this.tournament_maps.add(mapFile);
	}
	
}
