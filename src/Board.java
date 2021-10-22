import java.util.ArrayList;

public class Board {
	private static final int BOARDSIZE = 40;
	private ArrayList <GameBoardSquare> board;

	
	public Board(){
		this.board = new ArrayList<GameBoardSquare>();
	}

	public void addSquare(GameBoardSquare square){
		
		this.board.add(square);
	}

	public int getSize(){
		return board.size();
	}

	public GameBoardSquare getSquare(int index){
		return board.get(index);
	}
	
}