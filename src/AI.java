import java.util.ArrayList;
import java.util.HashMap;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Action Class: A basic AI to choose a random option in the actions class
 */

public class AI {
	public static void calculateTurn(Player player, HashMap<String, BooleanOperation> options) {
		int rand = (int) (Math.random() * options.size());
		new ArrayList<>(options.values()).get(rand).run(player);
	}
}
