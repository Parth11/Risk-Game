package ca.concordia.app.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ca.concordia.app.strategies.PlayerStrategy;

public class TournamentConfiguration {
	
	private static TournamentConfiguration instance = null;
	
	private TournamentConfiguration(){

	}

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

	public int getNum_players() {
		return num_players;
	}

	public void setNum_players(int num_players) {
		this.num_players = num_players;
	}

	public List<? extends PlayerStrategy> getStrategies() {
		return strategies;
	}

	public void setStrategies(List<? extends PlayerStrategy> strategies) {
		this.strategies = strategies;
	}

	public int getMax_turns() {
		return max_turns;
	}

	public void setMax_turns(int max_turns) {
		this.max_turns = max_turns;
	}

	public int getNum_games() {
		return num_games;
	}

	public void setNum_games(int num_games) {
		this.num_games = num_games;
	}

	public List<File> getTournament_maps() {
		return tournament_maps;
	}

	public void setTournament_maps(List<File> tournament_maps) {
		this.tournament_maps = tournament_maps;
	}
	
	public void addMap(File mapFile){
		this.tournament_maps.add(mapFile);
	}
	
}
