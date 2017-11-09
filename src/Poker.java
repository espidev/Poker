import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
 * ADD A TODO HEADER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

public class Poker {

	public static List<Card> cardsOnStack = new ArrayList<>(), cardsOnTable = new ArrayList<>();
	public static List<Player> players = new ArrayList<>();

	public static Scanner scan = new Scanner(System.in);

	public static Player bigBlind, smallBlind;

	public static int curPlayer, curOrbit;

	public static boolean inRound = false, inGame = false;

	public static int getPot() {
		int sum = 0;
		for(Player p : players) {
			sum += p.betMoney;
		}
		return sum;
	}

	public static void setBlinds() {
		int rand = (int) Math.random()*players.size();
		int n2 = (rand == players.size()-1) ? 0 : rand+1 ;
		bigBlind = players.get(rand);
		smallBlind = players.get(n2);
	}

	public static void main(String[] args) {
		while(true) {
			MainMenu.displayMainMenu();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			clearConsole();
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
							System.out.println("How much should it be? (integer)");
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
						System.out.println("How much should this player start with? (Starting balance integer)");
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
						System.out.println("How much should this player start with? (Starting balance integer)");
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
		setBlinds();
		for (; curOrbit < 4 && inRound; curOrbit++) {
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
		boolean exit = false;
		int a; //blind
		while(!exit) {

		}
	}
	public static List<Runnable> getOptions(Player player) {

	}
	public static void resetRound() {
		curOrbit = 0;
		curPlayer = 0;
		cardsOnStack = new ArrayList<>();
		cardsOnTable = new ArrayList<>();	
		for(int i = 1; i < 13; i++) {
			cardsOnStack.add(new Card(i, Suit.CLOVER));
			cardsOnStack.add(new Card(i, Suit.DIAMOND));
			cardsOnStack.add(new Card(i, Suit.HEART));
			cardsOnStack.add(new Card(i, Suit.SPADE));
		}

	}
	public static void resetGame() {
		resetRound();
		players = new ArrayList<>();
		bigBlind = null;
		smallBlind = null;
		curOrbit = 0;
	}
	public static void calculateWinners() {

	}
	public static void playerTurn(Player player, List<Runnable> options) {

	}
	public static void clearConsole() {
		for(int i = 0; i < 500; i++) {
			System.out.println("\n");
		}
	}
}
