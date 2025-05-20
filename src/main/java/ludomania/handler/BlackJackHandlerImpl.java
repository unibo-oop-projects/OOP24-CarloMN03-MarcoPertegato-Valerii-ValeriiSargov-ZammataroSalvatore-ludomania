package ludomania.handler;

import java.util.function.Consumer;

public class BlackJackHandlerImpl implements BlackJackHandler {

    // Callback per ogni azione
    private Consumer<Integer> onPlaceBet;
    private Runnable onClearBet;
    private Runnable onHit;
    private Runnable onStand;
    private Runnable onNewRound;
    private Runnable onBackToMenu;

    // Costruttore vuoto o builder-style, le callback si settano dopo
    public BlackJackHandlerImpl() {
    }

    // Setters per i callback (es: dal controller)
    public void setOnPlaceBet(Consumer<Integer> onPlaceBet) {
        this.onPlaceBet = onPlaceBet;
    }

    public void setOnClearBet(Runnable onClearBet) {
        this.onClearBet = onClearBet;
    }

    public void setOnHit(Runnable onHit) {
        this.onHit = onHit;
    }

    public void setOnStand(Runnable onStand) {
        this.onStand = onStand;
    }

    public void setOnNewRound(Runnable onNewRound) {
        this.onNewRound = onNewRound;
    }

    public void setOnBackToMenu(Runnable onBackToMenu) {
        this.onBackToMenu = onBackToMenu;
    }

    @Override
    public void handlePlaceBet(int amount) {
        if (onPlaceBet != null) onPlaceBet.accept(amount);
    }

    @Override
    public void handleClearBets() {
        if (onClearBet != null) onClearBet.run();
    }

    @Override
    public void handleHit() {
        if (onHit != null) onHit.run();
    }

    @Override
    public void handleStand() {
        if (onStand != null) onStand.run();
    }

    @Override
    public void handleNewRound() {
        if (onNewRound != null) onNewRound.run();
    }

    @Override
    public void handleBackToMenu() {
        if (onBackToMenu != null) onBackToMenu.run();
    }
    
}
