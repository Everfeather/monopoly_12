/**
 * The unique player identifier enum
 * @author Giancarlo Salvador
 */
public enum Piece {
    HORSE,
    SHOE,
    CAR,
    BOAT;
    private String s = "";

    public String getName(Piece piece){
        switch(piece){
            case CAR -> s   += "c";
            case BOAT -> s  += "b";
            case SHOE -> s  += "s";
            case HORSE -> s += "h";
        }
        return s;

    }

}
