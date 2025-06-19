package ludomania.controller.roulette;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.util.Builder;
import ludomania.controller.api.Controller;
import ludomania.controller.roulette.core.RouletteAppereanceController;
import ludomania.controller.roulette.core.RouletteGameController;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.model.game.api.Game;
import ludomania.model.game.roulette.RouletteGame;
import ludomania.view.roulette.RouletteViewBuilder;

public class RouletteController implements Controller {
    private final Builder<Parent> viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;

    private final RouletteGame game;

    private final RouletteAppereanceController appereanceController;
    private final RouletteGameController gameController;
    
    public RouletteController(
    final SceneManager sceneManager,
    final AudioManager audioManager
    ) {
        this.game = new RouletteGame(this, sceneManager);

        this.appereanceController = new RouletteAppereanceController();
        this.gameController = new RouletteGameController();
        this.viewBuilder = new RouletteViewBuilder(this, sceneManager.getLanguageManager(), sceneManager.getImageProvider());
        
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
    }
    
    @Override
    public Parent getView() {
        return this.game.getView();
    }
    
    @FXML
    private void pleinBet(MouseEvent event) {
        this.game.pleinBet(event);
    }
    
    @FXML
    private void chevalBet(MouseEvent event) {
        this.game.chevalBet(event);
    }
    
    @FXML
    private void carreBet(MouseEvent event) {
        this.game.carreBet(event);
    }
    
    @FXML
    private void colonneBet(MouseEvent event) {
        this.game.colonneBet(event);
    }
    
    @FXML
    private void noirBet(MouseEvent event) {
        this.game.noirBet(event);
    }
    
    @FXML
    private void rougeBet(MouseEvent event) {
        this.game.rougeBet(event);
    }
    
    @FXML
    private void pairBet(MouseEvent event) {
        this.game.pairBet(event);
    }
    
    @FXML
    private void impairBet(MouseEvent event) {
        this.game.impairBet(event);
    }
    
    @FXML
    private void passeBet(MouseEvent event) {
        this.game.passeBet(event);
    }
    
    @FXML
    private void manqueBet(MouseEvent event) {
        this.game.manqueBet(event);
    }
    
    @FXML
    private void douzineBet(MouseEvent event) {
        this.game.douzineBet(event);
    }
    
    @FXML
    private void spinWheel(MouseEvent event) {
        this.game.douzineBet(event);
    }
    
    @FXML
    private void highlightCarre(MouseEvent event) {
        this.game.highlightCarre(event);
    }
        
    @FXML
    private void unhighlightCarre(MouseEvent event) {
        this.game.unhighlightCarre(event);
    }

    @FXML
    private void glowWheel(MouseEvent event) {
        this.game.glowWheel(event);
    }
    
    @FXML
    private void unglowWheel(MouseEvent event) {
        this.game.unglowWheel(event);
    }

    @FXML
    private void quitGame(MouseEvent event) {
        this.game.quitGame();
    }

    @FXML
    private void selectAmount(MouseEvent event) {
        this.game.selectAmount(event);
    }
}
