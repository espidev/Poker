import java.util.HashMap;

public class InputManager<T> {

	/*
	 * Using generics to obtain type
	 */

	/*
	 * Get input for user on console with specific input
	 * 
	 * Parameters:
	 * String prompt: The prompt to display to the user for input.
	 * HashMap<String, Runnable> validInput: A list of valid input (keys), with the value being the runnable to run when the input is found.
	 */
	
	public void getInput(String prompt, HashMap<String, Runnable> validInput) {
		while(true) {
			try {
				System.out.println(prompt); //print prompt
				String input = Poker.scan.nextLine();
				for(String str : validInput.keySet()) { //Loop through all of the keys
					if(input.equalsIgnoreCase(str)) { //If the input was found in the keys
						validInput.get(str).run(); //Run the value 
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
	 * 
	 * Parameters:
	 * String prompt: The prompt to display to the user for input.
	 * RunnableInputBoolean rib: Method that runs with the user's input, and returns a boolean on whether or not it is valid.
	 * T t: Specify type (put in a String for string, int for int...)
	 */
	
	public T getInput(String prompt, RunnableInputBoolean rib, T t) { //t parameter to use type
		while(true) {
			try {
				System.out.println(prompt); //print prompt
				String input = Poker.scan.nextLine();
				if(rib.run(input)) { //if the input is valid 
					if(t instanceof Integer) { //attempt cast to integer if integer
						return (T) (Integer) Integer.parseInt(input); 
					}
					else {
						return (T) input; //attempt cast (possible cast exception)
					}
				}
			}
			catch(Exception e) { //handle errors
				System.out.println("Error. Try again.");
			}
		}
	}
}
