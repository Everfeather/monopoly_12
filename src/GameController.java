import java.sql.SQLOutput;
import java.util.*;

/** Controls the Monopoly game
 * @author Team 12
 * @author Giancarlo Salvador, Spencer Antliff, Robbie Kuhn, Daniel Wang
 */
public class GameController {
    /** The maximum number of players allowed */
    private static final int MAXNUMPLAYERS = 4;
    /** Starting balance of the players */
    private static final int STARTINGBALANCE = 1500;
    /** Scanner to allow input from terminal */
    private Scanner in;
    /** Counter to help decide player turn */
    private int counter = 0;
    /** Contains the board of the game */
    private Board board;
    /** Contains the dice to be used */
    private Die[] dice;
    /** List of players */
    private List<Player> players;
    /** The current player*/
    private Player curPlayer;

    /**
     * Only and default constructor for Gamecontroller
     */
    public GameController(){
        in = new Scanner(System.in);
        dice = new Die[2];
        dice[0] = new Die();
        dice[1] = new Die();
        players = new ArrayList<>();
        board = new Board();
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

    }

    /**
     * Pays rent
     * @param payee The player to be paid
     * @param property The property whose rent will be paid
     */
    public void payRent(Player payee,Property property){
        int cost = property.getCost();

        if(curPlayer.getBalance() >= cost){
            curPlayer.decreaseBalance(cost);
            payee.increaseBalance(cost);
        }else{
            payee.increaseBalance(curPlayer.getBalance());
            curPlayer.decreaseBalance(cost);
            System.out.println(String.format("%s cannot afford rent, %s has gone bankrupt!",curPlayer.getPlayerPiece(),curPlayer.getPlayerPiece()));
            curPlayer.setBankrupt(true);
        }
    }

    /**
     * Creates players and allows them to choose their own piece
     */
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

    /**
     * Rolls all the dice
     * @return The sum of all dice rolls
     */
    public int rollDice(){
        int sum =0;
        for(Die d: dice){
            sum = sum + d.roll();

        }
        return sum;

    }

    /**
     * Checks if the player is bankrupt
     */
    public void goneBankrupt(){
        HashMap<Property, PropertyType> properties = curPlayer.getProperties();
        if(curPlayer.getBalance() == 0){
            curPlayer.setBankrupt(true);
            // TODO: loop through the properties owned by the curplayer and set all to null
            for(Property key: properties.keySet()){
                key.removeOwner();
            }
            curPlayer.getProperties().clear();
            System.out.println(curPlayer.getPlayerPiece() + "is Bankrupt");


        }
    }

    /**
     * Prints each players' current position and their current square
     */
    public void printBoardState(){
        for(Player p: players){
            System.out.println(String.format("Player: %s \nPosition: %s \n", p.getPlayerPiece().toString(), board.getSquare(p.getCurrentPos())));
        }

    }

    /**
     * Checks if a player wins
     * @return True if a player has won, false otherwise
     */
    public boolean win(){
        int bankruptPLayers = 0;
        for (Player p : players) {
            if (!curPlayer.isBankrupt()  && p.isBankrupt()) {
                bankruptPLayers ++;
            }
        }

        if (bankruptPLayers == players.size() - 1){
            return true;
        }
        return false;
    }

    /**
     * Buys a property
     * @param p The player buying the property
     * @param prop The property to be bought
     */
    public void buyProperty(Player p, Property prop){
        int val = prop.getCost();
        p.decreaseBalance(val);
        p.addProperty(prop);
        prop.setOwner(p);
    }

    /**
     * Runs the game
     */
    public void run(){
        //Initialize players (get names and pieces)
        initializePlayers();
        boolean gameRunning = true;
        boolean endTurn = true;

        while(gameRunning){
            //Reset
            endTurn = true;

            //Roll Dice
            int movement = rollDice();
            String movementMessage = String.format("%s rolled a: %s", curPlayer.getPlayerPiece().toString(),  movement);
            if(dice[0].getRollValue() == dice[1].getRollValue()){
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
                        payRent(p, (Property)squareLanded);

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
                            buyProperty(curPlayer, (Property) squareLanded);
                        }
                    } else {
                        System.out.println(String.format("%s did not buy the property...",curPlayer.getPlayerPiece()));
                    }
                }
            }

            //Checks if current player has gone bankrupt
            goneBankrupt();

            //Check if a player has won
            gameRunning = !win();
            //Check which player has won


            //Change player turn
            if(endTurn) {
                nextTurn();
            }

        }

        nextTurn();

        System.out.println(String.format("%s has won the game!", curPlayer.getPlayerPiece().toString()));
    }

    /**
     * Main function
     * @param args
     */
    public static void main(String[] args) {
        GameController gc = new GameController();

        gc.run();
    }

}
