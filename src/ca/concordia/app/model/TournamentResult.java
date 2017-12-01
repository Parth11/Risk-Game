package ca.concordia.app.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TournamentResult {

	private static TournamentResult instance = null;
	
	public Map<Integer,List<String>> results;
	
	private TournamentResult(){
		results = new HashMap<>();
	}
	
	public static TournamentResult getInstance(){
		if(instance == null){
			instance = new TournamentResult();
		}
		return instance;
	}
	
}
