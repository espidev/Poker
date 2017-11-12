import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Hands class: To find which poker hand a certain list of cards comprises of
 */

public class Hands {

	/*
	 * 	1 = High Card / HC
	 * 	2 = One Pair / P1
	 * 	3 = Two Pairs / PP
	 * 	4 = Three of a Kind / K3
	 * 	5 = Straight / ST
	 * 	6 = Flush / FL
	 * 	7 = Full House / FH
	 * 	8 = 4 of a Kind / K4
	 *  9 = Straight Flush / SF
	 *  10 = Royal Flush / RF
	 */
	
	public static boolean RF (List<Card> c, List<Card> h) {
		if (c.size() < 5) {
			return false;
		}
		
		sortCards (c, false);
		
		for (int i = c.size() - 1; i > 3; i--) {
			if (c.get(i).suit == c.get(i - 4).suit && c.get(i).number == 14 && c.get(i - 4).number == 10) {
				h = c.subList(i-4, i);
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean SF (List<Card> c, List<Card> h) {
		if (c.size() < 5) {
			return false;
		}

		sortCards (c, false);
		List<Card> temp = null;
		
		for (int i = c.size() - 1; i > 3; i--) {
			if (c.get(i).suit == c.get(i - 4).suit) {
				
			}
		}
		
		return false;
	}
	
	public static boolean K4 (List<Card> c, List<Card> h) {
		if (c.size() < 4) {
			return false;
		}
		
		sortCards(c, true);
		
		for (int i = c.size() - 1; i > 2; i--) {
			if (c.get(i).number == c.get(i-3).number) {
				h = c.subList(i-3, i);
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean FH (List<Card> c, List<Card> h) {
		if (c.size() < 5) {
			return false;
		}
		
		return false;
	}
	
	public static boolean FL (List<Card> c, List<Card> h) {
		if (c.size() < 5) {
			return false;
		}
		
		sortCards(c, false);
		
		for (int i = c.size() - 1; i > 3; i--) {
			if (c.get(i).suit == c.get(i - 4).suit) {
				h = c.subList(i-4, i);
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean ST (List<Card> c, List<Card> h) {
		if (c.size() < 5) {
			return false;
		}
		
		return false;
	}
	
	public static boolean K3 (List<Card> c, List<Card> h) {
		if (c.size() < 3) {
			return false;
		}
		
		sortCards(c, true);
		
		for (int i = c.size() - 1; i > 1; i--) {
			if (c.get(i).number == c.get(i-2).number) {
				h = c.subList(i-2, i);
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean PP (List<Card> c, List<Card> h) {
		if (c.size() < 4) {
			return false;
		}
		
		sortCards(c, true);
		List<Card> firstPair = null;
		int counter = 0;
		int j = 0;
		
		for (int i = c.size() - 1; i > 0; i--) {
			if (c.get(i).number == c.get(i-1).number) {
				firstPair.add(c.get(i));
				firstPair.add(c.get(i-1));
				counter++;
				j = i-2;
				break;
			}
		}
		
		for (int i = j; i > 0; i--) {
			if (c.get(i).number == c.get(i-1).number) {
				h = c.subList(i-1, i);
				h.addAll(firstPair);
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean P1 (List<Card> c, List<Card> h) {
		if (c.size() < 2) {
			return false;
		}
		
		sortCards(c, true);
		
		for (int i = c.size() - 1; i > 0; i--) {
			if (c.get(i).number == c.get(i-1).number) {
				h = c.subList(i-1, i);
				return true;
			}
		}
		
		return false;
	}
	
	public static void HC (List<Card> c, List<Card> h) {
		sortCards(c, true);
		h.add(c.get(c.size() - 1));
	}
	
	public static void sortCards(List<Card> i, boolean m) {
		//True: Number, then Suit
		//False: Suit, then Number
		Card.mode = m;
		Collections.sort(i);
	}
	
}
