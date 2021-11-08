import javax.swing.*;
import java.util.HashMap;
import java.util.List;

public class PlayerPanel extends JPanel {

    private JLabel pieceLabel;
    private JLabel moneyLabel;
    private JLabel curPosLabel;
    private Piece playerPiece;
    private List<String> properties;

    public void setPlayerPiece(Piece playerPiece) {
        this.playerPiece = playerPiece;
    }

    public Piece getPlayerPiece() {
        return playerPiece;
    }

    public PlayerPanel(){
        this.playerPiece = null;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        //HashMap<String, JLabel> playerInfo = new HashMap<String, JLabel>();
        //infoLabels.add(playerInfo);

        pieceLabel = new JLabel("Piece:");
        this.add(pieceLabel);


        moneyLabel = new JLabel("Money:");
        this.add(moneyLabel);


        curPosLabel = new JLabel("Current Position:");
        this.add(curPosLabel);

    /*
        JLabel propertyLabel = new JLabel("Properties:");
        this.add(propertyLabel);
     */
    }
    public void addProperty(String property){
        this.properties.add(property);
    }
    public void setPiece(String piece) {
        this.pieceLabel.setText(String.format("Piece: %s",piece));
    }

    public void setMoney(int money) {
        this.moneyLabel.setText(String.format("Money: %d",money));
    }

    public void setCurPos(int curPos) {
        this.curPosLabel.setText(String.format("Current Position: %d",curPos));
    }
}
