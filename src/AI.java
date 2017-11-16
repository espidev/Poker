import java.util.ArrayList;
import java.util.HashMap;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * AI Class: A basic AI to choose a random option in the actions class
 */

public class AI {
	public static void calculateTurn(Player player, HashMap<String, BooleanOperation> options) {
		options.remove("Reveal Cards");
		
		if(((int)(Math.random() * 100)) > 5) { 
			options.remove("Fold");
		}
		
		if((((int)(Math.random() * 100)) > 10) && options.size() > 2) { 
			options.remove("All-In");
		}
		if(Poker.curOrbit == 0) {
			options.remove("All-In");
		}
		
		if(player.money < 6) {
			options.remove("Raise");
		}
		
		if(((int)(Math.random() * 100)) > 30) { 
			options.remove("Raise");
		}
		
		DisplayManager.wipeConsole();
		System.out.println("Calculating the AI's turn...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int rand = (int) (Math.random() * options.size());
		new ArrayList<>(options.values()).get(rand).run(player);
	}
}
