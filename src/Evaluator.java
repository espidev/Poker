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
	
		//Royal Flush
		sortCards(c, true);
		sortCards(c, false);

		for (int i = 0; i < c.size() - 4; i++) {
			if (c.get(i).number == 10) {
				for (int j = i; j < c.size(); j++) {
					if (c.get(j).number == (j + 9) && (c.get(j).suit == c.get(0).suit)){
						score[0] = 10;
						return score;
					}
				}
				break;
			}
		}
		
		//Straight Flush
		int sf;
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
		int[] w_case = new int[5];
		for (int i = 0; i < c.size(); i++) {
			if (c.get(i).number == 13){
				w_case[0]++;
			} else if (c.get(i).number == 1){
				w_case[1]++;
			} else if (c.get(i).number == 2){
				w_case[2]++;
			} else if (c.get(i).number == 3){
				w_case[3]++;
			} else if (c.get(i).number == 4){
				w_case[4]++;
			}
			int matcher = 1;
			for(int i = 0; i < 5 ;i++)
			{
				if(int[i] == 0)
				{
					matcher = 0;
				}
			}
			if(matcher==1)
			{
				score[0] =9;
				return score;
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
