import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;

/**
 * The board view
 * @author Team 12
 * @author Giancarlo Salvador, Spencer Antliff, Robbie Kuhn, Daniel Wang
 */
public class BoardPanel extends JPanel implements GameView{

    /** The board the view represents */
    private Board board;
    /** The dice of the board */
    private Dice dice;

    /** The squares on the board */
    private ArrayList<JPanel> squares;
    /** The panel containing the dice roll */
    private JTextPane diceRollPane;
    private static int JAIL = 10;
    /**
     * Only constructor
     * @param board The board to represent
     * @param dice The dice to be used by the board/model
     */
    public BoardPanel(Board board, Dice dice){
        super(new GridBagLayout());
        createBoardPanel(board, dice);
    }

    public void createBoardPanel(Board board, Dice dice){
        this.removeAll();
        this.board = board;
        this.dice = dice;
        this.squares = new ArrayList<>();
        for(int i = 0; i < board.getSize(); i++){
            if(i % 10 == 0 || (i % 10 == 2 && i < 30 && i != 12) || i == 4
                    || i == 7 || i == 17 || i == 33 || i == 36 || i == 38){
                squares.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(i)));

            }else{
                squares.add(new PropertyPanel((Property) board.getBoard().get(i)));
            }
        }

        GridBagConstraints c = new GridBagConstraints();
        //from top left to bottom right, property name and position in board array
        c.fill = GridBagConstraints.BOTH;
        int x = 0;
        int y = 0;

        //free parking 20
        c.gridx = 0;
        c.gridy = 0;
        this.add(squares.get(20), c);

        //Kentucky_Avenue 21
        c.gridx = 1;
        c.gridy = 0;
        this.add(squares.get(21),c);

        //chance 22
        c.gridx = 2;
        c.gridy = 0;
        this.add(squares.get(22), c);

        //Indiana_Avenue 23
        c.gridx = 3;
        c.gridy = 0;
        this.add(squares.get(23),c);

        //Illinois_Avenue 24
        c.gridx = 4;
        c.gridy = 0;
        this.add(squares.get(24),c);

        //B. & O. Railroad 25
        c.gridx = 5;
        c.gridy = 0;
        this.add(squares.get(25), c);

        //Atlantic_Avenue 26
        c.gridx = 6;
        c.gridy = 0;
        this.add(squares.get(26),c);

        //Ventnor_Avenue 27
        c.gridx = 7;
        c.gridy = 0;
        this.add(squares.get(27),c);

        //Water Works 28
        c.gridx = 8;
        c.gridy = 0;
        this.add(squares.get(28), c);

        //Marvin_Gardens 29
        c.gridx = 9;
        c.gridy = 0;
        this.add(squares.get(29),c);

        //goToJail 30
        c.gridx = 10;
        c.gridy = 0;
        this.add(squares.get(30), c);

        //New_York_Avenue 19
        c.gridx = 0;
        c.gridy = 1;
        this.add(squares.get(19),c);

        //Center of board with dice roll display
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 9;
        c.gridheight = 9;
        diceRollPane = new JTextPane();
        diceRollPane.setText(String.format("You Rolled: %d",dice.getRollValue()));
        diceRollPane.setEditable(false);
        //centering diceRollText text
        StyledDocument style = diceRollPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        style.setParagraphAttributes(0,style.getLength(),center,false);
        this.add(diceRollPane,c);
        c.gridwidth = 1;
        c.gridheight = 1;

        //Pacific_Avenue 31
        c.gridx = 10;
        c.gridy = 1;
        this.add(squares.get(31),c);

        //Tennessee_Avenue 18
        c.gridx = 0;
        c.gridy = 2;
        this.add(squares.get(18),c);

        //North_Carolina_Avenue 32
        c.gridx = 10;
        c.gridy = 2;
        this.add(squares.get(32),c);

        //17
        c.gridx = 0;
        c.gridy = 3;
        this.add(squares.get(17), c);

        //33
        c.gridx = 10;
        c.gridy = 3;
        this.add(squares.get(33), c);

        //16
        c.gridx = 0;
        c.gridy = 4;
        this.add(squares.get(16),c);

        //34
        c.gridx = 10;
        c.gridy = 4;
        this.add(squares.get(34),c);

        //15
        c.gridx = 0;
        c.gridy = 5;
        this.add(squares.get(15), c);

        //35
        c.gridx = 10;
        c.gridy = 5;
        this.add(squares.get(35), c);

        //14
        c.gridx = 0;
        c.gridy = 6;
        this.add(squares.get(14),c);

        //36
        c.gridx = 10;
        c.gridy = 6;
        this.add(squares.get(36), c);

        //13
        c.gridx = 0;
        c.gridy = 7;
        this.add(squares.get(13),c);

        //37
        c.gridx = 10;
        c.gridy = 7;
        this.add(squares.get(37),c);

        //12
        c.gridx = 0;
        c.gridy = 8;
        this.add(squares.get(12), c);

        //38
        c.gridx = 10;
        c.gridy = 8;
        this.add(squares.get(38), c);

        //11
        c.gridx = 0;
        c.gridy = 9;
        this.add(squares.get(11),c);

        //39
        c.gridx = 10;
        c.gridy = 9;
        this.add(squares.get(39),c);

        //10
        c.gridx = 0;
        c.gridy = 10;
        this.add(squares.get(10), c);

        //9
        c.gridx = 1;
        c.gridy = 10;
        this.add(squares.get(9),c);

        //8
        c.gridx = 2;
        c.gridy = 10;
        this.add(squares.get(8),c);

        //7
        c.gridx = 3;
        c.gridy = 10;
        this.add(squares.get(7), c);

        //6
        c.gridx = 4;
        c.gridy = 10;
        this.add(squares.get(6),c);

        //5
        c.gridx = 5;
        c.gridy = 10;
        this.add(squares.get(5), c);

        //4
        c.gridx = 6;
        c.gridy = 10;
        this.add(squares.get(4), c);

        //3
        c.gridx = 7;
        c.gridy = 10;
        this.add(squares.get(3),c);
        //2
        c.gridx = 8;
        c.gridy = 10;
        this.add(squares.get(2), c);

        //1
        c.gridx = 9;
        c.gridy = 10;
        this.add(squares.get(1),c);
        //0
        c.gridx = 10;
        c.gridy = 10;
        this.add(squares.get(0), c);
        this.setSize(600,600);
        revalidate();
    }

    /**
     * getter for the dice roll panel
     * @return The JTextPane containing the dice roll message
     */
    public JTextPane getDiceRollPane() {
        return diceRollPane;
    }

    /**
     * getter method for the squares on the board
     * @return The squares on the board
     */
    public ArrayList<JPanel> getSquares() {
        return squares;
    }

    private PropertyPanel findPropertyPanel(Property target){
        for(JPanel panel: squares){
            if(panel instanceof PropertyPanel){

                if(((PropertyPanel)panel).getProperty().equals(target)){

                    return (PropertyPanel)panel;
                }
            }
        }
        return null;
    }

    private String playersOnSquare(GameBoardSquare square){
        String s = "";
        for(Player player : square.getPlayersOnSquare()){
            switch(player.getPlayerPiece()){
                case CAR -> s += player.getPlayerPiece().getName(Piece.CAR);
                case BOAT -> s += player.getPlayerPiece().getName(Piece.BOAT);
                case SHOE -> s += player.getPlayerPiece().getName(Piece.SHOE);
                case HORSE ->  s += player.getPlayerPiece().getName(Piece.HORSE);
            }
        }
        return s;
    }

    private void updatePlayerPositionSquare(GameModel model){
        int count = 0;
        for(GameBoardSquare bs : model.getBoard().getBoard()){
            String s = playersOnSquare(bs);
            if(this.getSquares().get(count) instanceof PropertyPanel){
                ((PropertyPanel) this.squares.get(count)).getInfoButton().setText(s);
            }else{
                ((SpecialSquarePanel) this.squares.get(count)).getInfoButton().setText(s);
            }
            count++;
        }
    }

    public void update(MonopolyEvent event){
        MonopolyEvent.EventType type = event.getEvent();
        GameModel model = (GameModel)event.getSource();
        Player p = model.getCurrentPlayer();

        switch(type){
            case JAIL:
                GameBoardSquare jail = model.getBoard().getSquare(JAIL);
                System.out.println(jail.getName());
                String prisoners = playersOnSquare(jail);
                System.out.println("Prisoners: "+prisoners);
                ((SpecialSquarePanel) this.getSquares().get(JAIL)).getInfoButton().setText(prisoners);
                break;
            case BUY_BUILDING:
                PropertyPanel target = findPropertyPanel(event.getProperty());
                assert target != null;
                target.setPropertyDescription(event.getProperty().toString());
                break;
            case BUY:
                JPanel panel = this.getSquares().get(p.getCurrentPos());
                if (panel instanceof PropertyPanel) {
                    ((PropertyPanel) panel).setPropertyDescription(model.getBoard().getBoard().get(p.getCurrentPos()).toString());
                }
                break;
            case ROLL:
                diceRollPane.setText(p.getPlayerPiece() + " rolled a " + model.getDice().getRollValue());
                updatePlayerPositionSquare(model);
                if(model.getDice().getRollDouble()){
                    diceRollPane.setText(model.getCurrentPlayer().getPlayerPiece() + " Rolled: " + model.getDice().getRollValue() + ". Nice, doubles!");
                }
                break;
            case LOAD:
                System.out.println("Updating board LOAD");
                updatePlayerPositionSquare(model);
                break;
        }

    }

    /*
    public static void main(String[] args) {
        Board board = new Board();
        Dice dice = new Dice();
        JPanel pane = new BoardPanel(board,dice);
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        //frame.setBackground(Color.BLACK);
        frame.add(pane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

    }
    */
}
