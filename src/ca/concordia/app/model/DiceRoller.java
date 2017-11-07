package ca.concordia.app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * For Rolling Dice during attack phase 
 * @author Parth Nayak
 * 
 * 
 */

public class DiceRoller {

	/**
	 * number of dice
	 */
	int no_of_dice;
	
	/**
	 * result of the dice
	 */
	List<Integer> result;
	
	/**
	 * Parameterized constructor
	 * @param game
	 * @param noOfDice
	 */
	public DiceRoller(int noOfDice) {
		this.no_of_dice = noOfDice;
		this.result = new ArrayList<>();
	}

	/**
	 * Roll the dice
	 * @param diesNo
	 * @return
	 */
	public int roll(int diesNo) {
		return (int)((Math.random() * 6) + 1);
	}


	public List<Integer> rollAll() {
		for(int i=1; i<=no_of_dice; i++)
			result.add(roll(i));
		Collections.sort(result);
		Collections.reverse(result);
		return result;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s="";
		for(int a:result) {
			s+=" "+a;
		}
		return s;
	}

}
