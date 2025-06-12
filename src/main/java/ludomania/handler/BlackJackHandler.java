package ludomania.handler;

import io.lyuda.jcards.Hand;

/**
 * Interface defining the core actions and data access methods for managing
 * a Blackjack game session. Implementing classes handle game logic, user actions,
 * and game state retrieval.
 */
public interface BlackJackHandler {
    
    /**
     * Handles the "Hit" action where the player requests an additional card.
     */
    void handleHit();

    /**
     * Handles the "Stand" action where the player ends their turn without drawing more cards.
     */
    void handleStand();

    /**
     * Handles placing a bet at the start of a round.
     *
     * @param amount the amount of money the player wants to bet
     */
    void handlePlaceBet(double amount);

    /**
     * Starts a new game or round of Blackjack.
     */
    void handleStartGame();

    /**
     * Cancels the current bet before the game begins.
     */
    void handleCancelBet();

    /**
     * Handles exiting the game and returning to the main menu.
     */
    void handleExitToMenu();

    /**
     * Returns the name of the player.
     *
     * @return the player's name
     */
    String getPlayerName();

    /**
     * Returns the current balance of the player.
     *
     * @return the player's balance
     */
    double getPlayerBalance();

    /**
     * Returns a message indicating the outcome of the game.
     * This may be "You win!", "Dealer wins", "Push", etc.
     *
     * @return a string representing the game outcome
     */
    String getGameOutcomeMessage();

    /**
     * Returns the current hand of the player.
     *
     * @return the player's hand
     */
    Hand getPlayerHand();

    /**
     * Returns the current hand of the dealer.
     *
     * @return the dealer's hand
     */
    Hand getDealerHand();

    /**
     * Returns the total value of the player's hand.
     *
     * @return the player's total hand value
     */
    int getPlayerTotal();

    /**
     * Returns the total value of the dealer's hand.
     *
     * @return the dealer's total hand value
     */
    int getDealerTotal();

    /**
     * Indicates whether the current game round is over.
     *
     * @return true if the game is over, false otherwise
     */
    Boolean isGameOver();
    
}
