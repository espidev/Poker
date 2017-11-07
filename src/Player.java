import java.util.List;

public class Player implements Comparable<Player>{
	List<Card> cards;
	int money, betMoney, orderOfDeath; //IMPLEMENT ORDEROFDEATH TODO
	String name;
	boolean isAI, stillInRound = true, stillInGame = true, allIn = false;
	
	public int compareTo(Player p) {
		
	}
}
