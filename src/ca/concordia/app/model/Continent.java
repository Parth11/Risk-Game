
package ca.concordia.app.model;

import java.awt.Color;

/**
 * This class contains the continent information with getters and setters.
 * @author harvi
 *
 */
public class Continent {
	

	private String continent_name;
	
	private int control_value;
	
	private Color colour;

	/**
	 * A parameterized constructor.
	 * @param continentName
	 * @param controlValue
	 * @param colour
	 */
	public Continent(String continentName, int controlValue, Color colour) {
		this.continent_name = continentName;
		this.control_value = controlValue;
		this.colour = colour;
	}

	/**
	 * 
	 * @return the continent name.
	 */
	public String getContinentName() {
		return continent_name;
	}
	
	/**
	 * Sets the continent name.
	 * @param continentName
	 */
	public void setContinentName(String continentName) {
		this.continent_name = continentName;
	}

	/**
	 * 
	 * @return the control value of the continent.
	 */
	public int getControlValue() {
		return control_value;
	}

	/**
	 * Sets the control value of the continent.
	 * @param controlValue
	 */
	public void setControlValue(int controlValue) {
		this.control_value = controlValue;
	}
	/**
	 * 
	 * @return the color which will be used for defining the continent. 
	 */
	public Color getColor() {
		return colour;
	}
	
	/**
	 * sets the color to the continent.
	 * @param color
	 */
	public void setColor(Color color) {
		this.colour = color;
	}

	/**
	 * @return boolean value True if the two continents are same.
	 */
	public boolean equals(Object obj) {
		  
		  if(obj instanceof Continent){
			  if(this.continent_name.equals(((Continent) obj).getContinentName())){
				return true;  
			  }
		  }
	      return false;
	  }
	  
	
}
