package ludomania.controller.roulette;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
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
    public void pleinBet(MouseEvent event) {
        this.gameController.pleinBet(event);
    }
    
    @FXML
    public void chevalBet(MouseEvent event) {
        this.gameController.chevalBet(event);
    }
    
    @FXML
    public void carreBet(MouseEvent event) {
        this.gameController.carreBet(event);
    }
    
    @FXML
    public void colonneBet(MouseEvent event) {
        this.gameController.colonneBet(event);
    }
    
    @FXML
    public void noirBet(MouseEvent event) {
        this.gameController.noirBet(event);
    }
    
    @FXML
    public void rougeBet(MouseEvent event) {
        this.gameController.rougeBet(event);
    }
    
    @FXML
    public void pairBet(MouseEvent event) {
        this.gameController.pairBet(event);
    }
    
    @FXML
    public void impairBet(MouseEvent event) {
        this.gameController.impairBet(event);
    }
    
    @FXML
    public void passeBet(MouseEvent event) {
        this.gameController.passeBet(event);
    }
    
    @FXML
    public void manqueBet(MouseEvent event) {
        this.gameController.manqueBet(event);
    }
    
    @FXML
    public void douzineBet(MouseEvent event) {
        this.gameController.douzineBet(event);
    }
    
    @FXML
    void highlightCarre(MouseEvent event) {
        this.appereanceController.highlightCarre(event);
    }
    
    @FXML
    void unhighlightCarre(MouseEvent event) {
        this.appereanceController.unhighlightCarre(event);
    }
}
