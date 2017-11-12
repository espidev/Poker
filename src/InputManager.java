import java.util.HashMap;

public class InputManager<T> {

	/*
	 * Using generics to obtain type
	 */

	/*
	 * Get input for user on console with specific input
	 */
	
	public void getInput(String prompt, HashMap<String, Runnable> validInput) {
		while(true) {
			try {
				System.out.println(prompt);
				String input = Poker.scan.nextLine();
				for(String str : validInput.keySet()) {
					if(input.equalsIgnoreCase(str)) {
						validInput.get(str).run();
						return;
					}
				}
			}
			catch(Exception e) {
				System.out.println("Error. Try again.");
			}
		}
	}

	/*
	 * Get input for user on console with conditions
	 */
	
	public T getInput(String prompt, RunnableInputBoolean rib, T t) { //t parameter to use type
		while(true) {
			try {
				System.out.println(prompt);
				String input = Poker.scan.nextLine();
				if(rib.run(input)) {
					if(t instanceof Integer) { //attempt cast to integer if integer
						return (T) (Integer) Integer.parseInt(input);
					}
					else {
						return (T) input; //attempt cast (possible cast exception)
					}
				}
			}
			catch(Exception e) {
				System.out.println("Error. Try again.");
			}
		}
	}
}
