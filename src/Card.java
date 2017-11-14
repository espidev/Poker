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
	 * https://en.wikipedia.org/wiki/Playing_cards_in_Unicode
	 * http://www.russellcottrell.com/greek/utilities/SurrogatePairCalculator.htm
	 * ughhhhhhhhhhhhhhhhhhhh
	 */

	public String getCard() {
		
		/*
		 * This code is bad, I know.
		 * Without using external APIs I can't assemble a unicode string literal so
		 * I have to resort to this pile of stuff..............
		 */
		
		if(suit.equals(Suit.SPADE)) {
			switch(number) {
			case 1: return "🂡";
			case 2: return "🂢";
			case 3: return "🂣";
			case 4: return "🂤";
			case 5: return "🂥";
			case 6: return "🂦";
			case 7: return "🂧";
			case 8: return "🂨";
			case 9: return "🂩";
			case 10: return "🂪";
			case 11: return "🂫";
			case 12: return "🂭";
			case 13: return "🂮";
			}
		}
		else if(suit.equals(Suit.HEART)) {
			switch(number) {
			case 1: return "🂱";
			case 2: return "🂲";
			case 3: return "🂳";
			case 4: return "🂴";
			case 5: return "🂵";
			case 6: return "🂶";
			case 7: return "🂷";
			case 8: return "🂸";
			case 9: return "🂹";
			case 10: return "🂺";
			case 11: return "🂻";
			case 12: return "🂽";
			case 13: return "🂾";
			}
		}
		else if(suit.equals(Suit.DIAMOND)) {
			switch(number) {
			case 1: return "\uD83C\uDCC1";
			case 2: return "\uD83C\uDCC2";
			case 3: return "\uD83C\uDCC3";
			case 4: return "\uD83C\uDCC4";
			case 5: return "\uD83C\uDCC5";
			case 6: return "\uD83C\uDCC6";
			case 7: return "\uD83C\uDCC7";
			case 8: return "\uD83C\uDCC8";
			case 9: return "\uD83C\uDCC9";
			case 10: return "\uD83C\uDCCA";
			case 11: return "\uD83C\uDCCB";
			case 12: return "\uD83C\uDCCD";
			case 13: return "\uD83C\uDCCE";
			}
		}
		else {
			switch(number) {
			case 1: return "\uD83C\uDCD1";
			case 2: return "\uD83C\uDCD2";
			case 3: return "\uD83C\uDCD3";
			case 4: return "\uD83C\uDCD4";
			case 5: return "\uD83C\uDCD5";
			case 6: return "\uD83C\uDCD6";
			case 7: return "\uD83C\uDCD7";
			case 8: return "\uD83C\uDCD8";
			case 9: return "\uD83C\uDCD9";
			case 10: return "\uD83C\uDCDA";
			case 11: return "\uD83C\uDCDB";
			case 12: return "\uD83C\uDCDD";
			case 13: return "\uD83C\uDCDE";
			}
		}
		return "";
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
