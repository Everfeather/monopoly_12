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
        model.initializeGame();
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
        model.initializeGame();
        Player curP = model.getCurrentPlayer();
        model.nextTurn();
        Player nextP = model.getCurrentPlayer();
        assertNotEquals(curP, nextP);
    }

    @org.junit.jupiter.api.Test
    void win() {
        new GameFrame();
        model.initializeGame();
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
        model.initializeGame();
        Property prop = new Property("test", 500, 2, PropertyType.PINK);
        model.buyProperty(prop);
        assertEquals( PropertyType.PINK, model.getCurrentPlayer().getProperties().get(prop));
        assertEquals( 1000, model.getCurrentPlayer().getBalance());
    }

    @org.junit.jupiter.api.Test
    void movePlayer() {
        model.initializeGame();

        model.getDice().rollDice();
        int moveVal = model.getDice().getRollValue();
        model.movePlayer();
        assertEquals(moveVal, model.getCurrentPlayer().getCurrentPos(), "Player not moving correctly");

        model.getDice().setRollValue(40);
        model.movePlayer();
        assertEquals(moveVal, model.getCurrentPlayer().getCurrentPos(), "Player not crossing go correctly");

    }

    @Test
    void testUtility(){
        model.initializeGame();
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

//    @Test
//    void testRailroad(){
//        model.initializeGame();
//        model.getDice().setRollValue(5);
//        model.movePlayer();
//        model.buyProperty((Property)model.getBoard().getBoard().get(model.getCurrentPlayer().getCurrentPos()));
//        model.nextTurn();
//        model.movePlayer();
//        assertEquals(model.getSTARTINGBALANCE() - 48, model.getCurrentPlayer().getBalance());
//        //Goes back to player 1
//        while(model.getCurrentPlayer().getPlayerPiece() != Piece.HORSE){
//            model.nextTurn();
//        }
//        //First player again
//        model.getDice().setRollValue(16);
//        model.movePlayer();
//        model.buyProperty((Property)model.getBoard().getBoard().get(model.getCurrentPlayer().getCurrentPos()));
//        model.nextTurn();
//        model.movePlayer();
//        assertEquals(model.getSTARTINGBALANCE() - 48 - 160, model.getCurrentPlayer().getBalance());
//    }
}