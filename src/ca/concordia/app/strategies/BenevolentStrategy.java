/**
 * 
 */
package ca.concordia.app.strategies;

import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.ConsoleLoggerService;
import ca.concordia.app.service.GamePlayService;

/**
 * This class implements the Benevolent Strategy
 * A benevolent computer player strategy that focuses on protecting its weak countries 
 * (reinforces its weakest countries, never attacks, then fortifies in order to move armies to weaker countries).
 * @author harvi
 *
 */
public class BenevolentStrategy implements PlayerStrategy {

	
	/**
	 * This method computes the reinforcement move
	 * @param p player object
	 */
	@Override
	public Map<String, Object> computeReinforcementMove(Player p) {
		
		/**
		 * The List
		 */
		Map<String, Object> strategyAs = new HashMap<>();
		
		Country reinforcementCountry = GamePlayService.getInstance().getWeakestCountry(p);
		
		strategyAs.put("country", reinforcementCountry);
		strategyAs.put("beforeArmies", reinforcementCountry.getNoOfArmy());
		
		int reinforcementArmies = GamePlayService.getInstance().getReinforcementArmyForPlayer(p);				
		
		p.doReinforcement(reinforcementCountry, reinforcementArmies);
		
		strategyAs.put("afterArmies", reinforcementCountry.getNoOfArmy());
		
		return strategyAs;
		
	}

	/**
	 * attack implementation
	 * @param p the variable of type player
	 */
	@Override
	public Map<String, Object> computeAttackMove(Player p) {
		// TODO Auto-generated method stub
				
		ConsoleLoggerService.getInstance(null).write("Skipping the AttackPhase in Benevolent Strategy");
		
		return null;
	}

	/**
	 * fortification implementation
	 * @param p the variable of type player
	 */
	@Override
	public Map<String, Object> computeFortifyMove(Player p) {
		// TODO Auto-generated method stub
		
		Map<String, Object> strategyRs = new HashMap<>();
		
		// Player's country
		List<Country> countrySelection = GamePlayService.getInstance().getCountriesConqueredBy(p);
		
		//Country eligible for fortification phase
		List<Country> fortificationEligibleCountries = new ArrayList<>();
		
		// Filer list which > 1 armies
		List<Country> countrySelectionFiltered = new ArrayList<>();
		
		for (Country c : countrySelection) {
			if(c.getNoOfArmy() > 1) {
				countrySelectionFiltered.add(c);
			}
		}
		
		if(countrySelectionFiltered.isEmpty()) {
			return null;
		}

		Country toCountry = GamePlayService.getInstance().getWeakestCountry(p);
		strategyRs.put("toBeforeArmies", toCountry.getNoOfArmy());
		
		//adding those countries which are connected to weakest country who has more than one army on it.
		for (int i = 0; i < countrySelectionFiltered.size(); i++) {
			if (countrySelectionFiltered.get(i) != toCountry && GamePlayService.getInstance().isConnected(toCountry, countrySelectionFiltered.get(i), p)) {
				fortificationEligibleCountries.add(countrySelectionFiltered.get(i));
			}
		}

		if (fortificationEligibleCountries.isEmpty()) {
			return null;
		}

		//choosing the first index from the list
		Country fromCountry = fortificationEligibleCountries.get(0);
		strategyRs.put("fromBeforeArmies", fromCountry.getNoOfArmy());

		//leaving one army behind and move rest of it to the weakest country
		int armies = (fromCountry.getNoOfArmy()-1);
		
		p.doFortification(fromCountry, toCountry, armies);

		strategyRs.put("fromAfterArmies", fromCountry.getNoOfArmy());
		strategyRs.put("from", fromCountry);
		strategyRs.put("to", toCountry);
		strategyRs.put("armies", armies);
		return strategyRs;

	}
	
	/**
	 * gives the name of the strategy
	 * @return the strategy
	 */
	@Override
	public String getName() {
		return "Benevolent";
	}

}
