package ludomania.model.bet.impl;

import ludomania.model.bet.api.TrenteEtQuaranteBetColor;
import ludomania.model.bet.api.TrenteEtQuaranteBet;

public class RougeBet extends TrenteEtQuaranteBet{
    private final TrenteEtQuaranteBetColor color;

    public RougeBet(int value, TrenteEtQuaranteBetColor color) {
        super(value);
        this.color = color;
    }

    public TrenteEtQuaranteBetColor getColor(){
        return color;
    }

    @Override
    public Double evaluate(Double amount) {
        return amount * color.getPayout();
    }

} 
