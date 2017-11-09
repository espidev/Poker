/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Action Class: A list of action that the AI and the 
 *               player can do
 */

public class Actions {
	
	public static boolean check(Player player) {
		System.out.println("You've checked.");
		DisplayManager.globalConsole.add(player.name + " has checked.");
		return true;
	}
	
	public static boolean bet(Player player, int money) {
		player.betMoney += money;
		player.money -= money;
		System.out.println("You have bet $" + money + ".");
		return true;
	}
	
	public static boolean fold(Player player) {
		player.stillInRound = false;
		System.out.println(player.name + " has folded.");
		return true;
	}
	
	public static boolean call(Player player) {
		player.betMoney = Poker.prevBet;
		System.out.println("You've called. ($" + player.betMoney + ")");
		DisplayManager.globalConsole.add(player.name + " has called. ($" + player.betMoney + ")");
		return true;
	}
	
	public static boolean raise(Player player, int money) {
		player.betMoney += money;
		player.money -= money;
		System.out.println("you bet"+money+"amount of money");
		return true;
	}
	
	public static boolean allIn(Player player) {
		player.betMoney = player.money;
		player.allIn = true;
		if(player.money > Poker.prevBet) {
			Poker.prevBet = player.money;
		}
		System.out.println(player.name + " has gone all in.");
		DisplayManager.globalConsole.add(player.name + " has gone all in. ($" + player.money + ")");
		return true;
	}
}
