package ludomania.handler;

import ludomania.model.bet.impl.TrenteEtQuaranteBetType;

public interface TrenteEtQuaranteHandler {

    void handleBetValueSelection(int value);

    void handleBetPlacement(TrenteEtQuaranteBetType type);

    void handleNextPlayer();

    void handleNewRound();

    void handleReturnHome();

}
