import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {

    GameModel model;

    public GameController(GameModel model){
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
