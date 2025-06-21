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
 * Implementation of the {@link Game} interface for Roulette, represents the model component for the MVC design pattern.
 * <p>
 * Operates as <i>facade</i> for the Roulette game logic.
 */
public class RouletteGame implements Game<Pair<Integer, RouletteColor>> {

    private final RouletteSceneManager sceneManager;
    private final RouletteGameManager gameManager;

    /**
     * Instantiate the {@link RouletteSceneManager} and the {@link RouletteGameManager}.
     * @param sceneManager the manager for the application scenes.
     * @param audioManager the manager for audio features.
     */
    public RouletteGame(final SceneManager sceneManager, final AudioManager audioManager) {
        this.sceneManager = new RouletteSceneManager(sceneManager, audioManager);
        this.gameManager = new RouletteGameManager(new RouletteCroupier());
    }

    /**
     * {@inheritDoc}
     * @return the result of the Roulette wheel.
     */
    @Override
    public CounterResult<Pair<Integer, RouletteColor>> runGame() {
        return this.gameManager.runGame();
    }

    /**
     * Calls the corresponding function to create a plein bet.
     * @param event the mouse click event on the plein bet button.
     */
    public void pleinBet(final MouseEvent event) {
        this.gameManager.pleinBet(event);
    }

    /**
     * Calls the corresponding function to create a cheval bet.
     * @param event the mouse click event on the cheval bet separator.
     */
    public void chevalBet(final MouseEvent event) {
        this.gameManager.chevalBet(event);
    }

    /**
     * Calls the corresponding function to create a carre bet.
     * @param event the mouse click event on the carre bet button.
     */
    public void carreBet(final MouseEvent event) {
        this.gameManager.carreBet(event);
    }

    /**
     * Calls the corresponding function to create a colonne bet.
     * @param event the mouse click event on the colonne button.
     */
    public void colonneBet(final MouseEvent event) {
        this.gameManager.colonneBet(event);
    }

    /**
     * Calls the corresponding function to create a noir bet.
     */
    public void noirBet() {
        this.gameManager.noirBet();
    }

    /**
     * Calls the corresponding function to create a rouge bet.
     */
    public void rougeBet() {
        this.gameManager.rougeBet();
    }

    /**
     * Calls the corresponding function to create a pair bet.
     */
    public void pairBet() {
        this.gameManager.pairBet();
    }

    /**
     * Calls the corresponding function to create an impair bet.
     */
    public void impairBet() {
        this.gameManager.impairBet();
    }

    /**
     * Calls the corresponding function to create a passe bet.
     */
    public void passeBet() {
        this.gameManager.passeBet();
    }

    /**
     * Calls the corresponding function to create a manque bet.
     */
    public void manqueBet() {
        this.gameManager.manqueBet();
    }

    /**
     * Calls the corresponding function to create a douzaine bet.
     * @param event the mouse click event on the douzaine button
     */
    public void douzineBet(final MouseEvent event) {
        this.gameManager.douzaineBet(event);
    }

    /**
     * Calls the corresponding function to highligth the cells forming the carre bet.
     * @param event the mouse enter event on the carre bet btn.
     */
    public void highlightCarre(final MouseEvent event) {
        this.sceneManager.highlightCarre(event);
    }

    /**
     * Calls the corresponding function to unhighligth the cells forming the carre bet.
     * @param event the mouse exit event on the carre bet btn.
     */
    public void unhighlightCarre(final MouseEvent event) {
        this.sceneManager.unhighlightCarre(event);
    }

    /**
     * Calls the corresponding function to glow the Roulette wheel.
     * @param event the mouse enter event on the Roulette wheel image.
     */
    public void glowWheel(final MouseEvent event) {
        this.sceneManager.glowWheel(event);
    }

    /**
     * Calls the corresponding function to unglow the Roulette wheel.
     * @param event the mouse exit event on the Roulette wheel image.
     */
    public void unglowWheel(final MouseEvent event) {
        this.sceneManager.unglowWheel(event);
    }

    /**
     * Calls the corresponding function to handle the quit game request.
     */
    public void quitGame() {
        this.sceneManager.quitGame();
    }

    /**
     * Calls the corresponding function to increase next bet amount.
     * @param amount the amount to add.
     * @return the resulting bet amount.
     */
    public Double addBetAmount(final Integer amount) {
        return this.gameManager.addBetAmount(amount);
    }

    /**
     * Calls the corresponding function to get the current player balance.
     * @return the current player balance.
     */
    public Double getBalance() {
        return this.gameManager.getPlayerBalance();
    }

    /**
     * Calls the corresponding function to check on all bets which are successful and pays the corresponding player,
     * checks if the current player has no more money.
     */
    public void evaluateRound() {
        this.gameManager.evaluateGame();
        if (this.gameManager.checkGameOver()) {
            this.sceneManager.alertAndQuit();
        }
    }

    /**
     * Class the corresponding function to attach fiches images and event handling on the main scene.
     * @param pane the element on which adding the fiches.
     * @param controlProperty the control property to which attaching event listeners.
     */
    public void attachFiches(final Pane pane, final IntegerProperty controlProperty) {
        this.sceneManager.attachFiches(pane, controlProperty);
    }

    /**
     * Calls the corresponding function to open the rules window.
     */
    public void showRules() {
        this.sceneManager.showRules();
    }

    /**
     * Calls the corresponding function to open the bets summary window.
     */
    public void showBets() {
        this.sceneManager.showBets(this.gameManager.getBets());
    }

    public void spinWheel(final MouseEvent event) {
        this.sceneManager.spinWheel(event);
    }
}
