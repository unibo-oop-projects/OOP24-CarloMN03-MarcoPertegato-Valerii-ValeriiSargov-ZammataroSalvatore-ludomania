package ludomania.model.bet.impl;

import ludomania.model.bet.api.Bet;
import static ludomania.model.bet.impl.BlackJackBetType.BASE;
import static ludomania.model.bet.impl.BlackJackBetType.BLACKJACK;
import static ludomania.model.bet.impl.BlackJackBetType.LOSE;
import static ludomania.model.bet.impl.BlackJackBetType.PUSH;

/**
 * Represents a blackjack-specific bet.
 * Extends the generic {@link Bet} class and evaluates payout based on the bet type.
 */
public class BlackJackBet extends Bet {
     /**
     * Constructs a new BlackJackBet with a specified value and type.
     *
     * @param value the amount of the bet
     * @param type the type of blackjack bet (e.g., BASE, BLACKJACK, PUSH, LOSE)
     */
    public BlackJackBet(final double value, final BlackJackBetType type) {
        super(value, type);
    }

    /**
     * Evaluates the payout of the bet based on the bet type.
     *
     * @return the payout amount, calculated as bet value * payout multiplier
     */
    @Override
    public Double evaluate() {
        return switch (getType()) {
            case BASE -> getValue() * BASE.getPayout();
            case BLACKJACK -> getValue() * BLACKJACK.getPayout(); 
            case PUSH -> getValue() * PUSH.getPayout();
            case LOSE -> getValue() * LOSE.getPayout();
            default -> 0.0;
        };
    }
}
