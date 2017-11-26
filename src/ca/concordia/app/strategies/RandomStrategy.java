/**
 * 
 */
package ca.concordia.app.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.Player;
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
		return strategyRs;
	}

	@Override
	public Map<String, Object> computeAttackMove(Player p) {
		Map<String, Object> strategyRs = new HashMap<>();
		List<Country> countries = GamePlayService.getInstance().getEligibleAttackingCountriesForPlayer(p);
		strategyRs.put("attackCountry", countries.get(Randomizer.randomize(countries.size())));
		countries = GamePlayService.getInstance()
				.getEligibleAttackableCountries((Country) strategyRs.get("attackCountry"));
		strategyRs.put("defenceCountry", countries.get(Randomizer.randomize(countries.size())));
		List<Integer> attack = new ArrayList<>();
		attack.add(5);
		attack.add(4);
		attack.add(3);

		List<Integer> defence = new ArrayList<>();
		defence.add(4);
		defence.add(3);
		strategyRs.put("attacks", attack);
		strategyRs.put("defences", defence);
		return strategyRs;
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
		
		Country from = countrySelectionFiltered.get(Randomizer.randomize(countrySelectionFiltered.size()));

		countrySelectionFiltered.clear();

		List<Country> countries = GamePlayService.getInstance().getCountriesConqueredBy(from.getRuler());
		
		int index = countries.indexOf(from);
		
		for (int i = 0; i < countries.size(); i++) {
			if (i != index && GamePlayService.getInstance().isConnected(from, countries.get(i), from.getRuler())) {
				countrySelectionFiltered.add(countries.get(i));
			}
		}

		if (countrySelectionFiltered.isEmpty()) {
			return null;
		}

		Country to = countrySelectionFiltered.get(Randomizer.randomize(countrySelectionFiltered.size()));

		int armies = from.getNoOfArmy() - Randomizer.randomize(from.getNoOfArmy()-1);

		strategyRs.put("from", from);
		strategyRs.put("to", to);
		strategyRs.put("armies", armies);
		return strategyRs;

	}

}
