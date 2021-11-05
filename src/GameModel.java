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
    /** Scanner to allow input from terminal */
    private Scanner in;
    /** Counter to help decide player turn */
    private int counter = 0;
    /** Contains the board of the game */
    private Board board;
    /** Contains the dice to be used */
    private Dice dice;
    /** List of players */
    private List<Player> players;
    /** The current player*/
    private Player curPlayer;

    /**
     * Only and default constructor for Gamecontroller
     */
    public GameModel(){
        dice = new Dice();
        in = new Scanner(System.in);
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

            //Checks if current player has gone bankrupt
            curPlayer.goneBankrupt();

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
        GameModel gc = new GameModel();

        gc.run();
    }

}
