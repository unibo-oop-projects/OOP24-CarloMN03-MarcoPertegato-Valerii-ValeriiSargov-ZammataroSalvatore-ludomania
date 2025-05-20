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
 * Controller per il gioco del Blackjack. Gestisce le interazioni dell'utente
 * e il flusso di gioco tra il model e la view.
 */
public final class BlackJackController implements Controller, BlackJackHandler {

    private final Builder<Parent> viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;
    private final BlackJackPlayer player;
    private final BlackJackGame game;

    /**
     * Crea un nuovo controller per Blackjack.
     *
     * @param sceneManager gestore delle scene
     * @param audioManager gestore dei suoni
     */
    public BlackJackController(final SceneManager sceneManager, final AudioManager audioManager) {
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        Wallet wallet = new WalletImpl(1000.0);
        this.player = new BlackJackPlayer(wallet);
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
        game.playAgain(); // Resetta il round
    }

    @Override
    public void handleExitToMenu() {
        audioManager.playSound("click");
        sceneManager.switchToMainMenu();
    }

    @Override
    public String getPlayerName() {
        return player.toString();
    }

    @Override
    public double getPlayerBalance() {
        return player.getBalance();
    }

    @Override
    public void handleHit() {
        audioManager.playSound("card");
        game.hit();
        if (game.isOver()) {
            game.runGame(); // Valuta esito e aggiorna saldo
        }
    }

    @Override
    public void handleStand() {
        audioManager.playSound("stand");
        game.stand();
        game.runGame(); // Valuta esito e aggiorna saldo
    }

    @Override
    public void handlePlaceBet(int amount) {
        audioManager.playSound("bet");
        game.placeBet(amount);
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

    
}

