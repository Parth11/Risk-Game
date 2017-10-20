package ca.concordia.app.model;

import java.util.ArrayList;

/**
 * @author Parth Nayak
 * 
 * 
 */
public class Player {

	public String name;
	public int total_armies;
	public String color;
	public ArrayList<Card> cards_list;
	
	public Player(String name) {
		this.name = name;
		this.color = null;
		this.cards_list = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalArmies() {
		return total_armies;
	}
	public void setTotalArmies(int totalArmies) {
		this.total_armies = totalArmies;
	}
	
	public void addArmy(int n) {
		this.total_armies+=n;
	}
	public void subArmy(int n) {
		this.total_armies-=n;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public ArrayList<Card> getCards() {
		return cards_list;
	}
	public void addCard(Card card) {
		cards_list.add(card);
	}
}
