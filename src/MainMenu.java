import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.*;

/*
 * POKER PROGRAM
 * Names: Alex, John, Jack, Devin
 * MainMenu class: The main menu for the beginning of the game.
 */

public class MainMenu extends JFrame{
	
	public MainMenu() {
		JLabel label = new JLabel("Welcome to Poker!");
		JButton start = new JButton("Start the game.");
		//start.addActionListener((ActionEvent e) -> {
			
		//});
		JButton rules = new JButton("Rules");
		JButton credits = new JButton("Credits");
		JButton exit = new JButton("Exit");
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(label);
		panel.add(start);
		panel.add(rules);
		panel.add(credits);
		panel.add(exit);
		
		for(Component c : panel.getComponents()) {
			((JComponent) c).setAlignmentX(Component.CENTER_ALIGNMENT);
			c.setSize(150, 50);
		}
		
		add(panel, BorderLayout.CENTER);
		setSize(300,300);  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void displayMainMenu() {
		new MainMenu();
		
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

		String input = Poker.scan.nextLine();

		switch(input) {
			case "1": DisplayManager.wipeConsole();
					Poker.startGame();
					break;
			case "2": printRules();
					break;
			case "3": System.out.println("Developers: Devin Lin, Jack Li, John Li, Alex Chan");
					break;
			case "4": System.out.println("TODO!");
					break;
			case "0": System.out.println("Exiting program.");
					System.exit(0);
					break;
			default: System.out.println("Incorrect input, try again.");
					break;
		}
	}

	public static void printRules () {
		System.out.println("Here are the rules to the game:");
	}
}
