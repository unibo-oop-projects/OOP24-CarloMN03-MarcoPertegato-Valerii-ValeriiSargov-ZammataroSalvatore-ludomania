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
    public BlackJackOutcomeResult(BlackJackOutcome outcome, BlackJackBetType betType) {
        this.outcome = outcome;
        this.betType = betType;
    }

    // Returns the outcome of the blackjack game
    public BlackJackOutcome getOutcome() {
        return outcome;
    }

    // Returns the type of bet placed in the blackjack game
    public BlackJackBetType getBetType() {
        return betType;
    }
}

