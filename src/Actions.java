public class Actions {
	public static boolean check(Player player) {
		int max = 0;
		for (int i = 0; i < Poker.players.size(); i++) {
			if (i == 0) {
				max = Poker.players.get(i).betMoney;
			} else if (max < Poker.players.get(i).betMoney) {
				max = Poker.players.get(i).betMoney;
			}
		}
		
		if (max - player.betMoney < player.money) {
			return true;	
		} else {
			return false;
		}
	}
	public static boolean bet(Player player, int money) {
		return true;
	}
	public static boolean fold(Player player) {
		return true;
	}
	public static boolean call(Player player) {
		return true;
	}
	public static boolean raise(Player player, int money) {
		return true;
	}
	public static boolean allIn(Player player) {
		return true;
	}
}
