/**
 * 
 */
package ca.concordia.app.model;

/**
 * @author harvi
 *
 */
public class Country {
	
	private String countryName;
	
	private int locX;
	
	private int locy;
	
	private String continentName;
	
	public Country(String countryName, int locX, int locy, String continentName) {
		this.countryName = countryName;
		this.locX = locX;
		this.locy = locy;
		this.continentName = continentName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getLocX() {
		return locX;
	}

	public void setLocX(int locX) {
		this.locX = locX;
	}

	public int getLocy() {
		return locy;
	}

	public void setLocy(int locy) {
		this.locy = locy;
	}

	public String getContinentName() {
		return continentName;
	}

	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	  public boolean equals(Object obj) {
		  
		  if(obj instanceof Country){
			  if(this.countryName.equals(((Country) obj).getCountryName())){
				return true;  
			  }
		  }
	      return false;
	  }
	  
	  public String toString(){
		  return this.countryName;
	  }

}
