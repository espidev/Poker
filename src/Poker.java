import java.util.ArrayList;
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
		// TODO Auto-generated method stub
		
	}
	public static void prepareGame() {
		
	}
	public static void round() {
		
	}
	public static void orbit() {
		
	}
	public static List<Runnable> getOptions(Player player) {
		
	}
	public static void resetRound() {
		
	}
	public static void resetGame() {
		
	}
	public static void calculateWinners() {
		
	}
	public static void playerTurn(Player player, List<Runnable> options) {
		
	}
}
