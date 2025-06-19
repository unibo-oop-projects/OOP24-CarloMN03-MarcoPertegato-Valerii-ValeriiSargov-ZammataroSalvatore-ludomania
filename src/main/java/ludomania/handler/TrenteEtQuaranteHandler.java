package ludomania.handler;

import ludomania.model.bet.impl.TrenteEtQuaranteBetType;

/**
 * Interface for handling user actions in the Trente et Quarante game.
 * <p>
 * Defines methods to manage game flow and interactions.
 */
public interface TrenteEtQuaranteHandler {

    /**
     * Handles the event when a player places a bet of the specified type.
     *
     * @param type the type of bet placed
     */
    void handleBetPlacement(TrenteEtQuaranteBetType type);

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
