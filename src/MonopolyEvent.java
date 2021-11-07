import java.util.EventObject;

public class MonopolyEvent extends EventObject {

    private Player player;
    private EventType type;

    public enum EventType {
        INIT, UPDATE, BUY, ROLL
    };

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MonopolyEvent(GameModel source, Player player, EventType type) {
        super(source);
        this.player = player;
        this.type = type;
    }
    //event when no specific player is required
    public MonopolyEvent(GameModel source, EventType type) {
        super(source);
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public EventType getEvent(){
        return this.type;
    }
}
