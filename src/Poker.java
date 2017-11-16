import java.util.ArrayList;
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
		smallBlind.money -= 1;
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
		SetupManager.prepareGame();

		System.out.println("Starting game...");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		inGame = true;

		while (inGame) {

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
			
			//Starts a round of poker
			round();

			//Check that everyone has enough money
			for(Player p : players) {
				if(p.money < 2) {
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
				try {
					System.out.println("The game has ended, since there is only one or less people left with money.");
					for(Player p : players) {
						if(p.stillInGame) {
							System.out.println(p.name + " has won! ($" + p.money + ")");
						}
					}

					//horrible algorithm but it works TODO
					List<Player> losing = new ArrayList<>();

					Thread.sleep(9000);

				}
				catch(Exception e) {
					e.printStackTrace();
				}
				break; //exit game loop
			}

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
	}

	/*
	 * Executed per round (in a game)
	 */

	public static void round() {

		inRound = true;

		setBlinds();
		for (; curOrbit < 4 && inRound; curOrbit++) { //curOrbit already set to zero from resetRound()
			orbit();
		}

		System.out.println("The round has now ended. Now showing all of the players's cards.");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Show everyone's cards in the output
		displayPlayerCards();

		//calc winners.
		calculateWinners();

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
	public static void printRoundMessage(String edit) {
		System.out.println("—————————————————————————————————");
		System.out.println("| Welcome to the " + edit + " round.");
		System.out.println("—————————————————————————————————");
		DisplayManager.globalConsole.add("The " + edit + " round has started.");
	}

	/*
	 * Executed per orbit (in a round)
	 */

	public static void orbit() {

		DisplayManager.wipeConsole();

		try {
			switch(curOrbit) { //give appropriate actions for each orbit.
			case 0:
				printRoundMessage("Preflop");
				Thread.sleep(2000);
				break;
			case 1:
				printRoundMessage("Flop");
				//add cards to table from stack
				for(int i = 0; i < 3; i++) addCardToTable();
				DisplayManager.globalConsole.add("3 cards are now on the table.");
				Thread.sleep(2000);
				break;
			case 2:
				printRoundMessage("Turn");
				//add card to table from stack
				addCardToTable();

				DisplayManager.globalConsole.add("4 cards are now on the table.");
				Thread.sleep(2000);
				break;
			case 3:
				printRoundMessage("River");
				//add card to table from stack
				addCardToTable();

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
		for(int i = 0; i < players.size() && inRound; i++) {
			if(curOrbit == 0) { //starting preflop round
				if(players.get(i).name.equals(bigBlind.name)) { //find big blind
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
		do { //TODO REDO WITH PREVIOUS PERSON THAT RAISED 
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
		while(cur != orbitEnd && inRound);
	}

	/*
	 * Return a list of options that the player can choose to do
	 */

	public static HashMap<String, BooleanOperation> getOptions(Player player) {
		HashMap<String, BooleanOperation> hash = new HashMap<>();

		hash.put("Fold", (Player p) -> { //lambda list 
			return Actions.fold(p);
		});

		if(!player.allIn) { 
			if(player.money + player.betMoney > Poker.prevBet) {
				hash.put("Call", (Player p) -> {
					return Actions.call(p);
				});
				hash.put("Raise", (Player p) -> {
					if(p.isAI) {
						int a = ((int) (Math.random() * 5)) + prevBet + 1;
						return Actions.raise(p, a);
					}
					else {
						while(true) {
							try {
								System.out.println("What do you want to raise the bet to?");
								int input = Integer.parseInt(scan.nextLine());
								if(input > p.money + p.betMoney) {
									System.out.println("You don't have enough money!");
								}
								else if(input <= prevBet) {
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
			hash.put("All-In", (Player p) -> {
				return Actions.allIn(p);
			});
		}
		else {
			hash.put("Check", (Player p) -> {
				return Actions.check(p);
			});
		}

		hash.put("Reveal Cards", (Player p) -> {
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
		DisplayManager.globalConsole = new ArrayList<>();
	}

	/*
	 * Displays the players's cards in the console.
	 */
	
	public static void displayPlayerCards() {
		for(Player p : players) {
			System.out.println("——————————————————————————————————————————————————————————");
			System.out.println("| Player " + p.name);
			System.out.print("Cards (including on table): ");

			List<Card> cards = new ArrayList<>();
			List<Card> tempHand = new ArrayList<>();
			for(Card c : cardsOnTable) {
				cards.add(c);
			}
			String assemble = "| ";
			for(Card c : p.cards) {
				assemble += c.getCard();
				cards.add(c);
			}
			System.out.println(assemble);

			for(Card c : p.cards) {
				System.out.print(c.getCardOutput() + " " + Suit.getSuit(c.suit) + "  ");
			}

			for(Card c : cardsOnTable) {
				System.out.print(c.getCardOutput() + " " + Suit.getSuit(c.suit) + "  ");
			}
						
			//evaluate hand
			int f = (Integer) Evaluator.evaluateHand(cards, tempHand).get(0);
			tempHand = (List<Card>) Evaluator.evaluateHand(cards, tempHand).get(1);
			Evaluator.Hand hand = null;
			for(Evaluator.Hand h : Evaluator.Hand.values()) {
				if(h.ordinal()+1 == f) {
					hand = h;
				}
			}
			
			System.out.println();
			System.out.println("——————————————————————————————————————————————————————————");
			System.out.print("Hand: " + hand.toString().replaceAll("_", " ") + "  ");
			for(Card z : tempHand) {
				System.out.print(z.getCardOutput() + " " + Suit.getSuit(z.suit) + "  ");
			}
			System.out.println();
			System.out.println("——————————————————————————————————————————————————————————");
			System.out.println("\n\n");
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Executed once the round is over.
	 */

	public static void calculateWinners() {
		Player m = null;

		ArrayList<Player> parray = new ArrayList<>();
		parray.addAll(players);

		//Bubble sort the players by their "hand".

		try {
			for(int i = 1; i < parray.size(); i++) {
				for(int j = 0; j < parray.size()-i; j++) {
					if(Evaluator.comparePlayers(parray.get(j), parray.get(j+1)) == (Boolean)false) {
						Player t = parray.get(j);
						parray.set(j, parray.get(j+1));
						parray.set(j+1, t);
					}
				}
			}

			m = parray.get(0);

			System.out.println("Winners in order:");
			for(Player p : parray) {
				System.out.println(p.name);
			}
			//TODO
			win(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Handle when a player wins a round.
	 */

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

	/*
	 * Executed when it is a human player's turn.
	 */

	public static void playerTurn(Player player, HashMap<String, BooleanOperation> options) {
		HashMap<String, String> contextAssemble = new HashMap<>(); //assemble context for the console output
		for(int i = 0; i < options.size(); i++) {
			contextAssemble.put(i + "", new ArrayList<>(options.keySet()).get(i));
		}
		DisplayManager.displayContextRedo(contextAssemble); //show the console prompt (the menu when it's a human player's turn)

		//handle player's input
		boolean notFound = true, showCards = false;
		while(notFound) {
			String input = scan.nextLine();//get input from player
			for(String str : contextAssemble.keySet()) { //iterate over possible player options
				if(str.equals(input)) { //if the player's input matches an option
					if(options.get(contextAssemble.get(str)).run(player)) { //executes the player option based on input, and return true if it worked.
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						notFound = false; //exit the infinite loop to get console input, and go on to the next player's turn.
					}
					else {
						if(contextAssemble.get(str).equals("Reveal Cards")) { //if the player wanted to reveal cards, loop around the loop
							showCards = true;
						}
						break;
					}
				}
			}
			if(notFound) { //if the input was not found.
				if(showCards) { //if the player wanted to reveal cards, wipe the screen and display the context menu again.
					DisplayManager.wipeConsole();
					DisplayManager.displayContextRedo(contextAssemble);
					showCards = false;
				}
				else { //incorrect input
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
}
