import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;


/**
 * The view that contains all GUI elements for the Monopoly game
 * @author Team 12
 * @author Giancarlo Salvador, Spencer Antliff, Robbie Kuhn, Daniel Wang
 */
public class GameFrame extends JFrame implements GameView {

    /** Button Values */
    private final int ROLL = 0;
    private final int NEXT_TURN = 1;
    private final int BUY = 2;
    private final int BUY_BUILDING = 3;
    private final int START_GAME = 4;
    private final int ADD_BOT = 5;
    private final int REMOVE_BOT = 6;
    private final int SAVE_GAME = 7;
    private final int LOAD_GAME = 8;
    /** End of Button Values/

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

    private final int MAX_BUILDINGS = 4;

    /** The background colour of the game */
    private static final Color bgColour = Color.PINK;

    /**
     * The default constructor
     */
    public GameFrame(){
        super("MONOPOLY GAME TIME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1080,720);
        this.buttons = new ArrayList<>();

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

            contentPane.add(playerPanels[i], c);

        }

        JPanel botPanel = new JPanel();
        botPanel.setBackground(bgColour);
        botPanel.setLayout(new GridLayout(2,1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,2));
        buttonPanel.setBackground(bgColour);

        //Buttons
        addButton("Roll!","roll",buttonPanel,gc,botPanel);
        addButton("Next Turn","nextTurn",buttonPanel,gc,botPanel);
        addButton("Buy","buy",buttonPanel,gc,botPanel);

        //addButton("Buy building","buyBuilding",buttonPanel,gc,botPanel);
        JButton buyBuildingButton = new JButton("Buy building");
        buyBuildingButton.setActionCommand("buyBuilding");

        buyBuildingButton.addActionListener(e -> {
            //purchasing building
            JFrame propertyListFrame = new JFrame();
            JPanel propertyListPanel = new JPanel();

            DefaultListModel propertyListModel = new DefaultListModel();
            for(Property p : model.getCurrentPlayer().getProperties().keySet()){
                if(model.getCurrentPlayer().hasPropertySet(p) && p.getNumBuildings() < MAX_BUILDINGS){
                    propertyListModel.addElement(p);

                }
            }

            JList propertyList = new JList<Property>(propertyListModel);
            propertyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            propertyList.addListSelectionListener(f -> {
                //this.model.setPropertyGettingBuilding((Property) propertyList.getSelectedValue());
                model.buyBuilding((Property) propertyList.getSelectedValue());
                propertyListFrame.setVisible(false);
                propertyListFrame.dispose();
            });

            propertyListPanel.add(propertyList);
            propertyListFrame.add(propertyListPanel);
            propertyListFrame.pack();
            propertyListFrame.setVisible(true);
        });
        buttonPanel.add(buyBuildingButton);

        this.buttons.add(buyBuildingButton);

        addButton("Start Game","init",buttonPanel,gc,botPanel);

        addButton("Add Bot","add_bot",buttonPanel,gc,botPanel);

        addButton("Remove Bot","remove_bot",buttonPanel,gc,botPanel);

        addButton("Save Game", "save", buttonPanel, gc, botPanel);

        addButton("Load Game", "load", buttonPanel, gc, botPanel);

        botPanel.add(buttonPanel);

        this.buttons.get(ROLL).setEnabled(false);
        this.buttons.get(NEXT_TURN).setEnabled(false);
        this.buttons.get(BUY).setEnabled(false);
        this.buttons.get(BUY_BUILDING).setEnabled(false);

        eventView = new JLabel("Welcome to monopoly.", SwingConstants.CENTER);
        botPanel.add(eventView);

        c.gridy = 2;
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        contentPane.add(botPanel, c);

        this.setVisible(true);
    }

    public void addButton(String buttonName, String buttonCommand, JPanel buttonPanel, ActionListener gc, JPanel botPanel){
        JButton newButton = new JButton(buttonName);
        newButton.setActionCommand(buttonCommand);
        newButton.addActionListener(gc);
        buttonPanel.add(newButton);
        this.buttons.add(newButton);

        botPanel.add(buttonPanel);
    }


    private void handleJail(){
        Player curP = model.getCurrentPlayer();
        if(!curP.getInJail()){
            buttons.get(ROLL).setEnabled(false);
            buttons.get(NEXT_TURN).setEnabled(true);
        }
    }

    private void updatePlayerMoney(Player curP){
        for(PlayerPanel p : playerPanels){
            if(p.getPlayerPiece() == curP.getPlayerPiece()){
                p.setMoney(curP.getBalance());
            }
        }
    }

    private void updatePlayerPosition(Player curP){
        for(PlayerPanel p : playerPanels){
            if(p.getPlayerPiece() == model.getCurrentPlayer().getPlayerPiece()){
                p.setCurPos(curP.getCurrentPos());
            }
        }
    }

    private void handleGameOver(){
        if(model.getGameOver()){
            eventView.setText(model.getCurrentPlayer().getPlayerPiece() + " has Won!");
            JOptionPane.showMessageDialog(null, model.getCurrentPlayer().getPlayerPiece() + " has Won!");
            this.dispose();
        }
    }

    private void gameInitialization(){
        int i = 0;
        System.out.println("updating view");

        //Adding board
        board.createBoardPanel(model.getBoard(), model.getDice());
        revalidate();

        for(PlayerPanel p: playerPanels){
            if(model.getPlayers().get(i).isBot()){
                p.setPiece(model.getPlayers().get(i).getPlayerPiece().toString() + "_bot");
            }else{
                p.setPiece(model.getPlayers().get(i).getPlayerPiece().toString());
            }
            p.setMoney(model.getSTARTINGBALANCE());
            p.setPlayerPiece(model.getPlayers().get(i).getPlayerPiece());
            p.setCurPos(model.getPlayers().get(i).getCurrentPos());
            i++;
        }
        for(JButton b : buttons){
            b.setEnabled(false);
        }
        eventView.setText(model.getCurrentPlayer().getPlayerPiece() + "'s turn");
        buttons.get(ROLL).setEnabled(true);
    }

    private void handleRentOccurrance(Player curP, GameBoardSquare curSquare){
        eventView.setText(curP.getPlayerPiece() + " landed on " + curSquare.getName());
        if(!(model.getBoard().getBoard().get(curP.getCurrentPos()) instanceof SpecialSquare)){
            if(((Property) curSquare).getOwner() != null){
                buttons.get(BUY).setEnabled(false);
                updatePlayerMoney(curP);
                updatePlayerMoney(((Property)curSquare).getOwner());
            }else{
                buttons.get(BUY).setEnabled(curP.getBalance() > ((Property)curSquare).getCost());
            }
        }
    }

    private void handlePotentialDoubles(){
        if(!model.getDice().getRollDouble()){
            buttons.get(ROLL).setEnabled(false);
            buttons.get(NEXT_TURN).setEnabled(true);
        }
    }

    /**
     * Update GameFrame based on event: BUY, NEXT, INIT, ROLL
     */
    @Override
    public void update(MonopolyEvent event) {
        buttons.get(SAVE_GAME).setEnabled(true);
        if (model.isGameSaved()){
            buttons.get(LOAD_GAME).setEnabled(true);
        }
        switch (event.getEvent()){
            case JAIL -> {
                handleJail();
            }

            case BUY_BUILDING -> {
                Player curP = model.getCurrentPlayer();
                updatePlayerMoney(curP);
                buttons.get(BUY_BUILDING).setEnabled(false);
                for(Map.Entry<Property,PropertyType> entry : curP.getProperties().entrySet()){
                    if(entry.getKey().getNumBuildings() < MAX_BUILDINGS){
                        buttons.get(BUY_BUILDING).setEnabled(true);
                        break;
                    }
                }
            }
            case BUY -> {
                Player curP = model.getCurrentPlayer();
                GameBoardSquare curSquare = model.getBoard().getBoard().get(curP.getCurrentPos());
                updatePlayerMoney(curP);
                buttons.get(BUY).setEnabled(false);
                buttons.get(BUY_BUILDING).setEnabled(curP.hasPropertySet((Property) curSquare));
                eventView.setText(curP.getPlayerPiece() + " bought " + curSquare.getName());
            }

            case NEXT ->{
                Player curP = model.getCurrentPlayer();
                eventView.setText(model.getCurrentPlayer().getPlayerPiece() + "'s turn");
                //Enable or disable buttons
                buttons.get(BUY_BUILDING).setEnabled(curP.containsAPropertySet());
                buttons.get(NEXT_TURN).setEnabled(false);
                buttons.get(ROLL).setEnabled(true);
                handleGameOver();
            }
            case INIT -> {
                gameInitialization();
            }
            case ROLL -> {
                buttons.get(BUY).setEnabled(true);
                Player curP = model.getCurrentPlayer();
                GameBoardSquare curSquare = model.getBoard().getBoard().get(curP.getCurrentPos());
                updatePlayerPosition(curP);
                handlePotentialDoubles();
                handleRentOccurrance(curP, curSquare);
            }
            case LOAD -> {

                for(JButton b : buttons){
                    b.setEnabled(true);
                }
                System.out.println("LOAD BRUH");
                gameLoad();
            }
        }
    }

    private void gameLoad() {
        int i = 0;
        System.out.println("updating view with loaded game");

        //Adding board
        board.createBoardPanel(model.getBoard(), model.getDice());

        for(PlayerPanel p: playerPanels){
            if(model.getPlayers().get(i).isBot()){
                p.setPiece(model.getPlayers().get(i).getPlayerPiece().toString() + "_bot");

            }else{
                p.setPiece(model.getPlayers().get(i).getPlayerPiece().toString());
            }
            p.setMoney(model.getPlayers().get(i).getBalance());
            p.setPlayerPiece(model.getPlayers().get(i).getPlayerPiece());
            p.setCurPos(model.getPlayers().get(i).getCurrentPos());
            System.out.println(model.getPlayers().get(i).toString());
            i++;
        }
        for(JButton b : buttons){
            b.setEnabled(false);
        }
        eventView.setText(model.getCurrentPlayer().getPlayerPiece() + "'s turn");
        buttons.get(ROLL).setEnabled(true);
        System.out.println("ROLL SET TO TRUE");
    }

    public static void main(String[] args){
        new GameFrame();
    }
}
