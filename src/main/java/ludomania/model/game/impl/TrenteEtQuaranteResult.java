package ludomania.model.game.impl;

import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.game.api.CounterResult;
import javafx.util.Pair;

public class TrenteEtQuaranteResult extends CounterResult<Pair<TrenteEtQuaranteBetType,TrenteEtQuaranteBetType>>{

    public TrenteEtQuaranteResult(Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType> result) {
        super(result);
    }

    public TrenteEtQuaranteBetType getColor(){
        return result.getKey();
    }

    public TrenteEtQuaranteBetType getKind(){
        return result.getValue();
    }
    
}
