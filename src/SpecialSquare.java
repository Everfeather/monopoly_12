import java.io.Serializable;

/** Made to represent a square that is not a property
 * Will have more functionality once a GUI is added
 * @author Giancarlo Salvador
 */
public class SpecialSquare extends GameBoardSquare implements Serializable {
    public SpecialSquare(String name,SquareType type ){
        super(type,name);
    }
    public SpecialSquare(){
        super();
    };
}
