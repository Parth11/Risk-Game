/**
 * 
 */
package ca.concordia.app.util;

import java.util.Random;

/**
 * @author harvi
 *
 */
public class Randomizer {

	static Random r = new Random();

	public static int randomize(int n){
		return r.nextInt(n);
	}
	
}
