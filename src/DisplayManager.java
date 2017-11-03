import java.util.ArrayList;
import java.util.List;

public class DisplayManager {
	public static List<String> globalConsole = new ArrayList<>();
	public static void displayContext() {
		
	}
	public static void wipeConsole() {
		for(int i = 0; i < 1000; i++) System.out.println("\n");
	}
}
