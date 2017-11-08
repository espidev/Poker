public class Card implements Comparable<Card>{
	int number;
	Suit suit;

	static boolean mode = true;
	
	public Card(int number, Suit suit) {
		this.number = number;
		this.suit = suit;
	}

	public int compareTo(Card o) {
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
