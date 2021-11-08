import javax.swing.*;
import java.util.HashMap;
import java.util.List;

/**
 * The panel containing player information
 * @author Team 12
 * @author Giancarlo Salvador, Spencer Antliff, Robbie Kuhn, Daniel Wang
 */
public class PlayerPanel extends JPanel {

    /** The label displaying the piece of the player */
    private JLabel pieceLabel;
    /** The label displaying the money of the player */
    private JLabel moneyLabel;
    /** The label displaying the current position of the player */
    private JLabel curPosLabel;
    /** The piece of the player */
    private Piece playerPiece;
    /** The list of properties that the player owns */
    private List<String> properties;

    /**
     * Default constructor
     */
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

    /**
     * Setter method for the playerPiece field
     * @param playerPiece The piece of the selected player
     */
    public void setPlayerPiece(Piece playerPiece) {
        this.playerPiece = playerPiece;
    }

    /**
     * Getter method for the playerPiece field
     * @return The piece enum of the player
     */
    public Piece getPlayerPiece() {
        return playerPiece;
    }

    /**
     * Adds a property to the list
     * @param property the property to be added
     */
    public void addProperty(String property){
        this.properties.add(property);
    }

    /**
     * Sets the label of the player
     * @param piece the piece to be set
     */
    public void setPiece(String piece) {
        this.pieceLabel.setText(String.format("Piece: %s",piece));
    }

    /**
     * Sets label containing balance of player
     * @param money the new balance of the player
     */
    public void setMoney(int money) {
        this.moneyLabel.setText(String.format("Money: %d",money));
    }

    /**
     * Sets label containing current position of the player
     * @param curPos The new current position of the player
     */
    public void setCurPos(int curPos) {
        this.curPosLabel.setText(String.format("Current Position: %d",curPos));
    }
}
