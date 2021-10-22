public abstract class GameBoardSquare{
	private SquareType type;
	private String name;
	public GameBoardSquare(SquareType type, String name){
		this.type = type;
		this.name = name;
	}
	public String getName(){
		return this.name;
	}

	public String toString(){
		String outputString = "Name: " + this.name + "\nType: " + this.type;
		return outputString;
	}
}