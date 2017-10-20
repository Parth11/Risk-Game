
package ca.concordia.app.model;

/**
 * This class contains the Country data along with getters and setters.
 * @author harvi
 *
 */
public class Country {
	
	/**
	 * The Country name of type String.
	 */
	private String country_name;
	/**
	 * The location at x coordinate of type Int.
	 */
	private int loc_x;
	/**
	 * The location at y coordinate of type Int.
	 */
	private int loc_y;
	
	/**
	 * The continent name of type String.
	 */
	private String continent_name;
	
	/**
	 * the number of armys of type int.
	 */
	private int no_of_army = 0;
	/**
	 * Object of type player.
	 */
	private Player ruler;
	
	/**
	 * 
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

	public String getCountryName() {
		return country_name;
	}

	public void setCountryName(String countryName) {
		this.country_name = countryName;
	}

	public int getLocX() {
		return loc_x;
	}

	public void setLocX(int locX) {
		this.loc_x = locX;
	}

	public int getLocY() {
		return loc_y;
	}

	public void setLocY(int locY) {
		this.loc_y = locY;
	}

	public String getContinentName() {
		return continent_name;
	}

	public void setContinentName(String continentName) {
		this.continent_name = continentName;
	}

	  public boolean equals(Object obj) {
		  
		  if(obj instanceof Country){
			  if(this.country_name.equals(((Country) obj).getCountryName())){
				return true;  
			  }
		  }
	      return false;
	  }
	  
	  public String toString(){
		  return this.country_name;
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
