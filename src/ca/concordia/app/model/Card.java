package ca.concordia.app.model;

/**
 * This Class contains the data for the cards with getters and setters.
 * @author Parth Nayak
 * 
 * 
 */
public class Card {

	
	
	private String card_type;
	private int numerOfCard;

	public Card(String card_type, int numerOfCard) {
		super();
		this.card_type = card_type;
		this.numerOfCard = numerOfCard;
	}
	
	

	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public int getNumerOfCard() {
		return numerOfCard;
	}
	public void setNumerOfCard(int numerOfCard) {
		this.numerOfCard = numerOfCard;
	}

	
	
}
