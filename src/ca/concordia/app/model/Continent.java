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

	private String continentName;
	
	private int controlValue;
	
	private Color colour;

	public Continent(String continentName, int controlValue, Color colour) {
		this.continentName = continentName;
		this.controlValue = controlValue;
		this.colour = colour;
	}

	public String getContinentName() {
		return continentName;
	}

	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	public int getControlValue() {
		return controlValue;
	}

	public void setControlValue(int controlValue) {
		this.controlValue = controlValue;
	}
	public Color getColor() {
		return colour;
	}

	public void setColor(Color color) {
		this.colour = color;
	}

	public boolean equals(Object obj) {
		  
		  if(obj instanceof Continent){
			  if(this.continentName.equals(((Continent) obj).getContinentName())){
				return true;  
			  }
		  }
	      return false;
	  }
	  
	
}
