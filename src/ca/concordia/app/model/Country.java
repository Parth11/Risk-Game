
package ca.concordia.app.model;

import java.io.Serializable;

/**
 * This class contains the Country data along with getters and setters.
 * @author harvi
 *
 */
public class Country implements Serializable{
	
	private static final long serialVersionUID = 8218595675913048375L;

	private String country_name;

	private int loc_x;

	private int loc_y;

	private String continent_name;
	
	private int no_of_army = 0;

	private Player ruler;
	
	/**
	 * Parameterized Constructor
	 * @param countryName
	 * @param locX
	 * @param locy
	 * @param continentName
	 */
	public Country(String countryName, int locX, int locy, String continentName) {
		this.country_name = countryName;
		this.loc_x = locX;
		this.loc_y = locy;
		this.continent_name = continentName;
	}

	/**
	 * this method gets country name
	 * @return The name of the Country
	 */
	public String getCountryName() {
		return country_name;
	}
	
	/**
	 * Sets the name of the Country.
	 * @param countryName
	 */
	public void setCountryName(String countryName) {
		this.country_name = countryName;
	}

	/**
	 * gets the location of X coordinate
	 * @return The location at X coordinate
	 */
	public int getLocX() {
		return loc_x;
	}

	/**
	 * Sets the location at x coordinate
	 * @param locX
	 */
	public void setLocX(int locX) {
		this.loc_x = locX;
	}

	/**
	 * gets the Y coordinate 
	 * @return the location at Y coordinate
	 */
	public int getLocY() {
		return loc_y;
	}

	/**
	 * sets the location at y coordinate
	 * @param locY
	 */
	public void setLocY(int locY) {
		this.loc_y = locY;
	}
	
	/**
	 * gets the continent name
	 * @return The  continent name
	 */
	public String getContinentName() {
		return continent_name;
	}

	/**
	 * Sets the Continent name.
	 * @param continentName
	 */
	public void setContinentName(String continentName) {
		this.continent_name = continentName;
	}
	
	  /**
	   * compare the country name
	   * returns boolean true if country names are equal.
	   */
	  public boolean equals(Object obj) {
		  
		  if(obj instanceof Country){
			  if(this.country_name.equals(((Country) obj).getCountryName())){
				return true;  
			  }
		  }
	      return false;
	  }
	  
	  /**
	   * country name hash code
	   * @return country_name
	   */
	  @Override
	public int hashCode() {
		return country_name.hashCode();
	}
	  
	  /**
	   * Country name in the string 
	   * @return the name of the country object.
	   */
	  public String toString(){
		  return this.country_name;
	  }

	/**
	 * get number of army 
	 * @return the no_of_army
	 */
	public int getNoOfArmy() {
		return no_of_army;
	}

	/**
	 * set the number of army 
	 * @param no_of_army the no_of_army to set
	 */
	public void setNoOfArmy(int no_of_army) {
		this.no_of_army = no_of_army;
	}

	/**
	 * get the ruler
	 * @return the ruler
	 */
	public Player getRuler() {
		return ruler;
	}

	/**
	 * Set the ruler
	 * @param ruler the ruler to set
	 */
	public void setRuler(Player ruler,int noOfArmies) {
		this.ruler = ruler;
		this.no_of_army+=noOfArmies;
	}
	
	/**
	 * Add Armies
	 * @param noOfArmies
	 */
	public void addArmies(int noOfArmies){
		this.no_of_army+=noOfArmies;
	}
	/**
	 * Remove Armies
	 * @param noOfArmies
	 */
	public void removeArmies(int noOfArmies){
		this.no_of_army-=noOfArmies;
	}
	
	
	  
}
