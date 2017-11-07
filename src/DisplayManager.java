import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DisplayManager {
	public static List<String> globalConsole = new ArrayList<>();
	
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
			for(int i = 0; i < longest-str.length(); i++) {
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
			valueLongest = getLongestStringLength((Set<String>) options.values()), 
			playerLongest = getLongestStringLength((Set<String>) pNames),
			totalMoneyLongest = getLongestStringLength((Set<String>) totalMoney),
			betMoneyLongest = getLongestStringLength((Set<String>) betMoney);
		
		String heading = "| Key";
		for(int i = 0; i < valueLongest-3; i++) heading += " ";
		heading += " | Option";
		for(int i = 0; i < optionLongest-5; i++) heading += " ";
		heading += " | | Players";
		for(int i =  0; i < playerLongest-7; i++) heading += " ";
		heading += " | Total Money ";
		for(int i = 0; i < totalMoneyLongest-11; i++) heading += " ";
		heading += " | betMoney";
		prepare.add(heading);
		prepare.add(a);
		int consoleDivide = prepare.size()-1;
		//Print player options.
		for(String key : options.keySet()) {
			key = "| " + key;
			
		}
		prepare.add(a);
		//Print all the players in the game with their output.
		
		
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
