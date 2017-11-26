/**
 * 
 */
package ca.concordia.app.strategies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;

/**
 * A cheater computer player strategy whose reinforce() method doubles the number of armies on all its countries, 
 * whose attack() method automatically conquers all the neighbors of all its countries, and whose fortify() method doubles 
 * the number of armies on its countries that have neighbors that belong to other players.
 * 
 * @author harvi
 *
 */
public class CheaterStrategy implements PlayerStrategy {

	/* (non-Javadoc)
	 * @see ca.concordia.app.strategies.PlayerStrategy#computeReinforcementMove(ca.concordia.app.model.Player)
	 */
	@Override
	public Map<String, Object> computeReinforcementMove(Player p) {
		// TODO Auto-generated method stub
		
		Map<String, Object> strategyAs = new HashMap<>();
				
		strategyAs.put("country", GamePlayService.getInstance().getCountriesConqueredBy(p));
		
		// computing logic for doubling the armies
		for(Country c : GamePlayService.getInstance().getCountriesConqueredBy(p)) {
			int armies = c.getNoOfArmy();
			c.setNoOfArmy(2 * armies);
		}
		
		return strategyAs;

	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.strategies.PlayerStrategy#computeAttackMove(ca.concordia.app.model.Player)
	 */
	@Override
	public Map<String, Object> computeAttackMove(Player p) {
		// TODO Auto-generated method stub
		
		
		
		return null;
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
