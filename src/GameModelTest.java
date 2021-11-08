import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    @org.junit.Before
    public void setUp() throws Exception {
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
    }
}