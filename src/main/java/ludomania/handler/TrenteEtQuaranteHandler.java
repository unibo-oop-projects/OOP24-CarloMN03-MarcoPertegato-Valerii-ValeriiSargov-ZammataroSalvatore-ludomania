package ludomania.handler;

/**
 * Interface for handling user actions in the Trente et Quarante game.
 * <p>
 * Defines methods to manage game flow and interactions.
 */
public interface TrenteEtQuaranteHandler {

    /**
     * Handles the action to start the game.
     * It initializes game resources, prepares the interface, and transitions to the main game screen.
     */
    void handleStartGame();

    /**
     * Handles the event when a player places a bet of the specified type.
     *
     * @param type the type of bet placed
     */
    void handleBetPlacement(String type);

    /**
     * Handles the transition to the next player's turn.
     */
    void handleNextPlayer();

    /**
     * Handles starting a new round of the game.
     */
    void handleNewRound();

    /**
     * Handles the action to return to the home screen.
     */
    void handleReturnHome();

}
