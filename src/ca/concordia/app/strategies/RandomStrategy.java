/**
 * 
 */
package ca.concordia.app.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicIconFactory;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GamePlayEvent;
import ca.concordia.app.model.Player;
import ca.concordia.app.model.GamePlayEvent.EventType;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.util.Randomizer;

/**
 * @author harvi
 *
 */
public class RandomStrategy implements PlayerStrategy {

	@Override
	public Map<String, Object> computeReinforcementMove(Player p) {
		Map<String, Object> strategyRs = new HashMap<>();
		List<Country> countries = GamePlayService.getInstance().getCountriesConqueredBy(p);
		strategyRs.put("country", countries.get(Randomizer.randomize(countries.size())));
		
		Country randomCountry = countries.get(Randomizer.randomize(countries.size()));
		int randomArmies = Randomizer.randomize(GamePlayService.getInstance().getReinforcementArmyForPlayer(p));
		p.doReinforcement(randomCountry, randomArmies);
		
		return strategyRs;
	}

	@Override
	public Map<String, Object> computeAttackMove(Player p) {


		while(p.canAttack() && Randomizer.randomize(2)==0){

			List<Country> countries = GamePlayService.getInstance().getEligibleAttackingCountriesForPlayer(p);
			Country attackerCountry =  countries.get(Randomizer.randomize(countries.size()));
			
			countries = GamePlayService.getInstance().getEligibleAttackableCountries(attackerCountry);
			Country defenderCountry = countries.get(Randomizer.randomize(countries.size()));
			
			List<Integer> attackResult = new ArrayList<>();
			attackResult.add(Randomizer.randomize(6)+1);
			attackResult.add(Randomizer.randomize(6)+1);
			attackResult.add(Randomizer.randomize(6)+1);
			
			List<Integer> defenceResult = new ArrayList<>();
			defenceResult.add(Randomizer.randomize(6)+1);
			defenceResult.add(Randomizer.randomize(6)+1);
			
			p.doAttack(attackerCountry, defenderCountry, attackResult, defenceResult);
			
			if(p.country_captured==true){
				
				int n = attackerCountry.getNoOfArmy()-attackResult.size();
				Integer armies = Randomizer.randomize(n)+attackResult.size();
				
				GamePlayService.getInstance().moveArmyFromTo(p, attackerCountry, defenderCountry, armies);
				
				HashMap<String, Object> eventPayload = new HashMap<>();
				eventPayload.put("attackCountry", attackerCountry);
				eventPayload.put("capturedCountry", defenderCountry);
				eventPayload.put("armies", armies);
				GamePlayEvent gpe = new GamePlayEvent(EventType.ATTACK_CAPTURE, eventPayload);
				p.publishGamePlayEvent(gpe);
				
				p.country_captured = false;
				
				if(GamePlayService.getInstance().isThisTheEnd()){
					eventPayload = new HashMap<>();
					eventPayload.put("winner", p);
					gpe = new GamePlayEvent(EventType.THE_END, eventPayload);
					p.publishGamePlayEvent(gpe);
					
				}
				
			}
			
		}
		
		return null;
	}

	@Override
	public Map<String, Object> computeFortifyMove(Player p) {

		Map<String, Object> strategyRs = new HashMap<>();

		List<Country> countrySelection = GamePlayService.getInstance().getCountriesConqueredBy(p);
		
		List<Country> countrySelectionFiltered = new ArrayList<>();
		
		for (Country c : countrySelection) {
			if (c.getNoOfArmy() > 1) {
				countrySelectionFiltered.add(c);
			}
		}

		if (countrySelectionFiltered.isEmpty()) {
			return null;
		}
		
		Country fromCountry = countrySelectionFiltered.get(Randomizer.randomize(countrySelectionFiltered.size()));

		countrySelectionFiltered.clear();

		List<Country> countries = GamePlayService.getInstance().getCountriesConqueredBy(fromCountry.getRuler());
		
		int index = countries.indexOf(fromCountry);
		
		for (int i = 0; i < countries.size(); i++) {
			if (i != index && GamePlayService.getInstance().isConnected(fromCountry, countries.get(i), fromCountry.getRuler())) {
				countrySelectionFiltered.add(countries.get(i));
			}
		}

		if (countrySelectionFiltered.isEmpty()) {
			return null;
		}

		Country toCountry = countrySelectionFiltered.get(Randomizer.randomize(countrySelectionFiltered.size()));

		int armies = fromCountry.getNoOfArmy() - Randomizer.randomize(fromCountry.getNoOfArmy()-1);
		
		p.doFortification(fromCountry, toCountry, armies);

		strategyRs.put("from", fromCountry);
		strategyRs.put("to", toCountry);
		strategyRs.put("armies", armies);
		return strategyRs;

	}

}
