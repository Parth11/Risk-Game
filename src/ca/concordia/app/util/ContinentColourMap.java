
package ca.concordia.app.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The Class ContinentColourMap adds color to the continents during map load.
 *
 * @author harvi
 */
public class ContinentColourMap {

	private static Map<String, Colours> continent_colour_map = new HashMap<String, Colours>();

	private static List<Colours> available_colours = new ArrayList<Colours>();
	
	private enum Colours {
		
		/** The red. */
		RED(Color.RED),
		
		/** The blue. */
		BLUE(Color.BLUE),
		
		/** The cyan. */
		CYAN(Color.CYAN),
		
		/** The grey. */
		GREY(Color.GRAY),
		
		/** The magenta. */
		MAGENTA(Color.MAGENTA),
		
		/** The orange. */
		ORANGE(Color.ORANGE),
		
		/** The yellow. */
		YELLOW(Color.YELLOW),
		
		/** The pink. */
		PINK(Color.PINK),
		
		/** The light grey. */
		LIGHT_GREY(Color.LIGHT_GRAY),
		
		/** The dark grey. */
		DARK_GREY(Color.DARK_GRAY);
		
		private Color color;

		private Colours(Color color){
			this.color = color;
		}
	}
	
	static {
		for(Colours c : Colours.values()){
			available_colours.add(c);
		}
	}
	
	/**
	 * Gets the continent colour.
	 *
	 * @param continentName the continent name
	 * @return the continent colour
	 */
	public static Color getContinentColour(String continentName){
		
		return continent_colour_map.get(continentName).color;
	}
	
	/**
	 * Sets the continent colour.
	 *
	 * @param continentName the new continent colour
	 * @throws Exception the exception
	 */
	public static void setContinentColour(String continentName) throws Exception{
		if(available_colours.isEmpty()){
			throw new Exception("No More Colours Available");
		}
		Colours c = available_colours.remove(0);
		continent_colour_map.put(continentName, c);
	}
	
	/**
	 * Reset colors.
	 */
	public static void resetColors(){
		available_colours.clear();
		continent_colour_map.clear();
		for(Colours c : Colours.values()){
			available_colours.add(c);
		}
	}
}

