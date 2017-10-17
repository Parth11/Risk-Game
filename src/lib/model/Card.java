package lib.model;

import lib.model.Card.CardType;

/**
 * @author Parth Nayak
 * 
 * 
 */

public class Card {
enum CardType {INFANTRY, CAVALRY, ARTILLERY};
	
	private CardType cardType;

	public Card(CardType cardType) {
		super();
		this.cardType = cardType;
	}

	public CardType getCardType() {
		return cardType;
	}

}
