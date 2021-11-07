import javax.swing.*;
import java.util.HashMap;
import java.util.List;

public class PlayerPanel extends JPanel {

    JLabel pieceLabel;
    JLabel moneyLabel;
    JLabel curPosLabel;


    List<String> properties;

    public PlayerPanel(){
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
