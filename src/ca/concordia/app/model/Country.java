/**
 * 
 */
package ca.concordia.app.model;

import lib.model.Player;

/**
 * @author harvi
 *
 */
public class Country {
	
	private String countryName;
	
	private int locX;
	
	private int locy;
	
	private String continentName;
	
	private int no_of_army = 0;
	
	private Player ruler;
	
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

	/**
	 * @return the no_of_army
	 */
	public int getNoOfArmy() {
		return no_of_army;
	}

	/**
	 * @param no_of_army the no_of_army to set
	 */
	public void setNoOfArmy(int no_of_army) {
		this.no_of_army = no_of_army;
	}

	/**
	 * @return the ruler
	 */
	public Player getRuler() {
		return ruler;
	}

	/**
	 * @param ruler the ruler to set
	 */
	public void setRuler(Player ruler,int noOfArmies) {
		this.ruler = ruler;
		this.no_of_army+=noOfArmies;
	}
	

	public void addArmies(int noOfArmies){
		this.no_of_army+=noOfArmies;
	}
	
	public void removeArmies(int noOfArmies){
		this.no_of_army-=noOfArmies;
	}
	
	
	  
}
