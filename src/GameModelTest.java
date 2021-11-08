import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    @BeforeAll
    public static void setUp() throws Exception {
    }

    @org.junit.jupiter.api.Test
    void initializeGame() {
    }

    @org.junit.jupiter.api.Test
    void nextTurn() {
        GameModel model = new GameModel();
        new GameFrame();
        model.initializeGame();
        Player curP = model.getCurrentPlayer();
        model.nextTurn();
        Player nextP = model.getCurrentPlayer();
        assertNotEquals(curP, nextP);
    }

    @org.junit.jupiter.api.Test
    void win() {
        GameModel model = new GameModel();
        new GameFrame();
        model.initializeGame();
        model.getCurrentPlayer().setBankrupt(true);
        model.nextTurn();
        model.getCurrentPlayer().setBankrupt(true);
        model.nextTurn();model.getCurrentPlayer().setBankrupt(true);
        model.nextTurn();
        model.win();
        assertEquals( true, model.getGameOver());
    }

    @org.junit.jupiter.api.Test
    void buyProperty() {
        GameModel model = new GameModel();
        new GameFrame();
        model.initializeGame();
        Property prop = new Property("test", 500, 2, PropertyType.PINK);
        model.buyProperty(prop);
        assertEquals( PropertyType.PINK, model.getCurrentPlayer().getProperties().get(prop));
        assertEquals( 1000, model.getCurrentPlayer().getBalance());
    }

    @org.junit.jupiter.api.Test
    void movePlayer() {
        GameModel model = new GameModel();
        model.initializeGame();

        model.getDice().rollDice();
        int moveVal = model.getDice().getRollValue();
        model.movePlayer();
        assertEquals(moveVal, model.getCurrentPlayer().getCurrentPos(), "Player not moving correctly");

        model.getDice().setRollValue(40);
        model.movePlayer();
        assertEquals(moveVal, model.getCurrentPlayer().getCurrentPos(), "Player not crossing go correctly");

    }
}