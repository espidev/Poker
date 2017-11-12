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

		hash.put("Reveal Cards", (Player p) -> {
			System.out.println("Press enter to hide the cards.");
			for(Card c : p.cards) {
				System.out.println("| (" + c.number + " " + c.suit.toString() + ") |");
			}
			scan.nextLine();
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
			System.out.println("——————————————————————————————————————————————————————————");
			System.out.println("| Player " + p.name + "'s cards  (with the cards on table).");
			System.out.println("——————————————————————————————————————————————————————————");

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

			System.out.println("——————————————————————————————————————————————————————————");
			System.out.println("\n\n");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
			int score = Evaluator.evaluateScore(69, cards); //TODO LOL
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
		boolean notFound = true, showCards = false;
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
						if(contextAssemble.get(str).equals("Reveal Cards")) {
							showCards = true;
						}
						break;
					}
				}
			}
			if(notFound) {
				if(showCards) {
					DisplayManager.wipeConsole();
					DisplayManager.displayContextRedo(contextAssemble);
					showCards = false;
				}
				else {
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
