package ludomania.controller.impl;

import io.lyuda.jcards.Hand;
import javafx.scene.Parent;
import javafx.util.Builder;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.handler.BlackJackHandler;
import ludomania.model.game.impl.BlackJackGame;
import ludomania.model.player.impl.BlackJackPlayer;
import ludomania.model.wallet.api.Wallet;
import ludomania.model.wallet.impl.WalletImpl;
import ludomania.view.blackjack.BlackJackMenuViewBuilder;

/**
 * Controller for the Blackjack game. It manages user interactions 
 * and game flow between the model and the view.
 */
public final class BlackJackController implements Controller, BlackJackHandler {

    private final Builder<Parent> viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;
    private final BlackJackPlayer player;
    private final BlackJackGame game;

    /**
     * Create a new controller for Blackjack.
     *
     * @param sceneManager manages the scenes
     * @param audioManager manages sounds
     */
    public BlackJackController(final SceneManager sceneManager, final AudioManager audioManager) {
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        Wallet wallet = new WalletImpl(1000.0); //Saldo 
        this.player = new BlackJackPlayer(wallet, "Pippo");
        this.game = new BlackJackGame(player);
        viewBuilder = new BlackJackMenuViewBuilder(this,
                sceneManager.getLanguageManager(), sceneManager.getImageProvider());
    }

    @Override
    public Parent getView() {
        return viewBuilder.build();
    }

    @Override
    public void handleStartGame() {
        audioManager.playSound("start");
        game.startNewRound();  
        game.dealInitialCards();
    }

    @Override
    public void handleCancelBet() {
        audioManager.playSound("cancel");
        game.playAgain(); 
    }

    @Override
    public void handleExitToMenu() {
        audioManager.playSound("click");
        sceneManager.switchToMainMenu();
    }

    @Override
    public String getPlayerName() {
        return player.getUsername();
    }

    @Override
    public double getPlayerBalance() {
        return game.getPlayerFinance();
    }

    @Override
    public void handleHit() {
        audioManager.playSound("card");
        game.hit();
        if (game.isOver()) {
            game.runGame(); // Evaluate outcome and update balance
        }
    }

    @Override
    public void handleStand() {
        audioManager.playSound("stand");
        game.stand();
        game.runGame(); // Evaluate outcome and update balance
    }

    @Override
    public void handlePlaceBet(double amount) {
        audioManager.playSound("bet");
        try {
            game.placeBet(amount);
        } catch (IllegalArgumentException e) {
            sceneManager.switchToMainMenu();
        }
    }

    @Override
    public String getGameOutcomeMessage() {
        if (!game.isOver()) {
            return "";
        }

        int playerTotal = game.getPlayerTotal();
        int dealerTotal = game.getDealerTotal();

        if (playerTotal > 21) {
            return "Hai sballato! 😢";
        } else if (dealerTotal > 21) {
            return "Il banco ha sballato! Hai vinto! 🎉";
        } else if (playerTotal == dealerTotal) {
            return "Pareggio! 😐";
        } else if (playerTotal == 21 && game.getPlayerTotalCards() == 2) {
            return "Blackjack! 💥";
        } else if (playerTotal > dealerTotal) {
            return "Hai vinto! 🎉";
        } else {
            return "Hai perso! 😢";
        }
    }

    @Override
    public Hand getPlayerHand() {
        return game.getPlayerHand();
    }

    @Override
    public Hand getDealerHand() {
         return game.getDealerHand();
    }   

    @Override
    public int getPlayerTotal() {
        return game.getPlayerTotal();
    }

    @Override
    public int getDealerTotal() {
        return game.getDealerTotal();
    }

    @Override
    public Boolean isGameOver() {
        return game.isOver();
    }

}

