package ca.concordia.app.model;

import ca.concordia.app.service.GamePlayService;

/**
 * For Rolling Dice during attack phase 
 * @author Parth Nayak
 * 
 * 
 */

public class DiceRoller {
	/**
	 * create a object of type GamePlayService
	 */
	GamePlayService game;
	
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
	public DiceRoller(GamePlayService game, int noOfDice) {
		this.game=game;
		this.no_of_dice = noOfDice;
		this.result = new int[noOfDice];
	}
	/**
	 * Roll the dice
	 * @param diesNo
	 * @return
	 */
	public int roll(int diesNo) {
		int i = diesNo-1, res = 0;
		result[i] = res = (int)((Math.random() * 6) + 1);
		return res;
	}
	/**
	 * Roll all dice
	 */
	public void rollAll() {
		for(int i=1; i<=no_of_dice; i++)
			roll(i);
	}
	/**
	 * 
	 * @return the result of the rolled dice.
	 */
	public int[] getResults() {
		return result;
	}
}
