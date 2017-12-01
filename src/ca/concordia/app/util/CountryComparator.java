
package ca.concordia.app.util;

import java.util.Comparator;

import ca.concordia.app.model.Country;

/**
 * The Class CountryComparator for comparing the countries
 *
 * @author harvi
 */
public class CountryComparator implements Comparator<Country>{

	/**
	 * method for comparing countries
	 * @param o1 ,o2
	 */

	@Override
	/**
	 * Compare countries
	 * return c country
	 */
	public int compare(Country o1, Country o2) {
		
		int c;
	    
		c = o1.getContinentName().compareTo(o2.getContinentName());
	    
		if (c == 0)
	       c = o1.getCountryName().compareTo(o2.getCountryName());
		
		return c;
		
	}

}
