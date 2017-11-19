import java.util.ArrayList;
import java.util.List;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Player class: A class to generate a player object
 */

public class Player implements Comparable<Player>{
	
	//All the variables required for a player
	List<Card> cards = new ArrayList<>();
	int money, tempMoney, betMoney = 0, orderOfDeath;
	String name;
	boolean isAI, stillInRound = true, stillInGame = true, allIn = false;
	
	public Player(String name, int money, boolean isAI) {
		this.name = name;
		this.money = money;
		this.tempMoney = money;
		this.isAI = isAI;
	}
	
	//Implements sorting of players based on money as well as whether or not they are still in game
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
