/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Card Class: the object for the cards in play.
 */

public class Card implements Comparable<Card>{
	
	int number; //ace is one, and king is 12
	Suit suit; //suit enum

	static boolean mode = true;
	
	public Card(int number, Suit suit) {
		this.number = number;
		this.suit = suit;
	}

	public int compareTo(Card o) { //implement comparing for Collections.sort
		if (mode) { //Number
			if (number > o.number) {
				return 1;
			} else {
				return -1;
			}
		} else { //Suit
			if (Suit.compare(suit, o.suit)) {
				return 1;
			} else {
				return -1;
			}
			
		}
	}
}
