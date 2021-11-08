import java.util.ArrayList;
import java.util.List;

/** Represents a square on the monopoly board
 * @author Team 12
 * @author Giancarlo Salvador, Spencer Antliff, Robbie Kuhn, Daniel Wang
 */
public abstract class GameBoardSquare{

	/** The type of square */
	private SquareType type;
	/** The name of the square */
	private String name;
	/** The players currently on the square */
	private List<Player> playersOnSquare;

	/**
	 * Only constructor
	 * @param type The type of square
	 * @param name The name of the square
	 */
	public GameBoardSquare(SquareType type, String name){
		this.type = type;
		this.name = name;
		this.playersOnSquare = new ArrayList<>();
	}

	/**
	 * Adds a player to the square
	 * @param p the player to add
	 */
	public void addPlayerToSquare(Player p){
		playersOnSquare.add(p);
	}

	/**
	 * Removes a player from the square
	 * @param p the player to remove
	 */
	public void removePlayerFromSquare(Player p){
		playersOnSquare.remove(p);
	}

	/**
	 * Getter for Players on square method
	 * @return The list of players on the square
	 */
	public List<Player> getPlayersOnSquare() {
		return playersOnSquare;
	}

	/**
	 * Getter for the name of the square
	 * @return
	 */
	public String getName(){
		return this.name;
	}

	@Override
	public String toString(){
		return this.name + "\nType: " + this.type;

	}
}