import java.util.ArrayList;
import java.util.List;

public class Board {
	private static final int BOARDSIZE = 40;
	private List<GameBoardSquare> board;

	
	public Board(){
		this.board = new ArrayList<>();
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
		addSquare(emptySquare); //GO
		addSquare(Mediterranean_Avenue);
		addSquare(emptySquare); //community chest
		addSquare(Baltic_Avenue);
		addSquare(emptySquare); //income tax
		addSquare(emptySquare); //reading railroad
		addSquare(Oriental_Avenue);
		addSquare(emptySquare); //chance
		addSquare(Vermont_Avenue);
		addSquare(Connecticut_Avenue);
		addSquare(emptySquare); //jail
		addSquare(St_Charles_Place);
		addSquare(emptySquare); //Electric Company
		addSquare(States_Avenue);
		addSquare(Virginia_Avenue);
		addSquare(emptySquare); // Pennsylvania Railroad
		addSquare(St_James_Place);
		addSquare(emptySquare); // Community Chest
		addSquare(Tennessee_Avenue);
		addSquare(New_York_Avenue);
		addSquare(emptySquare); //Free Parking
		addSquare(Kentucky_Avenue);
		addSquare(emptySquare); //chance
		addSquare(Indiana_Avenue);
		addSquare(Illinois_Avenue);
		addSquare(emptySquare); //B. & O. Railroad
		addSquare(Atlantic_Avenue);
		addSquare(Ventnor_Avenue);
		addSquare(emptySquare); //Water Works
		addSquare(Marvin_Gardens);
		addSquare(emptySquare); //goToJail
		addSquare(Pacific_Avenue);
		addSquare(North_Carolina_Avenue);
		addSquare(emptySquare); //Community Chest
		addSquare(Pennsylvania_Avenue);
		addSquare(emptySquare); //Short Line (railroad)
		addSquare(emptySquare); //chance
		addSquare(Park_Place);
		addSquare(emptySquare); //LuxuryTax
		addSquare(Boardwalk);




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
	public List<GameBoardSquare> getBoard() {
		return board;
	}
	
}