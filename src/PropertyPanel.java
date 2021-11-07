import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class PropertyPanel extends JPanel {
    private GameBoardSquare property;
    private JButton propertyInfoPopUp;
    public PropertyPanel(Property property) {
        super(new GridBagLayout());
        this.property = property;
        GridBagConstraints c = new GridBagConstraints();

        //name
        JTextPane name = new JTextPane();
        //centering name text
        StyledDocument style = name.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        style.setParagraphAttributes(0,style.getLength(),center,false);

        name.setEditable(false);
        name.setText(property.getName());

        //information on property pop up
         propertyInfoPopUp = new JButton();

        propertyInfoPopUp.addActionListener( e -> {
            String s = property.toString();
            JTextArea info = new JTextArea(s);
            info.setEditable(false);
            JFrame infoFrame = new JFrame();
            infoFrame.setLocationRelativeTo(null);
            infoFrame.add(info);
            infoFrame.setSize(200,200);
            infoFrame.setVisible(true);
        });

        switch(property.getPropertyType()){
            case RED -> propertyInfoPopUp.setBackground(Color.RED);
            case PINK -> propertyInfoPopUp.setBackground(Color.PINK);
            case BLUE -> propertyInfoPopUp.setBackground(Color.BLUE);
            case BROWN -> propertyInfoPopUp.setBackground(new Color(95,42,42));
            case GREEN -> propertyInfoPopUp.setBackground(Color.GREEN);
            case ORANGE -> propertyInfoPopUp.setBackground(Color.ORANGE);
            case YELLOW -> propertyInfoPopUp.setBackground(Color.yellow);
            case LIGHTBLUE -> propertyInfoPopUp.setBackground(new Color(160,200,250));
            default -> propertyInfoPopUp.setBackground(Color.white);
        }

        //c.fill = GridBagConstraints.HORIZONTAL;
        c.fill = GridBagConstraints.BOTH;
        //c.weighty = 1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
       // this.add(name,c);
        c.weighty = 1;

        c.gridy = 1;
        c.ipady = 40;
        c.ipadx = 40;
        c.gridheight = 3;
        this.add(propertyInfoPopUp, c);

    }
    public JButton getPropertyInfoPopUp(){
        return this.propertyInfoPopUp;
    }
    public GameBoardSquare getProperty() {
        return property;
    }
    //testing stuff
    /*
    public static void main(String[] args) {
        PropertyPanel pane = new PropertyPanel(new Property("bruh avenue",420,3,PropertyType.BLUE));
        JFrame frame = new JFrame();
        frame.setSize(300,300);
        frame.setBackground(Color.BLACK);
        frame.add(pane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
     */
}