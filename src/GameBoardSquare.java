import java.util.ArrayList;
import java.util.List;

/** Represents a square on the monopoly board
 * @author Daniel Wang
 */
public abstract class GameBoardSquare{
	private SquareType type;
	private String name;
	private List<Player> playersOnSquare;


	/**
	 * Constructor for GameBoardSquare
	 */
	public GameBoardSquare(SquareType type, String name){
		this.type = type;
		this.name = name;
		this.playersOnSquare = new ArrayList<>();
	}

	/**
	 * Add Player p on the Arraylist of playersOnSquare
	 */
	public void addPlayerToSquare(Player p){
		playersOnSquare.add(p);
	}

	/**
	 * Remove Player p on the Arraylist of playersOnSquare
	 */

	public void removePlayerFromSquare(Player p){
		playersOnSquare.remove(p);
	}

	/**
	 * Returns list of Players currently on square
	 * @return the list of Players
	 */
	public List<Player> getPlayersOnSquare() {
		return playersOnSquare;
	}

	/**
	 * Returns the name of the GameBoardSquare
	 * @return string name of the GameBoardSquare
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Returns the description of the GameBoardSquare
	 * @return string description of GameBoardSquare
	 */
	public String toString(){
		return this.name + "\nType: " + this.type;

	}
}