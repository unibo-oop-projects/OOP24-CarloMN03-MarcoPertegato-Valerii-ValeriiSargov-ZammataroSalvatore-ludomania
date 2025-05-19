package ludomania.handler;

public interface BlackJackHandler {
    
    void handleHit();

    void handStand();

    void handleNewRound();

    void handleBackToMenu();

    void handlePlaceBet(int amount);

    void handleClearBets();
}
