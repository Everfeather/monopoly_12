/**
 * Interface meant to handle the game views for the model
 * @author Team 12
 * @author Giancarlo Salvador, Spencer Antliff, Robbie Kuhn, Daniel Wang
 */
public interface GameView {
    /**
     * Updates the view with new information from the model
     * @param event Contains information about the new game state
     */
    void update(MonopolyEvent event);
}
