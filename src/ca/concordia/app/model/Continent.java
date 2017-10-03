/**
 * 
 */
package ca.concordia.app.model;

/**
 * @author harvi
 *
 */
public class Continent {

	private String continentName;
	
	private int controlValue;

	public Continent(String continentName, int controlValue) {
		this.continentName = continentName;
		this.controlValue = controlValue;
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
	
}
