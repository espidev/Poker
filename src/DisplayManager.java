import java.util.ArrayList;
import java.util.HashMap;
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
		int longest = getLongestStringLength((Set<String>) globalConsole);
		String a = "";
		for(int i = 0; i < longest+4; i++) {
			a += "—";
		}
		prepare.add(a);
		
		//Print global console.
		for(String str : globalConsole) {
			str = "| " + str;
			for(int i = 0; i < longest-str.length()-2; i++) {
				str += " ";
			}
			str += " |";
			prepare.add(str);
		}
		prepare.add(a);
		
		List<String> pNames = new ArrayList<>(), totalMoney = new ArrayList<>(), betMoney = new ArrayList<>();
		for(Player p : Poker.players) {
			pNames.add(p.name);
			totalMoney.add(Integer.toString(p.money));
			betMoney.add(p.betMoney + "");
		}
		
		//Print heading.
		int optionLongest = getLongestStringLength(options.keySet(), "Key"),
			valueLongest = getLongestStringLength((Set<String>) options.values(), "Option"), 
			playerLongest = getLongestStringLength((Set<String>) pNames, "Players"),
			totalMoneyLongest = getLongestStringLength((Set<String>) totalMoney, "Total Money"),
			betMoneyLongest = getLongestStringLength((Set<String>) betMoney, "Bet Money");
		
		String heading = "| Key";
		for(int i = 0; i < optionLongest-3; i++) heading += " ";
		heading += " | Option";
		for(int i = 0; i < valueLongest-5; i++) heading += " ";
		heading += " | | Players";
		for(int i =  0; i < playerLongest-7; i++) heading += " ";
		heading += " | Total Money ";
		for(int i = 0; i < totalMoneyLongest-11; i++) heading += " ";
		heading += " | Bet Money";
		for(int i = 0; i < betMoneyLongest-9; i++) heading += " ";
		heading += " |";
		prepare.add(heading);
		prepare.add(a);
		int consoleDivide = prepare.size(); //get the index of prepare in which the line is after the heading.
		//Print player key options.
		int cache = 0;
		for(String key : options.keySet()) {
			key = "| " + key;
			for(int i = 0; i < optionLongest-key.length(); i++) key += " ";
			prepare.add(key);
		}
		//Print player options.
		for(String val : options.values()) {
			val = " | " + val;
			for(int i = 0; i < valueLongest-val.length(); i++) val += " "; 
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
			pName = " | " + pName;
			for(int i = 0; i < playerLongest-pName.length(); i++) pName += " ";
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
			for(int i = 0; i < totalMoneyLongest-money.length(); i++) money += " ";
			if(consoleDivide + cache > prepare.size()-1) {
				String prep = "| ";
				for(int i = 0; i < optionLongest; i++) prep += " ";
				prep += " | ";
				for(int i = 0; i < valueLongest; i++) prep += " ";
				prep += " | ";
				for(int i = 0; i < totalMoneyLongest; i++) prep += " ";
				prep += money;
				prepare.add(prep); 
			}
			else {
				prepare.set(consoleDivide + cache, prepare.get(consoleDivide + cache) + money);
			}
		}
		cache = 0;
		//Print the bet money for the players in the game
		for(String bmoney : betMoney) {
			bmoney = " | " + bmoney;
			for(int i = 0; i < betMoneyLongest-bmoney.length(); i++) bmoney += " ";
			if(consoleDivide + cache > prepare.size()-1) {
				String prep = "| ";
				for(int i = 0; i < optionLongest; i++) prep += " ";
				prep += " | ";
				for(int i = 0; i < valueLongest; i++) prep += " ";
				prep += " | ";
				for(int i = 0; i < totalMoneyLongest; i++) prep += " ";
				prep += " | ";
				for(int i = 0; i < betMoneyLongest; i++) prep += " ";
				prep += bmoney + " |";
				prepare.add(prep); 
			}
			else {
				prepare.set(consoleDivide + cache, prepare.get(consoleDivide + cache) + bmoney);
			}
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
