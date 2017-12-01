
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
	 * @param continentName the name of continent
	 * @param controlValue the control value
	 * @param colour the color on the map
	 */
	public Continent(String continentName, int controlValue, Color colour) {
		this.continent_name = continentName;
		this.control_value = controlValue;
		this.colour = colour;
	}

	/**
	 * this method gets the Continent name
	 * @return the continent name.
	 */
	public String getContinentName() {
		return continent_name;
	}
	
	/**
	 * Sets the continent name.
	 * @param continentName Name of Continent
	 */
	public void setContinentName(String continentName) {
		this.continent_name = continentName;
	}

	/**
	 * this method gets the Control value
	 * @return the control value of the continent.
	 */
	public int getControlValue() {
		return control_value;
	}

	/**
	 * Sets the control value of the continent.
	 * @param controlValue The control value of the continent
	 */
	public void setControlValue(int controlValue) {
		this.control_value = controlValue;
	}
	/**
	 * This method get color of the continent
	 * @return the color which will be used for defining the continent. 
	 */
	public Color getColor() {
		return colour;
	}
	
	/**
	 * sets the color to the continent.
	 * @param color the color allocated to this continent
	 */
	public void setColor(Color color) {
		this.colour = color;
	}

	/**
	 * Compares the value of continents
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
