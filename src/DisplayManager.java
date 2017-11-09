import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * ADD A TODO HEADER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

public class DisplayManager {
	
	public static List<String> globalConsole = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public static void displayContext(HashMap<String, String> options) {
		wipeConsole();
		List<String> prepare = new ArrayList<>();
		List<String> pNames = new ArrayList<>(), totalMoney = new ArrayList<>(), betMoney = new ArrayList<>();
		int consoleLongest = getLongestStringLength(new HashSet<String>(globalConsole)),
				optionLongest = getLongestStringLength(new HashSet<String>(options.keySet()), "Key"),
				valueLongest = getLongestStringLength(new HashSet<String>(options.values()), "Option"), 
				playerLongest = getLongestStringLength(new HashSet<String>(pNames), "Players"),
				totalMoneyLongest = getLongestStringLength(new HashSet<String>(totalMoney), "Total Money"),
				betMoneyLongest = getLongestStringLength(new HashSet<String>(betMoney), "Bet Money");
		
		//TODO
		
		String a = "";
		List<Integer> insertLine = new ArrayList<>();
		//world's worst way to get the max value for 6 variables below
		int longestLongest = optionLongest + valueLongest + playerLongest + totalMoneyLongest + betMoneyLongest + 16;
		prepare.add(a);
		insertLine.add(prepare.size()-1);
		String prepHeading = "| Console";
		for(int i = 0; i < longestLongest-7; i++) prepHeading += " ";
		prepare.add(prepHeading + " |");
		prepare.add(a);
		insertLine.add(prepare.size()-1);
		
		//Print global console.
		for(String str : globalConsole) {
			str = "| " + str;
			for(int i = 0; i < longestLongest-(str.length()-2); i++) {
				str += " ";
			}
			str += " |";
			prepare.add(str);
		}
		prepare.add(a);
		insertLine.add(prepare.size()-1);
		
		for(Player p : Poker.players) {
			pNames.add(p.name);
			totalMoney.add(Integer.toString(p.money));
			betMoney.add(p.betMoney + "");
		}
		
		prepare.add("| Player " + Poker.players.get(Poker.curPlayer).name + "'s turn.");
		for(int i = 0; i < longestLongest-(prepare.get(prepare.size()-1).length()-2); i++) {
			prepare.set(prepare.size()-1, prepare.get(prepare.size()-1) + " ");
		}
		prepare.set(prepare.size()-1, prepare.get(prepare.size()-1) + " |");
		prepare.add(a);
		insertLine.add(prepare.size()-1);
		//Print heading.
		
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
		prepare.add(a);
		insertLine.add(prepare.size()-1);
		int consoleDivide = prepare.size(); //get the index of prepare in which the line is after the heading.
		//Print player key options.
		int cache = 0;
		for(String key : options.keySet()) {
			key = "| " + key;
			int l = key.length()-2;
			for(int i = 0; i < optionLongest-l; i++) {
				System.out.println(optionLongest + " " + key.length() + "HEYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY " + key); //TODO
				key += " ";
			}
			prepare.add(key);
		}
		//Print player options.
		for(String val : options.values()) {
			val = " | " + val;
			int l = val.length()-3;
			System.out.println("WTH " + valueLongest + " " + val.length());
			for(int i = 0; i < valueLongest-l; i++) {
				System.out.println(valueLongest + " " + val.length() + "HEYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY " + val);
				val += " "; 
			}
			if(consoleDivide + cache > prepare.size()-1) {
				String prep = "| ";
				for(int i = 0; i < optionLongest; i++) prep += " ";
				prep += val;
				prepare.add(prep); // yikes
			}
			else {
				prepare.set(consoleDivide + cache, prepare.get(consoleDivide + cache) + val);
			}
			cache++;
		}
		cache = 0;
		//Print all the players in the game with their output.
		for(String pName : pNames) {
			pName = " | | " + pName;
			int l = pName.length()-5;
			for(int i = 0; i < playerLongest-l; i++) pName += " ";
			if(consoleDivide + cache > prepare.size()-1) {
				String prep = "| ";
				for(int i = 0; i < optionLongest; i++) prep += " ";
				prep += " | ";
				for(int i = 0; i < valueLongest; i++) prep += " ";
				prep += pName;
				prepare.add(prep); 
			}
			else {
				prepare.set(consoleDivide + cache, prepare.get(consoleDivide + cache) + pName);
			}
			cache++;
		}
		cache = 0;
		//Print the total money for the players in the game
		for(String money : totalMoney) {
			money = " | " + money;
			int l = money.length()-3;
			for(int i = 0; i < totalMoneyLongest-l; i++) money += " ";
			if(consoleDivide + cache > prepare.size()-1) {
				String prep = "| ";
				for(int i = 0; i < optionLongest; i++) prep += " ";
				prep += " | ";
				for(int i = 0; i < valueLongest; i++) prep += " ";
				prep += " | | ";
				for(int i = 0; i < playerLongest; i++) prep += " ";
				prep += money;
				prepare.add(prep); 
			}
			else {
				prepare.set(consoleDivide + cache, prepare.get(consoleDivide + cache) + money);
			}
			cache++;
		}
		cache = 0;
		//Print the bet money for the players in the game
		for(String bmoney : betMoney) {
			bmoney = " | " + bmoney;
			int l = bmoney.length()-3;
			for(int i = 0; i < betMoneyLongest-l; i++) bmoney += " ";
			if(consoleDivide + cache > prepare.size()-1) {
				String prep = "| ";
				for(int i = 0; i < optionLongest; i++) prep += " ";
				prep += " | ";
				for(int i = 0; i < valueLongest; i++) prep += " ";
				prep += " | | ";
				for(int i = 0; i < playerLongest; i++) prep += " ";
				prep += " | ";
				for(int i = 0; i < totalMoneyLongest; i++) prep += " ";
				prep += bmoney + " |";
				prepare.add(prep); 
			}
			else {
				bmoney += " |";
				prepare.set(consoleDivide + cache, prepare.get(consoleDivide + cache) + bmoney);
			}
			cache++;
		}
		
		prepare.add(a);
		insertLine.add(prepare.size()-1);
		
		int longestLine = 0;
		for(String str : prepare) {
			if(str.length() > longestLine) longestLine = str.length();
		}
		for(int i = 0; i < longestLine; i++) a += "—";
		for(int c : insertLine) {
			prepare.set(c, a);
		}
		for(String str : prepare) {
			System.out.println(str);
		}
	}
	public static int getLongestStringLength(Set<String> set, String... addedStrings) {
		for(String s : addedStrings) set.add(s);
		int max = 0;
		for(String str : set) {
			if(str.length() > max) max = str.length();
		}
		return max;
	}
	public static void wipeConsole() {
		for(int i = 0; i < 1000; i++) System.out.println("\n");
	}
}
