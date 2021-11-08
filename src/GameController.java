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
                //System.out.println("Dice in controller 2: " + model.getDice());
                System.out.println(model.getCurrentPlayer().getCurrentPos());
            }
            case "buy" -> {
                model.buyProperty((Property) model.getBoard().getSquare(model.getCurrentPlayer().getCurrentPos()));
            }
            case "init" -> {

                model.initializeGame();
                //System.out.println("game initialized");
            }
            case "nextTurn" ->{
                model.nextTurn();
            }
        }
    }
}
