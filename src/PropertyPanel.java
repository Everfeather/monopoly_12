import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class PropertyPanel extends JPanel {
    private Property property;
    private JButton infoButton;
    private String propertyDescription;
    private JTextArea propertyInfo;

    public PropertyPanel(Property property) {
        super(new GridBagLayout());
        this.property = property;
        GridBagConstraints c = new GridBagConstraints();


        propertyDescription = property.toString();
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
         infoButton = new JButton();

        infoButton.addActionListener( e -> {
            System.out.println("\n------------");
            System.out.println(property);
            System.out.println("-------------");
            System.out.println(propertyDescription);
            System.out.println("-----------------\n");

            propertyInfo = new JTextArea(propertyDescription);
            propertyInfo.setEditable(false);
            JFrame infoFrame = new JFrame();
            infoFrame.setLocationRelativeTo(null);
            infoFrame.add(propertyInfo);
            infoFrame.setSize(200,200);
            infoFrame.setVisible(true);
        });

        switch(property.getPropertyType()){
            case RED -> infoButton.setBackground(Color.RED);
            case PINK -> infoButton.setBackground(Color.PINK);
            case BLUE -> infoButton.setBackground(Color.BLUE);
            case BROWN -> infoButton.setBackground(new Color(95,42,42));
            case GREEN -> infoButton.setBackground(Color.GREEN);
            case ORANGE -> infoButton.setBackground(Color.ORANGE);
            case YELLOW -> infoButton.setBackground(Color.yellow);
            case LIGHTBLUE -> infoButton.setBackground(new Color(160,200,250));
            default -> infoButton.setBackground(Color.white);
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
        this.add(infoButton, c);

    }

    public void setProperty(Property property){
        this.property = property;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }
    public JTextArea getInfoArea(){
        return propertyInfo;
    }
    public JButton getInfoButton(){
        return this.infoButton;
    }
    public Property getProperty() {
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