package lib.model;

import java.util.ArrayList;

/**
 * @author Parth Nayak
 * 
 * 
 */
public class Player {

	public String name;
	public int totalArmies;
	//public List<Country> conqueredContries;
	public String color;
	public ArrayList<Card> cardsList;
	
	public Player(String name) {
		super();
		this.name = name;
		this.color = null;
		//conqueredContries = new ArrayList<>();
		cardsList = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalArmies() {
		return totalArmies;
	}
	public void setTotalArmies(int totalArmies) {
		this.totalArmies = totalArmies;
	}
	
	public void addArmy(int n) {
		this.totalArmies+=n;
	}
	public void subArmy(int n) {
		this.totalArmies-=n;
	}
	/*public List<Country> getConqueredContries() {
		return conqueredContries;
	}
	public void setConqueredContries(List<Country> conqueredContries) {
		this.conqueredContries = conqueredContries;
	}*/
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public ArrayList<Card> getCards() {
		return cardsList;
	}
	public void addCard(Card card) {
		cardsList.add(card);
	}
}
