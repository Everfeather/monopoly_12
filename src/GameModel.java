import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

/** Controls the Monopoly game
 * @author Team 12
 * @author Giancarlo Salvador, Spencer Antliff, Robbie Kuhn, Daniel Wang
 */
public class GameModel implements Serializable {
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
    /** boolean for if the game is over or not */
    private Boolean gameOver;
    /** number of bots in the game */
    private int numBots;
    /** constant for how many turns in jail */
    private final int jailTurns = 3;
    /** amount to bail out of jail */
    private final int jailBailAmount = 50;
    /** amount gained when passing go or when landing on $200 tax*/
    private final int passGoAmount = 200;
    /** amount to pay when landing on $75 tax */
    private final int tax = 75;
    /** in jail square number*/
    private final int inJail = 10;
    /** save file name */
    private final String fileName = "gameModel.txt";
    /** List of players */
    private List<Player> players;
    /** The current player*/
    private Player curPlayer;
    /** List of views*/
    private List<GameView> views;
    /** boolean for if a game is saved*/
    private boolean gameSaved;

    /**
     * Only and default constructor for GameModel
     */
    public GameModel(){
        dice = new Dice();
        players = new ArrayList<>();
        board = new Board("CanadianMonopolyXML.xml");
        this.views = new ArrayList<>();
        this.gameOver = false;

        File f = new File(fileName);
        this.setGameSaved(f.exists() && !f.isDirectory());
    }
    /**
     * Imports a game file
     */
    public void importFile(String fileName){

        try {
            DefaultHandler test = new DefaultHandler();
            File inputFile = new File(fileName);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(inputFile, test);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Initializes the game
     */
    public void initializeGame(String filename){
        System.out.println("initializeGame called");
        dice = new Dice();
        players = new ArrayList<>();
        board = new Board(filename);
        List<Piece> availablePieces = new ArrayList<>();
        availablePieces.add(Piece.HORSE);
        availablePieces.add(Piece.SHOE);
        availablePieces.add(Piece.CAR);
        availablePieces.add(Piece.BOAT);
        for( int i = 0; i < MAXNUMPLAYERS; i++){
            if (i < numBots){
                players.add(new Bot(availablePieces.remove(0), STARTINGBALANCE, this));
            }else{
                players.add(new Player(availablePieces.remove(0), STARTINGBALANCE));
            }
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
            ((Bot)curPlayer).botTurn();
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
            ((Bot)curPlayer).botTurn();
        }
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
            if(prop.getPropertyType() == PropertyType.RAILROAD){
                int numOwnedRailRoads = 0;
                for(Map.Entry<Property, PropertyType> entry : curPlayer.getProperties().entrySet() ){
                    System.out.println("PROPERTIES: \n------");
                    System.out.println(curPlayer.getProperties().entrySet());
                    if( entry.getValue() == PropertyType.RAILROAD){
                        numOwnedRailRoads++;
                    }
                }
                for(Map.Entry<Property, PropertyType> entry : curPlayer.getProperties().entrySet() ){
                    if( entry.getValue() == PropertyType.RAILROAD){
                        entry.getKey().setRent(numOwnedRailRoads * 15);
                    }
                }
            }
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

        if(curPlayer.getInJail()){
            handleInJail();
            return;
        }

        board.getBoard().get(curPlayer.getCurrentPos()).removePlayerFromSquare(curPlayer);

        int landedSquareIndex = (getDice().getRollValue() + curPlayer.getCurrentPos()) % board.getSize();
        GameBoardSquare curSquare = board.getBoard().get(landedSquareIndex);
        curPlayer.setCurrentPos(landedSquareIndex);

        curSquare.addPlayerToSquare(curPlayer);

        if(oldPos > landedSquareIndex){
            handlePassGo();
        }
        System.out.println("Dice num doubles: " + dice.getNumDoublesRolled());
        handleLandedSquare(landedSquareIndex, curSquare);

        if(curPlayer.getBalance() <= 0){
            curPlayer.goneBankrupt();
            board.getBoard().get(curPlayer.getCurrentPos()).removePlayerFromSquare(curPlayer);
        }

        for(GameView v: this.views){
            v.update(new MonopolyEvent(this,MonopolyEvent.EventType.ROLL));
        }

    }

    private void handleLandedSquare(int landedSquareIndex, GameBoardSquare curSquare) {
        System.out.println(curSquare);
        if(!(curSquare instanceof SpecialSquare)){
            if(((Property) curSquare).getOwner() != null){
                Player owner = ((Property) curSquare).getOwner();
                if(owner.getPlayerPiece() != curPlayer.getPlayerPiece()) {
                    System.out.println(curPlayer.getPlayerPiece() + " is paying rent!\n---------");
                    if(((Property) curSquare).getPropertyType() == PropertyType.UTILITY){
                        curPlayer.payRent(owner, (Property) curSquare, dice.getRollValue());
                    } else {
                        curPlayer.payRent(owner, (Property) curSquare);
                    }
                    System.out.println(curPlayer);
                }
            }

        }
        if (curSquare.getType() == SquareType.GOTOJAIL || dice.triple_Roll()){
            board.getBoard().get(curPlayer.getCurrentPos()).removePlayerFromSquare(curPlayer);
            System.out.println("Player landed in Jail");
            curPlayer.setInJail(true);
            curPlayer.setCurrentPos(inJail);
            board.getBoard().get(inJail).addPlayerToSquare(curPlayer);
            for(GameView v: this.views){
                v.update(new MonopolyEvent(this,MonopolyEvent.EventType.JAIL));
            }


        }
        else{
            if(curSquare.getType() == SquareType.TAX){
                if(landedSquareIndex == 4){
                    curPlayer.decreaseBalance(passGoAmount);
                }else{
                    curPlayer.decreaseBalance(tax);
                }
            }
        }
    }

    private void handlePassGo() {
        //player passed go
        System.out.println("Player passed go");
        curPlayer.increaseBalance(passGoAmount);
        System.out.println(curPlayer.getBalance());
    }

    private void handleInJail() {
        System.out.println("Turns in jail: " + curPlayer.getTurnsInJail());
        if(!dice.getRollDouble() && curPlayer.getTurnsInJail() < jailTurns){
            curPlayer.increaseTurnsInJail();

        }else{
            if(curPlayer.getTurnsInJail() == jailTurns){
                curPlayer.decreaseBalance(jailBailAmount); //criminally owned
            }
            curPlayer.setInJail(false);
            board.getBoard().get(curPlayer.getCurrentPos()).removePlayerFromSquare(curPlayer);
            int landedSquareIndex = (getDice().getRollValue() + curPlayer.getCurrentPos()) % board.getSize();
            GameBoardSquare curSquare = board.getBoard().get(landedSquareIndex);
            curPlayer.setCurrentPos(landedSquareIndex);
            curPlayer.setTurnsInJail(0);
            curSquare.addPlayerToSquare(curPlayer);
        }
        for(GameView v: this.views) {
            v.update(new MonopolyEvent(this, MonopolyEvent.EventType.JAIL));
            v.update(new MonopolyEvent(this, MonopolyEvent.EventType.ROLL));
        }
    }

    /**
     * Getter for the list of players
     * @return The list of players playing the game
     */
    public List<Player> getPlayers() {
        return players;
    }

    public void save() {
        FileOutputStream fileOutputStream
                = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(counter);
            objectOutputStream.writeObject(board);
            objectOutputStream.writeObject(dice);
            objectOutputStream.writeObject(gameOver);
            objectOutputStream.writeObject(numBots);
            objectOutputStream.writeObject(curPlayer);
            objectOutputStream.writeObject(players);
            objectOutputStream.writeObject(views);

            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("** Data Written to the file successfully **");
        this.setGameSaved(true);
    }

    public void loadGame(){

        try {
            FileInputStream fileInputStream
                    = new FileInputStream(fileName);
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
                try {
                    this.counter = (Integer) objectInputStream.readObject();
                    this.board = (Board) objectInputStream.readObject();
                    this.dice = (Dice) objectInputStream.readObject();
                    this.gameOver = (Boolean) objectInputStream.readObject();
                    this.numBots = (Integer) objectInputStream.readObject();
                    this.curPlayer = (Player) objectInputStream.readObject();
                    this.players = (List<Player>) objectInputStream.readObject();
                    //this.views = (List<GameView>) objectInputStream.readObject();
                }catch(IOException | ClassNotFoundException objectNotFoundException) {
                    objectNotFoundException.printStackTrace();
                }
            objectInputStream.close();
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        for(GameView v: this.views){
            v.update(new MonopolyEvent(this,MonopolyEvent.EventType.LOAD));
        }
        System.out.println("*** Game loaded ***");
    }

    public boolean isGameSaved() {
        return gameSaved;
    }

    public void setGameSaved(boolean gameSaved) {
        this.gameSaved = gameSaved;
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
