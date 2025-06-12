package ludomania.model.game.impl;

import ludomania.model.Pair;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.game.api.CounterResult;

public final class TrenteEtQuaranteResult extends CounterResult<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> {

    public TrenteEtQuaranteResult(final Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType> result) {
        super(result);
    }

    public TrenteEtQuaranteBetType getColor() {
        return getResult().getKey();
    }

    public TrenteEtQuaranteBetType getKind() {
        return getResult().getValue();
    }
}
