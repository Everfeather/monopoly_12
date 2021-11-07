import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {

    GameModel model;

    public GameController(GameModel model){
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        System.out.println("Button pressed!");
        switch(actionCommand){
            case "roll" -> {
                System.out.println(model.getCurrentPlayer().getPlayerPiece().toString() + " is Rolling");

                model.getDice().rollDice();
                model.movePlayer();

                System.out.println(model.getCurrentPlayer().getCurrentPos());
            }
            case "buy" -> model.getCurrentPlayer().buyProperty((Property) model.getBoard().getSquare(model.getCurrentPlayer().getCurrentPos()));
            //TODO: add init case
            case "init" -> {
                model.initializeGame();
                //System.out.println("game initialized");
            }
        }
    }
}
