import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameFrame extends JFrame implements GameView {


    private final int ROLL = 0;
    private final int NEXT_TURN = 1;
    private final int BUY = 2;
    private final int NEW_GAME = 3;
    private BoardPanel board;
    private JPanel contentPane;
    private PlayerPanel[] playerPanels;
    private JLabel eventView;
    private int numPlayers;
    private ArrayList<JButton> buttons;
    private GameModel model;

    private static final Color bgColour = Color.PINK;


    public GameFrame(){
        super("MONOPOLY GAME TIME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1080,720);
        this.buttons = new ArrayList<JButton>();
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
                //find panel player is on
                //get the propertyPopUpText
                //edit it so the player owner is updated

                JPanel panel = this.getBoardPanel().getSquares().get(curP.getCurrentPos());
                if (panel instanceof PropertyPanel) {
                    ((PropertyPanel) panel).setPropertyDescription(model.getBoard().getBoard().get(curP.getCurrentPos()).toString());
                }
                buttons.get(BUY).setEnabled(false);
                eventView.setText(curP.getPlayerPiece() + " bought " + model.getBoard().getBoard().get(curP.getCurrentPos()).getName());
            }

            case NEXT ->{
                for(JButton b : buttons){
                    b.setEnabled(true);
                }
                buttons.get(NEW_GAME).setEnabled(false);
                buttons.get(BUY).setEnabled(false);

                eventView.setText(model.getCurrentPlayer().getPlayerPiece() + "'s turn");


                buttons.get(NEXT_TURN).setEnabled(false);
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
                    p.setPlayerPiece(model.getPlayers().get(i).getPlayerPiece());
                    p.setCurPos(model.getPlayers().get(i).getCurrentPos());
                    i++;
                }
                for(JButton b : buttons){
                    b.setEnabled(true);
                }
                eventView.setText(model.getCurrentPlayer().getPlayerPiece() + "'s turn");
                buttons.get(NEXT_TURN).setEnabled(false);
                buttons.get(BUY).setEnabled(false);
                buttons.get(NEW_GAME).setEnabled(false);
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
                this.board.getDiceRollPane().setText(model.getCurrentPlayer().getPlayerPiece().toString() + " Rolled: " + model.getDice().getRollValue());
                int count = 0;
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
                    if(this.getBoardPanel().getSquares().get(count) instanceof PropertyPanel){
                        PropertyPanel square = (PropertyPanel) this.getBoardPanel().getSquares().get(count);
                        square.getPropertyInfoPopUp().setText(s);
                    }else{
                        SpecialSquarePanel square = (SpecialSquarePanel) this.getBoardPanel().getSquares().get(count);
                        square.getSpecialSquarePopUp().setText(s);
                    }
                    count++;
                }
                if(model.getDice().getRollDouble()){
                    this.board.getDiceRollPane().setText(model.getCurrentPlayer().getPlayerPiece().toString() + " Rolled: " + model.getDice().getRollValue() + ". Nice, doubles!");
                    //System.out.println(model.getDice().getDiceValues()[0] + " " + model.getDice().getDiceValues()[1]);
                }else{
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
    public BoardPanel getBoardPanel() {
        return board;
    }
}
