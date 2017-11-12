/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Card Class: Ah................RIP AI.
 */

public class Card implements Comparable<Card>{
	
	int number;
	Suit suit;
	
	public static boolean mode = true;
	
	public Card(int number, Suit suit) {
		this.number = number;
		this.suit = suit;
	}

	public int compareTo(Card o) {
		if (mode) { //Sort by number, then suit
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
