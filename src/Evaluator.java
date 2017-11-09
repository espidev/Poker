import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Evaluator class: An evaluator to calculate the score of the pokerhand
 */

public class Evaluator {
	
	public static boolean HC, P1, P2, K3, ST, FL, FH, K4, SF, RF;
	
	public static int[] calculateScore (List<Card> card) {
		return 0;
	}
	
	public static int[] evaluate(List<Card> card) {
		
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
			
			
			
			int rf_s;
			for (int i = 0; i < c.size() - 4; i++) {
				if (c.get(i).number == 10) {
					rf_s = i;
				}
			}
			
			
			
			for (int i = 0; i < c.size(); i++) {
				if (c.get(i).number == (i + 9) && (c.get(i).suit == c.get(0).suit)){
					score[0] = 10;
					return score;
				}
			}	
			
			//Straight Flush
			sortCards(c, true);
			sortCards(c, false);
			int sf = 0;
			for (int i = 0; i < c.size(); i++) {
				if (c.get(i).number + 1 == c.get(i+1).number) {
					sf++;
				} else if (c.get(i).number != c.get(i+1).number) {
				    sf = 0;	
				}
				if(sf == 4) {
					score[0] = 9;
					return score;
				}
			}
			//Four of a Kind
			sortCards(c, true);
			int fk = 0;
			for (int i = 0; i <= c.size() - 5; i++) {
				for (int j = i; j < i + 4; j++) {
					if (c.get(j).number == c.get(j+1).number) {
						fk++;
					} else {
						fk = 0;
					}
					if (fk == 3) {
						
					}
				}
			}
			
			
		}
		
	}
	
	public static void sortCards(List<Card> i, boolean m) {
		//True: Number Sort
		//False: Suit Sort
		Card.mode = m;
		Collections.sort(i);
	}
}
