/**
 * 
 */
package ca.concordia.app.model;

import java.awt.Color;

/**
 * @author harvi
 *
 */
public class Continent {

	private String continent_name;
	
	private int control_value;
	
	private Color colour;

	public Continent(String continentName, int controlValue, Color colour) {
		this.continent_name = continentName;
		this.control_value = controlValue;
		this.colour = colour;
	}

	public String getContinentName() {
		return continent_name;
	}

	public void setContinentName(String continentName) {
		this.continent_name = continentName;
	}

	public int getControlValue() {
		return control_value;
	}

	public void setControlValue(int controlValue) {
		this.control_value = controlValue;
	}
	public Color getColor() {
		return colour;
	}

	public void setColor(Color color) {
		this.colour = color;
	}

	public boolean equals(Object obj) {
		  
		  if(obj instanceof Continent){
			  if(this.continent_name.equals(((Continent) obj).getContinentName())){
				return true;  
			  }
		  }
	      return false;
	  }
	  
	
}
