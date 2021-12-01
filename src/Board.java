import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Represents the Board
 * @author Daniel Wang
 */
public class Board extends DefaultHandler implements Serializable {

	/** The list of GameBoardSquare objects */
	private List<GameBoardSquare> board;
	private GameBoardSquare tempSquare;
	private StringBuffer tagContent;

	/**
	 * Default, only constructor. Defines board
	 */
	public Board(String filename){
		this.board = new ArrayList<>();

		try {
			SAXParserFactory fact = SAXParserFactory.newInstance();
			SAXParser saxParser = fact.newSAXParser();
			saxParser.parse(filename, this);
		} catch(Exception e){
			e.printStackTrace();
		}
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
	 * @return the board size
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

	private PropertyType getPropertyTypeFromString(String type){
		for(PropertyType possible: PropertyType.values()){
			if(possible.toString().equals(type)){
				return possible;
			}
		}
		return null;
	}

	private SquareType getSquareTypeFromString(String type){
		for(SquareType possible: SquareType.values()){
			if(possible.toString().equals(type)){
				return possible;
			}
		}
		return null;
	}

	@Override
	public void startDocument(){
		System.out.println("Reading board xml");
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		switch(qName){
			case "SpecialSquare":
				tempSquare = new SpecialSquare();
				break;
			case "Property":
				tempSquare = new Property();
				break;
			default:
				tagContent = new StringBuffer();
				break;
		}
	}

	@Override
	public void endElement(String url, String localName, String qName){
		switch(qName){
			case "name":
				tempSquare.setName(tagContent.toString());
				break;
			case "type":
				tempSquare.setType(getSquareTypeFromString(tagContent.toString()));
				break;
			case "cost":
				((Property)tempSquare).setCost(Integer.parseInt(tagContent.toString()));
				break;
			case "propertyType":
				((Property)tempSquare).setPropertyType(getPropertyTypeFromString(tagContent.toString()));
				break;
			case "colourSetSize":
				((Property)tempSquare).setColourSetSize(Integer.parseInt(tagContent.toString()));
				break;
			case "SpecialSquare":
			case "Property":
				addSquare(tempSquare);
				break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length){
		String info = new String(ch, start, length);
		if(!info.equals("\n")) {
			tagContent.append(info);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("Done reading board xml");
	}

	public static void main (String[] args){
		new Board("monopolyXML.xml");
	}

}