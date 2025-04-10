package ludomania.model.bet.impl;

import ludomania.model.bet.api.Bet;

public class RougeBet extends Bet {
    private final TrenteEtQuaranteBetColor color;

    public RougeBet(int value, TrenteEtQuaranteBetColor color) {
        super(value);
        this.color = color;
    }

    public TrenteEtQuaranteBetColor getColor() {
        return color;
    }

    @Override
    public Double evaluate() {
        return value * color.getPayout();
    }

}
