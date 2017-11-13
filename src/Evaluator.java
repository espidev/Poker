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
	
	public enum Hand {
		High_Card, One_Pair, Two_Pairs, Three_of_a_Kind, Straight, Flush, Full_House, Four_of_a_Kind, Straight_Flush, Royal_Flush;
	}

	public static Object comparePlayers(Player p1, Player p2) {
		List<Card> c1 = new ArrayList<>(), c2 = new ArrayList<>();
		c1.addAll(Poker.cardsOnTable);
		c1.addAll(p1.cards);
		c2.addAll(Poker.cardsOnTable);
		c2.addAll(p2.cards);
		if(compareHands(c1, c2) == 1) {
			return true;
		}
		else if(compareHands(c1, c2) == 0) {
			return false;
		}
		else {
			return null;
		}
	}
	
	/* Compares the scores of two different lists of cards.
	 * Returns: 
	 * 1 (card01 wins)
	 * 0 (card01 loses)
	 * -1 (tie)
	 */
	public static int compareHands (List<Card> card01, List<Card> card02) {
		return compareScores(evaluateFinal(card01), evaluateFinal(card02));
	}

	//Compares two scores in the format of [a, b]
	public static int compareScores (int[] score01, int[] score02) {
		if (score01[0] > score02[0]) {
			return 1;
		} else if (score01[0] == score02[0]) {
			if (score01[1] > score02[1]) {
				return 1;
			} else if (score01[1] < score02[1]) {
				return 0;
			} else {
				return -1;  //Tie!
			}
		} else {
			return 0;
		}
	}
	
	//Gets a score when a list of cards is inputed
	public static int[] evaluateFinal (List<Card> card) {
		int[] score = new int[2];
		List<Card> tempCard = new ArrayList<>();
		tempCard.addAll(card);
		List<Card> hand = null;
		
		score[0] = evaluateHand(tempCard, hand);		
		score[1] = evaluateScore(score[0], hand);
		
		return score;
	}
	
	//Figures out specific score of poker hand based on list of cards in case of two of the same poker hand
	public static int evaluateScore (int type, List<Card> hand) {
		int score = 0;
		
		switch (type) {
		case 10:
			score = 0;
			break;
		case 9:
			score = hand.get(4).number * 100;
			break;
		case 8:
			score = (hand.get(3).number * 100) + hand.get(4).number;
			break;
		case 7:
			score = (hand.get(2).number * 100) + hand.get(4).number;
			break;
		case 6:
			score = (hand.get(4).number * 100000000) + (hand.get(3).number * 1000000) + (hand.get(2).number * 10000) + (hand.get(1).number * 100) + hand.get(0).number;
			break;
		case 5:
			score = hand.get(4).number * 100;
			break;
		case 4:
			score = (hand.get(0).number * 10000) + (hand.get(3).number * 100) + hand.get(4).number;
			break;
		case 3:
			score = (hand.get(3).number * 10000) + (hand.get(0).number * 100) + hand.get(4).number;
			break;
		case 2:
			score = (hand.get(0).number * 1000000) + (hand.get(2).number * 10000) + (hand.get(3).number * 100) + hand.get(4).number;
			break;
		case 1:
			score = (hand.get(0).number * 100000000) + (hand.get(1).number * 1000000) + (hand.get(2).number * 10000) + (hand.get(3).number * 100) + hand.get(4).number;
		}
		return score;
	}
	
	//Figures out the poker hand in a list of cards
	public static int evaluateHand (List<Card> card, List<Card> hand) {
		if (Hands.RF(card, hand)) {
			return 10;
		} else if (Hands.SF(card, hand)) {
			return 9;
		} else if (Hands.K4(card, hand)) {
			return 8;
		} else if (Hands.FH(card, hand)) {
			return 7;
		} else if (Hands.FL(card, hand)) {
			return 6;
		} else if (Hands.ST(card, hand)) {
			return 5;
		} else if (Hands.K3(card, hand)) {
			return 4;
		} else if (Hands.PP(card, hand)) {
			return 3;
		} else if (Hands.P1(card, hand)) {
			return 2;
		} else {
			Hands.HC(card, hand);
			return 1;
		}
	}
}
