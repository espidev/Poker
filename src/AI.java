import java.util.HashMap;

/*
 * ADD A TODO HEADER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

public class AI {
	

public static int numGenerator (int min, int max) {
	return (int) (Math.random()*(max-min) + min);
}

public static void calculateTurn(Player player, HashMap<String, BooleanOperation> options) {
        int probability = numGenerator(0, 99);
		
	if (probability < 20) {
	       Actions.check();
	} else if (probability => 20 && probability < 40) {
		Actions.call();
	} else if (probability => 40 && probability < 60) {
		Actions.raise();
	} else if (probability => 60 && probability < 70) {
		Actions.fold();
	} else if (probability => 70 && probability < 90) {
		Actions.bet();
	} else {
		Actions.allIn();
	}
}
}
