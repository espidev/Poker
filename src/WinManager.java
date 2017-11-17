import java.util.ArrayList;
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
			System.out.println("——————————————————————————————————————————————————————————");
			System.out.println("| Player " + p.name);
			System.out.print("Cards (including on table): ");

			List<Card> cards = new ArrayList<>();
			List<Card> tempHand = new ArrayList<>();
			for(Card c : cardsOnTable) {
				cards.add(c);
			}
			String assemble = "| ";
			for(Card c : p.cards) {
				assemble += c.getCard();
				cards.add(c);
			}
			System.out.println(assemble);

			for(Card c : p.cards) {
				System.out.print(c.getCardOutput() + " " + Suit.getSuit(c.suit) + "  ");
			}

			for(Card c : cardsOnTable) {
				System.out.print(c.getCardOutput() + " " + Suit.getSuit(c.suit) + "  ");
			}

			//evaluate hand
			int f = (Integer) Evaluator.evaluateHand(cards, tempHand).get(0);
			tempHand = (List<Card>) Evaluator.evaluateHand(cards, tempHand).get(1);
			Evaluator.Hand hand = null;
			for(Evaluator.Hand h : Evaluator.Hand.values()) {
				if(h.ordinal()+1 == f) {
					hand = h;
				}
			}

			System.out.println();
			System.out.println("——————————————————————————————————————————————————————————");
			System.out.print("Hand: " + hand.toString().replaceAll("_", " ") + "  ");
			for(Card z : tempHand) {
				System.out.print(z.getCardOutput() + " " + Suit.getSuit(z.suit) + "  ");
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
		Player m = null;

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

			m = parray.get(0);

			System.out.println("Winners in order:");
			for(Player p : parray) {
				System.out.println(p.name);
				p.tempMoney = p.money;
			}
			//TODO
			win(m);
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
}
