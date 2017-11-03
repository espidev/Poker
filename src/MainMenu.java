import java.util.*;

public class MainMenu {
	public static void displayMainMenu() {
		Scanner sc = new Scanner (System.in);
	
		System.out.println("Welcome to this game of Poker!");
		System.out.println("Please select one of the following options:");
		System.out.println("1: Play\n2: Rules\n3: Credits\n4: Exit");
	
		int userInput = sc.nextInt();
	
		do {
			if (userInput == 1){
				Poker.startGame();
			} else if (userInput == 2) {
				printRules();
			} else if (userInput == 3) {
				System.out.println("Developers: Devin Lin, Jack Li, John Li, Alex Chan");
			} else if (userInput == 4) {
				System.out.println("Thanks for playing!");
				System.exit(0);
			}
		} while (userInput == 0);
	        
	}
	
	public static void printRules () {
		System.out.println("Here are the rules to the game:");
	}
}
