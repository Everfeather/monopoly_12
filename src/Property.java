/** Represents a property square in monopoly
 * @author Spencer Antliff
 */
public class Property extends GameBoardSquare {

    /** The size of the property set */
    private int colourSetSize;
    /** The cost of the property */
    private int cost;
    /** The cost of buying a building */
    private int buildingCost;
    /** The rent that is owed if someone lands on the property */
    private int rent;
    /** The owner of the property */
    private Player owner;
    /** The type of property that it is */
    private PropertyType propertyType;
    /** The number of buildings on the property */
    private int numBuildings;

    /**
     * constructor
     * @param name The name of the property
     * @param cost The initial cost of the property
     * @param colourSetSize The size of the property set
     * @param propertyType The property type
     * rent is 10% of cost, building cost is 55% of cost.
     *owner starts as null and numBuildings is 0
     */
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

    /**
     * Getter for the colourSetSize field
     * @return Number of total matching properties
     */
    public int getColourSetSize() {
        return colourSetSize;
    }

    /**
     * Getter for the cost field
     * @return The cost of the property
     */
    public int getCost() {
        return cost;
    }

    /**
     * Getter for the buildingCost field
     * @return The cost to buy a building
     */
    public int getBuildingCost() {
        return buildingCost;
    }

    /**
     * Getter for the rent field
     * @return The rent that another player will owe when landing on this property
     */
    public int getRent() {
        return rent;
    }

    /**
     * Getter for the owner field
     * @return The Player that owns this property
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Getter method for the propertyType field
     * @return The property set that this property belongs to
     */
    public PropertyType getPropertyType() {
        return propertyType;
    }

    /**
     * Getter method for the numBuildings field
     * @return The number of buildings currently on the property
     */
    public int getNumBuildings() {
        return numBuildings;
    }

    /**
     * Removes the owner of the property
     */
    public void removeOwner() {
        this.owner = null;
    }

    /**
     * Setter of the owner field
     * @param owner The Player who now owns the property
     */
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
