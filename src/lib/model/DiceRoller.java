package lib.model;

import lib.Game;

/**
 * @author Parth Nayak
 * 
 * 
 */

public class DiceRoller {
	Game game;
	int noOfDice;
	int[] result;
	
	public DiceRoller(Game game, int noOfDice) {
		this.game=game;
		this.noOfDice = noOfDice;
		this.result = new int[noOfDice];
	}
	
	public int roll(int diesNo) {
		int i = diesNo-1, res = 0;
		result[i] = res = (int)((Math.random() * 6) + 1);
		return res;
	}
	
	public void rollAll() {
		for(int i=1; i<=noOfDice; i++)
			roll(i);
	}
	
	public int[] getResults() {
		return result;
	}
}
