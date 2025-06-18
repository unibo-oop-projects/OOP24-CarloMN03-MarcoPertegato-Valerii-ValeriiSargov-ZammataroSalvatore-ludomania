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
import ludomania.view.roulette.RouletteViewBuilder;

public class RouletteController implements Controller {
    private final Builder<Parent> viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;

    private final RouletteAppereanceController appereanceController;
    private final RouletteGameController gameController;
    
    public RouletteController(
    final SceneManager sceneManager,
    final AudioManager audioManager
    ) {
        this.appereanceController = new RouletteAppereanceController();
        this.gameController = new RouletteGameController();
        this.viewBuilder = new RouletteViewBuilder(this, sceneManager.getLanguageManager(), sceneManager.getImageProvider());
        
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
    }
    
    @Override
    public Parent getView() {
        return viewBuilder.build();
    }
    
    @FXML
    private void pleinBet(MouseEvent event) {
        this.gameController.pleinBet(event);
    }
    
    @FXML
    private void chevalBet(MouseEvent event) {
        this.gameController.chevalBet(event);
    }
    
    @FXML
    private void carreBet(MouseEvent event) {
        this.gameController.carreBet(event);
    }
    
    @FXML
    private void colonneBet(MouseEvent event) {
        this.gameController.colonneBet(event);
    }
    
    @FXML
    private void noirBet(MouseEvent event) {
        this.gameController.noirBet(event);
    }
    
    @FXML
    private void rougeBet(MouseEvent event) {
        this.gameController.rougeBet(event);
    }
    
    @FXML
    private void pairBet(MouseEvent event) {
        this.gameController.pairBet(event);
    }
    
    @FXML
    private void impairBet(MouseEvent event) {
        this.gameController.impairBet(event);
    }
    
    @FXML
    private void passeBet(MouseEvent event) {
        this.gameController.passeBet(event);
    }
    
    @FXML
    private void manqueBet(MouseEvent event) {
        this.gameController.manqueBet(event);
    }
    
    @FXML
    private void douzineBet(MouseEvent event) {
        this.gameController.douzineBet(event);
    }
    
    @FXML
    private void spinWheel(MouseEvent event) {
        this.gameController.douzineBet(event);
    }
    
    @FXML
    private void highlightCarre(MouseEvent event) {
        this.appereanceController.highlightCarre(event);
    }
        
    @FXML
    private void unhighlightCarre(MouseEvent event) {
        this.appereanceController.unhighlightCarre(event);
    }

    @FXML
    private void highlightWheel(MouseEvent event) {
        this.appereanceController.glowWheel(event);
    }
    
    @FXML
    private void unhighlightWheel(MouseEvent event) {
        this.appereanceController.unglowWheel(event);
    }

    @FXML
    private void quitGame(MouseEvent event) {
        this.gameController.quitGame();
    }

    @FXML
    private void selectAmount(MouseEvent event) {
        this.gameController.selectAmount(event);
    }
}
