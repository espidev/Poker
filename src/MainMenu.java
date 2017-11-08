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
		System.out.println("1: Play\n2: Rules\n3: Credits\n4: Exit");

		int userInput = Poker.scan.nextInt();
		if (userInput == 1){
			Poker.startGame();
		} else if (userInput == 2) {
			printRules();
		} else if (userInput == 3) {
			System.out.println("Developers: Devin Lin, Jack Li, John Li, Alex Chan");
		} else if (userInput == 0) {
			System.out.println("Thanks for playing!");
			System.exit(0);
		}

	}

	public static void printRules () {
		System.out.println("Here are the rules to the game:");
	}
}
