import java.util.Random;

public class Die {

    static final int MAX = 6;
    static final int MIN = 1;
    int rollValue;
    Random rand;

    public Die(){
        this.rand = new Random();
    }

    public int roll(){
        rollValue = rand.nextInt((MAX - MIN) + 1) + MIN;
        return rollValue;
    }

    public int getRollValue(){
        return rollValue;
    }
}
