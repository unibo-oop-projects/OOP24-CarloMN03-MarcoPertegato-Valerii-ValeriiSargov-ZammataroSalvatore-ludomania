package ludomania.model.game.roulette;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import ludomania.controller.roulette.RouletteController;
import ludomania.core.api.SceneManager;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.api.Game;
import ludomania.view.ViewBuilder;
import ludomania.view.roulette.RouletteViewBuilder;

public class RouletteGame implements Game {

    private final ViewBuilder viewBuilder;

    public RouletteGame(RouletteController controller, final SceneManager sceneManager) {
        this.viewBuilder = new RouletteViewBuilder(
                controller, sceneManager.getLanguageManager(), sceneManager.getImageProvider());
    }

    @Override
    public CounterResult<Integer> runGame() {
        return null;
    }

    public Parent getView() {
        return viewBuilder.build();
    }

    public void pleinBet(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void chevalBet(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void carreBet(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void colonneBet(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void noirBet(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void rougeBet(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void pairBet(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void impairBet(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void passeBet(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void manqueBet(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void douzineBet(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void spinWheel(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void highlightCarre(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void unhighlightCarre(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void glowWheel(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void unglowWheel(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void quitGame() {
        throw new UnsupportedOperationException();
    }

    public void selectAmount(MouseEvent event) {
        throw new UnsupportedOperationException();
    }
}
