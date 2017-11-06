public class Actions {
	public static boolean check(Player player) {
		return true;
                System.out.println("you checked")
	}
	public static boolean bet(Player player, int money) {
                player.betMoney += money;
                player.money -= money;
                System.out.println("you bet"+money+"amount of money")
		return true;
	}
	public static boolean fold(Player player) {
		player.stillInRound = false;
		System.out.println(player.name + " has folded.");
		return true;
	}
	public static boolean call(Player player) {
		int max = 0;
		for (int i = 0; i < Poker.players.size(); i++) {
			if (i == 0) {
				max = Poker.players.get(i).betMoney;
			} else if (max < Poker.players.get(i).betMoney) {
				max = Poker.players.get(i).betMoney;
			}
		}
                System.out.println("you called")
		return true;
	}
	public static boolean raise(Player player, int money) {
                player.betMoney += money;
                player.money -= money;
                System.out.println("you bet"+money+"amount of money")
		return true;
	}
	public static boolean allIn(Player player) {
		player.betMoney = player.money;
		player.money = 0;
		player.allIn = true;
		System.out.println(player.name + " has gone all in.");
		return true;
	}
}
