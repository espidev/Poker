import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * WinManager class: Handle when a round ends.
 */

public class WinManager extends Poker{

	/*
	 * Displays the players's cards in the console.
	 */

	public static void displayPlayerCards() {
		for(Player p : players) {
			if(!p.stillInGame) continue;
			System.out.println("——————————————————————————————————————————————————————————");
			System.out.println("| Player " + p.name);
			System.out.print("| Cards (including on table): ");

			List<Card> cards = new ArrayList<>();
			List<Card> tempHand = new ArrayList<>();
			String assemble = "| ";
			
			for(Card c : p.cards) {
				cards.add(c);
			}
			for(Card c : cardsOnTable) {
				cards.add(c);
			}
			
			Hands.sortCards(cards, true); //Sort the cards into order
			for(Card c : cards) {
				assemble += c.getCard();
			}
			System.out.print(assemble + "    ");

			for(Card c : cards) {
				System.out.print(c.getCardOutput() + " " + Suit.getSuit(c.suit) + "  ");
			}

			//evaluate hand
			int f = Evaluator.evaluateHand(cards);
			tempHand = Evaluator.getHand(cards);
			Evaluator.Hand hand = null;
			String assemble2 = "| ";
			for(Evaluator.Hand h : Evaluator.Hand.values()) {
				if(h.ordinal()+1 == f) {
					hand = h;
				}
			}

			System.out.println();
			System.out.print("| Hand: " + hand.toString().replaceAll("_", " ") + "  ");
			for(Card c : tempHand) {
				assemble2 += c.getCard();
			}
			System.out.print(assemble2 + "    ");
			
			for (Card c : tempHand) {
				System.out.print(c.getCardOutput() + " " + Suit.getSuit(c.suit) + "  ");
			}
			System.out.println();
			System.out.println("——————————————————————————————————————————————————————————");
			System.out.println("\n\n");
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Executed once the round is over.
	 */

	public static void calculateWinners() {

		ArrayList<Player> parray = new ArrayList<>();
		parray.addAll(players);

		for(int i = 0; i < parray.size(); i++) {
			if(!parray.get(i).stillInRound) {
				parray.remove(i);
			}
			else if(!parray.get(i).stillInGame) {
				parray.remove(i);
			}
		}
		
		//Bubble sort the players by their "hand".

		try {
			for(int i = 1; i < parray.size(); i++) {
				for(int j = 0; j < parray.size()-i; j++) {
					if(Evaluator.comparePlayers(parray.get(j), parray.get(j+1)) == (Boolean)false) {
						Player t = parray.get(j);
						parray.set(j, parray.get(j+1));
						parray.set(j+1, t);
					}
				}
			}
			
			System.out.println("Players in order:");
			for(Player p : parray) {
				System.out.println(p.name);
				p.tempMoney = p.money;
			}
			
			boolean tie = false;
			ArrayList<Player> tied = new ArrayList<>();
			tied.add(parray.get(0));
			
			for (int i = 1; i < parray.size(); i++) {
				if (Evaluator.comparePlayers(parray.get(0), parray.get(i)) == null) {
					tie = true;
					tied.add(parray.get(i));
				} else {
					break;
				}
			}
			
			if (tie) {
				win(tied);
			} else {
				Player m = parray.get(0);
				win(m);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Handle when a player wins a round.
	 */

	public static void win(Player p) {
		System.out.println("And the winner of this round is...");
		for(int i = 0; i < 3; i++) {
			sleep(1000);
			System.out.println(".");
		}
		sleep(1000);
		System.out.println(p.name + "!");
		p.money += pot;
		p.tempMoney = p.money;
		sleep(500);
		System.out.println("This player has won $" + pot + "!");
	}
	
	//Handles the cases in which a tie is detected
	public static void win(ArrayList<Player> p) {
		System.out.println("And the winners of this round are...");
		for(int i = 0; i < 3; i++) {
			sleep(1000);
			System.out.println(".");
		}
		sleep(1000);
		
		for (int i = 0; i < p.size(); i++) {
			if (i == p.size() - 1) {
				System.out.print("and " + p.get(i).name + "!");
			} else {
				System.out.print(p.get(i).name + ", ");
			}
		}
		System.out.println();

		for (Player z : p) {
			z.money += pot/p.size();
			z.tempMoney = z.money;
		}
		sleep(500);
		System.out.println("These players have each won $" + (pot/p.size()) + "!");
	}
}
