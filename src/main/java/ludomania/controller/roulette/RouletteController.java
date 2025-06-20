package ludomania.controller.roulette;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.model.Pair;
import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.game.roulette.RouletteGame;

public class RouletteController implements Controller {

    @FXML
    private Button okBtn;

    @FXML
    private Label resultLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label betAmountLabel;

    @FXML
    private ImageView wheel;

    @FXML
    private HBox ficheBox;

    @FXML
    private GridPane board;

    private final RouletteGame game;

    private final BooleanProperty okBtnDisabled = new SimpleBooleanProperty(true);
    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty total = new SimpleStringProperty();
    private final StringProperty bet = new SimpleStringProperty("0 $");
    private final IntegerProperty betAmount = new SimpleIntegerProperty();

    public RouletteController(final SceneManager sceneManager, final AudioManager audioManager) {
        this.game = new RouletteGame(this, sceneManager, audioManager);
    }

    @FXML
    public void initialize() {
        this.okBtn.disableProperty().bind(this.okBtnDisabled);
        this.resultLabel.textProperty().bind(this.result);
        this.totalLabel.textProperty().bind(this.total);
        this.betAmountLabel.textProperty().bind(this.bet);
        this.wheel.disableProperty().bind(this.okBtnDisabled.not());
        this.board.disableProperty().bind(this.okBtnDisabled.not());
        this.ficheBox.disableProperty().bind(this.okBtnDisabled.not());

        this.resultLabel.textProperty()
                .addListener((observable, oldValue, newValue) -> okBtnDisabled.set(newValue.trim().isEmpty()));

        this.attachFiches(this.ficheBox, this.betAmount);

        this.betAmount.addListener((observable, oldValue, newValue) -> {
            Double betAmount = this.selectAmount(newValue.intValue());
            this.bet.set(betAmount.intValue() + " $");
            this.total.set(this.getBalance().intValue() + " $");
        });

        this.total.set(this.getBalance().intValue() + " $");
    }

    @Override
    public Parent getView() {
        return this.game.getView();
    }

    @FXML
    private void pleinBet(MouseEvent event) {
        try {
            this.game.pleinBet(event);
            this.resetBelLabel();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void chevalBet(MouseEvent event) {
        try {
            this.game.chevalBet(event);
            this.resetBelLabel();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void carreBet(MouseEvent event) {
        try {
            this.game.carreBet(event);
            this.resetBelLabel();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void colonneBet(MouseEvent event) {
        try {
            this.game.colonneBet(event);
            this.resetBelLabel();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void noirBet(MouseEvent event) {
        this.game.noirBet(event);
        this.resetBelLabel();
    }

    @FXML
    private void rougeBet(MouseEvent event) {
        this.game.rougeBet(event);
        this.resetBelLabel();
    }

    @FXML
    private void pairBet() {
        this.game.pairBet();
        this.resetBelLabel();
    }

    @FXML
    private void impairBet() {
        this.game.impairBet();
        this.resetBelLabel();
    }

    @FXML
    private void passeBet() {
        this.game.passeBet();
        this.resetBelLabel();
    }

    @FXML
    private void manqueBet() {
        this.game.manqueBet();
    }

    @FXML
    private void douzineBet(MouseEvent event) {
        try {
            this.game.douzineBet(event);
            this.resetBelLabel();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void spinWheel() {
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
    private void quitGame() {
        this.game.quitGame();
    }

    @FXML
    private Double selectAmount(Integer amount) {
        return this.game.addBetAmount(amount);
    }

    @FXML
    private void evaluateRound(MouseEvent event) {
        this.result.set("");
        this.game.evaluateRound();

        this.total.set(this.getBalance().intValue() + " $");
    }

    @FXML
    private void showRules() {
        this.game.showRules();
    }

    private String resultColor(RouletteColor color) {
        return switch (color.name()) {
            case "NOIR" -> "black";
            case "ROUGE" -> "red";
            case "GREEN" -> "green";
            default -> "white";
        };
    }

    private void attachFiches(Pane pane, IntegerProperty controlProperty) {
        this.game.attachFiches(pane, controlProperty);
    }

    private Double getBalance() {
        return this.game.getBalance();
    }

    private void resetBelLabel() {
        this.bet.set("0 $");
    }
}
