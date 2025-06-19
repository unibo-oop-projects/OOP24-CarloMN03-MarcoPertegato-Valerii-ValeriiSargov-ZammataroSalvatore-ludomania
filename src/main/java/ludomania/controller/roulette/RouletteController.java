package ludomania.controller.roulette;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.model.Pair;
import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.game.roulette.RouletteGame;

public class RouletteController implements Controller {
    private final RouletteGame game;

    @FXML
    private Button okBtn;

    @FXML
    private Label resultLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label betAmount;

    @FXML
    private ImageView wheel;

    private final BooleanProperty submitDisabled = new SimpleBooleanProperty(true);
    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty total = new SimpleStringProperty();
    private final StringProperty bet = new SimpleStringProperty();

    public RouletteController(
    final SceneManager sceneManager,
    final AudioManager audioManager
    ) {
        this.game = new RouletteGame(this, sceneManager, audioManager);
    }

    @FXML
    public void initialize() {
        this.okBtn.disableProperty().bind(this.submitDisabled);
        this.resultLabel.textProperty().bind(this.result);
        this.totalLabel.textProperty().bind(this.total);
        this.betAmount.textProperty().bind(this.bet);
        this.wheel.disableProperty().bind(this.submitDisabled.not());

        this.resultLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            submitDisabled.set(newValue.trim().isEmpty());
        });
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
        Pair<Integer, RouletteColor> result = this.game.runGame().getResult();
        this.result.set(result.getKey().toString());
        String colorStyle = "-fx-text-fill: " + this.resultColor(result.getValue()) + ";";
        this.resultLabel.setStyle(colorStyle);
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
        this.bet.set("Bet: " + this.game.selectAmount(event) + " $");
    }

    @FXML
    private void evaluateRound(MouseEvent event) {
        this.result.set("");
        this.game.evaluateRound(event);
    }

    private String resultColor(RouletteColor color) {
        return switch (color.name()) {
            case "NOIR" -> "black";
            case "ROUGE" -> "red";
            case "GREEN" -> "green";
            default -> "white";
        };
    }
}
