package ca.concordia.app.model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ca.concordia.app.model.DiceRoller;

public class DiceRollerTest {
	
	private DiceRoller dice_roller;
	
	/**
	 * Test dice roll.
	 */
	@Test
	public void  testDiceRoll() {
		List<Integer> result = new ArrayList<>();
		result.add(1);
		result.add(2);
		result.add(3);
		dice_roller = new DiceRoller(3);
		List<Integer> attack = dice_roller.rollAll();
		assertNotSame(result, attack);
	
	}

}
