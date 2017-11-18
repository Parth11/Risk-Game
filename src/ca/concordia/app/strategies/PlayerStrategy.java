package ca.concordia.app.strategies;

import java.util.Map;

import ca.concordia.app.model.Player;

public interface PlayerStrategy {
	
	Map<String,Object> computeReinforcementMove(Player p); 
	
	Map<String,Object> computeAttackMove(Player p);
	
	Map<String,Object> computeFortifyMove(Player p);
	
}
