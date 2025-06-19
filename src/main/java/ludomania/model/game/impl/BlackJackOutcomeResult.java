package ludomania.model.game.impl;

import ludomania.model.bet.impl.BlackJackBetType;

/**
 * Represents the result of a blackjack bet, including the outcome and the type of bet.
 */
public class BlackJackOutcomeResult {

    private final BlackJackOutcome outcome;
    private final BlackJackBetType betType;

    /**
     * Constructs a BlackJackOutcomeResult with the specified outcome and bet type.
     *
     * @param outcome the result of the blackjack game
     * @param betType the type of bet that was placed
     */
    public BlackJackOutcomeResult(final BlackJackOutcome outcome, final BlackJackBetType betType) {
        this.outcome = outcome;
        this.betType = betType;
    }

    /**
     * Returns the outcome of the Blackjack game.
     *
     * @return the {@link BlackJackOutcome} representing the result of the game
     */
    public BlackJackOutcome getOutcome() {
        return outcome;
    }

    /**
     * Returns the type of bet placed in the Blackjack game.
     *
     * @return the {@link BlackJackBetType} used for this round
     */
    public BlackJackBetType getBetType() {
        return betType;
    }
}

