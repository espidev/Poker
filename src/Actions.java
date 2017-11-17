/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Action Class: A list of actions that the AI and the 
 *               player can do.
 */

public class Actions {
	
	public static boolean check(Player player) {
		System.out.println("You've checked.");
		DisplayManager.globalConsole.add(player.name + " has checked.");
		return true;
	}
	
	public static boolean bet(Player player, int money) {
		player.betMoney += money;
		Poker.prevBet = player.betMoney;
		player.money = player.tempMoney - Poker.prevBet;
		Poker.orbitEnd = Poker.players.indexOf(player);
		System.out.println("You have bet $" + Poker.prevBet + ".");
		DisplayManager.globalConsole.add(player.name + " has bet $" + Poker.prevBet + ".");
		return true;
	}
	
	public static boolean fold(Player player) {
		player.stillInRound = false;
		System.out.println(player.name + " has folded.");
		DisplayManager.globalConsole.add(player.name + " has folded.");
		
		//check if only one person is left
		int folded = 0;
		for(Player p : Poker.players) {
			if(!p.stillInRound) folded++;
		}
		if(folded >= Poker.players.size()-1) {
			Poker.inRound = false; //end game since one person won.
			System.out.println("There is only one player left. Automatic win.");
			
			for(Player p : Poker.players) { //Move money to pot.
				Poker.pot += p.betMoney;
				p.betMoney = 0;
			}
			
			Poker.sleep(2000);
		}
		
		return true;
	}
	
	public static boolean call(Player player) {
		player.money = player.money + player.betMoney - Poker.prevBet;
		player.betMoney = Poker.prevBet;
		System.out.println("You've called. ($" + player.betMoney + ")");
		DisplayManager.globalConsole.add(player.name + " has called. ($" + player.betMoney + ")");
		return true;
	}
	
	public static boolean raise(Player player, int money) {
		player.betMoney = Poker.prevBet + money;
		Poker.prevBet = player.betMoney;
		player.money = player.tempMoney - Poker.prevBet;
		Poker.orbitEnd = Poker.players.indexOf(player);
		System.out.println("You've raised the bet to $" + Poker.prevBet + ".");
		DisplayManager.globalConsole.add(player.name + " has raised the bet to $" + Poker.prevBet + ".");
		return true;
	}
	
	public static boolean allIn(Player player) {
		player.betMoney = player.money;
		player.money = 0;
		player.allIn = true;
		if(player.betMoney > Poker.prevBet) {
			Poker.prevBet = player.betMoney;
			Poker.orbitEnd = Poker.players.indexOf(player);
		}
		System.out.println(player.name + " has gone all in.");
		DisplayManager.globalConsole.add(player.name + " has gone all in. ($" + player.betMoney + ")");
		return true;
	}
}
