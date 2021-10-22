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

    public void turn(){
        counter++;
        if(counter > MAXNUMPLAYERS){
            counter -= players.length;
        }
        curPlayer = players[counter % players.length];
    }

    public void payRent(Player payee,Player payer,Property property){
        int cost = property.getCost();

        if(payer.getBalance() >= cost){
            payer.decreaseBalance(cost);
            payee.increaseBalance(cost);
        }else{
            payee.increaseBalance(payer.getBalance());
            payer.decreaseBalance(cost);
            System.out.println("You cannot afford rent, you have gone bankrupt!");
            payer.setBankrupt(true);
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
            turn();

        }

        return false;
    }


}
