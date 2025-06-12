package ludomania.model.bet.impl;

import ludomania.model.bet.api.Bet;

public final class TrenteEtQuaranteBet extends Bet {

    public TrenteEtQuaranteBet(final double value, final TrenteEtQuaranteBetType type) {
        super(value, type);
    }

    @Override
    public Double evaluate() {
        return value + (value * type.getPayout());
    }
}
