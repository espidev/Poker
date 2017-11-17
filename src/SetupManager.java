import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SetupManager extends Poker{

	/*
	 * Setup process
	 */

	public static void prepareGame() {
		System.out.println("Welcome to the setup process. \nPlease answer the next few questions.");
		sleep(1000);
		int numOfPlayers;

		//Use type safe input manager tool
		InputManager<Integer> iManagerInt = new InputManager<>();
		InputManager<String> iManagerStr = new InputManager<>();

		
		/*
		 * Number of players in a game
		 */

		numOfPlayers = iManagerInt.getInput("How many players will there be in the game?", (input) -> {
			int n = Integer.parseInt((String)input);
			if(n < 2) {
				System.out.println("You must have 2 or more people to play.");
				sleep(300);
				return false;
			}
			else if(n > 10) {
				System.out.println("Too many people! (10 max)");
				sleep(300);
				return false;
			}
			else {
				System.out.println(n + " people? Nice!");
				sleep(300);
				return true;
			}
		}, 1);

		
		/*
		 * Default starting balance
		 */

		HashMap<String, Runnable> sBalance = new HashMap<>();
		
		sBalance.put("y", () -> { //if the user inputed y...
			defaultStarting = true;
			iManagerInt.getInput("How much should it be? (50 recommended)", (input) -> {
				int num = Integer.parseInt((String)input);
				if(num < 10) {
					System.out.println("Too little. Please specify a number larger than 9.");
					sleep(300);
					return false;
				}
				else {
					startingBalance = num;
					System.out.println("$" + num + " is perfect!");
					sleep(1000);
					return true;
				}
			}, 1);
		});
		
		sBalance.put("n", () -> { //if the user inputed n...
			defaultStarting = false;
			System.out.println("Okay, got it. \nEach player's balance will now have to be specified manually.");
			sleep(1000);
		});
		
		iManagerStr.getInput("Should there be a default starting balance? (y/n)", sBalance); //call the input manager to process and get the input.

		
		/*
		 * Prepare players in the game.
		 */

		System.out.println("Now on to preparing the players.");
		sleep(1000);

		for(int i = 0; i < numOfPlayers; i++) {
			System.out.println("Player " + (i+1) + ":");
			sleep(500);

			String name = null;
			
			//Get if player will be an AI.
			String input = iManagerStr.getInput("Will this player be an AI? (y/n)", (in) -> {
				String inp = (String) in;
				if(inp.equalsIgnoreCase("y") || inp.equalsIgnoreCase("n")) {
					return true;
				}
				return false;
			}, "");

			if(input.equalsIgnoreCase("y")) { //If the player will be an AI
				System.out.println("Okay.");
				sleep(300);

				//Choose a random name for the AI
				List<String> names = new ArrayList<>(Arrays.asList("Joe", "Bob", "Mark", "Jill", "Peter", "Cosmo", "Stalin", "Kim Jong Un", "Mao Zedong", "Fidel Castro", "Donald Trump"));

				int j = 0;
				String rand = "";

				while(j < names.size()) { //check if there is already a person with the name
					rand = names.get((int) (Math.random() * names.size()));
					boolean notFound = true;
					for(Player p : players) {
						if(p.name.equals(rand)) {
							notFound = false;
						}
					}
					if(notFound) {
						name = rand;
						break;
					}
					names.remove(rand); //Remove name from list if already found.
					j++;
				}
				if(name == null) { //If it still can't find a name not taken, add a random number to the current name.
					name = rand + ((int) (Math.random() * 100)); //Still possibility for an AI to have the same name as another person but it should be fine...
				}
				
				sleep(300);
				System.out.println("This AI's name is " + name + ".");
				sleep(300);

				//Ask for a starting balance if there isn't a starting balance
				if(Poker.defaultStarting) { //if there is a default starting balance
					players.add(new Player(name, startingBalance, true)); //initialize player with settings.
				}
				else {
					
					//If not, set how much the player starts with.
					int start = iManagerInt.getInput("How much should this player start with? (50 recommended)", (in) -> {
						int inp = Integer.parseInt((String) in);
							if(inp < 10) {
								System.out.println("Too little. Please specify a number larger than 10.");
								sleep(300);
								return false;
							}
							else {
								System.out.println("$" + inp + " is perfect!");
								sleep(700);
								return true;
							}
					}, 1);
					
					players.add(new Player(name, start, true)); //initialize player with settings.
				}
			}
			else if(input.equalsIgnoreCase("n")) { //If not an AI
				System.out.println("Okay.");
				sleep(300);
				
				System.out.println("What is the name of this player?");
				
				//Prompt for player's name and set it to name variable.
				while(true) {
					String inputName = scan.nextLine();
					boolean found = false;
					for(Player p : players) {
						if(p.name.equals(inputName)) {
							System.out.println("There is already a player with this name! Try another one.");
							sleep(300);
							found = true;
							break;
						}
					}
					if(!found) {
						System.out.println("Hello " + inputName + "!");
						name = inputName;// <- name variable set to the input.
						sleep(300);
						break;
					}
				}

				
				if(Poker.defaultStarting) { //if there is a default starting balance
					Poker.players.add(new Player(name, Poker.startingBalance, false)); //initialize player with these settings.
				}
				else { //if not...
					
					//get input and assign it to start
					int start = iManagerInt.getInput("How much should this player start with? (50 recommended)", (in) -> {
						int inp = Integer.parseInt((String) in);
						if(inp < 10) {
							System.out.println("Too little. Please specify a number larger than 10.");
							sleep(300);
							return false;
						}
						else {
							System.out.println("$" + inp + " is perfect!");
							sleep(1000);
							return true;
						}
					}, 1);
					
					Poker.players.add(new Player(name, start, false)); //initialize player with these settings.
				}
				
			}
		}
		System.out.println("Setup process complete!");
		sleep(1000);
	}
}
