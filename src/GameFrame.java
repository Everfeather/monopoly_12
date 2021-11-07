import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameFrame extends JFrame implements GameView {



    private BoardPanel board;
    private JPanel contentPane;
    //private ArrayList<HashMap<String, JLabel>> infoLabels;
    //private JPanel[] playerPanels;
    private PlayerPanel[] playerPanels;
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
        model.addGameView(this);


        GameController gc = new GameController(model);

        //DEBUG VALUES
        this.numPlayers = 4;
        playerPanels = new PlayerPanel[numPlayers];
        //infoLabels = new ArrayList<>();

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
        board = new BoardPanel(model.getBoard(), model.getDice());

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

            playerPanels[i] = new PlayerPanel();
            playerPanels[i].setBackground(bgColour);
            //playerPanels[i].setLayout(new BoxLayout(playerPanels[i] , BoxLayout.PAGE_AXIS));
            //createInfoPanel(playerPanels[i], i);

            contentPane.add(playerPanels[i], c);

        }

        JPanel botPanel = new JPanel();
        botPanel.setBackground(bgColour);
        botPanel.setLayout(new GridLayout(2,1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColour);

        //Buttons
        JButton rollButton = new JButton("Roll!");
        rollButton.setActionCommand("roll");
        rollButton.addActionListener(gc);
        buttonPanel.add(rollButton);

        JButton buyButton = new JButton("Buy");
        buyButton.setEnabled(false);
        buyButton.setActionCommand("buy");
        buyButton.addActionListener(gc);
        buttonPanel.add(buyButton);

        JButton initButton = new JButton("New Game");
        initButton.setActionCommand("init");
        initButton.addActionListener(gc);
        buttonPanel.add(initButton);



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
        //infoLabels.add(playerInfo);

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

    public static void main(String[] args){
        new GameFrame();
    }

    @Override
    public void update(MonopolyEvent event) {
        switch (event.getEvent()){
            case BUY -> {

            }
            case INIT -> {

                /*
                int i = 0;
                for(HashMap p: infoLabels){
                    p.put("piece", new JLabel(String.format()))
                }

                 */
                int i = 0;
                //System.out.println("updating view");
                for(PlayerPanel p: playerPanels){
                    p.setMoney(model.getSTARTINGBALANCE());
                    p.setPiece(model.getPlayers().get(i).getPlayerPiece().toString());
                    p.setCurPos(model.getPlayers().get(i).getCurrentPos());

                    i++;
                }

            }
            case ROLL -> {
                this.board.diceRollPane.setText(model.getCurrentPlayer().getPlayerPiece().toString() + " Rolled a " + model.getDice().rollValue);
                System.out.println("bruh");
                int count = 0;
                System.out.println("board 2: " + this.board);
                for(GameBoardSquare bs : this.model.getBoard().getBoard()){
                    String s = "";
                    for(Player p : bs.getPlayersOnSquare()){
                        switch (p.getPlayerPiece()) {
                            case CAR -> s   += "c";
                            case BOAT -> s  += "b";
                            case SHOE -> s  += "s";
                            case HORSE -> s += "h";
                        }

                    }
                    System.out.println("S is : " + s);
                    if(this.getBoardPanel().getSquares().get(count) instanceof PropertyPanel){
                        PropertyPanel square = (PropertyPanel) this.getBoardPanel().getSquares().get(count);
                        square.getPropertyInfoPopUp().setText(s);
                    }else{
                        SpecialSquarePanel square = (SpecialSquarePanel) this.getBoardPanel().getSquares().get(count);
                        square.getSpecialSquarePopUp().setText(s);
                    }
                    count++;
                }
            }

    }
}

    public BoardPanel getBoardPanel() {
        return board;
    }
}
