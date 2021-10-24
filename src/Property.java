/**
 * @author Spencer Antliff
 */
public class Property extends GameBoardSquare {


    private int colourSetSize;
    private int cost;
    private int buildingCost;
    private int rent;
    private Player owner;
    private PropertyType propertyType;
    private int numBuildings;

    public Property(String name, int cost, int colourSetSize, PropertyType propertyType) {
        super(SquareType.PROPERTY,name);
        this.colourSetSize = colourSetSize;
        this.cost = cost;
        this.propertyType = propertyType;
        this.rent = (int) (cost * 0.1);
        this.buildingCost = (int) (cost * 0.55);
        this.owner = null;
        this.numBuildings = 0;
    }

    /**
     * This methode changes the relevant attributes of the property accordingly when a building is purchased.
     * increases the number of buildings by 1.
     * increases the rent.
     */
    public void buildingPurchased(){
        this.numBuildings ++;
        this.rent += (int) (this.numBuildings * this.cost * 0.4);
    }
    public int getColourSetSize() {
        return colourSetSize;
    }

    public int getCost() {
        return cost;
    }

    public int getBuildingCost() {
        return buildingCost;
    }

    public int getRent() {
        return rent;
    }

    public Player getOwner() {
        return owner;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public int getNumBuildings() {
        return numBuildings;
    }

    public void removeOwner() {
        this.owner = null;
    }
    public void setOwner(Player owner){
        this.owner = owner;
    }

    @Override
    public String toString() {
        if (this.owner == null) {
            return String.format("%s \nCost: %d \nNumber of Buildings: %d \nRent: %d \nOwner: Unowned",
                    this.getName(), this.getCost(), this.getNumBuildings(), this.getRent() );
        } else {
            return String.format("%s \nCost: %d \nNumber of Buildings: %d \nRent: %d \nOwner: %s",
                    this.getName(), this.getCost(), this.getNumBuildings(), this.getRent(), this.getOwner().getPlayerPiece().toString());
        }
    }
}
