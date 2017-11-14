/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Card Class: the object for the cards in play.
 */

public class Card implements Comparable<Card>{
	
	int number; //From 2-14 : 2(Two), 3(Three) ... 13(King), 14(Ace)
	Suit suit; //suit enum

	public static boolean mode = true;
	
	public Card(int number, Suit suit) {
		this.number = number;
		this.suit = suit;
	}

	/*
	 * Get the unicode value for the card.
	 */
	
	public static String getCard() {
		
	}
	
	public int compareTo(Card o) { //implement comparing for Collections.sort
		if (mode) {  //Sort by number, then suit
			if (number > o.number) {
				return 1;
			} else if (number == o.number) {
				if (Suit.compare(suit, o.suit)) {
					return 0;
				} else {
					return -1;
				}
			} else {
				return -2;
			}
		} else { //Sort by suit, then number
			if (Suit.compare(suit, o.suit)) {
				return 1;
			} else if (suit == o.suit) {
				if (number > o.number) {
					return 0;
				} else {
					return -1;
				}
			} else {
				return -2;
			}
		}
		
	}
}
