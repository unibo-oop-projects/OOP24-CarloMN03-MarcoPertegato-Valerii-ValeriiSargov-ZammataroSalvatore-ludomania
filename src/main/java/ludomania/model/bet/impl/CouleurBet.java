package ludomania.model.bet.impl;
import ludomania.model.bet.api.Bet;

public class CouleurBet extends Bet{
private final TrenteEtQuaranteBetType type;

    public CouleurBet(int value, TrenteEtQuaranteBetType type) {
        super(value);
        this.type = type;
    }

    public TrenteEtQuaranteBetType getType(){
        return type;
    }

    @Override
    public Double evaluate(Double amount) {
        return amount * type.getPayout();
    }
}
