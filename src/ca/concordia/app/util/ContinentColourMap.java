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
	
	private static Map<String, Colours> continent_colour_map = new HashMap<String, Colours>();
	
	private static List<Colours> available_colours = new ArrayList<Colours>();
	

	private enum Colours {
		
		RED(Color.RED),
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
			available_colours.add(c);
		}
	}
	
	public static Color getContinentColour(String continentName){
		
		return continent_colour_map.get(continentName).color;
	}
	
	public static void setContinentColour(String continentName) throws Exception{
		if(available_colours.isEmpty()){
			throw new Exception("No More Colours Available");
		}
		Colours c = available_colours.remove(0);
		continent_colour_map.put(continentName, c);
	}
	
	public static void resetColors(){
		available_colours.clear();
		continent_colour_map.clear();
		for(Colours c : Colours.values()){
			available_colours.add(c);
		}
	}
}

