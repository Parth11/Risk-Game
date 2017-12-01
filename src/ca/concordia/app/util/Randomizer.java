/**
 * 
 */
package ca.concordia.app.util;

import java.util.Random;

/**
 * The Class for Randomizer
 * @author harvi
 *
 */
public class Randomizer {
	/**
	 * a random object
	 */
	static Random r = new Random();
	
	/**
	 * this method randomize
	 * @param n variable of type int
	 * @return the random number
	 */
	public static int randomize(int n){
		return r.nextInt(n);
	}
	
}
