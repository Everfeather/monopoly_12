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
            case CAR -> {
                return "c";
            }
            case BOAT -> {
                return "b";
            }
            case SHOE -> {
                return "s";
            }
            case HORSE -> {
                return "h";
            }
        }
        return s;

    }

}
