package ca.concordia.app.strategies;

import java.util.Map;

import ca.concordia.app.model.Player;
/**
 * The interface of the Strategy pattern.
 * @author AbhinavSingh
 *
 */
public interface PlayerStrategy {
	
	/**
	 * reinforcement implementation
	 * @param p the player
	 * @return the list
	 */
	Map<String,Object> computeReinforcementMove(Player p); 
	
	/**
	 * attack implementation
	 * @param p the player
	 * @return the list
	 */
	Map<String,Object> computeAttackMove(Player p);
	
	/**
	 * fortify implementation
	 * @param p the player
	 * @return the list
	 */
	Map<String,Object> computeFortifyMove(Player p);
	
	/**
	 * Return the strategy
	 * @return the strategy name
	 */
	String getName();
}
