import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Hands class: To find which poker hand a certain list of cards comprises of and return the hand in which it is located
 */

public class Hands {

	/* Hands ranked from least to greatest:
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

	/*
	 * All of the following checker methods also return a 5-card hand
	 */
	
	//Checks for a royal flush
	public static List<Object> RF (List<Card> c) {
		List<Card> h = new ArrayList<>();

		if (c.size() < 5) {
			return Arrays.asList(false, h);
		}

		sortCards (c, false);

		for (int i = c.size() - 1; i > 3; i--) {
			if (c.get(i).suit == c.get(i - 4).suit && c.get(i).number == 14 && c.get(i - 4).number == 10) {
				h = new ArrayList<>();
				for(int j = i-4; j <= i; j++) {
					h.add(c.get(j));
				}
				return Arrays.asList(true, h);
			}
		}

		return Arrays.asList(false, h);
	}

	//Checks for a straight flush
	public static List<Object> SF (List<Card> c) {
		List<Card> h = new ArrayList<>();
		if (c.size() < 5) {
			return Arrays.asList(false, h);
		}

		sortCards (c, false);

		for (int i = c.size() - 1; i > 3; i--) {
			if (c.get(i).suit == c.get(i - 4).suit && c.get(i).number == c.get(i - 4).number + 4) {
				for(int j = i-4; j <= i; j++) {
					h.add(c.get(j));
				}
				return Arrays.asList(true, h);
			}
		}

		//Checks for the low straight flush (A, 2, 3, 4, 5)
		List<Card> temp = new ArrayList<>();
		Card ace = new Card (14, c.get(c.size() - 1).suit);
		boolean[] hasLow = new boolean[5];
		Arrays.fill(hasLow, false);
		int low = 0;
		h.clear();

		List<Suit> isLow_Suits = new ArrayList<>();
		int num_of_Aces = 0;

		for (int i = 0; i < c.size(); i++) {
			if (c.get(i).number == 14) {
				isLow_Suits.add(c.get(i).suit);
				num_of_Aces++;
			}
			if (num_of_Aces >= 4) {
				break;
			}
		}

		for (Suit z : isLow_Suits) {
			for (int i = c.size() - 1; i >= 0; i--) {
				if (c.get(i).number == 14 && (!hasLow[0]) && c.get(i).suit == z) {
					low++;
					hasLow[0] = true;
					ace = c.get(i);
				} else if ((c.get(i).number == 2 && (!hasLow[1])) && c.get(i).suit == z) {
					low++;
					hasLow[1] = true;
					temp.add(c.get(i));
				} else if ((c.get(i).number == 3 && (!hasLow[2])) && c.get(i).suit == z) {
					low++;
					hasLow[2] = true;
					temp.add(c.get(i));
				} else if ((c.get(i).number == 4 && (!hasLow[3])) && c.get(i).suit == z) {
					low++;
					hasLow[3] = true;
					temp.add(c.get(i));
				} else if ((c.get(i).number == 5 && (!hasLow[4])) && c.get(i).suit == z) {
					low++;
					hasLow[4] = true;
					temp.add(c.get(i));
				}
				if(low == 5) {
					sortCards(temp, true);
					h.add(ace);
					h.addAll(temp);

					return Arrays.asList(true, h); 
				}
			}
		}

		return Arrays.asList(false, h);
	}

	//Checks for a 4 of a Kind
	public static List<Object> K4 (List<Card> c) {
		List<Card> h = new ArrayList<>();
		if (c.size() < 4) {
			return Arrays.asList(false, h);
		}

		sortCards(c, true);

		for (int i = c.size() - 1; i > 2; i--) {
			if (c.get(i).number == c.get(i-3).number) {
				for(int p = i-3; p <= i; p++) {
					h.add(c.get(p));
				}
				if (c.get(c.size()-1).number != c.get(i).number) {
					h.add(c.get(c.size() - 1));
				} else {
					h.add(c.get(c.size() - 5));
				}
				return Arrays.asList(true, h);
			}
		}

		return Arrays.asList(false, h);
	}

	//Checks for a Full House
	public static List<Object> FH (List<Card> c) {
		List<Card> h = new ArrayList<>();
		if (c.size() < 5) {
			return Arrays.asList(false, h);
		}

		List<Card> Triple = new ArrayList<>();
		boolean isTriple = false;
		int num_of_K3;

		for (int i = c.size() - 1; i > 1; i--) {
			if (c.get(i).number == c.get(i-2).number) {
				for(int p = i-2; p <= i; p++) {
					Triple.add(c.get(p));
				}
				isTriple = true;

			}
		}
		if (isTriple) {
			num_of_K3 = Triple.get(0).number;

			sortCards(c, true);

			for (int i = c.size() - 1; i > 0; i--) {
				if (c.get(i).number != num_of_K3) {
					if (c.get(i).number == c.get(i-1).number) {
						h.addAll(Triple);
						for(int p = i-1; p <= i; p++) {
							h.add(c.get(p));
						}
						return Arrays.asList(true, h);
					}
				}
			}
		}

		return Arrays.asList(false, h);
	}

	//Checks for a flush
	public static List<Object> FL (List<Card> c) {
		List<Card> h = new ArrayList<>();
		if (c.size() < 5) {
			return Arrays.asList(false, h);
		}

		sortCards(c, false);

		for (int i = c.size() - 1; i > 3; i--) {
			if (c.get(i).suit == c.get(i - 4).suit) {
				for(int p = i-4; p <= i; p++) {
					h.add(c.get(p));
				}
				return Arrays.asList(true, h);
			}
		}

		return Arrays.asList(false, h);
	}

	//Checks for a straight
	public static List<Object> ST (List<Card> c) {
		List<Card> h = new ArrayList<>();
		if (c.size() < 5) {
			return Arrays.asList(false, h);
		}

		sortCards(c, true);

		int counter = 0;
		int highest = 0;

		for (int i = c.size() - 1; i > 0; i--) {
			if (c.get(i).number == c.get(i - 1).number + 1) {
				counter++;
				h.add(c.get(i + highest));
				highest = 0;
			} else if (c.get(i).number == c.get(i - 1).number) {
				highest++;
			} else {
				counter = 0;
				h = new ArrayList<>();
			}
			if (counter == 4) {
				h.add(c.get(i-1));
				Collections.reverse(h);
				return Arrays.asList(true, h);
			}
		}

		
		//Checks for low straight (A, 2, 3, 4, 5)
		List<Card> temp = new ArrayList<>();
		Card ace = new Card (14, c.get(c.size() - 1).suit);
		boolean[] hasLow = new boolean[5];
		Arrays.fill(hasLow, false);
		int low = 0;
		h.clear();

		for (int i = c.size() - 1; i >= 0; i--) {
			if (c.get(i).number == 14 && (!hasLow[0])) {
				low++;
				hasLow[0] = true;
				ace = c.get(i);
			} else if ((c.get(i).number == 2 && (!hasLow[1]))) {
				low++;
				hasLow[1] = true;
				temp.add(c.get(i));
			} else if ((c.get(i).number == 3 && (!hasLow[2]))) {
				low++;
				hasLow[2] = true;
				temp.add(c.get(i));
			} else if ((c.get(i).number == 4 && (!hasLow[3]))) {
				low++;
				hasLow[3] = true;
				temp.add(c.get(i));
			} else if ((c.get(i).number == 5 && (!hasLow[4]))) {
				low++;
				hasLow[4] = true;
				temp.add(c.get(i));
			}
			if(low == 5) {
				sortCards(temp, true);
				h.add(ace);
				h.addAll(temp);

				return Arrays.asList(true, h); 
			}
		}

		return Arrays.asList(false, h);
	}

	//Checks for a 3 of a Kind
	public static List<Object> K3 (List<Card> c) {
		List<Card> h = new ArrayList<>();
		if (c.size() < 3) {
			return Arrays.asList(false, h);
		}

		sortCards(c, true);

		for (int i = c.size() - 1; i > 1; i--) {
			if (c.get(i).number == c.get(i-2).number) {
				for(int p = i-2; p <= i; p++) {
					h.add(c.get(p));
				}
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
				return Arrays.asList(true, h);
			}
		}

		return Arrays.asList(false, h);
	}

	//Checks for a two pairs
	public static List<Object> PP (List<Card> c) {
		List<Card> h = new ArrayList<>();
		if (c.size() < 4) {
			return Arrays.asList(false, h);
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
				for(int p = i-1; p <= i; p++) {
					h.add(c.get(p));
				}
				h.addAll(firstPair);
				for (int k = c.size() - 1; k >= 0; k--) {
					if (c.get(k).number != h.get(0).number && c.get(k).number != h.get(2).number) {
						h.add(c.get(k));
						break;
					}
				}
				return Arrays.asList(true, h);
			}
		}

		return Arrays.asList(false, h);
	}

	//Checks for one pair
	public static List<Object> P1 (List<Card> c) {
		List<Card> h = new ArrayList<>();
		if (c.size() < 2) {
			return Arrays.asList(false, h);
		}

		sortCards(c, true);
		List<Card> kickers = new ArrayList<>();

		for (int i = c.size() - 1; i > 0; i--) {
			if (c.get(i).number == c.get(i-1).number) {
				h.add(c.get(i-1));
				h.add(c.get(i));
				for (int k = c.size() - 1; k >= 0; k--) {
					if (c.get(k).number != h.get(0).number) {
						kickers.add(c.get(k));
					}
					if (kickers.size() == 3) {
						break;
					}
				}
				h.addAll(kickers);
				return Arrays.asList(true, h);
			}
		}

		return Arrays.asList(false, h);
	}

	//Method that returns a hand with a high card (if all other hands are not present)
	public static List<Card> HC (List<Card> c) {
		List<Card> h = new ArrayList<>();
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
		return h;
	}

	//Helper method to sort cards
	public static void sortCards(List<Card> i, boolean m) {
		//True: Number, then Suit
		//False: Suit, then Number
		Card.mode = m;
		Collections.sort(i);
	}

}
