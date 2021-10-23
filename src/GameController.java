import java.sql.SQLOutput;
import java.util.*;

public class GameController {
    private static final int MAXNUMPLAYERS = 4;
    private static final int STARTINGBALANCE = 200;
    private Scanner in;
    private int counter = 0;
    private Board board;
    private Die[] dice;
    private List<Player> players;
    private Player curPlayer;


    public GameController(){
        in = new Scanner(System.in);
        dice = new Die[2];
        dice[0] = new Die();
        dice[1] = new Die();
        players = new ArrayList<>();
        board = new Board();

        //Create board here

    }

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

    public void payRent(Player payee,Property property){
        int cost = property.getCost();

        if(curPlayer.getBalance() >= cost){
            curPlayer.decreaseBalance(cost);
            payee.increaseBalance(cost);
        }else{
            payee.increaseBalance(curPlayer.getBalance());
            curPlayer.decreaseBalance(cost);
            System.out.println("You cannot afford rent, you have gone bankrupt!");
            curPlayer.setBankrupt(true);
        }
    }

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

        if(playerNum < 2 || playerNum > 4){
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

    public int rollDice(){
        int sum =0;
        for(Die d: dice){
            sum = sum + d.roll();

        }
        return sum;

    }

    public void goneBankrupt(){
        HashMap<Property, PropertyType> properties = curPlayer.getProperties();
        if(curPlayer.getBalance() == 0){
            curPlayer.setBankrupt(true);
            // TODO: loop through the properties owned by the curplayer and set all to null
            for(Property key: properties.keySet()){
                key.setNull();
            }
            curPlayer.getProperties().clear();
            System.out.println(curPlayer.getPlayerPiece() + "is Bankrupt");


        }
    }

    public void printBoard(){
        for(Player p: players){
            System.out.println(String.format("Player: %s \n Position: %s \n", p.getPlayerPiece().toString(), board.getSquare(p.getCurrentPos())));
        }

    }



    public boolean win(){
        int bankruptPLayers = 0;
        for (Player p : players) {
            if (curPlayer.isBankrupt() == false && p.isBankrupt() == true) {
                bankruptPLayers ++;
            }
        }

        if (bankruptPLayers == players.size() - 1){
            return true;
        }
        return false;
    }

    public void buyProperty(Player p, Property prop){
        int val = prop.getCost();
        p.decreaseBalance(val);
        p.addProperty(prop);
    }

    public boolean run(){
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
            System.out.println("You landed on: ");
            System.out.println(squareLanded);

            System.out.println("Would you like to view board state? (Y/N)");
            if(in.nextLine().toLowerCase().equals("y")){
                printBoard();
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
                    System.out.println("No one owns this property, would you like to buy it? (Y/N)");
                    if(in.nextLine().toLowerCase().equals("y")){
                        if (curPlayer.getBalance() < ((Property) squareLanded).getCost()){
                            System.out.println("You cannot afford this property...");
                        }else {
                            buyProperty(curPlayer, (Property) squareLanded);
                        }
                    } else {
                        System.out.println("You did not buy the property...");
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

        return false;
    }

    public static void main(String[] args) {
        GameController gc = new GameController();

        gc.run();
    }

}
