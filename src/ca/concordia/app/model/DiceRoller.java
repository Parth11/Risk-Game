package ca.concordia.app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * For Rolling Dice during attack phase
 * 
 * @author Parth Nayak
 */

public class DiceRoller {

	int no_of_dice;

	List<Integer> result;

	/**
	 * Parameterized constructor
	 * @param noOfDice the number of dice
	 */
	public DiceRoller(int noOfDice) {
		this.no_of_dice = noOfDice;
		this.result = new ArrayList<>();
	}

	/**
	 * Roll the dice
	 * @param diesNo the dice number
	 * @return the dice value
	 */
	public int roll(int diesNo) {
		return (int) ((Math.random() * 6) + 1);
	}

	/**
	 * rolls the dice
	 * @return the result as a List
	 */
	public List<Integer> rollAll() {
		for (int i = 1; i <= no_of_dice; i++)
			result.add(roll(i));
		Collections.sort(result);
		Collections.reverse(result);
		return result;
	}

	/**
	 * Dice value in the string format
	 * @return the dice value in String format
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = "";
		for (int a : result) {
			s += " " + a;
		}
		return s;
	}

}
