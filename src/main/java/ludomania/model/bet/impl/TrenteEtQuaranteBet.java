package ludomania.model.bet.impl;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.TrenteEtQuaranteBetType;

public class TrenteEtQuaranteBet extends Bet{    

    private final TrenteEtQuaranteBetType type;
    
    public TrenteEtQuaranteBet(double value, TrenteEtQuaranteBetType type) {
        super(value);
        this.type = type;
    }

    public TrenteEtQuaranteBetType getType() {
        return type;
    }

    @Override
    public Double evaluate() {
        return value * type.getPayout();
    }
}
