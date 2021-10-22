import java.sql.SQLOutput;
import java.util.Scanner;

public class GameController {
    private static final int MAXNUMPLAYERS = 4;
    private Scanner in;
    private int counter = 0;
    private Board board;
    private Die[] dice;
    private Player[] players;
    private Player curPlayer;


    public GameController(){
        in = new Scanner(System.in);
        dice = new Die[2];
        dice[0] = new Die();
        dice[1] = new Die();

        //Create board here

    }

    public void nextTurn(){
        boolean canPlay = false;
        while(!canPlay){
            counter++;

            if(!players[counter % players.length].isBankrupt()){
                curPlayer = players[counter % players.length];
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
        //TODO
    }
    
    public boolean win(){
        return false;
    }

    public boolean run(){
        //Initialize players (get names and pieces)
        initializePlayers();
        boolean gameRunning = true;

        while(gameRunning){
            //play turn



            //Check if a player has won
            gameRunning = win();
            //Check which player has won

            //Change player turn
            nextTurn();

        }

        return false;
    }


}
