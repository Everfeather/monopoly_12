import java.util.Random;

/** Represents a single die
 * @author Giancarlo Salvador
 */
public class Die {

    /** The maximum value a die can roll */
    static final int MAX = 6;
    /** The minimum a die can roll */
    static final int MIN = 1;
    /** The value of the roll */
    int rollValue;
    /** Random number generator */
    Random rand;

    /**
     * Default, only constructor
     */
    public Die(){
        this.rand = new Random();
    }

    /**
     * Rolls the die
     * @return The random number between MIN and MAX inclusive
     */
    public int roll(){
        rollValue = rand.nextInt((MAX - MIN) + 1) + MIN;
        return rollValue;
    }

    /**
     * Getter for the rollValue field
     * @return The current value of the die
     */
    public int getRollValue(){
        return rollValue;
    }
}
