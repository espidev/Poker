import java.util.List;

public class Player {
	List<Card> cards;
	int money, betMoney, orderOfDeath; //IMPLEMENT ORDEROFDEATH TODO
	String name;
	boolean isAI, stillInRound = true, stillInGame = true, allIn = false;
}
