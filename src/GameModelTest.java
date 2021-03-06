import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for GameModel functions
 */
class GameModelTest {

    static GameModel model;
    static String filename = GameController.ORIGINAL_V;

    @BeforeAll
    public static void setUp() {
        model = new GameModel();
    }

    @BeforeEach
    public void reset(){
        model = new GameModel();
    }

    @org.junit.jupiter.api.Test
    void initializeGame() {
        new GameFrame();
        model.initializeGame(filename);
        List<Player> players = model.getPlayers();
        assertEquals(4, players.size());
        for(Player p : players){
            assertEquals(1500, p.getBalance());
        }
        Board board = model.getBoard();
        assertEquals(40, board.getSize());
        assertEquals(2, model.getDice().getDiceValues().length);



    }

    @org.junit.jupiter.api.Test
    void nextTurn() {
        new GameFrame();
        model.initializeGame(filename);
        Player curP = model.getCurrentPlayer();
        model.nextTurn();
        Player nextP = model.getCurrentPlayer();
        assertNotEquals(curP, nextP);
    }

    @org.junit.jupiter.api.Test
    void win() {
        new GameFrame();
        model.initializeGame(filename);
        model.getCurrentPlayer().setBankrupt(true);
        model.nextTurn();
        model.getCurrentPlayer().setBankrupt(true);
        model.nextTurn();
        model.getCurrentPlayer().setBankrupt(true);
        model.nextTurn();
        model.win();
        assertEquals( true, model.getGameOver());
    }

    @org.junit.jupiter.api.Test
    void buyProperty() {
        new GameFrame();
        model.initializeGame(filename);
        Property prop = new Property("test", 500, 2, PropertyType.PINK);
        model.buyProperty(prop);
        assertEquals( PropertyType.PINK, model.getCurrentPlayer().getProperties().get(prop));
        assertEquals( 1000, model.getCurrentPlayer().getBalance());
    }

    @org.junit.jupiter.api.Test
    void movePlayer() {
        model.initializeGame(filename);

        model.getDice().rollDice();
        int moveVal = model.getDice().getRollValue();
        model.movePlayer();
        assertEquals(moveVal, model.getCurrentPlayer().getCurrentPos(), "Player not moving correctly");

        model.getDice().setRollValue(40);
        model.movePlayer();
        assertEquals(moveVal, model.getCurrentPlayer().getCurrentPos(), "Player not crossing go correctly");

    }

    @org.junit.jupiter.api.Test
    void testUtility(){
        model.initializeGame(filename);
        model.getDice().setRollValue(12);
        model.movePlayer();
        model.buyProperty((Property)model.getBoard().getBoard().get(model.getCurrentPlayer().getCurrentPos()));
        model.nextTurn();
        model.movePlayer();
        assertEquals(model.getSTARTINGBALANCE() - 48, model.getCurrentPlayer().getBalance());
        //Goes back to player 1
        while(model.getCurrentPlayer().getPlayerPiece() != Piece.HORSE){
            model.nextTurn();
        }
        //First player again
        model.getDice().setRollValue(16);
        model.movePlayer();
        model.buyProperty((Property)model.getBoard().getBoard().get(model.getCurrentPlayer().getCurrentPos()));
        model.nextTurn();
        model.movePlayer();
        assertEquals(model.getSTARTINGBALANCE() - 48 - 160, model.getCurrentPlayer().getBalance());
    }

    @org.junit.jupiter.api.Test
    void passGo(){
        model.initializeGame(filename);
        int balance = model.getCurrentPlayer().getBalance();

        model.getDice().setRollValue(35);
        model.movePlayer();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();
        model.getDice().setRollValue(10);
        model.movePlayer();

        assertEquals(balance + 200, model.getCurrentPlayer().getBalance());
    }

    @org.junit.jupiter.api.Test
    void goToJailTripleDoubles(){
        model.initializeGame(filename);

        model.getDice().setRollValue(10);
        model.getDice().setRollDouble(true);
        model.movePlayer();
        model.getDice().setRollValue(10);
        model.getDice().setRollDouble(true);
        model.movePlayer();
        model.getDice().setRollValue(10);
        model.getDice().setRollDouble(true);
        model.movePlayer();

        assertEquals(true, model.getCurrentPlayer().getInJail());
    }

    @org.junit.jupiter.api.Test
    void goToJail(){
        new GameFrame();
        model.initializeGame(filename);
        model.getDice().setRollValue(30);
        model.movePlayer();

        assertEquals(true, model.getCurrentPlayer().getInJail());
    }


    @org.junit.jupiter.api.Test
    void goToJailFor3Turns(){
        new GameFrame();
        model.initializeGame(filename);
        model.getDice().setRollValue(30);
        model.movePlayer();
        System.out.println(model.getCurrentPlayer().getPlayerPiece());
        assertEquals(true, model.getCurrentPlayer().getInJail());

        model.nextTurn();
        System.out.println(model.getCurrentPlayer().getBalance());
        int i = 0;
        while(i < 19){
            model.getDice().rollDice();
            model.getDice().setRollDouble(false);
            model.movePlayer();
            model.nextTurn();
            i++;
        }



        assertFalse(model.getCurrentPlayer().getInJail(), "player still in jail");
        assertEquals(1450, model.getCurrentPlayer().getBalance(), "balance not equal");
    }



    @org.junit.jupiter.api.Test
    void testRailroad(){
        model.initializeGame(filename);
        model.getDice().setRollValue(5);
        model.movePlayer();
        model.buyProperty((Property)model.getBoard().getBoard().get(model.getCurrentPlayer().getCurrentPos()));
        model.nextTurn();
        model.movePlayer();
        assertEquals(model.getSTARTINGBALANCE() - 15, model.getCurrentPlayer().getBalance(), "First purchase wrong");
        //Goes back to player 1
        while(model.getCurrentPlayer().getPlayerPiece() != Piece.HORSE){
            model.nextTurn();
        }
        //First player again
        model.getDice().setRollValue(10);
        model.movePlayer();
        model.buyProperty((Property)model.getBoard().getBoard().get(model.getCurrentPlayer().getCurrentPos()));
        model.nextTurn();
        model.movePlayer();
        assertEquals(model.getSTARTINGBALANCE() - 15 - 30, model.getCurrentPlayer().getBalance(), "Second purchase wrong");
    }

    @org.junit.jupiter.api.Test
    void testSaveAndLoad(){
        model.initializeGame(filename);
        model.getDice().setRollValue(5);
        model.movePlayer();
        int pos = model.getCurrentPlayer().getCurrentPos();
        boolean gameDone = model.getGameOver();
        Player curP = model.getCurrentPlayer();

        model.save();
        model = new GameModel();
        model.loadGame();

        assertEquals(model.getCurrentPlayer().getCurrentPos(), pos);
        assertEquals(model.getGameOver(), gameDone);
        assertEquals(model.getCurrentPlayer().toString(), curP.toString());
    }

    @Test
    void testLoadXML(){
        Board board = new Board(filename);
        Property test1 = new Property("Mediterranean Avenue", 60, 2, PropertyType.BROWN);
        assertEquals(test1.getRent(),((Property)board.getSquare(1)).getRent(), "First square rent wrong!");
        assertEquals(test1.getName(),((Property)board.getSquare(1)).getName(), "First square name wrong!");
        assertEquals(test1.getCost(),((Property)board.getSquare(1)).getCost(), "First square rent wrong!");


        Property test2 = new Property("Boardwalk", 400, 2, PropertyType.BLUE);
        assertEquals(test2.getRent(),((Property)board.getSquare(39)).getRent(), "Second square rent wrong!");
        assertEquals(test2.getName(),((Property)board.getSquare(39)).getName(), "Second square name wrong!");
        assertEquals(test2.getCost(),((Property)board.getSquare(39)).getCost(), "Second square rent wrong!");
    }
}