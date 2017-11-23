/**
 * 
 */
package ca.concordia.app.strategies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;

/**
 * @author harvi
 *
 */
public class AggressiveStrategy implements PlayerStrategy {

	/* (non-Javadoc)
	 * @see ca.concordia.app.strategies.PlayerStrategy#computeReinforcementMove(ca.concordia.app.model.Player)
	 */
	@Override
	public Map<String, Object> computeReinforcementMove(Player p) {
		return new HashMap<>();
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.strategies.PlayerStrategy#computeAttackMove(ca.concordia.app.model.Player)
	 */
	@Override
	public Map<String, Object> computeAttackMove(Player p) {
		return new HashMap<>();
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.strategies.PlayerStrategy#computeFortifyMove(ca.concordia.app.model.Player)
	 */
	@Override
	public Map<String, Object> computeFortifyMove(Player p) {
		return new HashMap<>();
	}

}
