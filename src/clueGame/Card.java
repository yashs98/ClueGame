package clueGame;

/**
 * 
 * @author Yash Sinha and Daniel Thorne and Calvin Mak
 * This Class will be for each Card object. This is just setting up the framework for the future.
 *
 */

public class Card {
	private String cardName;
	private CardType cardType;
	/**
	 * 
	 * @param cardName
	 * This a constructor for BoardCell where a row and a column of a cell is inputed. 
	 */
	public Card(String name, CardType Type) {
		this.cardName = name;
		this.cardType = Type;
	}
	
	/**
	 * 
	 * Compares two cards and returns if they are the same.
	 * @return
	 */
	public boolean equals(Card card) {
		
		return false;
	}

	public CardType getCardType() {
		return cardType;
	}

	public String getCardName() {
		return cardName;
	}
	
    
	
}
