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

        switch(actionCommand){
            case "roll" -> model.getDice().roll();
            case "buy" -> model.getCurrentPlayer().buyProperty((Property) model.getBoard().getSquare(model.getCurrentPlayer().getCurrentPos()));
            //TODO: add init case
        }
    }
}
