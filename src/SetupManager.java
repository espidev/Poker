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
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		int numOfPlayers;

		//Type safe input manager tool
		InputManager<Integer> iManagerInt = new InputManager<>();
		InputManager<String> iManagerStr = new InputManager<>();

		/*
		 * Number of players in a game
		 */

		numOfPlayers = iManagerInt.getInput("How many players will there be in the game?", (input) -> {
			int n = Integer.parseInt((String)input);
			try {
				if(n < 2) {
					System.out.println("You must have 2 or more people to play.");
					Thread.sleep(300);
					return false;
				}
				else if(n > 10) {
					System.out.println("Too many people! (10 max)");
					Thread.sleep(300);
					return false;
				}
				else {
					System.out.println(n + " people? Nice!");
					Thread.sleep(300);
					return true;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
		}, 1);

		/*
		 * Default starting balance
		 */

		HashMap<String, Runnable> sBalance = new HashMap<>();
		sBalance.put("y", () -> {
			defaultStarting = true;
			iManagerInt.getInput("How much should it be? (50 recommended)", (input) -> {
				int num = Integer.parseInt((String)input);
				try {
					if(num < 10) {
						System.out.println("Too little. Please specify a number larger than 9.");
						Thread.sleep(300);
						return false;
					}
					else {
						startingBalance = num;
						System.out.println("$" + num + " is perfect!");
						Thread.sleep(1000);
						return true;
					}
				}
				catch(InterruptedException e) {
					e.printStackTrace();
					return false;
				}
			}, 1);
		});
		sBalance.put("n", () -> {
			defaultStarting = false;
			System.out.println("Okay, got it. \nEach player's balance will now have to be specified manually.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		iManagerStr.getInput("Should there be a default starting balance? (y/n)", sBalance);

		/*
		 * Prepare players in the game.
		 */

		System.out.println("Now on to preparing the players.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for(int i = 0; i < numOfPlayers; i++) {
			System.out.println("Player " + (i+1) + ":");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String name = null;
			boolean AI;
			String input = iManagerStr.getInput("Will this player be an AI? (y/n)", (in) -> {
				String inp = (String) in;
				if(inp.equalsIgnoreCase("y") || inp.equalsIgnoreCase("n")) {
					return true;
				}
				return false;
			}, "");

			if(input.equalsIgnoreCase("y")) { //Is AI
				System.out.println("Okay.");
				AI = true;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				//choose a random name for the AI
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
					names.remove(rand);
					j++;
				}
				if(name == null) { //If it still can't find a name not taken, add a random number to the current name.
					name = rand + ((int) (Math.random() * 100)); //still possibility for an AI to have the same name as another person but it should be fine...
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("This AI's name is " + name + ".");

				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if(Poker.defaultStarting) { //if there is a default starting balance
					players.add(new Player(name, startingBalance, true));
				}
				else {
					int start = iManagerInt.getInput("How much should this player start with? (50 recommended)", (in) -> {
						int inp = Integer.parseInt((String) in);
						try {
							if(inp < 10) {
								System.out.println("Too little. Please specify a number larger than 10.");
								Thread.sleep(300);
								return false;
							}
							else {
								System.out.println("$" + inp + " is perfect!");
								Thread.sleep(1000);
								return true;
							}
						}
						catch(InterruptedException e) {
							e.printStackTrace();
							return false;
						}
					}, 1);
					players.add(new Player(name, start, true));
				}
			}
			else if(input.equalsIgnoreCase("n")) { //Not AI
				System.out.println("Okay.");
				AI = false;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("What is the name of this player?");
				while(true) {
					String inputName = scan.nextLine();
					boolean found = false;
					for(Player p : players) {
						if(p.name.equals(inputName)) {
							System.out.println("There is already a player with this name! Try another one.");
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							found = true;
							break;
						}
					}
					if(!found) {
						System.out.println("Hello " + inputName + "!");
						name = inputName;
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
					}
				}

				if(Poker.defaultStarting) { //if there is a default starting balance
					Poker.players.add(new Player(name, Poker.startingBalance, false));
				}
				else {
					int start = iManagerInt.getInput("How much should this player start with? (50 recommended)", (in) -> {
						int inp = Integer.parseInt((String) in);
						try {
							if(inp < 10) {
								System.out.println("Too little. Please specify a number larger than 10.");
								Thread.sleep(300);
								return false;
							}
							else {
								System.out.println("$" + inp + " is perfect!");
								Thread.sleep(1000);
								return true;
							}
						}
						catch(InterruptedException e) {
							e.printStackTrace();
							return false;
						}
					}, 1);
					Poker.players.add(new Player(name, start, false));
				}
			}
		}
		System.out.println("Setup process complete!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
