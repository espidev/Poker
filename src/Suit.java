
/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Suit enum: An enum to store the suits of the cards.
 */


public enum Suit {

	DIAMOND, CLOVER, HEART, SPADE;

	public static String getSuit(Suit suit) {
		switch(suit) {
		case DIAMOND: return "♦";
		case CLOVER: return "♣";
		case HEART: return "♥";
		case SPADE: return "♠";
		}
		return "";
	}

	public static boolean compare(Suit suit1, Suit suit2) {
		if(suit1.ordinal() > suit2.ordinal()) {
			return true;
		}
		else {
			return false;
		}
	}
}
