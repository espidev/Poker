import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/*
 * ADD A TODO HEADER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

public class Poker {

	public static List<Card> cardsOnStack = new ArrayList<>(), cardsOnTable = new ArrayList<>();
	public static List<Player> players = new ArrayList<>();

	public static Scanner scan = new Scanner(System.in);

	public static Player bigBlind = null, smallBlind = null;

	public static int curPlayer, curOrbit, numOfDead = 0, orbitEnd = 0;

	public static boolean inRound = false, inGame = false;

	public static int getPot() {
		int sum = 0;
		for(Player p : players) {
			sum += p.betMoney;
		}
		return sum;
	}

	public static void setBlinds() {
		if(bigBlind == null || smallBlind == null || (bigBlind == null && smallBlind == null)) {
			int rand = (int) Math.random()*players.size();
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
		DisplayManager.globalConsole.add(smallBlind.name + " is now the small blind.");
		DisplayManager.globalConsole.add(bigBlind.name + " is now the big blind.");
		bigBlind.betMoney = 2;
		smallBlind.betMoney = 1;
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
			Card c1 = cardsOnStack.get((int) Math.random() * cardsOnStack.size());
			cardsOnStack.remove(c1);
			Card c2 = cardsOnStack.get((int) Math.random() * cardsOnStack.size());
			cardsOnStack.remove(c2);
			p.cards.add(c1);
			p.cards.add(c2);
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
				String input = scan.next();
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
		int numOfPlayers, startingBalance = 0;
		boolean defaultStarting;

		/*
		 * Number of players in a game
		 */

		while(true) {
			try {
				System.out.println("How many players will there be in the game?");
				numOfPlayers = Integer.parseInt(scan.next());

				if(numOfPlayers < 2) {
					System.out.println("You must have 2 or more people to play.");
					Thread.sleep(300);
					continue;
				}
				if(numOfPlayers > 10) {
					System.out.println("Too many people! (10 max)");
					Thread.sleep(300);
					continue;
				}
				System.out.println(numOfPlayers + " people? Nice!");
				Thread.sleep(300);
				break;
			}
			catch(Exception e) {
				System.out.println("Error. Try again.");
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		/*
		 * Default starting balance
		 */

		while(true) {
			try {
				System.out.println("Should there be a default starting balance? (y/n)");
				String input = scan.next();
				if(input.equalsIgnoreCase("y")) {
					defaultStarting = true;
					while(true) {
						try {
							System.out.println("How much should it be? (50 recommended)");
							int num = Integer.parseInt(scan.next());
							if(num < 10) {
								System.out.println("Too little. Please specify a number larger than 10.");
								Thread.sleep(300);
							}
							else {
								startingBalance = num;
								System.out.println("$" + num + " is perfect!");
								Thread.sleep(1000);
								break;
							}
						}
						catch (Exception e) {
							System.out.println("Error. Try again.");
							try {
								Thread.sleep(300);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
					break;
				}
				else if(input.equalsIgnoreCase("n")) {
					defaultStarting = false;
					System.out.println("Okay, got it. \nEach player's balance will now have to be specified manually.");
					Thread.sleep(1000);
					break;
				}
				else {
					System.out.println("Error. Try again.");
					Thread.sleep(300);
				}
			}
			catch(Exception e) {
				System.out.println("Error. Try again.");
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

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
			while(true) {
				System.out.println("Will this player be an AI? (y/n)");
				String input = scan.next();

				if(input.equalsIgnoreCase("y")) { //Is AI
					System.out.println("Okay.");
					AI = true;
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					//choose a random name for the AI
					List<String> names = new ArrayList<>(Arrays.asList("Joe", "Bob", "Joney", "Cosine", "Tangent", "LOL HI"));

					int j = 0;
					String rand = "";

					while(j < names.size()) { //check if there is already a person with the name
						rand = names.get((int) Math.random() * names.size());
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
						name = rand + ((int) Math.random() * 100); //still possibility for an AI to have the same name as another person but it should be fine...
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
						System.out.println("How much should this player start with? (50 recommended)");
						while(true) {
							try {
								int start = Integer.parseInt(scan.next());
								if(start < 10) {
									System.out.println("Too little. Please specify a number larger than 10.");
									Thread.sleep(300);
								}
								else {
									players.add(new Player(name, start, true));
									System.out.println("$" + start + " is perfect!");
									Thread.sleep(1000);
									break;
								}
							}
							catch (Exception e) {
								System.out.println("Error. Try again.");
								e.printStackTrace();
								try {
									Thread.sleep(300);
								} catch (InterruptedException es) {
									es.printStackTrace();
								}
							}
						}
					}
					break;
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
					scan.nextLine();
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
						System.out.println("How much should this player start with? (50 recommended)");
						while(true) {
							try {
								int start = Integer.parseInt(scan.next());
								if(start < 10) {
									System.out.println("Too little. Please specify a number larger than 10.");
									Thread.sleep(300);
								}
								else {
									players.add(new Player(name, start, false));
									System.out.println("$" + start + " is perfect!");
									Thread.sleep(1000);
									break;
								}
							}
							catch (Exception e) {
								System.out.println("Error. Try again.");
								try {
									Thread.sleep(300);
								} catch (InterruptedException es) {
									es.printStackTrace();
								}
							}
						}
					}
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

		setBlinds();
		for (; curOrbit < 4 && inRound; curOrbit++) { //curOrbit already set to zero from resetRound()
			orbit();
		}
		//Show everyone's cards in the output
		calculateWinners();
		for (int i = 0; i < players.size(); i++) {
			System.out.println();
		}
		resetRound();
	}

	/*
	 * Executed per orbit (in a round)
	 */

	public static void orbit() {

		DisplayManager.wipeConsole();
		switch(curOrbit) { //give appropriate actions for each orbit.
		case 0:
			System.out.println("—————————————————————————————————");
			System.out.println("| Welcome to the Preflop round. |");
			System.out.println("—————————————————————————————————");
			DisplayManager.globalConsole.add("The preflop round has started.");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 1:

			System.out.println("—————————————————————————————————");
			System.out.println("| Welcome to the Flop round.    |");
			System.out.println("—————————————————————————————————");
			DisplayManager.globalConsole.add("The flop round has started.");

			//add cards to table from stack
			Card c1 = cardsOnStack.get((int) Math.random() * cardsOnStack.size());
			cardsOnStack.remove(c1);
			Card c2 = cardsOnStack.get((int) Math.random() * cardsOnStack.size());
			cardsOnStack.remove(c2);
			Card c3 = cardsOnStack.get((int) Math.random() * cardsOnStack.size());
			cardsOnStack.remove(c3);
			cardsOnTable.add(c1);
			cardsOnTable.add(c2);
			cardsOnTable.add(c3);
			DisplayManager.globalConsole.add("3 cards are now on the table.");

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("—————————————————————————————————");
			System.out.println("| Welcome to the Turn round.    |");
			System.out.println("—————————————————————————————————");
			DisplayManager.globalConsole.add("The turn round has started.");
			
			Card c4 = cardsOnStack.get((int) Math.random() * cardsOnStack.size());
			cardsOnStack.remove(c4);
			cardsOnTable.add(c4);
			
			DisplayManager.globalConsole.add("4 cards are now on the table.");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("—————————————————————————————————");
			System.out.println("| Welcome to the River round.   |");
			System.out.println("—————————————————————————————————");
			DisplayManager.globalConsole.add("The river round has started.");
			
			Card c5 = cardsOnStack.get((int) Math.random() * cardsOnStack.size());
			cardsOnStack.remove(c5);
			cardsOnTable.add(c5);
			
			DisplayManager.globalConsole.add("5 cards are now on the table.");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		boolean exit = false;
		int start = 0; //get the starting person
		for(int i = 0; i < players.size(); i++) {
			if(curOrbit == 0) {
				if(players.get(i).name.equals(bigBlind.name)) {
					if(i == players.size()-1) {
						start = 0;
					}
					else {
						start = i+1;
					}
				}
			}
			else {
				if(players.get(i).name.equals(smallBlind.name)) {
					start = i;
				}
			}
		}
		orbitEnd = start;
		int cur = start;
		do {
			curPlayer = cur;
			if(players.get(curPlayer).stillInGame && players.get(curPlayer).stillInRound) {
				//Player's turn
				HashMap<String, Runnable> options = getOptions(players.get(curPlayer)); //get what options the player can do

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

	public static HashMap<String, Runnable> getOptions(Player player) {
		HashMap<String, Runnable> hash = new HashMap<>();
		
		
		return hash;
	}

	/*
	 * Resets the round (happens several times in a game)
	 */

	public static void resetRound() {
		curOrbit = 0;
		curPlayer = 0;
		orbitEnd = 0;
		cardsOnStack = new ArrayList<>();
		cardsOnTable = new ArrayList<>();	
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
	}
	
	public static void calculateWinners() {

	}
	
	public static void playerTurn(Player player, HashMap<String, Runnable> options) {
		HashMap<String, String> contextAssemble = new HashMap<>();
		for(int i = 0; i < options.size(); i++) {
			contextAssemble.put(i + "", new ArrayList<>(options.keySet()).get(i));
		}
		DisplayManager.displayContext(contextAssemble);
		String input = scan.next();

	}
}
