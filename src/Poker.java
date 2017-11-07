import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Poker {
	public static List<Card> cardsOnStack = new ArrayList<>(), cardsOnTable = new ArrayList<>();
	public static List<Player> players = new ArrayList<>();
	
	public static Player bigBlind, smallBlind;
	
	public static int curPlayer, curOrbit;
	
	public static boolean inRound = false;
	
	public static int getPot() {
		int sum = 0;
		for(Player p : players) {
			sum += p.betMoney;
		}
		return sum;
	}
	
	public static void setBlinds() {
		int rand = (int) Math.random()*players.size();
		int n2 = (rand == players.size()-1) ? 0 : rand+1 ;
		bigBlind = players.get(rand);
		smallBlind = players.get(n2);
	}
	
	public static void main(String[] args) {
		while(true) {
			MainMenu.displayMainMenu();
		}
	}
	
	public static void startGame() {
		prepareGame();
		
		Collections.sort(players); //TODO
	}
	public static void prepareGame() {
		
	}
	public static void round() {
		setBlinds();
		for (int i = curOrbit; i < 4 && inRound; i++) {
			orbit();
		}
		//Show everyone's cards in the output
		calculateWinners();
		for (int i = 0; i < players.size(); i++) {
			System.out.println();
		}
		resetRound();
	}
	public static void orbit() {
		
	}
	public static List<Runnable> getOptions(Player player) {
		
	}
	public static void resetRound() {
		cardsOnStack = new ArrayList<>();
		cardsOnTable = new ArrayList<>();
	
		for(int i = 1; i < 13; i++) {
			cardsOnStack.add(new Card(i, Suit.CLOVER));
			cardsOnStack.add(new Card(i, Suit.DIAMOND));
			cardsOnStack.add(new Card(i, Suit.HEART));
			cardsOnStack.add(new Card(i, Suit.SPADE));
		}
		
	}
	public static void resetGame() {
		resetRound();
		bigBlind = null;
		smallBlind = null;
		curOrbit = 0;
	}
	public static void calculateWinners() {
		
	}
	public static int calculateScore(List<Card> card) {
		boolean HC, P1, P2, K3, ST, FL, FH, K4, SF, RF;
		//Royal Flush
		
		//straight flush
		
		
	}
	public static void playerTurn(Player player, List<Runnable> options) {
		
	}
}
