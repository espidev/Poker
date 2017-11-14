import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Display Manager Class: A crazy cool thing that we don't understand (Other than Devin).
 */

public class DisplayManager {

	public static List<String> globalConsole = new ArrayList<>();

	/*
	 * Displays player menu in console.
	 */

	public static void displayContextRedo(HashMap<String, String> options) {
		wipeConsole();
		List<String> prepare = new ArrayList<>();
		List<String> pNames = new ArrayList<>(), totalMoney = new ArrayList<>(), betMoney = new ArrayList<>();
		List<Integer> insertLine = new ArrayList<>();

		for(Player p : Poker.players) {
			if(p.stillInRound) {
				pNames.add(p.name);
			}
			else {
				pNames.add("(folded)" + p.name);
			}
			totalMoney.add(Integer.toString(p.money));
			betMoney.add(p.betMoney + "");
		}

		int consoleLongest = getLongestStringLength(new HashSet<String>(globalConsole)),
				optionLongest = getLongestStringLength(new HashSet<String>(options.keySet()), "Key"),
				valueLongest = getLongestStringLength(new HashSet<String>(options.values()), "Option"), 
				playerLongest = getLongestStringLength(new HashSet<String>(pNames), "Players"),
				totalMoneyLongest = getLongestStringLength(new HashSet<String>(totalMoney), "Total Money"),
				betMoneyLongest = getLongestStringLength(new HashSet<String>(betMoney), "Bet Money");

		//Console header

		prepare.add("");
		insertLine.add(prepare.size()-1);
		prepare.add("| Console");
		prepare.add("");
		insertLine.add(prepare.size()-1);

		for(String str : globalConsole) {
			prepare.add("| " + str);
		}

		prepare.add("");
		insertLine.add(prepare.size()-1);

		//Assemble value header

		String heading = "| Key";
		for(int i = 0; i < optionLongest-3; i++) heading += " ";
		heading += " | Option";
		for(int i = 0; i < valueLongest-6; i++) heading += " ";
		heading += " | | Players";
		for(int i =  0; i < playerLongest-7; i++) heading += " ";
		heading += " | Total Money";
		for(int i = 0; i < totalMoneyLongest-11; i++) heading += " ";
		heading += " | Bet Money";
		for(int i = 0; i < betMoneyLongest-9; i++) heading += " ";
		heading += " |";
		prepare.add(heading);

		prepare.add("");
		insertLine.add(prepare.size()-1);

		//assemble the data columns

		for(int i = 0; i < getMax(pNames.size(), options.size()); i++) {
			prepare.add(assembler(finder(new ArrayList<>(options.keySet()), i, ""), optionLongest) +
					assembler(finder(new ArrayList<>(options.values()), i, ""), valueLongest) + 
					assembler(finder(pNames, i, "| "), playerLongest+2) + 
					assembler(finder(totalMoney, i, ""), totalMoneyLongest) + 
					assembler(finder(betMoney, i, ""), betMoneyLongest) + " |");
		}

		prepare.add("");
		insertLine.add(prepare.size()-1);

		prepare.add("| " + Poker.players.get(Poker.curPlayer).name + "'s turn.");
		prepare.add("| The current bet is at $" + Poker.prevBet + ".");

		prepare.add("");
		insertLine.add(prepare.size()-1);
		if(Poker.cardsOnTable.size() == 0) {
			prepare.add("| There are no cards on the table yet.");
		}
		else {
			prepare.add("| Cards on the table:");
			String cards = "| ";
			for(Card c : Poker.cardsOnTable) {
				cards += c.getCard(); //get unicode representation of card
			}
			prepare.add(cards);
			for(Card c : Poker.cardsOnTable) {
				prepare.add("| " + c.number + " " + Suit.getSuit(c.suit) + " |");
			}
		}

		prepare.add("");
		insertLine.add(prepare.size()-1);

		int longestLine = 0;
		for(String str : prepare) {
			if(str.length() > longestLine) longestLine = str.length();
		}

		//Fill in horizontal dashes
		String a = "";
		for(int i = 0; i < longestLine; i++) {
			a += "—";
		}
		for(int c : insertLine) {
			prepare.set(c, a);
		}

		//Print everything
		for(String str : prepare) {
			System.out.println(str);
		}
	}

	/*
	 * Returns a completed string for assembling the line.
	 */

	public static String finder(List<String> l, int iter, String append) {
		if(l.size() <= iter) {
			return append + "| ";
		}
		else {
			return append + "| " + l.get(iter);
		}
	}

	/*
	 * Adds the spaces necessary to match up the line size count.
	 */

	public static String assembler(String str, int max) {
		int len = str.length();
		for(int i = 0; i < max-len+3; i++) str += " ";
		return str;
	}

	/*
	 * Math.max basically I don't know why it's here
	 */

	public static int getMax(int... ints) {
		int max = ints[0];
		for(int a : ints) {
			if(a > max) max = a;
		}
		return max;
	}

	/*
	 * Get longest string in a set plus unlimited arguments
	 */

	public static int getLongestStringLength(Set<String> set, String... addedStrings) {
		for(String s : addedStrings) set.add(s);
		int max = 0;
		for(String str : set) {
			if(str.length() > max) max = str.length();
		}
		return max;
	}

	/*
	 * Clear the console.
	 */

	public static void wipeConsole() {
		for(int i = 0; i < 500; i++) System.out.println("\n");
	}
}
