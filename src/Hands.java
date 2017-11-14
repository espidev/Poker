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

		for (int i = c.size() - 1; i > 3; i--) {
			if (c.get(i).suit == c.get(i - 4).suit && c.get(i).number == c.get(i - 4).number + 4) {
				h = c.subList(i-4, i);
				return true;
			}
		}

		//Checks if Special Case (A, 2, 3, 4, 5) is present
		List<Suit> isLow_Suits = new ArrayList<>();
		int num_of_Aces = 0;
		boolean isLow = false;

		for (int i = 0; i < c.size(); i++) {
			if (c.get(i).number == 13) {
				isLow_Suits.add(c.get(i).suit);
				num_of_Aces++;
				isLow = true;
			}
			if (num_of_Aces >= 4) {
				break;
			}
		}

		if (isLow) {
			Suit curSuit;

			for (int i = 0; i < isLow_Suits.size(); i++) {
				curSuit = isLow_Suits.get(i);

				for (int j = c.size() - 1; j > 2; j--) {
					if (c.get(i).suit == curSuit && c.get(i-3).suit == curSuit && c.get(i).number == 5 && c.get(i).number == 2) {
						Card ace = new Card (14, curSuit);
						h.add(ace);
						h = c.subList(i-3, i);
						return true;
					}
				}	
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
				if (c.get(c.size()-1).number != c.get(i).number) {
					h.add(c.get(c.size() - 1));
				} else {
					h.add(c.get(c.size() - 5));
				}
				return true;
			}
		}

		return false;
	}

	public static boolean FH (List<Card> c, List<Card> h) {
		if (c.size() < 5) {
			return false;
		}

		List<Card> Triple = new ArrayList<>();
		boolean isTriple = K3Triple(c, Triple);
		
		int num_of_K3;
		
		if (isTriple) {
			num_of_K3 = Triple.get(0).number;
			
			sortCards(c, true);

			for (int i = c.size() - 1; i > 0; i--) {
				if (c.get(i).number != num_of_K3) {
					if (c.get(i).number == c.get(i-1).number) {
						h.addAll(Triple);
						h.addAll(c.subList(i-1, i));
						return true;
					}
				}
			}
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
		
		sortCards(c, true);
		
		int highest = 0;
		int counter = 0;
		
		for (int i = c.size() - 1; i > 3; i--) {
			if (c.get(i).number == c.get(i - 1).number + 1) {
				counter++;
				try {
				h.add(c.get(i + highest));
				}
				catch(NullPointerException e) {
					//welp whatever
				}
			} else if (c.get(i).number == c.get(i - 1).number) {
				highest++;
			} else {
				counter = 0;
				h = new ArrayList<>();
			}
			if (counter == 4) {
				return true;
			}
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
				if (c.get(c.size()-1).number != c.get(i).number) {
					h.add(c.get(c.size() - 1));
				} else {
					h.add(c.get(c.size() - 4));
				}
				if (c.get(0).number != c.get(i).number) {
					h.add(c.get(0));
				} else {
					h.add(c.get(3));
				}
				return true;
			}
		}

		return false;
	}
	
	public static boolean K3Triple (List<Card> c, List<Card> h) {
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
		List<Card> firstPair = new ArrayList<>();
		int j = 0;

		for (int i = c.size() - 1; i > 0; i--) {
			if (c.get(i).number == c.get(i-1).number) {
				firstPair.add(c.get(i));
				firstPair.add(c.get(i-1));
				j = i-2;
				break;
			}
		}

		for (int i = j; i > 0; i--) {
			if (c.get(i).number == c.get(i-1).number) {
				h = c.subList(i-1, i);
				h.addAll(firstPair);
				for (int k = c.size() - 1; k >= 0; k--) {
					if (c.get(k).number != h.get(0).number && c.get(k).number != h.get(2).number) {
						h.add(c.get(k));
					}
				}
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
		List<Card> kickers = new ArrayList<>();

		for (int i = c.size() - 1; i > 0; i--) {
			if (c.get(i).number == c.get(i-1).number) {
				h = c.subList(i-1, i);
				for (int k = c.size() - 1; k >= 0; k--) {
					if (c.get(k).number != h.get(0).number) {
						kickers.add(c.get(k));
					}
					if (kickers.size() == 3) {
						break;
					}
				}
				h.addAll(kickers);
				return true;
			}
		}

		return false;
	}

	public static void HC (List<Card> c, List<Card> h) {
		sortCards(c, true);
		List<Card> kickers = new ArrayList<>();
		h.add(c.get(c.size() - 1));
		
		for (int k = c.size() - 1; k >= 0; k--) {
			if (c.get(k) != h.get(0)) {
				kickers.add(c.get(k));
			}
			if (kickers.size() == 4) {
				break;
			}
		}
		
		h.addAll(kickers);
	}

	public static void sortCards(List<Card> i, boolean m) {
		//True: Number, then Suit
		//False: Suit, then Number
		Card.mode = m;
		Collections.sort(i);
	}

}
