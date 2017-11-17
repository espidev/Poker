import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * Poker class: The main class for the game
 */


public class Poker {

	/*
	 * cardsOnStack: list of cards that is in the deck (not in play)
	 * cardsOnTable: list of cards that are shown and on the table
	 * players: list of player objects
	 */
	
	public static List<Card> cardsOnStack = new ArrayList<>(), cardsOnTable = new ArrayList<>();
	public static List<Player> players = new ArrayList<>();

	public static Scanner scan = new Scanner(System.in);

	/*
	 * bigBlind: player object that is the big blind
	 * smallBlind: player object that is the small blind
	 */
	
	public static Player bigBlind = null, smallBlind = null;

	/*
	 * curPlayer: the index of the current player
	 * curOrbit: the index of the current orbit
	 * numOfDead: number of players that are out of the game
	 * orbitEnd: The index of the player at which the orbit will end (changed when someone raises or bets)
	 * prevBet: The highest previous bet
	 * startingBalance: The amount of money each player starts with
	 * pot: amount of money in the pot
	 */
	
	public static int curPlayer, curOrbit, numOfDead = 0, orbitEnd = 0, prevBet = 0, startingBalance = 50, pot = 0;

	/*
	 * inRound: true if the game is in a round
	 * inGame: true if the game is in progress
	 * defaultStarting: true if there will be a default starting balance.
	 */
	
	public static boolean inRound = false, inGame = false, defaultStarting = true;

	/*
	 * Sleep for specified milliseconds
	 */

	public static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Set the blinds at the beginning of each round.
	 */

	public static void setBlinds() {
		if(bigBlind == null || smallBlind == null || (bigBlind == null && smallBlind == null)) { //if the big blind and the small blind haven't been set yet
			int rand = (int) (Math.random()*players.size());
			int n2 = (rand == players.size()-1) ? 0 : rand+1 ;
			bigBlind = players.get(rand); //get a random big blind
			smallBlind = players.get(n2); //the small blind is the person after
		}
		else { //the big blind and small blind will go to the people next to the current ones
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
		smallBlind.money -= 1;
		prevBet = 2;

		DisplayManager.globalConsole.add(smallBlind.name + " is now the small blind.");
		DisplayManager.globalConsole.add(bigBlind.name + " is now the big blind.");
		DisplayManager.globalConsole.add(smallBlind.name + " has bet $1.");
		DisplayManager.globalConsole.add(bigBlind.name + " has bet $2.");
	}

	/*
	 * Program Entrance
	 */

	public static void main(String[] args) {
		while(true) {
			MainMenu.displayMainMenu();
			sleep(3000);
			DisplayManager.wipeConsole();
		}
	}

	/*
	 * Method to call when starting a new game.
	 */

	public static void startGame() {

		resetGame(); //Reset the game before starting.
		SetupManager.prepareGame(); //Start the preparation process.

		System.out.println("Starting game...");

		sleep(2000);

		inGame = true;

		while (inGame) {

			//Pick cards for the players for each round.
			for(Player p : players) {
				if(p.stillInGame) {
					Card c1 = cardsOnStack.get((int) (Math.random() * cardsOnStack.size()));
					cardsOnStack.remove(c1);
					Card c2 = cardsOnStack.get((int) (Math.random() * cardsOnStack.size()));
					cardsOnStack.remove(c2);
					p.cards.add(c1);
					p.cards.add(c2);

					p.stillInRound = true;
				}
			}

			//Starts a round of poker
			round();

			//Check that everyone has enough money to play another round.
			for(Player p : players) {
				if(p.money < 2 && p.stillInGame) {
					System.out.println(p.name + " does not have enough money to continue playing.");
					System.out.println(p.name + " has left the game.");
					DisplayManager.globalConsole.add(p.name + " does not have enough money to continue playing.");
					DisplayManager.globalConsole.add(p.name + " has left the game.");
					p.stillInGame = false;
					p.orderOfDeath = numOfDead + 1;
					p.stillInRound = false;
					numOfDead++;
				}
			}

			//Check if there is one person left
			int playersStillInGame = 0;
			for(Player p : players) {
				if(p.stillInGame) {
					playersStillInGame++;
				}
			}

			if(playersStillInGame < 2) { //if only one person is left in the game
				System.out.println("The game has ended, since there is only one or less people left with money.");
				for(Player p : players) {
					if(p.stillInGame) {
						System.out.println(p.name + " has won poker! ($" + p.money + ")");
					}
				}

				//horrible algorithm but it works TODO
				//List<Player> losing = new ArrayList<>();

				sleep(9000);
				inGame = false;
				break; //exit game loop
			}

			//Get input from user if the game should continue.
			InputManager<String> im = new InputManager<>();

			HashMap<String, Runnable> hash = new HashMap<>();

			hash.put("y", () -> { //If the player inputed y
				System.out.println("Okay.");
			});
			hash.put("n", () -> { //If the player inputed n
				System.out.println("Okay.");
				inGame = false; //leave game (exit loop)
			});

			im.getInput("Do you want the game to continue? (y/n)", hash);
		}
	}

	/*
	 * Executed per round (in a game)
	 */

	public static void round() {

		inRound = true;

		setBlinds();
		for (; curOrbit < 4 && inRound; curOrbit++) { //curOrbit already set to zero from resetRound()
			orbit(); //4 orbits per round.
		}

		System.out.println("The round has now ended. Now showing all of the players's cards.");
		sleep(3000);

		//Show everyone's cards in the output
		WinManager.displayPlayerCards();

		//Calculate the winners of the round.
		WinManager.calculateWinners();

		//Reset the round to get ready for the next one
		resetRound();
	}

	/*
	 * Add random card to the table
	 */

	public static void addCardToTable() {
		Card c = cardsOnStack.get((int) (Math.random() * cardsOnStack.size()));
		cardsOnStack.remove(c);
		cardsOnTable.add(c);
	}

	/*
	 * Print the message when a round starts.
	 */

	public static void printRoundMessage(String edit) {
		System.out.println("—————————————————————————————————");
		System.out.println("| Welcome to the " + edit + " round.");
		System.out.println("—————————————————————————————————");
		DisplayManager.globalConsole.add("The " + edit + " round has started.");
	}

	/*
	 * Code to decide who starts the orbit.
	 */

	public static int getWhoStarts() {
		int start = 0; //get the starting person
		
		for(int i = 0; i < players.size() && inRound; i++) { //Loop through all of the players
			if(curOrbit == 0) { //If it's the preflop round, get the person next to the big blind starts.
				if(players.get(i).name.equals(bigBlind.name)) { //find big blind
					if(i == players.size()-1) {
						i = 0;
					}
					else {
						i = i+1;
					}
					for(int j = i; ;) { //if the person next to the big blind is out, go to the next person
						if(j == players.size()-1) {
							j = 0;
						}
						else {
							j++;
						}
						if(players.get(j).stillInGame && players.get(j).stillInRound) {
							start = j;
							break;
						}
					}
					break;
				}
			}
			else { //not preflop round
				if(players.get(i).name.equals(smallBlind.name)) { // on every orbit except the first orbit, the small blind starts.
					for(int j = i; ;) { //if the small blind is out, go to the next person
						System.out.println("j" + j);

						if(j == players.size()-1) {
							j = 0;
						}
						else {
							j++;
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
		return start;
	}

	/*
	 * Executed per orbit (in a round)
	 */

	public static void orbit() {

		DisplayManager.wipeConsole();

		switch(curOrbit) { //give appropriate actions for each orbit.
		case 0:
			printRoundMessage("Preflop");
			sleep(2000);
			break;
		case 1:
			prevBet = 0; //Reset the bet.
			printRoundMessage("Flop");
			//add cards to table from stack
			for(int i = 0; i < 3; i++) addCardToTable();
			DisplayManager.globalConsole.add("3 cards are now on the table.");
			sleep(2000);
			break;
		case 2:
			prevBet = 0; //Reset the bet.
			printRoundMessage("Turn");
			//add card to table from stack
			addCardToTable();

			DisplayManager.globalConsole.add("4 cards are now on the table.");
			sleep(2000);
			break;
		case 3:
			prevBet = 0; //Reset the bet.
			printRoundMessage("River");
			//add card to table from stack
			addCardToTable();

			DisplayManager.globalConsole.add("5 cards are now on the table.");
			sleep(2000);
			break;
		}

		/*
		 * Loop through people until you reach the last person that raised, or it's back to the starting person.
		 */

		int start = getWhoStarts(); //Player who starts the orbit (the index in the array)

		orbitEnd = start;
		int cur = start;
		do {
			curPlayer = cur;
			if(players.get(curPlayer).stillInGame && players.get(curPlayer).stillInRound) {
				//Player's turn
				HashMap<String, BooleanOperation> options = getOptions(players.get(curPlayer)); //get what options the player can do

				DisplayManager.globalConsole.add(players.get(curPlayer).name + "'s turn.");

				if(players.get(curPlayer).isAI) {
					AI.calculateTurn(players.get(curPlayer), options); //go to AI calculate turn if the player is an AI
				}
				else {
					playerTurn(players.get(curPlayer), options); //go to player turn if the player is not an AI
				}
			}
			if(cur == players.size()-1) { //go from array size back to zero (to loop around players)
				cur = 0;
			}
			else {
				cur++;
			}
		}
		while(cur != orbitEnd && inRound);

		//move the money to the pot at the end of an orbits.
		for(Player p : players) {
			pot += p.betMoney;
			p.betMoney = 0;
			p.tempMoney = p.money;
		}
	}

	/*
	 * Return a list of options that the player can choose to do
	 */

	public static HashMap<String, BooleanOperation> getOptions(Player player) {
		HashMap<String, BooleanOperation> hash = new HashMap<>(); //List of options that the player can do (name, option)

		//the player can fold whenever
		hash.put("Fold", (Player p) -> { //lambda list 
			return Actions.fold(p);
		});

		if(!player.allIn) { //if the player isn't all-in
			if(player.money + player.betMoney > Poker.prevBet) { //if the player has enough money to call or raise
				if(prevBet == 0) { //if no one has betted yet
					hash.put("Check", (Player p) -> {
						return Actions.check(p);
					});
					hash.put("Bet", (Player p) -> {
						if(p.isAI) {
							int a = ((int) (Math.random() * 5)) + 1;
							return Actions.bet(p, a);
						}
						else {
							while(true) {
								try {
									System.out.println("How much do you want to bet? (c to cancel)");
									String in = scan.nextLine();

									if(in.equalsIgnoreCase("c")) {
										System.out.println("Okay. Exiting bet.");
										return false; //cancel the action
									}

									int input = Integer.parseInt(in);
									if(input > p.money) {
										System.out.println("You don't have enough money!");
									}
									else if(input <= prevBet) {
										System.out.println("Please enter a positive integer above 0.");
									}
									else if(input == p.money) {
										System.out.println("Use all-in to bet all of your money.");
									}
									else {
										return Actions.bet(p, input);
									}
								}
								catch(Exception e) {
									System.out.println("Error. Try again.");
								}
							}
						}
					});
				}
				else { //if people have already bet
					hash.put("Call", (Player p) -> {
						return Actions.call(p);
					});
					hash.put("Raise", (Player p) -> {
						if(p.isAI) {
							int a = ((int) (Math.random() * 5)) + 1;
							return Actions.raise(p, a);
						}
						else {
							while(true) {
								try {
									System.out.println("How much do you want to raise the bet by? (c to cancel)");
									String in = scan.nextLine();

									if(in.equalsIgnoreCase("c")) {
										System.out.println("Okay. Exiting raise.");
										return false; //cancel the action
									}

									int input = Integer.parseInt(in);
									if(input > p.money) {
										System.out.println("You don't have enough money!");
									}
									else if(input <= 0) {
										System.out.println("This value is too low! The bet is currently $" + prevBet + ".");
									}
									else if(input == p.money) {
										System.out.println("Use all-in to bet all of your money.");
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
			hash.put("All-In", (Player p) -> { //player should be able to go all-in
				return Actions.allIn(p);
			});
		}
		else { //the player has already went all-in
			hash.put("Check", (Player p) -> {
				return Actions.check(p);
			});
		}

		//add show cards to allow human players to show their cards (AI's don't need this option)
		hash.put("Show Cards", (Player p) -> {
			System.out.println("Press enter to hide the cards.");
			String assemble = "";

			for(Card c : p.cards) {
				assemble += c.getCard(); //unicode representation of card
			}
			System.out.println(assemble);

			for(Card c : p.cards) {
				System.out.println("| " + c.getCardOutput() + " " + Suit.getSuit(c.suit) + " |");
			}

			scan.nextLine(); //when player presses enter
			return false;
		});
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
		pot = 0;
		cardsOnStack = new ArrayList<>();
		cardsOnTable = new ArrayList<>();	
		inRound = false;
		DisplayManager.globalConsole = new ArrayList<>();
		//add all 52 cards to the deck.
		for(int i = 2; i < 14; i++) {
			cardsOnStack.add(new Card(i, Suit.CLOVER));
			cardsOnStack.add(new Card(i, Suit.DIAMOND));
			cardsOnStack.add(new Card(i, Suit.HEART));
			cardsOnStack.add(new Card(i, Suit.SPADE));
		}
		for(Player p : players) {
			p.allIn = false;
			p.betMoney = 0;
			p.cards = new ArrayList<>();
			p.stillInRound = true;
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

	/*
	 * Executed when it is a human player's turn.
	 */

	public static void playerTurn(Player player, HashMap<String, BooleanOperation> options) {
		HashMap<String, String> contextAssemble = new HashMap<>(); //assemble context for the console output
		for(int i = 0; i < options.size(); i++) {
			contextAssemble.put(i + "", new ArrayList<>(options.keySet()).get(i));
		}

		DisplayManager.displayContext(contextAssemble); //show the console prompt (the menu when it's a human player's turn)

		boolean notFound = true, showCards = false; //notFound is true if the input was not found, and showCards is true when the player wants to reveal cards.

		//handle player's input
		while(notFound) {
			String input = scan.nextLine();//get input from player
			for(String str : contextAssemble.keySet()) { //iterate over possible player options
				if(str.equals(input)) { //if the player's input matches an option
					if(options.get(contextAssemble.get(str)).run(player)) { //executes the player option based on input, and return true if it worked.
						sleep(1000);
						notFound = false; //exit the infinite loop to get console input, and go on to the next player's turn.
					}
					else {
						if(contextAssemble.get(str).equals("Show Cards")) { //if the player wanted to reveal cards, loop around the loop
							showCards = true;
						}
						break;
					}
				}
			}
			if(notFound) { //if the input was not found.
				if(showCards) { //if the player wanted to reveal cards, wipe the screen and display the context menu again.
					DisplayManager.wipeConsole();
					DisplayManager.displayContext(contextAssemble);
					showCards = false;
				}
				else { //incorrect input
					System.out.println("Error. Try again.");
					sleep(500);
				}
			}
		}
	}
}
