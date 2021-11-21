import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The view that contains all GUI elements for the Monopoly game
 * @author Team 12
 * @author Giancarlo Salvador, Spencer Antliff, Robbie Kuhn, Daniel Wang
 */
public class GameFrame extends JFrame implements GameView {

    /** Value for the ROLL button */
    private final int ROLL = 0;
    /** Value for the NEXT_TURN button */
    private final int NEXT_TURN = 1;
    /** The value for the BUY button */
    private final int BUY = 2;
    /** The value for the NEW_GAME button */
    private final int NEW_GAME = 3;
    private final int ADD_BOT = 4;
    private final int REMOVE_BOT = 5;
    /** The board view */
    private BoardPanel board;
    /** The main content panel of the view */
    private JPanel contentPane;
    /** The player panels containing player information */
    private PlayerPanel[] playerPanels;
    /** The label containing messages about the game */
    private JLabel eventView;
    /** The number of players in the game */
    private int numPlayers;
    /** The number of buttons in the action panel*/
    private ArrayList<JButton> buttons;
    /** The model that the view represents */
    private GameModel model;

    /** The background colour of the game */
    private static final Color bgColour = Color.PINK;

    /**
     * The default constructor
     */
    public GameFrame(){
        super("MONOPOLY GAME TIME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1080,720);
        this.buttons = new ArrayList<JButton>();

        model = new GameModel();
        model.addGameView(this);
        //Adding the board
        board = new BoardPanel(model.getBoard(), model.getDice());
        model.addGameView(board);

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
        buttonPanel.setLayout(new GridLayout(3,2));
        buttonPanel.setBackground(bgColour);

        //Buttons
        JButton rollButton = new JButton("Roll!");
        rollButton.setActionCommand("roll");
        rollButton.addActionListener(gc);
        buttonPanel.add(rollButton);
        buttons.add(rollButton);

        JButton nextTurnButton = new JButton("next turn");
        nextTurnButton.setActionCommand("nextTurn");
        nextTurnButton.addActionListener(gc);
        buttonPanel.add(nextTurnButton);
        buttons.add(nextTurnButton);

        JButton buyButton = new JButton("Buy");
        buyButton.setEnabled(false);
        buyButton.setActionCommand("buy");
        buyButton.addActionListener(gc);
        buttonPanel.add(buyButton);
        buttons.add(buyButton);

        JButton initButton = new JButton("Start Game");
        initButton.setActionCommand("init");
        initButton.addActionListener(gc);
        buttonPanel.add(initButton);
        buttons.add(initButton);

        JButton addBotButton = new JButton("Add Bot");
        addBotButton.setActionCommand("add_bot");
        addBotButton.addActionListener(gc);
        buttonPanel.add(addBotButton);
        buttons.add(addBotButton);

        JButton removeBotButton = new JButton("Remove Bot");
        removeBotButton.setActionCommand("remove_bot");
        removeBotButton.addActionListener(gc);
        buttonPanel.add(removeBotButton);
        buttons.add(removeBotButton);

        botPanel.add(buttonPanel);


        eventView = new JLabel("Welcome to monopoly.", SwingConstants.CENTER);
        botPanel.add(eventView);

        c.gridy = 2;
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        contentPane.add(botPanel, c);

        this.setVisible(true);
    }

    public static void main(String[] args){
        new GameFrame();
    }

    /**
     * Update GameFrame based on event: BUY, NEXT, INIT, ROLL
     */
    @Override
    public void update(MonopolyEvent event) {
        switch (event.getEvent()){
            case BUY -> {
                Player curP = model.getCurrentPlayer();
                for(PlayerPanel p : playerPanels){
                    if(p.getPlayerPiece() == model.getCurrentPlayer().getPlayerPiece()){
                        p.setMoney(curP.getBalance());
                    }
                }
                //edit it so the player owner is updated
                buttons.get(BUY).setEnabled(false);
                eventView.setText(curP.getPlayerPiece() + " bought " + model.getBoard().getBoard().get(curP.getCurrentPos()).getName());
            }

            case NEXT ->{
                eventView.setText(model.getCurrentPlayer().getPlayerPiece() + "'s turn");
                buttons.get(NEXT_TURN).setEnabled(false);
                buttons.get(ROLL).setEnabled(true);
                if(model.getGameOver()){
                    eventView.setText(model.getCurrentPlayer().getPlayerPiece() + " has Won!");
                    for(JButton b: buttons){
                        b.setEnabled(false);
                    }
                    JOptionPane.showMessageDialog(null, model.getCurrentPlayer().getPlayerPiece() + " has Won!");
                    this.dispose();
                }
            }
            case INIT -> {

                int i = 0;
                //System.out.println("updating view");

                for(PlayerPanel p: playerPanels){
                    if(model.getPlayers().get(i).isBot()){
                        p.setMoney(model.getSTARTINGBALANCE());
                        p.setPiece(model.getPlayers().get(i).getPlayerPiece().toString() + "_bot");
                        p.setPlayerPiece(model.getPlayers().get(i).getPlayerPiece());
                        p.setCurPos(model.getPlayers().get(i).getCurrentPos());
                    }else{
                        p.setMoney(model.getSTARTINGBALANCE());
                        p.setPiece(model.getPlayers().get(i).getPlayerPiece().toString());
                        p.setPlayerPiece(model.getPlayers().get(i).getPlayerPiece());
                        p.setCurPos(model.getPlayers().get(i).getCurrentPos());
                    }
                    i++;
                }
                for(JButton b : buttons){
                    b.setEnabled(false);
                }
                eventView.setText(model.getCurrentPlayer().getPlayerPiece() + "'s turn");
                buttons.get(ROLL).setEnabled(true);
                System.out.println(6);
            }

            case ROLL -> {
                //disables next turn button
                buttons.get(BUY).setEnabled(true);
                //System.out.println("dice 1: " + this.model.getDice());
                Player curP = model.getCurrentPlayer();
                for(PlayerPanel p : playerPanels){
                    if(p.getPlayerPiece() == model.getCurrentPlayer().getPlayerPiece()){
                        p.setCurPos(curP.getCurrentPos());
                    }
                }

                if(!model.getDice().getRollDouble()){
                    System.out.println("No doubles ):");
                    buttons.get(ROLL).setEnabled(false);
                    buttons.get(NEXT_TURN).setEnabled(true);
                }
                eventView.setText(curP.getPlayerPiece() + " landed on " + model.getBoard().getBoard().get(curP.getCurrentPos()).getName());
                if(!(model.getBoard().getBoard().get(curP.getCurrentPos()) instanceof SpecialSquare)){
                    if(((Property)model.getBoard().getBoard().get(curP.getCurrentPos())).getOwner() != null){
                        buttons.get(BUY).setEnabled(false);
                        for(PlayerPanel p : playerPanels){
                            if(p.getPlayerPiece() == model.getCurrentPlayer().getPlayerPiece()){
                                p.setMoney(curP.getBalance());
                            }
                            if(p.getPlayerPiece() == ((Property)model.getBoard().getBoard().get(curP.getCurrentPos())).getOwner().getPlayerPiece()){
                                p.setMoney(((Property)model.getBoard().getBoard().get(curP.getCurrentPos())).getOwner().getBalance());
                            }
                        }
                    }else{
                        if(curP.getBalance() < ((Property)model.getBoard().getBoard().get(curP.getCurrentPos())).getCost()){
                            buttons.get(BUY).setEnabled(false);
                        }
                    }
                }else{
                    buttons.get(BUY).setEnabled(false);
                }

            }

        }
    }

    /**
     * getter method for the boardPanel
     * @return the board that represents the game
     */
    public BoardPanel getBoardPanel() {
        return board;
    }
}
