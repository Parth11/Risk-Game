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

	static Random r = new Random();
	
	/**
	 * this method randomize
	 * @param n
	 * @return
	 */
	public static int randomize(int n){
		return r.nextInt(n);
	}
	
}
