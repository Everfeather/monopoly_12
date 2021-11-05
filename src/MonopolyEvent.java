import java.util.EventObject;

public class MonopolyEvent extends EventObject {

    private Player player;

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
    }

    public Player getPlayer() {
        return player;
    }

}
