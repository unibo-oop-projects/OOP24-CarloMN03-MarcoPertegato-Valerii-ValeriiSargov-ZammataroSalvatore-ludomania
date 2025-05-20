package ludomania.controller.impl;

import javafx.scene.Parent;
import ludomania.controller.api.Controller;
import ludomania.handler.BlackJackHandlerImpl;
import ludomania.model.game.impl.BlackJackGame;
import ludomania.model.player.impl.BlackJackPlayer;
import ludomania.model.wallet.api.Wallet;
import ludomania.model.wallet.impl.WalletImpl;

public class BlackJackController implements Controller {

    private final BlackJackGame game;
    private final BlackJackPlayer player;

    public BlackJackController(BlackJackHandlerImpl handler) {
        Wallet startWallet = new WalletImpl(1000.0);
        this.player = new BlackJackPlayer(startWallet);  //SOLUZIONE TEMPORANEA, ANDRA PASSATO DOPO
        this.game = new BlackJackGame(this.player);

        handler.setOnPlaceBet(game::placeBet);
        handler.setOnHit(() -> {
            game.hit();
            checkEnd();
        });
        handler.setOnStand(() -> {
            game.stand();
            checkEnd();
        });
        handler.setOnClearBet(() -> game.placeBet(0));
        
        handler.setOnNewRound(game::startNewRound);
        handler.setOnBackToMenu(() -> System.out.println("Returning to menu"));
    }

    private void checkEnd() {
        if (game.isOver()) {
            // puoi notificare la View (es: popup, aggiorna mani, ecc.)
            
        }
    }

    @Override
    public Parent getView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getView'");
    }
    
}
