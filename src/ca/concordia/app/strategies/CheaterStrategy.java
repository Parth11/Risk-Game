/**
 * 
 */
package ca.concordia.app.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
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
			p.doReinforcement(c, armies);
		}
		
		return strategyAs;

	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.strategies.PlayerStrategy#computeAttackMove(ca.concordia.app.model.Player)
	 */
	@Override
	public Map<String, Object> computeAttackMove(Player p) {
		// TODO Auto-generated method stub				
		
		List<Country> playerCountries = GamePlayService.getInstance().getCountriesConqueredBy(p);
		
		for(Country c1 : playerCountries) {
			List<Country> countries = GameMap.getInstance().getNeighbourCountries(c1);
			for(Country c2 : countries) {
				
				if(!c2.getRuler().getName().equalsIgnoreCase(c1.getRuler().getName())) {
				 
				// 1. Remove a country from defender's country list
				GamePlayService.getInstance().unmapPlayerToCountry(c2.getRuler(), c2);

				// 2. Add defending country in attacker country list
				GamePlayService.getInstance().mapPlayerToCountry(c1.getRuler(), c2);

				// 3. Check defender is eleminated from the game or not
				if (GamePlayService.getInstance().getCountriesConqueredBy(c2.getRuler()).size() == 0) {
					// Remove this player from the player list
					GamePlayService.getInstance().knockPlayerOut(c2.getRuler());
				}
				
				
				c2.setRuler(c1.getRuler(), 1);
				
				// after this cheater player won't need any cards because by cheating he/she is winning.
				
				}
				/*//Check defender is eliminated from the game or not
				if (GamePlayService.getInstance().getCountriesConqueredBy(c2.getRuler()).size() != 0) {
					if(!c2.getRuler().getName().equalsIgnoreCase(c1.getRuler().getName())) {														
						c1.setNoOfArmy(c1.getNoOfArmy()-1);
						c2.setRuler(c1.getRuler(), 1);
						}	
				}
				else {
					// Remove this player from the player list
					GamePlayService.getInstance().knockPlayerOut(c2.getRuler());
				}
				
				if(GamePlayService.getInstance().isThisTheEnd()) {
					
					System.out.println("You win!");
				}*/
				
			}
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.strategies.PlayerStrategy#computeFortifyMove(ca.concordia.app.model.Player)
	 */
	@Override
	public Map<String, Object> computeFortifyMove(Player p) {
		// TODO Auto-generated method stub
		
		List<Country> playerCountries = GamePlayService.getInstance().getCountriesConqueredBy(p);
		
		List<Country> eligibleFortifyCountries = new ArrayList<>();
		
		for(Country ownCountry : playerCountries) {
			List<Country> neighborsCountries = GameMap.getInstance().getNeighbourCountries(ownCountry);
			for(Country c : neighborsCountries) {
				
				if(!c.getRuler().getName().equalsIgnoreCase(ownCountry.getRuler().getName())) {
					eligibleFortifyCountries.add(ownCountry);
					break;
				}
				
			}
		}
		
		for(Country c : eligibleFortifyCountries) {
			//c.setNoOfArmy(2 * c.getNoOfArmy());
			c.addArmies(c.getNoOfArmy());
		}
		
		return null;
	}
	@Override
	public String getName() {
		return "Cheater";
	}
}
