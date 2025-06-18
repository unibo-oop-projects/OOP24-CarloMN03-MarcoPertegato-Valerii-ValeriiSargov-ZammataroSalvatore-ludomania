package ludomania.model.bet.impl;

import ludomania.model.bet.api.Bet;

/**
 * A concrete implementation of {@link Bet} for the Trente et Quarante game.
 * <p>
 * This class represents a single bet placed by a player on a specific {@link TrenteEtQuaranteBetType}
 * The payout is calculated based on the type's defined payout multiplier.
 */
public final class TrenteEtQuaranteBet extends Bet {

    /**
     * Constructs a new Trente et Quarante bet with the specified amount and type.
     *
     * @param value the amount of money wagered
     * @param type  the type of bet placed in the Trente et Quarante game
     */
    public TrenteEtQuaranteBet(final double value, final TrenteEtQuaranteBetType type) {
        super(value, type);
    }

    @Override
    public Double evaluate() {
        return getValue() + (getValue() * getType().getPayout());
    }
}
