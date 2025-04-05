package ludomania.model.bet.impl;

import ludomania.model.bet.api.BetColor;
import ludomania.model.bet.api.TrenteEtQuaranteBet;

public class RougeBet extends TrenteEtQuaranteBet{
    private final BetColor color;

    public RougeBet(int value, BetColor color) {
        super(value);
        this.color=color;
        //TODO Auto-generated constructor stub
    }

    public BetColor getColor(){
        return color;
    }

    @Override
    public Double evaluate(Double amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
    }

} 
