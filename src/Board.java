import java.util.ArrayList;
import java.util.List;

/** Represents the Board
 * @author Daniel Wang
 */
public class Board {
	/** Maximum number of squares on the board */
	private static final int BOARDSIZE = 40;
	/** The list of GameBoardSquare objects */
	private List<GameBoardSquare> board;
	private List<GameView> views;
	/**
	 * Default, only constructor. Defines board
	 */
	public Board(){
		this.board = new ArrayList<>();
		this.views = new ArrayList<>();
		SpecialSquare emptySquare = new SpecialSquare("empty",SquareType.EMPTY);
		Property Mediterranean_Avenue = new Property("Mediterranean Avenue",60,2,PropertyType.BROWN);
		Property Baltic_Avenue = new Property("Baltic Avenue",60,2,PropertyType.BROWN);
		Property Oriental_Avenue = new Property("Oriental Avenue", 100, 3,PropertyType.LIGHTBLUE);
		Property Vermont_Avenue = new Property("Vermont Avenue", 100, 3,PropertyType.LIGHTBLUE);
		Property Connecticut_Avenue = new Property("Connecticut Avenue", 120, 3,PropertyType.LIGHTBLUE);
		Property St_Charles_Place = new Property("St Charles Place", 140, 3,PropertyType.PINK);
		Property States_Avenue = new Property("States Avenue", 140, 3,PropertyType.PINK);
		Property Virginia_Avenue = new Property("Virginia Avenue", 160, 3,PropertyType.PINK);
		Property St_James_Place = new Property("St James Place", 180, 3,PropertyType.ORANGE);
		Property Tennessee_Avenue = new Property("Tennessee Avenue", 180, 3,PropertyType.ORANGE);
		Property New_York_Avenue = new Property("New York Avenue", 200, 3,PropertyType.ORANGE);
		Property Kentucky_Avenue = new Property("Kentucky Avenue", 220, 3,PropertyType.RED);
		Property Indiana_Avenue = new Property("Indiana Avenue", 220, 3,PropertyType.RED);
		Property Illinois_Avenue = new Property("Illinois Avenue", 240, 3,PropertyType.RED);
		Property Atlantic_Avenue = new Property("Atlantic Avenue", 260, 3,PropertyType.YELLOW);
		Property Ventnor_Avenue = new Property("Ventnor Avenue", 260, 3,PropertyType.YELLOW);
		Property Marvin_Gardens = new Property("Marvin Gardens", 280, 3,PropertyType.YELLOW);
		Property Pacific_Avenue = new Property("Pacific Avenue", 300, 3,PropertyType.GREEN);
		Property North_Carolina_Avenue = new Property("North Carolina Avenue", 300, 3,PropertyType.GREEN);
		Property Pennsylvania_Avenue = new Property("Pennsylvania Avenue", 320, 3,PropertyType.GREEN);
		Property Park_Place = new Property("Park Place", 350, 2,PropertyType.BLUE);
		Property Boardwalk = new Property("Boardwalk", 400, 2,PropertyType.BLUE);
		addSquare(emptySquare); //GO 0
		addSquare(Mediterranean_Avenue); // 1
		addSquare(emptySquare); //community chest 2
		addSquare(Baltic_Avenue); // 3
		addSquare(emptySquare); //income tax 4
		addSquare(emptySquare); //reading railroad 5
		addSquare(Oriental_Avenue); // 6
		addSquare(emptySquare); //chance 7
		addSquare(Vermont_Avenue); // 8
		addSquare(Connecticut_Avenue); // 9
		addSquare(emptySquare); //jail 10
		addSquare(St_Charles_Place); //11
		addSquare(emptySquare); //Electric Company 12
		addSquare(States_Avenue); // 13
		addSquare(Virginia_Avenue); // 14
		addSquare(emptySquare); // Pennsylvania Railroad 15
		addSquare(St_James_Place); // 16
		addSquare(emptySquare); // Community Chest 17
		addSquare(Tennessee_Avenue); //18
		addSquare(New_York_Avenue); //19
		addSquare(emptySquare); //Free Parking 20
		addSquare(Kentucky_Avenue); //21
		addSquare(emptySquare); //chance 22
		addSquare(Indiana_Avenue); //23
		addSquare(Illinois_Avenue); //24
		addSquare(emptySquare); //B. & O. Railroad 25
		addSquare(Atlantic_Avenue); // 26
		addSquare(Ventnor_Avenue); //27
		addSquare(emptySquare); //Water Works 28
		addSquare(Marvin_Gardens); //29
		addSquare(emptySquare); //goToJail 30
		addSquare(Pacific_Avenue); //31
		addSquare(North_Carolina_Avenue); //32
		addSquare(emptySquare); //Community Chest 33
		addSquare(Pennsylvania_Avenue); //34
		addSquare(emptySquare); //Short Line (railroad) 35
		addSquare(emptySquare); //chance 36
		addSquare(Park_Place); //37
		addSquare(emptySquare); //LuxuryTax 38
		addSquare(Boardwalk); //39
	}

	/**
	 * Adds a square to the board
	 * @param square The GameBoardSquare to be added
	 */
	public void addSquare(GameBoardSquare square){
		this.board.add(square);
	}

	/**
	 * Returns the size of the board
	 * @return
	 */
	public int getSize(){
		return board.size();
	}

	/**
	 * Returns the square at a certain index
	 * @param index The position of the square to be taken
	 * @return The square at the certain index
	 */
	public GameBoardSquare getSquare(int index){
		return board.get(index);
	}

	/**
	 * Returns the entire board
	 * @return All squares on the board
	 */
	public List<GameBoardSquare> getBoard() {
		return board;
	}

	public void addBoardView(GameView view){
		views.add(view);
	}

	public void removeBoardView(GameView view){
		views.remove(view);
	}

	public List<List<Player>> findThePlayers(){
		List<List<Player>> playerLocations = new ArrayList<>();
		for(GameBoardSquare bs : this.getBoard()){
			playerLocations.add(bs.getPlayersOnSquare());
		}
		for(GameView v: this.views){
			v.update(new MonopolyEvent(this,MonopolyEvent.EventType.ROLL));
		}
		return playerLocations;
	}
}