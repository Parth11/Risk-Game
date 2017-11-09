package ca.concordia.app.model;

/**
 * This Class contains the data for the cards with getters and setters.
 * @author Parth Nayak
 * 
 * 
 */
public class Card {

	private String card_type;

	/**
	 * A constructor. Sets the card type
	 * @param card_type type of card (Atrillery, Cavalry, Infantry)  
	 */
	public Card(String card_type) {
		this.card_type = card_type;
	}

	/**
	 * Returns the type of card
	 * @return the card type
	 */
	public String getCardType() {
		return card_type;
	}
	/**
	 * Sets the card type
	 * @param cardType type of card {Artillery, Cavalry, Infantry} 
	 */
	public void setCardType(String cardType) {
		this.card_type = cardType;
	}
	
	/**
	 * @return True or False 
	 */
	@Override
	public boolean equals(Object obj) {
		return this.card_type.equals(((Card)obj).getCardType());
	}
	
	/**
	 * @return the card type in String format
	 */
	@Override
	public String toString() {
		return this.card_type;
	}
}
