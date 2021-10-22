import java.util.HashMap;

public class Player {

    private Piece playerPiece;
    private int balance;
    private HashMap<Property,PropertyType> properties;
    private int currentPos;
    private boolean inJail;
    private int turnsInJail;
    private boolean bankrupt;

    public Player(Piece p, int balance){
        this.playerPiece = p;
        this.bankrupt = false;
        this.balance = balance;
        this.properties = new HashMap<Property, PropertyType>();
        this.currentPos = 0;
        this.inJail = false;
        this.turnsInJail = 0;
    }


    public Piece getPlayerPiece() {
        return playerPiece;
    }

    public void setPlayerPiece(Piece playerPeice) {
        this.playerPiece = playerPeice;
    }

    public int getBalance() {
        return balance;
    }

    public void increaseBalance(int amount){
        this.balance += amount;
    }
    public void decreaseBalance(int amount){
        this.balance -= amount;
    }

    public HashMap<Property, PropertyType> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<Property, PropertyType> properties) {
        this.properties = properties;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public Boolean getInJail() {
        return inJail;
    }

    public void setInJail(Boolean inJail) {
        this.inJail = inJail;
    }

    public int getTurnsInJail() {
        return turnsInJail;
    }

    public void setTurnsInJail(int turnsInJail) {
        this.turnsInJail = turnsInJail;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void setBankrupt(boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    public boolean hasPropertySet(Property property){
       int num = 0;
       int setSize = property.getColourSetSize();
        for (PropertyType i:
             this.properties.values()) {
            if (i == property.getPropertyType()){
                num++;
            }
        }
        return num == setSize;
    }

    public void addProperty(Property prop){
        this.properties.put(prop, prop.getPropertyType());
    }
}
