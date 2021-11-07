import java.util.ArrayList;
import java.util.List;

/** Represents a square on the monopoly board
 * @author Daniel Wang
 */
public abstract class GameBoardSquare{
	private SquareType type;
	private String name;
	private List<Player> playersOnSquare;



	public GameBoardSquare(SquareType type, String name){
		this.type = type;
		this.name = name;
		this.playersOnSquare = new ArrayList<>();
	}
	public void addPlayerToSquare(Player p){
		playersOnSquare.add(p);
	}
	public void removePlayerFromSquare(Player p){
		playersOnSquare.remove(p);
	}

	public List<Player> getPlayersOnSquare() {
		return playersOnSquare;
	}
	public String getName(){
		return this.name;
	}

	public String toString(){
		return this.name + "\nType: " + this.type;

	}
}