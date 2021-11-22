import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller of the game
 * @author Team 12
 * @author Giancarlo Salvador, Spencer Antliff, Robbie Kuhn, Daniel Wang
 */
public class GameController implements ActionListener {

    /** The model that runs the game */
    private GameModel model;
    private static final String ROLL = "roll";
    private static final String BUY = "buy";
    private static final String INIT = "init";
    private static final String NEXT_TURN = "nextTurn";
    private static final String ADD_BOT = "add_bot";
    private static final String REMOVE_BOT = "remove_bot";
    private static final String BAILOUT = "bail_out";
    private static final String ROLL_DOUBLE = "roll_double";

    /**
     * Default (and only) constructor
     * @param model the model that will run the game
     */
    public GameController(GameModel model){
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        System.out.println("Button pressed!");
        switch(actionCommand){
            case ROLL -> {
                System.out.println(model.getCurrentPlayer().getPlayerPiece().toString() + " is Rolling");

                model.getDice().rollDice();
                model.movePlayer();
                //System.out.println("Dice in controller 2: " + model.getDice());
                //System.out.println(model.getCurrentPlayer().getCurrentPos());
            }
            case BUY -> {
                model.buyProperty((Property) model.getBoard().getSquare(model.getCurrentPlayer().getCurrentPos()));
            }
            case INIT -> {

                model.initializeGame();
                //System.out.println("game initialized");
            }
            case NEXT_TURN ->{
                model.nextTurn();
            }
            case ADD_BOT -> {
                model.addBot();
            }
            case REMOVE_BOT -> {
                model.removeBot();
            }
            case BAILOUT -> {

            }
            case ROLL_DOUBLE -> {

            }
        }
    }
}
