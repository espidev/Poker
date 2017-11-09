import java.util.ArrayList;
import java.util.HashMap;

/*
 * ADD A TODO HEADER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

public class AI {


	public static int numGenerator (int min, int max) {
		return (int) (Math.random()*(max-min) + min);
	}

	public static void calculateTurn(Player player, HashMap<String, BooleanOperation> options) {
		int rand = (int) (Math.random() * options.size());
		new ArrayList<>(options.values()).get(rand).run(player);
	}
}
