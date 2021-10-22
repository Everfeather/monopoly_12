import java.util.ArrayList;
import java.util.List;

public class Board {
	private static final int BOARDSIZE = 40;
	private List board;

	
	public Board(){
		this.board = new ArrayList<GameBoardSquare>();
	}

	public void addSquare(GameBoardSquare square){
		this.board.add(square);
	}
	
}