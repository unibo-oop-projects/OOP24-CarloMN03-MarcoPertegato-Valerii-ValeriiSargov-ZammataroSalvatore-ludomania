package ludomania.model.game.roulette;

import javafx.beans.property.IntegerProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.model.Pair;
import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.croupier.roulette.RouletteCroupier;
import ludomania.model.game.api.Game;
import ludomania.model.game.impl.CounterResult;

/**
 * Implementation of the {@link Game} interface for Roulette.
 * <p>
 * Handles the main game flow, including managing players, handling bets,
 * calling wheel results, and determining winners.
 */
public class RouletteGame implements Game<Pair<Integer, RouletteColor>> {

    private final RouletteSceneManager sceneManager;
    private final RouletteGameManager gameManager;

    public RouletteGame(final SceneManager sceneManager, final AudioManager audioManager) {
        this.sceneManager = new RouletteSceneManager(sceneManager, audioManager);
        this.gameManager = new RouletteGameManager(new RouletteCroupier());
    }

    @Override
    public CounterResult<Pair<Integer, RouletteColor>> runGame() {
        return this.gameManager.runGame();
    }

    public void pleinBet(final MouseEvent event) {
        this.gameManager.pleinBet(event);
    }

    public void chevalBet(final MouseEvent event) {
        this.gameManager.chevalBet(event);
    }

    public void carreBet(final MouseEvent event) {
        this.gameManager.carreBet(event);
    }

    public void colonneBet(final MouseEvent event) {
        this.gameManager.colonneBet(event);
    }

    public void noirBet() {
        this.gameManager.noirBet();
    }

    public void rougeBet() {
        this.gameManager.rougeBet();
    }

    public void pairBet() {
        this.gameManager.pairBet();
    }

    public void impairBet() {
        this.gameManager.impairBet();
    }

    public void passeBet() {
        this.gameManager.passeBet();
    }

    public void manqueBet() {
        this.gameManager.manqueBet();
    }

    public void douzineBet(final MouseEvent event) {
        this.gameManager.douzaineBet(event);
    }

    public void highlightCarre(final MouseEvent event) {
        this.sceneManager.highlightCarre(event);
    }

    public void unhighlightCarre(final MouseEvent event) {
        this.sceneManager.unhighlightCarre(event);
    }

    public void glowWheel(final MouseEvent event) {
        this.sceneManager.glowWheel(event);
    }

    public void unglowWheel(final MouseEvent event) {
        this.sceneManager.unglowWheel(event);
    }

    public void quitGame() {
        this.sceneManager.quitGame();
    }

    public Double addBetAmount(final Integer amount) {
        return this.gameManager.addBetAmount(amount);
    }

    public Double getBalance() {
        return this.gameManager.getPlayerBalance();
    }

    public void evaluateRound() {
        this.gameManager.evaluateGame();
        if (this.gameManager.checkGameOver()) {
            this.sceneManager.alertAndQuit();
        }
    }

    public void attachFiches(final Pane pane, final IntegerProperty controlProperty) {
        this.sceneManager.attachFiches(pane, controlProperty);
    }

    public void showRules() {
        this.sceneManager.showRules();
    }

    public void showBets() {
        this.sceneManager.showBets(this.gameManager.getBets());
    }
}
