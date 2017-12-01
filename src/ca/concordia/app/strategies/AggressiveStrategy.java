/**
 * 
 */
package ca.concordia.app.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.DiceRoller;
import ca.concordia.app.model.GamePlayEvent;
import ca.concordia.app.model.Player;
import ca.concordia.app.model.GamePlayEvent.EventType;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.util.Randomizer;
import ca.concordia.app.view.AttackInputView;

/**
 * This class implements the aggressive strategy
 * An aggressive computer player strategy that focuses on attack (reinforces its strongest country, then always attack with it until it cannot attack anymore, then fortifies in order to maximize aggregation of forces in one country).
 * 
 * @author harvi
 *
 */
public class AggressiveStrategy implements PlayerStrategy {

	/**
	 * This method compute the reinforcement move
	 * @param p player object
	 */
	@Override
	public Map<String, Object> computeReinforcementMove(Player p) {

		Map<String, Object> strategyAs = new HashMap<>();
		Country strongesCountry = GamePlayService.getInstance().getStrongestCountry(p);
		strategyAs.put("country", strongesCountry);
		strategyAs.put("beforeArmies", strongesCountry.getNoOfArmy());

		int armies = GamePlayService.getInstance().getReinforcementArmyForPlayer(p);

		p.doReinforcement(strongesCountry, armies);
		strategyAs.put("afterArmies", strongesCountry.getNoOfArmy());

		return strategyAs;

	}


	/**
	 * This method compute the attack move
	 * @param p player object
	 * @return null
	 */
	@Override
	public Map<String, Object> computeAttackMove(Player p) {

		Map<String, Object> strategyRs = new HashMap<>();
		Country strongestCountry = GamePlayService.getInstance().getStrongestCountry(p);

		if (strongestCountry != null) {
			if (!GamePlayService.getInstance().getEligibleAttackableCountries(strongestCountry).isEmpty()) {

				Country defendingCountry = GamePlayService.getInstance()
						.getEligibleAttackableCountries(strongestCountry).get(0);

				
				strategyRs.put("beforeAttackCountry", strongestCountry);
				strategyRs.put("beforeDefenceCountry", defendingCountry);
				while (strongestCountry.getNoOfArmy() > 1
						&& !defendingCountry.getRuler().getName().equals(strongestCountry.getRuler().getName())) {

					DiceRoller attackRoller = GamePlayService.getInstance().getAttackDiceRoller(p, strongestCountry);
					DiceRoller defenceRoller = GamePlayService.getInstance().getDefenceDiceRoller(p, defendingCountry);

					List<Integer> attackResult = attackRoller.rollAll();
					List<Integer> defenceResult = defenceRoller.rollAll();

					
					p.doAttack(strongestCountry, defendingCountry, attackResult, defenceResult);
					if (p.country_captured == true) {

						GamePlayService.getInstance().moveArmyFromTo(p, strongestCountry, defendingCountry, 1);

						HashMap<String, Object> eventPayload = new HashMap<>();
						eventPayload.put("attackCountry", strongestCountry);
						eventPayload.put("capturedCountry", defendingCountry);
						eventPayload.put("armies", 1);
						GamePlayEvent gpe = new GamePlayEvent(EventType.ATTACK_CAPTURE, eventPayload);
						p.publishGamePlayEvent(gpe);

						p.country_captured = false;

						if (GamePlayService.getInstance().isThisTheEnd()) {
							eventPayload = new HashMap<>();
							eventPayload.put("winner", p);
							gpe = new GamePlayEvent(EventType.THE_END, eventPayload);
							p.publishGamePlayEvent(gpe);

						}
					}
				}
			}
		}
		return strategyRs;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.concordia.app.strategies.PlayerStrategy#computeFortifyMove(ca.concordia.
	 * app.model.Player)
	 */
	/**
	 * this method compute fortify move
	 * @param p player object
	 * @return strategies
	 */
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

		Country from = countrySelectionFiltered.get(0);
		strategyRs.put("fromBeforeArmies", from.getNoOfArmy());

		countrySelectionFiltered.clear();

		int index = countrySelection.indexOf(from);

		for (int i = 0; i < countrySelection.size(); i++) {
			if (i != index
					&& GamePlayService.getInstance().isConnected(from, countrySelection.get(i), from.getRuler())) {
				countrySelectionFiltered.add(countrySelection.get(i));
			}
		}

		if (countrySelectionFiltered.isEmpty()) {
			return null;
		}

		Country to = countrySelectionFiltered.get(0);
		
		strategyRs.put("toBeforeArmies", to.getNoOfArmy());

		int armies = from.getNoOfArmy() - (from.getNoOfArmy() - 1);		

		p.doFortification(from, to, armies);
		strategyRs.put("fromAfterArmies", from.getNoOfArmy());
		strategyRs.put("from", from);
		strategyRs.put("to", to);
		strategyRs.put("armies", armies);
		return strategyRs;

	}

	/**
	 * get the name of the strategy
	 * @return Aggressive
	 */

	@Override
	public String getName() {
		return "Aggressive";
	}
}
