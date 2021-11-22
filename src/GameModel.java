import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/** Controls the Monopoly game
 * @author Team 12
 * @author Giancarlo Salvador, Spencer Antliff, Robbie Kuhn, Daniel Wang
 */
public class GameModel {
    /** The maximum number of players allowed */
    private static final int MAXNUMPLAYERS = 4;


    /** Starting balance of the players */
    private static final int STARTINGBALANCE = 1500;
    /** Counter to help decide player turn */
    private int counter = 0;
    /** Contains the board of the game */
    private Board board;
    /** Contains the dice to be used */
    private Dice dice;

    private Boolean gameOver;

    private int numBots;

    /** List of players */
    private List<Player> players;
    /** The current player*/
    private Player curPlayer;

    /** List of views*/
    private List<GameView> views;
    /**
     * Only and default constructor for GameModel
     */
    public GameModel(){
        dice = new Dice();
        players = new ArrayList<>();
        board = new Board();
        this.views = new ArrayList<>();
        this.gameOver = false;
    }
    /**
     * Initializes the game
     */

    public void initializeGame(){
        System.out.println("initializeGame called");
        dice = new Dice();
        players = new ArrayList<>();
        board = new Board();
        List<Piece> availablePieces = new ArrayList<>();
        availablePieces.add(Piece.HORSE);
        availablePieces.add(Piece.SHOE);
        availablePieces.add(Piece.CAR);
        availablePieces.add(Piece.BOAT);
        for( int i = 0; i < MAXNUMPLAYERS; i++){
            Player addedPlayer = new Player(availablePieces.remove(0), STARTINGBALANCE);
            if (i < numBots){
                addedPlayer.setBot(true);
            }
            players.add(addedPlayer);
        }
        curPlayer = players.get(0);
        if(views.size() > 0){
            System.out.println("views not empty");
        }else{
            System.out.println("views empty????");
        }
        curPlayer = players.get(0);

        for(GameView v: this.views){
            v.update(new MonopolyEvent(this,MonopolyEvent.EventType.INIT));
        }
        if(curPlayer.isBot()){
            botTurn();
        }
    }

    /**
     * Getter method for the current player
     * @return The current player
     */
    public Player getCurrentPlayer(){
        return this.curPlayer;
    }

    public int getNumBots() {
        return numBots;
    }
    public void addBot(){
        //if all the player are bots the gui updates too fast and a stackoverflowerror occures
        if(numBots < MAXNUMPLAYERS - 1){
            numBots++;
        }
    }
    public void removeBot(){
        if(numBots > 0){
            numBots--;
        }
    }
    public void setNumBots(int numBots) {
        this.numBots = numBots;
    }

    /**
     * The getter method for the gameOver field
     * @return True if the game is over, false otherwise
     */
    public Boolean getGameOver() {
        return gameOver;
    }

    /**
     * Sets current player to the next player in order to change the turn
     */
    public void nextTurn(){
        boolean canPlay = false;
        while(!canPlay){
            counter++;

            if(!players.get(counter % players.size()).isBankrupt()){
                curPlayer = players.get(counter % players.size());
                canPlay = true;
            }
        }
        win();

        for(GameView v: this.views){
            v.update(new MonopolyEvent(this,MonopolyEvent.EventType.NEXT));
        }

        if(curPlayer.isBot()){
            botTurn();
        }
    }
    public  void botTurn(){
        boolean doubles = true;
        GameBoardSquare curSquare;
        while(doubles){
            this.dice.rollDice();
            this.movePlayer();
            curSquare = this.getBoard().getSquare(this.getCurrentPlayer().getCurrentPos());

            if( curSquare instanceof Property){
                System.out.println(curPlayer.getPlayerPiece() + "_BOT IS BUYING PROPERTY");
                this.buyProperty((Property) curSquare);
                System.out.println(curPlayer.getBalance());
            }

            doubles = this.dice.getRollDouble();
        }
        this.nextTurn();
    }
    /**
     * Checks if a player wins
     * @return True if the current player has won, false otherwise
     */
    public void win(){
        int bankruptPLayers = 0;
        for (Player p : players) {
            if (!curPlayer.isBankrupt()  && p.isBankrupt()) {
                bankruptPLayers ++;
            }
        }

        if (bankruptPLayers == players.size() - 1){
            gameOver = true;
        }
    }

    /**
     * Getter method for the board
     * @return The board for the game
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * Getter method for the starting balance
     * @return The starting balance of the players
     */
    public static int getSTARTINGBALANCE() {
        return STARTINGBALANCE;
    }

    /**
     * Getter method for the dice used in the game
     * @return The dice object for the game
     */
    public Dice getDice(){
        return this.dice;
    }

    /**
     * Adds a game view to the model
     * @param view The view meant to represent the game
     */
    public void addGameView(GameView view){
        views.add(view);
    }

    /**
     * Removes a game view from the model
     * @param view The view to be removed from the game
     */
    public void removeGameView(GameView view){
        views.remove(view);
    }

    /**
     * Allows current player to buy a property
     * @param prop The property the current player will buy
     */
    public void buyProperty( Property prop){
        int cost = prop.getCost();
        if(prop.getOwner() == null && curPlayer.getBalance() > cost){
            curPlayer.decreaseBalance(cost);
            curPlayer.addProperty(prop);
            prop.setOwner(curPlayer);
        }


        for(GameView v: this.views){
            v.update(new MonopolyEvent(this,MonopolyEvent.EventType.BUY));
        }

    }
    public void buyBuilding(Property property){
        if(curPlayer.hasPropertySet(property) && curPlayer.getBalance() > property.getCost()){
            curPlayer.decreaseBalance((property.getBuildingCost()));
            property.buildingPurchased();
        }
        for(GameView v: this.views){
            v.update(new MonopolyEvent(this,MonopolyEvent.EventType.BUY_BUILDING, property));
        }
    }
    /**
     * Moves the player a certain number of squares
     */
    public void movePlayer(){

        //System.out.println(curPlayer.getPlayerPiece() + " is moving");
        //System.out.println("board 1: " + this.board);
        int oldPos = curPlayer.getCurrentPos();

        if(curPlayer.getInJail() || dice.triple_Roll()){
            for(GameView v: this.views){
                v.update(new MonopolyEvent(this,MonopolyEvent.EventType.JAIL));
                v.update(new MonopolyEvent(this,MonopolyEvent.EventType.ROLL));
            }
            return;
        }

        board.getBoard().get(curPlayer.getCurrentPos()).removePlayerFromSquare(curPlayer);

        int landedSquareIndex = (getDice().getRollValue() + curPlayer.getCurrentPos()) % board.getSize();
        GameBoardSquare curSquare = board.getBoard().get(landedSquareIndex);
        curPlayer.setCurrentPos(landedSquareIndex);


        curSquare.addPlayerToSquare(curPlayer);
        //System.out.println("please don't be empty " + curSquare.getPlayersOnSquare());
        //System.out.println("printing player pieces on square:");

        if(oldPos > landedSquareIndex){
            //player passed go
            System.out.println("Player passed go");
            curPlayer.increaseBalance(200);
            System.out.println(curPlayer.getBalance());
        }

        if(!(curSquare instanceof SpecialSquare)){
            if(((Property) curSquare).getOwner() != null){
                Player owner = ((Property) curSquare).getOwner();
                if(owner.getPlayerPiece() != curPlayer.getPlayerPiece()) {
                    if(((Property) curSquare).getPropertyType() == PropertyType.UTILITY){
                        curPlayer.payRent(owner, (Property) curSquare, dice.getRollValue());
                    } else {
                        curPlayer.payRent(owner, (Property) curSquare);
                    }
                }
            }
        }else if (curSquare.getType() == SquareType.GOTOJAIL){
            System.out.println("Player landed in Jail");
            curPlayer.setInJail(true);
            curPlayer.setCurrentPos(10);
            for(GameView v: this.views){
                v.update(new MonopolyEvent(this,MonopolyEvent.EventType.JAIL));
            }


        }
        else{
            if(curSquare.getType() == SquareType.TAX){
                if(landedSquareIndex == 4){
                    curPlayer.decreaseBalance(200);
                }else{
                    curPlayer.decreaseBalance(75);
                }
            }
        }

        if(curPlayer.getBalance() <= 0){
            curPlayer.goneBankrupt();
            board.getBoard().get(curPlayer.getCurrentPos()).removePlayerFromSquare(curPlayer);
        }

        for(GameView v: this.views){
            v.update(new MonopolyEvent(this,MonopolyEvent.EventType.ROLL));
            //v.update(new MonopolyEvent(this,MonopolyEvent.EventType.BUY));
        }

    }

    /**
     * Getter for the list of players
     * @return The list of players playing the game
     */
    public List<Player> getPlayers() {
        return players;
    }




    /*

    public void initializePlayers(){


        System.out.println("Number of Players: ");
        String playerString = in.nextLine();
        int playerNum;
        try{
            playerNum = Integer.parseInt(playerString);
        }catch (NumberFormatException ex) {
            System.out.println("Invalid player count!! 3 players being added");
            playerNum = 3;
        }

        if(playerNum < 2 || playerNum > MAXNUMPLAYERS){
            System.out.println("Invalid player count!! 3 players being added");
            playerNum = 3;
        }

        List<Piece> availablePieces = new ArrayList<>();
        availablePieces.add(Piece.HORSE);
        availablePieces.add(Piece.SHOE);
        availablePieces.add(Piece.CAR);
        availablePieces.add(Piece.BOAT);


        for(int i = 0; i<playerNum; i++){
            System.out.println("\nAvailable pieces:");
            for(int pieceIndex = 0; pieceIndex<availablePieces.size(); pieceIndex++){
                System.out.println("(" + pieceIndex + ")" + availablePieces.get(pieceIndex));
            }
            System.out.println("P" + i + ", What piece would you like?");
            String choice = in.nextLine();

            int chosenPiece;
            try{
                chosenPiece = Integer.parseInt(choice);
            }catch (NumberFormatException ex) {
                System.out.println("Invalid choice! Random piece will be given");
                chosenPiece = 0;
            }
            Player addedPlayer = new Player(availablePieces.remove(chosenPiece), STARTINGBALANCE);
            players.add(addedPlayer);
            System.out.println("You have the " + addedPlayer.getPlayerPiece());

        }
        curPlayer = players.get(0);

        }
         */






    /**
     * Prints each players' current position and their current square
     */
    /*
    public void printBoardState(){
        for(Player p: players){
            System.out.println(String.format("Player: %s \nPosition: %s \n", p.getPlayerPiece().toString(), board.getSquare(p.getCurrentPos())));
        }

    }

     */





    /**
     * Runs the game
     */
    /*
    public void run(){
        //Initialize players (get names and pieces)
        initializePlayers();
        boolean gameRunning = true;
        boolean endTurn = true;

        while(gameRunning){
            //Reset
            endTurn = true;

            //Roll Dice
            int movement = dice.rollDice();





            String movementMessage = String.format("%s rolled a: %s", curPlayer.getPlayerPiece().toString(),  movement);
            if(dice.checkDouble()){
                endTurn = false;
                movementMessage += " DOUBLES!!";
            }
            System.out.println(movementMessage);

            //Move Player
            int landedSquareIndex = (movement + curPlayer.getCurrentPos()) % board.getSize();
            curPlayer.setCurrentPos(landedSquareIndex);
            GameBoardSquare squareLanded = board.getSquare(landedSquareIndex);
            System.out.print(String.format("%s landed on: ",curPlayer.getPlayerPiece()));
            System.out.println(squareLanded);

            System.out.println(String.format("Would %s like to view their player info? (Y/N)",curPlayer.getPlayerPiece()));
            if(in.nextLine().toLowerCase().equals("y")){
                System.out.println(curPlayer);;
            }

            System.out.println(String.format("Would %s like to view board state? (Y/N)",curPlayer.getPlayerPiece()));
            if(in.nextLine().toLowerCase().equals("y")){
                printBoardState();
            }

            //Check if someone owns the square
            boolean notOwned = true;
            if(squareLanded instanceof Property) {
                for(Player p: players){
                    if (p.getProperties().containsKey((Property)squareLanded)) {
                        notOwned = false;
                        System.out.println("You landed on " + p.getPlayerPiece() + "'s property!");
                        System.out.println("Pay $" + ((Property)squareLanded).getRent());

                        //Pay rent
                        curPlayer.payRent(p, (Property)squareLanded);

                        break;
                    }
                }

                //BUY property
                if (notOwned) {
                    //Decide to buy
                    System.out.println(String.format("No one owns this property, would %s like to buy it? (Y/N)",curPlayer.getPlayerPiece()));
                    if(in.nextLine().toLowerCase().equals("y")){
                        if (curPlayer.getBalance() < ((Property) squareLanded).getCost()){
                            System.out.println(String.format("%s cannot afford this property...",curPlayer.getPlayerPiece()));
                        }else {
                            curPlayer.buyProperty((Property) squareLanded);
                        }
                    } else {
                        System.out.println(String.format("%s did not buy the property...",curPlayer.getPlayerPiece()));
                    }
                }
            }

            gameRunning = this.update(gameRunning, endTurn);

        }

        nextTurn();

        System.out.println(String.format("%s has won the game!", curPlayer.getPlayerPiece().toString()));
    }


     */
    
    /*
    public static void main(String[] args) {
        GameModel gc = new GameModel();

        gc.run();
    }

 payRent_turn
     */
  }
