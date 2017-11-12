import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Poker class: The main method for the game
 */


public class Poker {

	public static List<Card> cardsOnStack = new ArrayList<>(), cardsOnTable = new ArrayList<>();
	public static List<Player> players = new ArrayList<>();

	public static Scanner scan = new Scanner(System.in);

	public static Player bigBlind = null, smallBlind = null;

	public static int curPlayer, curOrbit, numOfDead = 0, orbitEnd = 0, prevBet = 0, startingBalance = 50;

	public static boolean inRound = false, inGame = false, defaultStarting = true;

	public static int getPot() {
		int sum = 0;
		for(Player p : players) {
			sum += p.betMoney;
		}
		return sum;
	}

	public static void setBlinds() {
		if(bigBlind == null || smallBlind == null || (bigBlind == null && smallBlind == null)) {
			int rand = (int) (Math.random()*players.size());
			int n2 = (rand == players.size()-1) ? 0 : rand+1 ;
			bigBlind = players.get(rand);
			smallBlind = players.get(n2);
		}
		else {
			int next = 0, cur = 0;
			for(int i = 0; i < players.size(); i++) { //searches the list for the big blind
				if(players.get(i).name.equals(bigBlind.name)) { //goes to the next person to set as big blind
					for(int j = i; ; j++) {
						if(j == players.size()-1) {
							j = 0;
						}
						else {
							j = i+1;
						}
						if(players.get(j).stillInGame) {
							next = j;
							break;
						}
					}
					cur = i;
					break;
				}
			}
			smallBlind = players.get(cur);
			bigBlind = players.get(next);
		}

		bigBlind.betMoney = 2;
		bigBlind.money -= 2;
		smallBlind.betMoney = 1;
		smallBlind.betMoney--;
		prevBet = 2;

		DisplayManager.globalConsole.add(smallBlind.name + " is now the small blind.");
		DisplayManager.globalConsole.add(bigBlind.name + " is now the big blind.");
		DisplayManager.globalConsole.add(smallBlind.name + " has bet $1.");
		DisplayManager.globalConsole.add(bigBlind.name + " has bet $2.");
	}

	public static void main(String[] args) {
		while(true) {
			MainMenu.displayMainMenu();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			DisplayManager.wipeConsole();
		}
	}

	public static void startGame() {

		resetGame();
		prepareGame();

		//Pick cards for the players
		for(Player p : players) {
			Card c1 = cardsOnStack.get((int) (Math.random() * cardsOnStack.size()));
			cardsOnStack.remove(c1);
			Card c2 = cardsOnStack.get((int) (Math.random() * cardsOnStack.size()));
			cardsOnStack.remove(c2);
			p.cards.add(c1);
			p.cards.add(c2);

			p.stillInGame = true;
			p.stillInRound = true;
		}

		System.out.println("Starting game...");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		inGame = true;

		while (inGame) {

			//Starts a round of poker
			round();

			//CHECK IF THERE IS ONE PERSON LEFT

			//Ask if the game should continue



			while (true) {
				System.out.println("Do you want the game to continue? (y/n)");
				String input = scan.nextLine();
				if(input.equalsIgnoreCase("y")) {
					System.out.println("Okay.");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}
				else if(input.equalsIgnoreCase("n")) {
					System.out.println("Okay.");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					inGame = false;
					break;
				}
				else {
					System.out.println("Error. Try again.");
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		//FIND THE DANG WINNERS
		Collections.sort(players); //TODO
	}

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

				if(defaultStarting) { //if there is a default starting balance
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

				if(defaultStarting) { //if there is a default starting balance
					players.add(new Player(name, startingBalance, false));
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
					players.add(new Player(name, start, false));
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

	/*
	 * Executed per round (in a game)
	 */

	public static void round() {

		//Check that everyone has enough money
		for(Player p : players) {
			if(p.money < 2) {
				System.out.println(p.name + " does not have enough money to continue playing.");
				System.out.println(p.name + " has forfeited the game.");
				DisplayManager.globalConsole.add(p.name + " does not have enough money to continue playing.");
				DisplayManager.globalConsole.add(p.name + " has forfeited the game.");
				p.stillInGame = false;
				p.orderOfDeath = numOfDead + 1;
				p.stillInRound = false;
				numOfDead++;
			}
		}

		inRound = true;

		setBlinds();
		for (; curOrbit < 4 && inRound; curOrbit++) { //curOrbit already set to zero from resetRound()
			orbit();
		}
		//Show everyone's cards in the output
		calculateWinners();

		//Reset the round to get ready for the next one
		resetRound();
	}

	/*
	 * Executed per orbit (in a round)
	 */

	public static void orbit() {

		DisplayManager.wipeConsole();
		
		try {
		switch(curOrbit) { //give appropriate actions for each orbit.
		case 0:
			System.out.println("—————————————————————————————————");
			System.out.println("| Welcome to the Preflop round. |");
			System.out.println("—————————————————————————————————");
			DisplayManager.globalConsole.add("The preflop round has started.");
			Thread.sleep(2000);
			break;
		case 1:
			System.out.println("—————————————————————————————————");
			System.out.println("| Welcome to the Flop round.    |");
			System.out.println("—————————————————————————————————");
			DisplayManager.globalConsole.add("The flop round has started.");

			//add cards to table from stack
			Card c1 = cardsOnStack.get((int) (Math.random() * cardsOnStack.size()));
			cardsOnStack.remove(c1);
			Card c2 = cardsOnStack.get((int) (Math.random() * cardsOnStack.size()));
			cardsOnStack.remove(c2);
			Card c3 = cardsOnStack.get((int) (Math.random() * cardsOnStack.size()));
			cardsOnStack.remove(c3);
			cardsOnTable.add(c1);
			cardsOnTable.add(c2);
			cardsOnTable.add(c3);
			DisplayManager.globalConsole.add("3 cards are now on the table.");
			Thread.sleep(2000);
			break;
		case 2:
			System.out.println("—————————————————————————————————");
			System.out.println("| Welcome to the Turn round.    |");
			System.out.println("—————————————————————————————————");
			DisplayManager.globalConsole.add("The turn round has started.");

			//add card to table from stack
			Card c4 = cardsOnStack.get((int) (Math.random() * cardsOnStack.size()));
			cardsOnStack.remove(c4);
			cardsOnTable.add(c4);

			DisplayManager.globalConsole.add("4 cards are now on the table.");
			Thread.sleep(2000);
			break;
		case 3:
			System.out.println("—————————————————————————————————");
			System.out.println("| Welcome to the River round.   |");
			System.out.println("—————————————————————————————————");
			DisplayManager.globalConsole.add("The river round has started.");

			//add card to table from stack
			Card c5 = cardsOnStack.get((int) (Math.random() * cardsOnStack.size()));
			cardsOnStack.remove(c5);
			cardsOnTable.add(c5);

			DisplayManager.globalConsole.add("5 cards are now on the table.");
				Thread.sleep(2000);
			break;
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		boolean exit = false;
		int start = 0; //get the starting person
		for(int i = 0; i < players.size(); i++) {
			if(curOrbit == 0) {
				if(players.get(i).name.equals(bigBlind.name)) {
					if(i == players.size()-1) {
						i = 0;
					}
					else {
						i = i+1;
					}
					for(int j = i; ; j++) { //if the person next to the big blind is out, go to the next person
						if(j == players.size()-1) {
							j = 0;
						}
						else {
							j = i+1;
						}
						if(players.get(j).stillInGame && players.get(j).stillInRound) {
							start = j;
							break;
						}
					}
					break;
				}
			}
			else {
				if(players.get(i).name.equals(smallBlind.name)) { // on every orbit except the first orbit, the small blind starts.
					for(int j = i; ; j++) { //if the small blind is out, go to the next person
						if(j == players.size()-1) {
							j = 0;
						}
						else {
							j = i+1;
						}
						if(players.get(j).stillInGame && players.get(j).stillInRound) {
							start = j;
							break;
						}
					}
					break;
				}
			}
		}
		orbitEnd = start;
		int cur = start;
		do {
			curPlayer = cur;
			if(players.get(curPlayer).stillInGame && players.get(curPlayer).stillInRound) {
				//Player's turn
				HashMap<String, BooleanOperation> options = getOptions(players.get(curPlayer)); //get what options the player can do

				DisplayManager.globalConsole.add(players.get(curPlayer).name + "'s turn.");

				if(players.get(curPlayer).isAI) {
					AI.calculateTurn(players.get(curPlayer), options);
				}
				else {
					playerTurn(players.get(curPlayer), options);
				}
			}
			if(cur == players.size()-1) { //go from array size back to zero (to loop around players)
				cur = 0;
			}
			else {
				cur++;
			}
		}
		while(cur != orbitEnd);
	}

	/*
	 * Return a list of options that the player can choose to do
	 */

	public static HashMap<String, BooleanOperation> getOptions(Player player) {
		HashMap<String, BooleanOperation> hash = new HashMap<>();

		hash.put("Fold", (Player p) -> { //lambda list 
			return Actions.fold(p);
		});

		/*if(player.betMoney >= Poker.prevBet) {
			hash.put("Check", (Player p) -> {
				return Actions.check(p);
			});
		}
		else {*/
		if(!player.allIn) {
			if(player.money + player.betMoney >= Poker.prevBet) {
				hash.put("Call", (Player p) -> {
					return Actions.call(p);
				});
				if(player.money + player.betMoney > prevBet) {
					hash.put("Raise", (Player p) -> {
						if(p.isAI) {
							return Actions.raise(p, (int) (Math.random()*(p.money + p.money - prevBet) + prevBet));
						}
						else {
							while(true) {
								try {
									System.out.println("What do you want to raise the bet to?");
									int input = Integer.parseInt(scan.nextLine());
									if(input > p.money + p.betMoney) {
										System.out.println("You don't have enough money!");
									}
									else if(input < prevBet) {
										System.out.println("This value is too low! The bet is currently $" + prevBet + ".");
									}
									else {
										return Actions.raise(p, input);
									}
								}
								catch(Exception e) {
									System.out.println("Error. Try again.");
								}
							}
						}
					});
				}
			}
			hash.put("All-In", (Player p) -> {
				return Actions.allIn(p);
			});
			//}
		}
		else {
			hash.put("Check", (Player p) -> {
				return Actions.check(p);
			});
		}



		//TODO

		return hash;
	}

	/*
	 * Resets the round (happens several times in a game)
	 */

	public static void resetRound() {
		curOrbit = 0;
		curPlayer = 0;
		orbitEnd = 0;
		prevBet = 0;
		cardsOnStack = new ArrayList<>();
		cardsOnTable = new ArrayList<>();	
		inRound = false;
		for(int i = 1; i < 13; i++) {
			cardsOnStack.add(new Card(i, Suit.CLOVER));
			cardsOnStack.add(new Card(i, Suit.DIAMOND));
			cardsOnStack.add(new Card(i, Suit.HEART));
			cardsOnStack.add(new Card(i, Suit.SPADE));
		}
		for(Player p : players) {
			p.allIn = false;
			p.betMoney = 0;
			p.cards = new ArrayList<>();
		}
	}

	/*
	 * Resets the entire game.
	 */

	public static void resetGame() {
		resetRound();
		players = new ArrayList<>();
		bigBlind = null;
		smallBlind = null;
		numOfDead = 0;
		DisplayManager.globalConsole = new ArrayList<>();
	}

	public static void displayPlayerCards() {
		for(Player p : players) {
			System.out.println("—————————————————————————————");
			System.out.println("| Player " + p + "'s cards  |");
			System.out.println("—————————————————————————————");

			List<Card> cards = new ArrayList<>();
			for(Card c : cardsOnTable) {
				cards.add(c);
			}
			for(Card c : p.cards) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("| (" + c.number + " " + c.suit.toString() + ") |");
				cards.add(c);
			}

			System.out.println("—————————————————————————————");
			System.out.println("\n\n");
		}
	}

	public static void calculateWinners() {
		int max = 0;
		Player m = null;
		for(Player p : players) {
			List<Card> cards = new ArrayList<>();
			for(Card c : cardsOnTable) {
				cards.add(c);
			}
			for(Card c : p.cards) {
				cards.add(c);
			}
			int score = Evaluator.evaluateScore(cards);
			if(score > max) {
				max = score;
				m = p;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		win(m);
	}

	public static void win(Player p) {
		try {
			System.out.println("And the winner of this round is...");
			for(int i = 0; i < 3; i++) {
				Thread.sleep(1000);
				System.out.println(".");
			}
			Thread.sleep(1000);
			System.out.println(p.name + "!");
			p.money += getPot();
			Thread.sleep(500);
			System.out.println("This player has won $" + getPot() + "!");
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void playerTurn(Player player, HashMap<String, BooleanOperation> options) {
		HashMap<String, String> contextAssemble = new HashMap<>();
		for(int i = 0; i < options.size(); i++) {
			contextAssemble.put(i + "", new ArrayList<>(options.keySet()).get(i));
		}
		DisplayManager.displayContextRedo(contextAssemble);
		boolean notFound = true;
		while(notFound) {
			String input = scan.nextLine();
			for(String str : contextAssemble.keySet()) {
				if(str.equals(input)) {
					if(options.get(contextAssemble.get(str)).run(player)) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						notFound = false;
					}
					else {
						break;
					}
				}
			}
			if(notFound) {
				System.out.println("Error. Try again.");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
