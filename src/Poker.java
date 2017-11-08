import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Poker {
	public static List<Card> cardsOnStack = new ArrayList<>(), cardsOnTable = new ArrayList<>();
	public static List<Player> players = new ArrayList<>();

	public static Scanner scan = new Scanner(System.in);

	public static Player bigBlind, smallBlind;

	public static int curPlayer, curOrbit;

	public static boolean inRound = false;

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
		prepareGame();

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
		int numOfPlayers, startingBalance;
		boolean defaultStarting;
		
		while(true) {
			try {
				System.out.println("How many players will there be in the game?");
				numOfPlayers = scan.nextInt();
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
		
		while(true) {
			try {
				System.out.println("Should there be a default starting balance? (y/n)");
				String input = scan.next();
				if(input.equals("y")) {
					defaultStarting = true;
					while(true) {
						try {
							System.out.println("How much should it be?");
							int num = scan.nextInt();
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
				}
				else if(input.equals("n")) {
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
		
		System.out.println("Now on to preparing the players.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < numOfPlayers; i++) {
			System.out.println("Player " + (i+1) + " assembly.");
		}
	}
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
