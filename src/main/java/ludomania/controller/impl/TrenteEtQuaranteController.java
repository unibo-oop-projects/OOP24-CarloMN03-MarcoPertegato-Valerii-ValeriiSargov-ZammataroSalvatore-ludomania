package ludomania.controller.impl;

import java.util.LinkedList;
import java.util.List;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.DeckFactory;
import javafx.scene.Parent;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.handler.TrenteEtQuaranteHandler;
import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.TrenteEtQuaranteBet;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.croupier.impl.TrenteEtQuaranteDealer;
import ludomania.model.game.impl.CounterResult;
import ludomania.model.game.impl.TrenteEtQuaranteGame;
import ludomania.model.player.api.Player;
import ludomania.model.player.impl.TrenteEtQuarantePlayer;
import ludomania.model.wallet.impl.WalletImpl;
import ludomania.view.TrenteEtQuaranteViewBuilder;

/**
 * Controller implementation for the Trente et Quarante game.
 * <p>
 * Manages interactions between the view (UI) and the model logic.
 * Handles user actions such as placing bets, starting rounds, and returning to the main menu.
 */
public final class TrenteEtQuaranteController implements Controller, TrenteEtQuaranteHandler {
    private static final double INITIAL_MONEY = 1000.0;
    private static final int DECK_NUM = 6;
    private static final String TURN_BET_SEPARETOR = "------";
    private final TrenteEtQuaranteViewBuilder viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;
    private final TrenteEtQuaranteGame game;
    private int turn;

    /**
     * Constructs a new TrenteEtQuaranteController.
     *
     * @param sceneManager the manager for switching scenes
     * @param audioManager the manager for playing sounds
     */
    public TrenteEtQuaranteController(final SceneManager sceneManager,
            final AudioManager audioManager) {
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        viewBuilder = new TrenteEtQuaranteViewBuilder(this, sceneManager.getLanguageManager(),
        sceneManager.getImageProvider());
        final List<Pair<Player, Bet>> roundBet = new LinkedList<>();
        final TrenteEtQuaranteDealer dealer = new TrenteEtQuaranteDealer(roundBet, new DeckFactory());
        final WalletImpl wallet = new WalletImpl(INITIAL_MONEY);
        final List<TrenteEtQuarantePlayer> players = new LinkedList<>(List.of(new TrenteEtQuarantePlayer(wallet, "Player1")));
        this.game = new TrenteEtQuaranteGame(dealer, players, DECK_NUM);
        turn = 1;
        viewBuilder.setTurn(turn);
    }

    @Override
    public Parent getView() {
        return viewBuilder.build();
    }

    @Override
        public void handleStartGame() {
            viewBuilder.setUsername(game.getCurrentUser());
            viewBuilder.setBalance(game.getCurrentPlayerBalance());
        }

    @Override
    public void handleBetPlacement(final String type) {
        final TrenteEtQuaranteBetType betType;
        switch (type) {
            case "Noir":
                betType = TrenteEtQuaranteBetType.NOIR;
                break;
            case "Rouge":
                betType = TrenteEtQuaranteBetType.ROUGE;
                break;
            case "Couleur":
                betType = TrenteEtQuaranteBetType.COULEUR;
                break;
            case "Enverse":
                betType = TrenteEtQuaranteBetType.ENVERSE;
                break;
            default:
                betType = TrenteEtQuaranteBetType.DRAW;
        }
        final int amount = viewBuilder.getSelectedFiche().getValue();
        game.playerMakesBet(new TrenteEtQuaranteBet(amount, betType));
        viewBuilder.setBalance(game.getCurrentPlayerBalance());
        viewBuilder.addBet(type + " " + amount + "$");
    }

    @Override
    public void handleNextPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleNextPlayer'");
    }

    @Override
    public void handleNewRound() {
        viewBuilder.addBet(TURN_BET_SEPARETOR);
        viewBuilder.clearNoirCards();
        viewBuilder.clearRougeCards();

        final CounterResult<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> result = game.runGame();
        game.payUp(game.evaluateBets(result));

        for (final Card card : game.getNoir().getCards()) {
            viewBuilder.addCardToNoir(card.getRank(), card.getSuit());
        }
        viewBuilder.setNoirTotal(game.getNoirTotalValue());

        for (final Card card : game.getRouge().getCards()) {
            viewBuilder.addCardToRouge(card.getRank(), card.getSuit());
        }
        viewBuilder.setRougeTotal(game.getRougeTotalValue());

        viewBuilder.setBalance(game.getCurrentPlayerBalance());

        final TrenteEtQuaranteBetType color = result.getResult().getKey();
        final TrenteEtQuaranteBetType kind = result.getResult().getValue();
        viewBuilder.addBet(color + " " + kind);
        viewBuilder.addBet(TURN_BET_SEPARETOR);
        turn++;
        viewBuilder.setTurn(turn);

        game.resetRound();
    }

    @Override
    public void handleReturnHome() {
        audioManager.playSound("click");
        sceneManager.switchToMainMenu();
    }
}
