import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;

public class BoardPanel extends JPanel {
    Board board;
    Dice dice;
    public BoardPanel(Board board,Dice dice){
        super(new GridBagLayout());
        this.board = board;
        this.dice = dice;

        GridBagConstraints c = new GridBagConstraints();
        //from top left to bottom right, property name and position in board array
        c.fill = GridBagConstraints.BOTH;

        //free parking 20
        c.gridx = 0;
        c.gridy = 0;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //Kentucky_Avenue 21
        c.gridx = 1;
        c.gridy = 0;
        this.add(new PropertyPanel((Property) board.getBoard().get(21)),c);

        //chance 22
        c.gridx = 2;
        c.gridy = 0;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //Indiana_Avenue 23
        c.gridx = 3;
        c.gridy = 0;
        this.add(new PropertyPanel((Property) board.getBoard().get(23)),c);

        //Illinois_Avenue 24
        c.gridx = 4;
        c.gridy = 0;
        this.add(new PropertyPanel((Property) board.getBoard().get(24)),c);

        //B. & O. Railroad 25
        c.gridx = 5;
        c.gridy = 0;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //Atlantic_Avenue 26
        c.gridx = 6;
        c.gridy = 0;
        this.add(new PropertyPanel((Property) board.getBoard().get(26)),c);

        //Ventnor_Avenue 27
        c.gridx = 7;
        c.gridy = 0;
        this.add(new PropertyPanel((Property) board.getBoard().get(27)),c);

        //Water Works 28
        c.gridx = 8;
        c.gridy = 0;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //Marvin_Gardens 29
        c.gridx = 9;
        c.gridy = 0;
        this.add(new PropertyPanel((Property) board.getBoard().get(29)),c);

        //goToJail 30
        c.gridx = 10;
        c.gridy = 0;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //New_York_Avenue 19
        c.gridx = 0;
        c.gridy = 1;
        this.add(new PropertyPanel((Property) board.getBoard().get(19)),c);

        //Center of board with dice roll display
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 9;
        c.gridheight = 9;
        JTextPane diceRollPane = new JTextPane();
        diceRollPane.setText(String.format("You Rolled: %d",dice.getRollValue()));
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
        this.add(new PropertyPanel((Property) board.getBoard().get(31)),c);

        //Tennessee_Avenue 18
        c.gridx = 0;
        c.gridy = 2;
        this.add(new PropertyPanel((Property) board.getBoard().get(18)),c);

        //North_Carolina_Avenue 32
        c.gridx = 10;
        c.gridy = 2;
        this.add(new PropertyPanel((Property) board.getBoard().get(32)),c);

        //17
        c.gridx = 0;
        c.gridy = 3;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //33
        c.gridx = 10;
        c.gridy = 3;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //16
        c.gridx = 0;
        c.gridy = 4;
        this.add(new PropertyPanel((Property) board.getBoard().get(16)),c);

        //34
        c.gridx = 10;
        c.gridy = 4;
        this.add(new PropertyPanel((Property) board.getBoard().get(34)),c);

        //15
        c.gridx = 0;
        c.gridy = 5;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //35
        c.gridx = 10;
        c.gridy = 5;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //14
        c.gridx = 0;
        c.gridy = 6;
        this.add(new PropertyPanel((Property) board.getBoard().get(14)),c);

        //36
        c.gridx = 10;
        c.gridy = 6;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //13
        c.gridx = 0;
        c.gridy = 7;
        this.add(new PropertyPanel((Property) board.getBoard().get(13)),c);

        //37
        c.gridx = 10;
        c.gridy = 7;
        this.add(new PropertyPanel((Property) board.getBoard().get(37)),c);

        //12
        c.gridx = 0;
        c.gridy = 8;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //38
        c.gridx = 10;
        c.gridy = 8;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //11
        c.gridx = 0;
        c.gridy = 9;
        this.add(new PropertyPanel((Property) board.getBoard().get(11)),c);

        //39
        c.gridx = 10;
        c.gridy = 9;
        this.add(new PropertyPanel((Property) board.getBoard().get(39)),c);

        //10
        c.gridx = 0;
        c.gridy = 10;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //9
        c.gridx = 1;
        c.gridy = 10;
        this.add(new PropertyPanel((Property) board.getBoard().get(9)),c);

        //8
        c.gridx = 2;
        c.gridy = 10;
        this.add(new PropertyPanel((Property) board.getBoard().get(8)),c);

        //7
        c.gridx = 3;
        c.gridy = 10;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //6
        c.gridx = 4;
        c.gridy = 10;
        this.add(new PropertyPanel((Property) board.getBoard().get(6)),c);

        //5
        c.gridx = 5;
        c.gridy = 10;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //4
        c.gridx = 6;
        c.gridy = 10;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //3
        c.gridx = 7;
        c.gridy = 10;
        this.add(new PropertyPanel((Property) board.getBoard().get(3)),c);
        //2
        c.gridx = 8;
        c.gridy = 10;
        this.add(new SpecialSquarePanel((SpecialSquare) board.getBoard().get(5)), c);

        //1
        c.gridx = 9;
        c.gridy = 10;
        this.add(new PropertyPanel((Property) board.getBoard().get(1)),c);
        //0
        c.gridx = 10;
        c.gridy = 10;

        this.setSize(600,600);
    }
    /*
    public static void main(String[] args) {
        Board board = new Board();
        Dice dice = new Dice();
        JPanel pane = new BoardPanel(board,dice);
        JFrame frame = new JFrame();
        frame.setSize(700,700);
        //frame.setBackground(Color.BLACK);
        frame.add(pane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

     */
}
