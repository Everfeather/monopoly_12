import java.util.ArrayList;

public class Board {
	private static final int BOARDSIZE = 40;
	private ArrayList <GameBoardSquare> board;

	
	public Board(){
		this.board = new ArrayList<GameBoardSquare>(BOARDSIZE);
		//this.board = new Arraylist<GameBoardSquare>();

		
	}
	public void addSquare(GameBoardSquare square){
		
		this.board.add(square);
	}
	
}