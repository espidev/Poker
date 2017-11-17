import java.util.ArrayList;
import java.util.HashMap;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * AI Class: A basic AI to choose a random option in the actions class
 */

public class AI {
	
	/*
	 * Calculate the AI's turn.
	 * Extremely basic random options because of lack of time to implement a better thing...
	 */
	
	public static void calculateTurn(Player player, HashMap<String, BooleanOperation> options) {
		options.remove("Show Cards"); //The AI doesn't need to see its cards.
		
		if(options.get("Call") == null && options.get("All-In") != null && options.get("Bet") == null) { //If the AI can only choose between going all-in or folding or checking...
			if(((int)(Math.random() * 100)) > 50) {  //50% chance of folding being in the list of options
				options.remove("Fold");
			}
			options.remove("Raise");
		}
		else {
			if(Poker.curOrbit == 0) { //If the cards on the table haven't been shown yet, don't go all in
				options.remove("All-In");
			}
			if(((int)(Math.random() * 100)) > 5) {  //5% chance of folding being in the list of options
				options.remove("Fold");
			}
			if((((int)(Math.random() * 100)) > 10) && options.size() > 2) { //10% chance of going all in in the list of options
				options.remove("All-In");
			}
		}
		
		if(player.money-Poker.prevBet < 6) { //If the player doesn't have enough money to raise a lot, don't raise.
			options.remove("Raise");
		}
		
		if(((int)(Math.random() * 100)) > 30) { //30% chance of raising in the list of options.
			options.remove("Raise");
		}
		
		DisplayManager.wipeConsole();
		System.out.println("Calculating the AI's turn...");
		Poker.sleep(500);
		
		int rand = (int) (Math.random() * options.size()); //Get random index of the list of options
		new ArrayList<>(options.values()).get(rand).run(player); //Execute the random option
	}
}
