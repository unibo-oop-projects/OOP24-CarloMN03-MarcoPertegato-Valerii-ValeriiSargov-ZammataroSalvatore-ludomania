package ludomania.handler;

import io.lyuda.jcards.Hand;

public interface BlackJackHandler {
    
    void handleHit();

    void handleStand();

    void handlePlaceBet(double amount);

    void handleStartGame();

    void handleCancelBet();

    void handleExitToMenu();

    String getPlayerName();

    double getPlayerBalance();

    String getGameOutcomeMessage();

    Hand getPlayerHand();

    Hand getDealerHand();

    int getPlayerTotal();

    int getDealerTotal();

    Boolean isGameOver();

}
