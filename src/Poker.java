import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Poker {
	public static List<Card> cardsOnStack = new ArrayList<>(), cardsOnTable = new ArrayList<>();
	public static List<Player> players = new ArrayList<>();
	
	public static Player bigBlind, smallBlind;
	
	public static int curPlayer, curOrbit;
	
	public static boolean inRound = false;
	
	public static int getPot() {
		int sum = 0;
		for(Player p : players) {
			
		}
	}
	
	public static void main(String[] args) {
		while(true) {
			MainMenu.displayMainMenu();
		}
	}
}
