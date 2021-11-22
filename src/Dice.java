import java.util.Random;

/** Represents a single die
 * @author Team 12
 * @author Giancarlo Salvador, Spencer Antliff, Robbie Kuhn, Daniel Wang
 */
public class Dice {

    /** The maximum value a die can roll */
    private static final int MAX = 6;
    /** The minimum a die can roll */
    private static final int MIN = 1;
    /** The value of the roll */
    private int rollValue;
    /** Random number generator */
    private Random rand;
    private boolean roll_double = false;

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
     * Getter for the rollValue field
     * @return The current value of the die
     */
    public int getRollValue(){
        return rollValue;
    }

    /**
     * Setter for the rollValue field
     * @param rollValue the combined value of the 2 dice
     */
    public void setRollValue(int rollValue){
        this.rollValue = rollValue;
    }

    /**
     * Getter for the rollDouble field
     * @return True if both dice equal the same value, false otherwise
     */
    public boolean getRollDouble(){
        return roll_double;
    }

    /**
     * Rolls all the dice
     * @return The sum of all dice rolls
     *
     */
    public void rollDice(){
        //System.out.println("Dice 2: " + this);
        int sum = 0;
        for(int i = 0; i < dice.length; i++){
            dice[i] = rand.nextInt((MAX - MIN) + 1) + MIN;
            sum += dice[i];
        }
        //System.out.println("dice[0] " + dice[0] + "dice[1]" + dice[1]);
        roll_double = dice[0] == dice[1];

        //rollValue = sum;
        rollValue = 12;
    }

    /**
     * Getter for the individual dice fields
     * @return values of each die
     */
    public int[] getDiceValues() {
        return dice;
    }


}
