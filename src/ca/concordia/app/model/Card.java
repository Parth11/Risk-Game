package ca.concordia.app.model;

/**
 * This Class contains the data for the cards with getters and setters.
 * @author Parth Nayak
 * 
 * 
 */
public class Card {

	enum CardType {INFANTRY, CAVALRY, ARTILLERY};
	
	/**
	 * Create an object of class CardType.
	 */
	private CardType card_type;
	
	/**
	 * Sets the card type
	 * @param cardType
	 */
	public Card(CardType cardType) {
		super();
		this.card_type = cardType;
	}
	
	/**
	 * 
	 * @return the card type.
	 */
	public CardType getCardType() {
		return card_type;
	}
}
