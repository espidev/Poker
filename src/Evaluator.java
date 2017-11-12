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
	
	public static void sortCards(List<Card> i, boolean m) {
		//True: Number Sort
		//False: Suit Sort
		Card.mode = m;
		Collections.sort(i);
	}
	
	public static void sortCards(List<Card> i) {
		Card.mode = true;
		Collections.sort(i);
		
		Card.mode = false;
		Collections.sort(i);
	}
	
	public static int[] finalEvaluate (List<Card> card) {
		List<Card> finalHand = card;
		
		int[] score = new int [2];
		score[0] = evaluateHand(card, finalHand);
		score[1] = evaluateScore(finalHand);
		
		return score;
	}
	
	public static int evaluateScore (List<Card> hand) {
		return 0;
	}
	
	public static int evaluateHand (List<Card> card, List<Card> hand) {
		List<Card> c = card;
		int pokerHand;
	
		//Royal Flush
		sortCards(c);
		
		for (int i = 0; i < c.size() - 4; i++) {
			if (c.get(i).number == 10) {
				int rf = 0;
				
				for (int j = i + 1; j < c.size(); j++) {
					if (c.get(j).number == (j + 9) && (c.get(j).suit == c.get(i).suit)){
						rf++;
					} else {
						break;
					}
				}
				
				if (rf == 4) {
					pokerHand = 10;
					hand = c.subList(i, i + 4);
					return pokerHand;
				} else {
					break;
				}
			}
		}
		
		
		//Straight Flush
		int sf = 0;

		for (int i = c.size() - 1; i > 0; i--) {
			if (c.get(i).number == c.get(i - 1).number + 1) {
				sf++;
			} else {
				sf = 0;
			}
			if (sf == 4) {
				pokerHand = 9;
				hand = c.subList(i, i + 4);
				return pokerHand;
			}
		}
		
		boolean sf_low = false;
		
		for (int i = 0; i < c.size(); i++) {
			if (c.get(i).number == 13) {
				sf_low = true;
				break;
			}
		}
		
		if (sf_low) {
			for (int i = c.size() - 1; i > 0; i--) {
				if (c.get(i).number == c.get(i - 1).number + 1) {
					sf++;
				} else {
					sf = 0;
				}
				if (sf == 4) {
					pokerHand = 9;
					hand = c.subList(i, i + 4);
					return pokerHand;
				}
			}
		}
		
		
		
		
		
		
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
}
