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
		Map<String,Object> strategyRs = new HashMap<>();
		strategyRs.put("country", GamePlayService.getInstance().getCountriesConqueredBy(p).get(0));
		strategyRs.put("armies", p.getTotalArmies());
		return strategyRs;
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.strategies.PlayerStrategy#computeAttackMove(ca.concordia.app.model.Player)
	 */
	@Override
	public Map<String, Object> computeAttackMove(Player p) {
		Map<String,Object> strategyRs = new HashMap<>();
		strategyRs.put("attackCountry", GamePlayService.getInstance().getEligibleAttackingCountriesForPlayer(p).get(0));
		strategyRs.put("defenceCountry", GamePlayService.getInstance().getEligibleAttackableCountries(
				GamePlayService.getInstance().getEligibleAttackingCountriesForPlayer(p).get(0)).get(0));
		int[] arr = {5,4,3};
		strategyRs.put("attacks", Arrays.asList(arr));
		strategyRs.put("defences", Arrays.asList(arr));
		return strategyRs;
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.strategies.PlayerStrategy#computeFortifyMove(ca.concordia.app.model.Player)
	 */
	@Override
	public Map<String, Object> computeFortifyMove(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

}
