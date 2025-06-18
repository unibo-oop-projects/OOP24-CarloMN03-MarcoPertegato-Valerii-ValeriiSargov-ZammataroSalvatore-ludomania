package ludomania.model.game.impl;

import ludomania.model.Pair;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.game.api.CounterResult;

/**
 * Represents the result of a Trente et Quarante game.
 * <p>
 * The result includes both the winning color (Rouge/Noir/Draw)
 * and the kind (Couleur/Enverse/Draw).
 */
public final class TrenteEtQuaranteResult extends CounterResult<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> {

    /**
     * Creates a new TrenteEtQuaranteResult with the given pair of results.
     *
     * @param result a pair containing color and kind of the result
     */
    public TrenteEtQuaranteResult(final Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType> result) {
        super(result);
    }

    /**
     * Gets the winning color (ROUGE, NOIR, or DRAW).
     *
     * @return the color result
     */
    public TrenteEtQuaranteBetType getColor() {
        return getResult().getKey();
    }

    /**
     * Gets the winning kind (COULEUR, ENVERSE, or DRAW).
     *
     * @return the kind result
     */
    public TrenteEtQuaranteBetType getKind() {
        return getResult().getValue();
    }
}
