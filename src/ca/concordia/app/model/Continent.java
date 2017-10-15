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
	public boolean equals(Object obj) {
		  
		  if(obj instanceof Continent){
			  if(this.continentName.equals(((Continent) obj).getContinentName())){
				return true;  
			  }
		  }
	      return false;
	  }
	  
	
}
