import java.sql.SQLOutput;
import java.util.Scanner;

public class GameController {
    private static final int MAXNUMPLAYERS = 4;
    private int counter = 0;
    private Board board;
    private Die[] dice;
    private Player[] players;
    private Player curPlayer;


    public void Turn(){
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
}
