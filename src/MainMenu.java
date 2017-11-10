
/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * MainMenu class: The main menu for the beginning of the game.
 */

public class MainMenu {
	
	public static void displayMainMenu() {
		System.out.println("———————————————————————————————————————————");
		System.out.println("| Welcome to Poker!                       |");
		System.out.println("———————————————————————————————————————————");
		System.out.println("| Key | Option                            |");
		System.out.println("———————————————————————————————————————————");
		System.out.println("| 1   | Start the game.                   |");
		System.out.println("———————————————————————————————————————————");
		System.out.println("| 2   | Rules                             |");
		System.out.println("———————————————————————————————————————————");
		System.out.println("| 3   | Credits                           |");
		System.out.println("———————————————————————————————————————————");
		System.out.println("| 4   | Adjust Settings                   |");
		System.out.println("———————————————————————————————————————————");
		System.out.println("| 0   | Exit                              |");
		System.out.println("———————————————————————————————————————————");

		String input = Poker.scan.next();

		switch(input) {
			case "1": DisplayManager.wipeConsole();
					Poker.startGame();
					break;
			case "2": printRules();
					break;
			case "3": System.out.println("Developers: Devin Lin, Jack Li, John Li, Alex Chan");
					break;
			case "4": System.out.println("TODO!");
					break;
			case "0": System.out.println("Exiting program.");
					System.exit(0);
					break;
			default: System.out.println("Incorrect input, try again.");
					break;
		}
	}

	public static void printRules () {
		System.out.println("Here are the rules to the game:");
	}
}
