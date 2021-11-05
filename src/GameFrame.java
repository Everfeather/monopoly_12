import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameFrame extends JFrame implements GameView {

    private BoardPanel board;
    private JPanel contentPane;
    private ArrayList<HashMap<String, JLabel>> infoLabels;
    private JPanel[] playerPanels;
    private JLabel eventView;
    private int numPlayers;

    GameModel model;

    /** Font for header */
    private static final Font HEADERFONT = new Font("Tahoma", Font.BOLD, 22);
    private static final Color bgColour = Color.PINK;


    public GameFrame(){
        super("MONOPOLY GAME TIME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1080,720);

        //TODO: Create game info
        //TODO: find number of players:
        model = new GameModel();

        //DEBUG VALUES
        this.numPlayers = 4;
        playerPanels = new JPanel[numPlayers];
        infoLabels = new ArrayList<>();

        //Content pane
        contentPane = new JPanel();
        contentPane.setBackground(bgColour);
        this.setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        //c.ipadx = 80;
        c.ipady = 50;

        //Adding the board
        board = new BoardPanel(new Board(), new Dice());
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 3;
        contentPane.add(board, c);

        c.gridheight = 1;

        for(int i = 0; i < numPlayers; i++)
        {
            c.insets = new Insets(15,15,15,50);
            c.gridx = 1 + (i % 2);
            c.gridy = (i < 2) ? 0 : 1;

            playerPanels[i] = new JPanel();
            playerPanels[i].setBackground(bgColour);
            playerPanels[i] .setLayout(new BoxLayout(playerPanels[i] , BoxLayout.PAGE_AXIS));
            createInfoPanel(playerPanels[i], i);

            contentPane.add(playerPanels[i], c);

        }

        JPanel botPanel = new JPanel();
        botPanel.setBackground(bgColour);
        botPanel.setLayout(new GridLayout(2,1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColour);

        JButton rollButton = new JButton("Roll!");
        rollButton.setActionCommand("roll");
        rollButton.addActionListener(model);
        buttonPanel.add(rollButton);

        JButton buyButton = new JButton("Buy");
        buyButton.setEnabled(false);
        buyButton.setActionCommand("buy");
        buyButton.addActionListener(model);
        buttonPanel.add(buyButton);

        botPanel.add(buttonPanel);

        eventView = new JLabel("Game start!", SwingConstants.CENTER);
        botPanel.add(eventView);

        c.gridy = 2;
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        contentPane.add(botPanel, c);

        this.setVisible(true);
    }

    private void createInfoPanel(JPanel curInfo, int index) {
        HashMap<String, JLabel> playerInfo = new HashMap<String, JLabel>();
        infoLabels.add(playerInfo);

        JLabel pieceLabel = new JLabel("Piece: ");
        curInfo.add(pieceLabel);
        playerInfo.put("piece", pieceLabel);

        JLabel moneyLabel = new JLabel("Money: ");
        curInfo.add(moneyLabel);
        playerInfo.put("money", moneyLabel);

        JLabel curPosLabel = new JLabel("Current Position: ");
        curInfo.add(curPosLabel);
        playerInfo.put("curPos", curPosLabel);

        JLabel propertyLabel = new JLabel("<html>Properties:<br/>");
        curInfo.add(propertyLabel);
        playerInfo.put("property", propertyLabel);
    }

    public void update(MonopolyEvent event){

    }

    public static void main(String[] args){
        new GameFrame();
    }

}
