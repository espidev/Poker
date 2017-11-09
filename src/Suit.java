
/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Suit enum: An enum to stor the suits of the cards.
 */


public enum Suit {
	
	DIAMOND, CLOVER, HEART, SPADE;
	
	static boolean compare(Suit suit1, Suit suit2) {
		if(suit1.ordinal() > suit2.ordinal()) {
			return true;
		}
		else {
			return false;
		}
	}
}
