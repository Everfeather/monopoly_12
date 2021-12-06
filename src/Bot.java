import java.io.Serializable;

public class Bot extends Player implements Serializable {
    private GameModel model;
    /**
     * Only constructor for the Bot_player object
     *
     * @param p       Piece, The unique piece that represents the player
     * @param balance int, The starting balance of the player
     */
    public Bot(Piece p, int balance, GameModel model) {
        super(p, balance);
        this.model = model;
        this.setBot(true);
    }

    public  void botTurn(){
        boolean doubles = true;
        GameBoardSquare curSquare;
        while(doubles){
            model.getDice().rollDice();
            model.movePlayer();
            curSquare = model.getBoard().getSquare(model.getCurrentPlayer().getCurrentPos());

            if( curSquare instanceof Property){
                System.out.println(model.getCurrentPlayer().getPlayerPiece() + "_BOT IS BUYING PROPERTY");
                model.buyProperty((Property) curSquare);
                System.out.println(model.getCurrentPlayer().getBalance());
            }

            doubles = model.getDice().getRollDouble();
        }
        model.nextTurn();
    }

}
