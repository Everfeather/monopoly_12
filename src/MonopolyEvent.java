import java.util.EventObject;

/**
 * @author Team 12
 * @author Giancarlo Salvador, Spencer Antliff, Robbie Kuhn, Daniel Wang
 */
public class MonopolyEvent extends EventObject {

    /** The type of event that is occurring */
    private EventType type;

    private Property property;

    /**
     * enum for the different types of monopoly events
     */
    public enum EventType {
        INIT, NEXT, BUY, ROLL, BOT, JAIL
    };

    /**
     * Constructs a monopoly event
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MonopolyEvent(GameModel source, EventType type) {
        super(source);
        this.type = type;
    }

    /**
     * Constructs a monopoly event
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MonopolyEvent(GameModel source, EventType type, Property property) {
        super(source);
        this.type = type;
        this.property = property;
    }

    public Property getProperty(){
        return property;
    }

    /**
     * getter for the event type field
     * @return The type of event that is called
     */
    public EventType getEvent(){
        return this.type;
    }
}
