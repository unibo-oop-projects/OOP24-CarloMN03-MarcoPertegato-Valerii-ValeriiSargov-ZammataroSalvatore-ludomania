package ludomania.model.game.impl;

import ludomania.model.bet.impl.BlackJackBetType;

public class BlackJackOutcomeResult {
    private final BlackJackOutcome outcome;
    private final BlackJackBetType betType;

    public BlackJackOutcomeResult(BlackJackOutcome outcome, BlackJackBetType betType) {
        this.outcome = outcome;
        this.betType = betType;
    }

    public BlackJackOutcome getOutcome() {
        return outcome;
    }

    public BlackJackBetType getBetType() {
        return betType;
    }
}

