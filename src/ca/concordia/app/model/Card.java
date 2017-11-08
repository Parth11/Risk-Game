package ca.concordia.app.model;

/**
 * This Class contains the data for the cards with getters and setters.
 * @author Parth Nayak
 * 
 * 
 */
public class Card {

	private String card_type;

	public Card(String card_type) {
		this.card_type = card_type;
	}

	public String getCardType() {
		return card_type;
	}
	public void setCardType(String cardType) {
		this.card_type = cardType;
	}
	@Override
	public boolean equals(Object obj) {
		return this.card_type.equals(((Card)obj).getCardType());
	}
	
	
	@Override
	public String toString() {
		return this.card_type;
	}
}
