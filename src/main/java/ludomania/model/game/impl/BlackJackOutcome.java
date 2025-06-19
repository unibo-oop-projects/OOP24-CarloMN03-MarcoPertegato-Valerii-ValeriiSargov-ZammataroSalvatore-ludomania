package ludomania.model.game.impl;

/**
 * Enum representing the possible outcomes of a blackjack game for a player.
 */
public enum BlackJackOutcome {

    /**
     * The player wins the hand with a regular victory (not a Blackjack).
     */
    WIN,

    /**
     * The player loses the hand.
     */
    LOSE,

    /**
     * The game ends in a draw or tie between the player and the dealer.
     */
    PUSH,

    /**
     * The player wins with a natural Blackjack (Ace + 10-value card).
     */
    BLACKJACK;
}
