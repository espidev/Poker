import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Evaluator class: An evaluator to calculate the score of the poker hand
 */

public class Evaluator {
	
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
	
	public static boolean compareScores (int[] score01, int[] score02) {
		if (score01[0] > score02[0]) {
			return true;
		} else if (score01[0] == score02[0]) {
			if (score01[1] > score02[1]) {
				return true;
			} else if (score01[1] < score02[1]) {
				return false;
			} else {
				return false;  //Tie
			}
		} else {
			return false;
		}
	}
	
	public static int[] evaluateFinal (List<Card> card) {
		int[] score = new int[2];
		List<Card> hand = null;
		
		score[0] = evaluateHand(card, hand);		
		score[1] = evaluateScore(score[0], hand);
		
		return score;
	}
	
	public static int evaluateScore (int type, List<Card> hand) {
		return 0;
	}
	
	public static int evaluateHand (List<Card> card, List<Card> hand) {
		if (RF(card, hand)) {
			return 10;
		} else if (SF(card, hand)) {
			return 10;
		} else if (K4(card, hand)) {
			return 10;
		} else if (FH(card, hand)) {
			return 10;
		} else if (FL(card, hand)) {
			return 10;
		} else if (ST(card, hand)) {
			return 10;
		} else if (K3(card, hand)) {
			return 10;
		} else if (PP(card, hand)) {
			return 10;
		} else if (P1(card, hand)) {
			return 10;
		} else {
			HC(card, hand);
			return 1;
		}
	}
	
	public static boolean RF (List<Card> c, List<Card> h) {
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
		return false;
	}
	
	public static boolean K4 (List<Card> c, List<Card> h) {
		return false;
	}
	
	public static boolean FH (List<Card> c, List<Card> h) {
		return false;
	}
	
	public static boolean FL (List<Card> c, List<Card> h) {
		return false;
	}
	
	public static boolean ST (List<Card> c, List<Card> h) {
		return false;
	}
	
	public static boolean K3 (List<Card> c, List<Card> h) {
		return false;
	}
	
	public static boolean PP (List<Card> c, List<Card> h) {
		return false;
	}
	
	public static boolean P1 (List<Card> c, List<Card> h) {
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
