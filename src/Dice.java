import java.util.Random;

/** Represents a single die
 * @author Giancarlo Salvador
 */
public class Dice {

    /** The maximum value a die can roll */
    static final int MAX = 6;
    /** The minimum a die can roll */
    static final int MIN = 1;
    /** The value of the roll */
    int rollValue;
    /** Random number generator */
    Random rand;
    boolean roll_double = false;
    /** Contains the dice to be used */
    private int[] dice;

    /**
     * Default, only constructor
     */
    public Dice(){
        this.rand = new Random();
        dice = new int[2];
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

    public boolean checkDouble(){
        return roll_double;
    }

    /**
     * Rolls all the dice
     * @return The sum of all dice rolls
     *
     */
    public int rollDice(){
        int sum = 0;
        for(int i : dice){
            i = rand.nextInt((MAX - MIN) + 1) + MIN;
            sum += i;
        }

        roll_double = dice[0] == dice[1];

        return sum;
    }


}
