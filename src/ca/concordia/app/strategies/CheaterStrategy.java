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
import ca.concordia.app.model.GamePlayEvent;
import ca.concordia.app.model.Player;
import ca.concordia.app.model.GamePlayEvent.EventType;
import ca.concordia.app.service.ConsoleLoggerService;
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

	/**
	 * reinforcement implementation
	 * @param p the player
	 */
	@Override
	public Map<String, Object> computeReinforcementMove(Player p) {
		// TODO Auto-generated method stub
		
		Map<String, Object> strategyAs = new HashMap<>();
				
		strategyAs.put("country", GamePlayService.getInstance().getCountriesConqueredBy(p));
		
		// computing logic for doubling the armies
		List<Country> playerCountries = GamePlayService.getInstance().getCountriesConqueredBy(p);
		
		strategyAs.put("beforeArmies", playerCountries.get(0).getNoOfArmy());
		
		for(Country c : playerCountries) {
			int armies = c.getNoOfArmy();
			p.doReinforcement(c, armies);
		}
		
		strategyAs.put("afterArmies", playerCountries.get(0).getNoOfArmy());
		
		return strategyAs;

	}

	/**
	 * attack implementation
	 * @param p the player
	 */
	@Override
	public Map<String, Object> computeAttackMove(Player p) {
	// TODO Auto-generated method stub				
	
	List<Country> playerCountries = GamePlayService.getInstance().getCountriesConqueredBy(p);
	
	//for(Country c1 : playerCountries) 
	
	for(int i = 0; i < playerCountries.size(); i++){
		
		List<Country> countries = GameMap.getInstance().getNeighbourCountries(playerCountries.get(i));
		
		//for(Country c2 : countries) 
		
		for(int j = 0; j < countries.size(); j++){
			
			if(!countries.get(j).getRuler().getName().equalsIgnoreCase(playerCountries.get(i).getRuler().getName())) {
			 
			// 1. Remove a country from defender's country list
			GamePlayService.getInstance().unmapPlayerToCountry(countries.get(j).getRuler(), countries.get(j));

			// 2. Add defending country in attacker country list
			GamePlayService.getInstance().mapPlayerToCountry(playerCountries.get(i).getRuler(), countries.get(j));

			// 3. Check defender is eleminated from the game or not
			if (GamePlayService.getInstance().getCountriesConqueredBy(countries.get(j).getRuler()).size() == 0) {
				// Remove this player from the player list
				GamePlayService.getInstance().knockPlayerOut(countries.get(j).getRuler());
			}
							
			countries.get(j).setRuler(playerCountries.get(i).getRuler(), 1);						
			
			// after this cheater player won't need any cards because by cheating he/she is winning.
			
			}			
			
		}
		
	}
	
	if(GamePlayService.getInstance().isThisTheEnd()){
		HashMap<String, Object> eventPayload = new HashMap<>();
		eventPayload.put("winner", p);
		GamePlayEvent gpe = new GamePlayEvent(EventType.THE_END, eventPayload);
		p.publishGamePlayEvent(gpe);
	}

	return null;
}


	/**
	 * fortify implementation
	 * @param p the player
	 */
	@Override
	public Map<String, Object> computeFortifyMove(Player p) {
		// TODO Auto-generated method stub
		
		Map<String, Object> strategyCs = new HashMap<>();
		
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
		
		strategyCs.put("fromBeforeArmies", eligibleFortifyCountries.get(0).getNoOfArmy());
		
		for(Country c : eligibleFortifyCountries) {
			//c.setNoOfArmy(2 * c.getNoOfArmy());
			c.addArmies(c.getNoOfArmy());
		}
		
		strategyCs.put("fromAfterArmies", eligibleFortifyCountries.get(0).getNoOfArmy());
		
		return strategyCs;
	}
	
	/**
	 * Give the strategy as a string
	 * @return the strategy
	 */
	@Override
	public String getName() {
		return "Cheater";
	}
}
