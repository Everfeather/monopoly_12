import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Represents a player in monopoly
 * @author Robbie Kuhn
 */
public class Player implements Serializable {

    /** Represents the unique player piece */
    private Piece playerPiece;

    /** The amount of money the player currently has */
    private int balance;

    /** A hashmap of the properties that the player owns */
    private HashMap<Property,PropertyType> properties;

    /** The current position of the player on the board */
    private int currentPos;

    /** Flag for if the player is currently in jail */
    private boolean inJail;

    /** Counter for the number of turns in a row the player has been in jail */
    private int turnsInJail;

    /** Flag for if the player is bankrupt or not (true if bankrupt) */
    private boolean bankrupt;


    private boolean isBot;



    /**
     * Only constructor for the player object
     * @param p Piece, The unique piece that represents the player
     * @param balance int, The starting balance of the player
     */
    public Player(Piece p, int balance){
        this.playerPiece = p;
        this.bankrupt = false;
        this.balance = balance;
        this.properties = new HashMap<Property, PropertyType>();
        this.currentPos = 0;
        this.inJail = false;
        this.turnsInJail = 0;
        this.isBot = false;
    }

    /**
     * Getter for the playerPiece field
     * @return Piece enum for playerPiece
     */
    public Piece getPlayerPiece() {
        return playerPiece;
    }

    /**
     * Setter for the player piece object
     * @param playerPeice The Piece enum that will uniquely represent the player
     */
    public void setPlayerPiece(Piece playerPeice) {
        this.playerPiece = playerPeice;
    }

    /**
     * Getter for the player's amount of money
     * @return int that is the balance of the player
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Increases the balance of the player
     * @param amount int, for the amount to be added
     */
    public void increaseBalance(int amount){
        this.balance += amount;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    /**
     * Decreases the balance of the player
     * @param amount int, for the amount to be removed
     */
    public void decreaseBalance(int amount){
        this.balance -= amount;
    }

    /**
     * Getter for properties field
     * @return Hashmap containing properties of the player
     */
    public HashMap<Property, PropertyType> getProperties() {
        return properties;
    }

    /**
     * Getter for current position of player
     * @return The current position of the player
     */
    public int getCurrentPos() {
        return currentPos;
    }

    /**
     * Setter for the player's current position
     * @param currentPos The new position of the player
     */
    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    /**
     * Getter for the inJail flag
     * @return True if in jail, false otherwise
     */
    public boolean getInJail() {
        return inJail;
    }

    /**
     * Setter for the inJail flag
     * @param inJail True if in jail, false otherwise
     */
    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    /**
     * Getter for turnsInJail counter
     * @return The number of turns in a row the player has been in jail
     */
    public int getTurnsInJail() {
        return turnsInJail;
    }

    /**
     * Setter for the turnsInJail counter
     * @param turnsInJail The number of turns in a row the player has been in jail
     */
    public void setTurnsInJail(int turnsInJail) {
        this.turnsInJail = turnsInJail;
    }

    public void increaseTurnsInJail(){
        turnsInJail++;
    }
    /**
     * Getter for the bankrupt flag
     * @return True if the player is bankrupt, false otherwise
     */
    public boolean isBankrupt() {
        return bankrupt;
    }

    /**
     * Setter for the bankrupt flag
     * @param bankrupt True if the player is bankrupt, false otherwise
     */
    public void setBankrupt(boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    /**
     * Checks if the user has the entire property set
     * @param property The property that contains the set in question
     * @return True if the user has all the properties in the set, false otherwise
     */
    public boolean hasPropertySet(Property property){
       int num = 0;
       int setSize = property.getColourSetSize();
        for (PropertyType i: this.properties.values()) {

            if (i == property.getPropertyType()){
                num++;
            }
        }
        return num == setSize;
    }

    public boolean containsAPropertySet(){
        HashMap<PropertyType, Integer> valueMap = new HashMap<>();
        HashMap<PropertyType, Integer> sizeMap = new HashMap<>();
        for(Property p: properties.keySet()){
            if(valueMap.containsKey(p.getPropertyType())){
                valueMap.put(p.getPropertyType(), valueMap.get(p.getPropertyType()) + 1);
            } else {
                valueMap.put(p.getPropertyType(), 1);
                sizeMap.put(p.getPropertyType(), p.getColourSetSize());
            }
        }

        for(PropertyType type: valueMap.keySet()){
            if(sizeMap.get(type) == valueMap.get(type)){
                return true;
            }
        }
        return false;

    }

    /**
     * Adds property to the hashmap of properties that the user owns
     * @param prop The property to be added
     */
    public void addProperty(Property prop){
        this.properties.put(prop, prop.getPropertyType());
    }

    @Override
    public String toString(){
        String s;
        if(this.isBot){
            s = String.format("\nBot: %s\nBalance: %d\nProperties:\n",this.getPlayerPiece(),this.getBalance());

        }else{
            s = String.format("\nPlayer: %s\nBalance: %d\nProperties:\n",this.getPlayerPiece(),this.getBalance());
        }
        return s;
    }

    /**
     * Checks if the player is bankrupt
     */
    public void goneBankrupt(){
        HashMap<Property, PropertyType> properties = this.getProperties();
        if(this.getBalance() == 0){
            this.setBankrupt(true);
            for(Property key: properties.keySet()){
                key.removeOwner();
            }
            this.getProperties().clear();
            System.out.println(this.getPlayerPiece() + "is Bankrupt");

        }
    }


    /**
     * Pays rent
     * @param payee The player to be paid
     * @param property The property whose rent will be paid
     */
    public void payRent(Player payee,Property property){
        //testing val
//        int rent = property.getBuildingCost();
        int rent = property.getRent();
        if(this.getBalance() >= rent){
            this.decreaseBalance(rent);
            payee.increaseBalance(rent);
        }else{
            payee.increaseBalance(this.getBalance());
            this.decreaseBalance(this.getBalance());
            System.out.println(String.format("%s cannot afford rent, %s has gone bankrupt!",this.getPlayerPiece(),this.getPlayerPiece()));
            this.setBankrupt(true);
        }
    }

    /**
     * Pays rent
     * @param payee The player to be paid
     * @param property The property whose rent will be paid
     */
    public void payRent(Player payee,Property property, int diceVal){
        int rent = payee.hasPropertySet(property)? diceVal * 10 : diceVal * 4;

        if(this.getBalance() >= rent){
            this.decreaseBalance(rent);
            payee.increaseBalance(rent);
        }else{
            payee.increaseBalance(this.getBalance());
            this.decreaseBalance(this.getBalance());
            System.out.println(String.format("%s cannot afford rent, %s has gone bankrupt!",this.getPlayerPiece(),this.getPlayerPiece()));
            this.setBankrupt(true);
        }
    }
}
