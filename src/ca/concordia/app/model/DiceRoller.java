package ca.concordia.app.model;

import ca.concordia.app.service.GamePlayService;

/**
 * For Rolling Dice during attack phase 
 * @author Parth Nayak
 * 
 * 
 */

public class DiceRoller {
	GamePlayService game;
	int no_of_dice;
	int[] result;
	
	public DiceRoller(GamePlayService game, int noOfDice) {
		this.game=game;
		this.no_of_dice = noOfDice;
		this.result = new int[noOfDice];
	}
	
	public int roll(int diesNo) {
		int i = diesNo-1, res = 0;
		result[i] = res = (int)((Math.random() * 6) + 1);
		return res;
	}
	
	public void rollAll() {
		for(int i=1; i<=no_of_dice; i++)
			roll(i);
	}
	
	public int[] getResults() {
		return result;
	}
}
