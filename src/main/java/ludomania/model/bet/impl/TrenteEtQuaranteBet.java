package ludomania.model.bet.impl;

import ludomania.model.bet.api.Bet;

public class TrenteEtQuaranteBet extends Bet{    
    
    public TrenteEtQuaranteBet(double value, TrenteEtQuaranteBetType type) {
        super(value,type);
    }

    @Override
    public Double evaluate() {
        return value + (value * type.getPayout());
    }
}
