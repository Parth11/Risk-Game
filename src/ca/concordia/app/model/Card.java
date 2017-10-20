package ca.concordia.app.model;

/**
 * @author Parth Nayak
 * 
 * 
 */
public class Card {

	enum CardType {INFANTRY, CAVALRY, ARTILLERY};
	
	private CardType card_type;

	public Card(CardType cardType) {
		super();
		this.card_type = cardType;
	}

	public CardType getCardType() {
		return card_type;
	}
}
