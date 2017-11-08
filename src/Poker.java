import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Poker {
	public static List<Card> cardsOnStack = new ArrayList<>(), cardsOnTable = new ArrayList<>();
	public static List<Player> players = new ArrayList<>();
	
	public static Scanner scan = new Scanner(System.in);
	
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
		int numOfPlayers;
		while(true) {
			try {
				System.out.println("How many players will there be in the game?");
				numOfPlayers = scan.nextInt();
				if(numOfPlayers < 2) {
					System.out.println("You must have 2 or more people to play.");
					continue;
				}
				if(numOfPlayers > 10) {
					System.out.println("Too many people! (10 max)");
					continue;
				}
				System.out.println(numOfPlayers + " people? Nice!");
				break;
			}
			catch(Exception e) {
				System.out.println("Error. Try again.");
			}
		}
		while(true) {
			System.out.println("Should their be a default starting balance?");
			
		}
	}
	public static void round() {
		setBlinds();
		for (; curOrbit < 4 && inRound; curOrbit++) {
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
		boolean exit = false;
		int a; //blind
		while(!exit) {
			
		}
	}
	public static List<Runnable> getOptions(Player player) {
		
	}
	public static void resetRound() {
		curOrbit = 0;
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
	public static void playerTurn(Player player, List<Runnable> options) {
		
	}
}
