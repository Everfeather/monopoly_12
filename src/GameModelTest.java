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
        model.initializeGame();
        Player curP = model.getCurrentPlayer();
        model.nextTurn();
        Player nextP = model.getCurrentPlayer();
        assertEquals(curP, nextP);
    }

    @org.junit.jupiter.api.Test
    void win() {
    }

    @org.junit.jupiter.api.Test
    void buyProperty() {
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