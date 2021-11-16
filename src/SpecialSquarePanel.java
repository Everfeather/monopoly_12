import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class SpecialSquarePanel extends JPanel{

    GameBoardSquare specialSquare;

    JButton infoButton;
    public SpecialSquarePanel(SpecialSquare specialSquare){
        super(new GridBagLayout());
        this.specialSquare = specialSquare;
        GridBagConstraints c = new GridBagConstraints();

        // SpecialSquare name
        JTextPane name = new JTextPane();
        //centering name text
        StyledDocument style = name.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        style.setParagraphAttributes(0,style.getLength(),center,false);


        name.setEditable(false);
        name.setText(specialSquare.getName());

        //information on specialSquare
        infoButton = new JButton();

        infoButton.addActionListener(e -> {
            String s = specialSquare.toString();
            JTextArea info = new JTextArea(s);
            info.setEditable(false);
            JFrame infoFrame = new JFrame();
            infoFrame.setLocationRelativeTo(null);
            infoFrame.add(info);
            infoFrame.setSize(200,200);
            infoFrame.setVisible(true);
        });

        switch(specialSquare.getName()){
            case "empty" -> infoButton.setBackground(Color.GRAY);
            case "go" -> infoButton.setBackground(Color.pink);
            case "goToJail" -> infoButton.setBackground(Color.CYAN);
            case "jail" -> infoButton.setBackground(new Color(18, 23, 99));
            case "freeParking" -> infoButton.setBackground(new Color(107, 2, 2));
            case "tax" -> infoButton.setBackground(Color.ORANGE);
        }

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        //this.add(name,c);
        c.weighty = 1;

        c.gridy = 1;
        c.ipady = 40;
        c.ipadx = 40;
        c.gridheight = 3;
        this.add(infoButton, c);
    }
    public JButton getInfoButton() {
        return infoButton;
    }
}
