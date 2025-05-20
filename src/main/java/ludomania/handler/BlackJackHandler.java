package ludomania.handler;

public interface BlackJackHandler {
    
    void handleHit();

    void handleStand();

    void handleNewRound();

    void handleBackToMenu();

    void handlePlaceBet(int amount);

    void handleClearBets();
    
}
