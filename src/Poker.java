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
}
