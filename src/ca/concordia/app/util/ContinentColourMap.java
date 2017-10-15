/**
 * 
 */
package ca.concordia.app.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author harvi
 *
 */
public class ContinentColourMap {
	
	private static Map<String, Colours> continentColourMap = new HashMap<String, Colours>();
	
	private static List<Colours> availableColours = new ArrayList<Colours>();
	

	private enum Colours {
		
		RED(Color.RED),
		BLACK(Color.BLACK),
		BLUE(Color.BLUE),
		CYAN(Color.CYAN),
		GREY(Color.GRAY),
		MAGENTA(Color.MAGENTA),
		ORANGE(Color.ORANGE),
		YELLOW(Color.YELLOW),
		PINK(Color.PINK),
		LIGHT_GREY(Color.LIGHT_GRAY),
		DARK_GREY(Color.DARK_GRAY);
		
		private Color color;
		
		private Colours(Color color){
			this.color = color;
		}
	}
	
	static {
		for(Colours c : Colours.values()){
			availableColours.add(c);
		}
	}
	
	public static Color getContinentColour(String continentName){
		
		return continentColourMap.get(continentName).color;
	}
	
	public static void setContinentColour(String continentName) throws Exception{
		if(availableColours.isEmpty()){
			throw new Exception("No More Colours Available");
		}
		Colours c = availableColours.remove(0);
		continentColourMap.put(continentName, c);
	}
}

