package ca.concordia.app.model;

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
	int[] result;
	
	/**
	 * Parameterized constructor
	 * @param game
	 * @param noOfDice
	 */
	public DiceRoller(int noOfDice) {
		this.no_of_dice = noOfDice;
		this.result = new int[noOfDice];
	}
	
	/**
	 * Roll all dice
	 */
	public int[] rollAll() {
		for(int i=0; i<no_of_dice; i++)
			result[i] = (int)((Math.random() * 6) + 1);
		
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
