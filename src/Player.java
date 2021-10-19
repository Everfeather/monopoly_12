import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    private Piece playerPiece;
    private int balance;
    private HashMap<Property,PropertyType> properties;
    private int currentPos;
    private Boolean inJail;
    private int turnsInJail;

    public Player(Piece p){
        this.playerPiece = p;
        this.balance = 1500;
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

    public void setBalance(int balance) {
        this.balance = balance;
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

    public boolean hasPropertySet(Property property){
       int num = 0;
       int setSize = property.getColourSetSize();
        for (PropertyType i:
             this.properties.values()) {
            if (i == property.getPropertyType()){
                num++;
            }
        }
        if (num == setSize){
            return true;
        }
        return false;
    }

    public void addProperty(Property prop){
        this.properties.put(prop, prop.getPropertyType());
    }
}
