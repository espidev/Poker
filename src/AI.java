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
		options.remove("Fold");
		DisplayManager.wipeConsole();
		System.out.println("Calculating the AI's turn...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int rand = (int) (Math.random() * options.size());
		new ArrayList<>(options.values()).get(rand).run(player);
	}
}
