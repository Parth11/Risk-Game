/**
 * 
 */
package lib.model;

/**
 * @author harvi
 *
 * @ modified by Part Nayak
 *
 */
public class Country extends Land {
	
	private int locX, locY;
	
	private Continent continent;
	
	private Player ruler = null;
	private int noOfArmy = 0;
	
	public Country(String countryName, int locX, int locY, Continent continent, Player ruler, int noOfArmy) {
		super(countryName);
		this.locX = locX;
		this.locY = locY;
		this.continent = continent;
		this.continent.addCountry(this);
		this.ruler = ruler;
		this.noOfArmy = noOfArmy;
	}

	public int getLocX() {
		return locX;
	}

	public void setLocX(int locX) {
		this.locX = locX;
		// update UI
	}

	public int getLocy() {
		return locY;
	}

	public void setLocy(int locY) {
		this.locY = locY;
		// update UI
	}
	
	public void setCoOrdinates(int x, int y) {
		this.locX = x;
		this.locY = y;
		// update UI
	}

	public Continent getContinent() {
		return continent;
	}

	public void setContinent(Continent c) {
		this.continent = c;
	}
	
	public int getNoOfArmies() {
		return noOfArmy;
	}
	
	public void addArmy(int n) {
		noOfArmy+=n;
		// update UI
	}
	
	public void subtractArmy(int n) {
		noOfArmy-=n;
		// update UI
	}
	
	public void setPlayer(Player ruler, int noOfArmy) {
		this.ruler = ruler;
		this.noOfArmy = noOfArmy;
		// update UI
	}
	
	public Player getRulerPlayer() {
		return ruler;
	}
}
