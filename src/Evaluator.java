import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * ADD A TODO HEADER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

public class Evaluator {
	
	public static boolean HC, P1, P2, K3, ST, FL, FH, K4, SF, RF;
	
	public static int[] calculateScore(List<Card> card) {
		
		/*
		 * Score Format: {a, b}
		 * A = Type of Poker Hand
		 * 	1 = High Card
		 * 	2 = One Pair
		 * 	3 = Two Pairs
		 * 	4 = Three of a Kind
		 * 	5 = Straight
		 * 	6 = Flush
		 * 	7 = Full House
		 * 	8 = 4 of a Kind
		 *  9 = Straight Flush
		 *  10 = Royal Flush
		 */
		
		List<Card> c = card;
		int[] score = new int[2];
		
		if (c.size() >= 5) {
			//Royal Flush
			sortCards(c, true);
			sortCards(c, false);
			for (int i = 0; i < c.size(); i++) {
				if (c.get(i).number == (i + 9) && (c.get(i).suit == c.get(0).suit)){
					score[0] = 10;
					break;
				}
			}	
			
			//Straight Flush
			for (int i = 0; i < c.size(); i++) {
				if (c.get(i).number == (i + 9)) {
					score[0] = 9;
				}
			}
			
		}
		
	}
	
	public static void sortCards(List<Card> i, boolean m) {
		Card.mode = m;
		Collections.sort(i);
	}
	public static void setScore(int[] s, int n1, int n2) {
		s[0] = n1;
		s[1] = n2;
	}
}
