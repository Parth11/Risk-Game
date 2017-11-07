package ca.concordia.app.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.concordia.app.model.DiceRoller;

public class TestDiceRoller {

	private DiceRoller dice_roller;
	
	@Test
	public void rollAllTest() {
		dice_roller.roll(2);
		dice_roller.rollAll();
		dice_roller.getResults();
		
		
	}

}
