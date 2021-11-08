import java.util.EventObject;

public class MonopolyEvent extends EventObject {

    private Player player;
    private EventType type;

    public enum EventType {
        INIT, NEXT, BUY, ROLL
    };

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    //event when no specific player is required
    public MonopolyEvent(GameModel source, EventType type) {
        super(source);
        this.type = type;
    }

    public EventType getEvent(){
        return this.type;
    }
}
