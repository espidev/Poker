import java.util.List;

/*
 * ADD A TODO HEADER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

public class Player implements Comparable<Player>{
	
	List<Card> cards;
	int money, betMoney = 0, orderOfDeath;
	String name;
	boolean isAI, stillInRound = true, stillInGame = true, allIn = false;
	
	public Player(String name, int money, boolean isAI) {
		this.name = name;
		this.money = money;
		this.isAI = isAI;
	}
	
	public int compareTo(Player p) {
		if (stillInGame) {
			if(p.stillInGame) {
				if (money < p.money) {
					return 1;
				} else {
					return -1;
				}
			}
			else {
				return 1;
			}
		}
		else {
			if(p.stillInGame) {
				return -1;
			}
			else {
				if (orderOfDeath > p.orderOfDeath) {
					return 1;
				} else {
					return -1;
				}
				
				//Order of deaths
			}
		}
	}
}
