package ludomania.handler;

/**
 * Interface for handling user interactions in the Blackjack game screen.
 * <p>
 * This interface defines the actions available to the user during a Blackjack game,
 * including placing bets, drawing cards, cancelling actions, and exiting the game.
 * </p>
 */
public interface BlackJackHandler {

    /**
     * Handles the action to place a bet.
     * <p>
     * This method should validate and register the player's bet before cards are dealt.
     * </p>
     *
     * @param amount the amount of money the player wants to bet
     * @param playerId the identifier of the player placing the bet
     */
    void handleBet(int amount, String playerId);

    /**
     * Handles the action to draw a card.
     * <p>
     * This method should deal one card to the requesting player, updating the UI and game state.
     * </p>
     *
     * @param playerId the identifier of the player requesting a card
     */
    void handleDrawCard(String playerId);

    /**
     * Handles the action to cancel the current bet or hand.
     * <p>
     * This method should clear the current bet or reset the player's hand before cards are dealt.
     * </p>
     *
     * @param playerId the identifier of the player cancelling the action
     */
    void handleCancel(String playerId);

    /**
     * Handles the action to exit the Blackjack game and return to the main menu.
     * <p>
     * This method should transition from the game screen back to the main menu or previous screen.
     * </p>
     */
    void handleExit();

    /**
     * Handles the initialization of the game round.
     * <p>
     * This method should start a new round of Blackjack, dealing cards to players and the dealer.
     * </p>
     */
    void handleStartRound();
}

