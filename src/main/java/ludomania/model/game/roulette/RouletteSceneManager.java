package ludomania.model.game.roulette;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import ludomania.core.api.AudioManager;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.core.api.SceneManager;
import ludomania.cosmetics.FicheValue;
import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.player.api.Player;

import java.util.*;

/**
 * Implements the Roulette game graphic logic.
 * <p>
 *     Handles operations related to the UI modifications.
 * </p>
 */
public class RouletteSceneManager {
    private static final int DIALOG_SIZE = 450;
    private static final int SCROLL_PANE_DEFAULT_HEIGHT = 400;
    private static final double GLOW_LEVEL = 0.7;
    private static final double CIRCLES = 3000;
    private static final int CYCLE_COUNT = 1;
    private static final int ANIMATION_TIME = 3;


    private final SceneManager sceneManager;
    private final LanguageManager languageManager;
    private final AudioManager audioManager;
    private final ImageProvider imageProvider;
    private Stage rulesWindow = new Stage();
    private Stage betsWindow = new Stage();

    private final ToggleGroup ficheToggleGroup;

    /**
     * Instantiate the Roulette game scene manager.
     * @param sceneManager the manager for the application scenes.
     * @param audioManager the manager for audio features.
     */
    @SuppressFBWarnings(
            value = "EI2",
            justification = "References to audioManager and sceneManager are shared intentionally"
    )
    public RouletteSceneManager(final SceneManager sceneManager, final AudioManager audioManager) {
        this.sceneManager = sceneManager;
        this.languageManager = sceneManager.getLanguageManager();
        this.audioManager = audioManager;
        this.imageProvider = sceneManager.getImageProvider();

        this.ficheToggleGroup = new ToggleGroup();
    }

    /**
     * Creates a dialog to ask the user confirmation to quit the game.
     */
    public void quitGame() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(languageManager.getString("confirm_exit"));
        alert.setHeaderText(languageManager.getString("back_to_menu"));
        alert.setContentText(languageManager.getString("all_progress_lost"));

        final ButtonType buttonYes = new ButtonType(languageManager.getString("yes"));
        final ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        final Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && Objects.equals(result.get(), buttonYes)) {
            audioManager.playSound("click");
            sceneManager.switchToMainMenu();
        }
    }

    /**
     * Highlights the carre bet cells.
     * @param event the mouse enter event on carre bet button.
     */
    public void highlightCarre(final MouseEvent event) {
        if (event.getSource() instanceof Node clickedButton) {
            clickedButton.getParent().setStyle("-fx-border-color: #00eeff; -fx-border-width: 3px;");
        }
    }

    /**
     * Unhighlights the carre bet cells.
     * @param event the mouse exit event on carre bet button.
     */
    public void unhighlightCarre(final MouseEvent event) {
        if (event.getSource() instanceof Node clickedButton) {
            clickedButton.getParent().setStyle("-fx-border-color: trasparent; -fx-border-width: 1px;");
        }
    }

    /**
     * Glows the roulette wheel.
     * @param event the mouse click enter on roulette wheel image.
     */
    public void glowWheel(final MouseEvent event) {
        if (event.getSource() instanceof ImageView node) {
            final Glow glow = new Glow();

            glow.setLevel(this.GLOW_LEVEL);
            node.setEffect(glow);
        }
    }

    /**
     * Unglows the roulette wheel.
     * @param event the mouse click exit on roulette wheel image.
     */
    public void unglowWheel(final MouseEvent event) {
        if (event.getSource() instanceof ImageView node) {
            node.setEffect(null);
        }
    }

    /**
     * Attaches the fiches images and creates corresponding event handlers on the game scene.
     * @param node the element on which attaching fiches.
     * @param controlProperty the control property to which attaching event listeners.
     */
    public void attachFiches(final Pane node, final IntegerProperty controlProperty) {
        Arrays.stream(FicheValue.values())
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .forEach(ficheValue -> {
                    final ToggleButton button = new ToggleButton();

                    button.setGraphic(this.imageProvider.getSVGFiche(ficheValue.getValue()));
                    button.setStyle(
                            "-fx-background-color: transparent; " + "-fx-background-insets: 0; -fx-end-margin: 10;");
                    button.setUserData(ficheValue);
                    button.setToggleGroup(ficheToggleGroup);

                    node.getChildren().add(button);
                });

        ficheToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (oldToggle != null && !oldToggle.equals(newToggle)) {
                ((ToggleButton) oldToggle).setEffect(null);
            }
            if (newToggle != null && newToggle.isSelected()) {
                final Glow glow = new Glow();
                glow.setLevel(this.GLOW_LEVEL);
                ((ToggleButton) newToggle).setEffect(glow);
            }

            if (newToggle != null) {
                controlProperty.setValue(((FicheValue) newToggle.getUserData()).getValue());
            }
        });
    }

    /**
     * Shows a dialog containing the game rules.
     */
    public void showRules() {
        if (this.rulesWindow != null) {
            this.rulesWindow.close();
        }

        final Label rulesLabel = new Label(languageManager.getString("roulette_rules"));
        rulesLabel.setWrapText(true);

        final ScrollPane scrollPane = new ScrollPane(rulesLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(this.SCROLL_PANE_DEFAULT_HEIGHT);
        scrollPane.setStyle("-fx-background: white;");

        final Button okBtn = new Button(languageManager.getString("close"));
        okBtn.setOnAction(e -> this.rulesWindow.close());

        final VBox dialogVBox = new VBox(10, scrollPane, okBtn);
        dialogVBox.setPadding(new Insets(10 * 2));
        dialogVBox.setAlignment(Pos.CENTER);

        this.rulesWindow = this.createDialogScene(dialogVBox, Modality.WINDOW_MODAL, languageManager.getString("bets"));
        this.rulesWindow.showAndWait();
    }

    /**
     * Shows a dialog containing the current placed bets.
     * @param bets the list of bets to show
     */
    public void showBets(final List<Pair<Player, Bet>> bets) {
        if (this.betsWindow != null) {
            this.betsWindow.close();
        }

        final ListView<String> betsList = new ListView<>();
        betsList.getItems().addAll(
                bets.stream().map(b -> String.format("%1$s, %2$s", b.getKey().getUsername(), b.getValue().toString())).toList());

        final ScrollPane scrollPane = new ScrollPane(betsList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(this.SCROLL_PANE_DEFAULT_HEIGHT);
        scrollPane.setStyle("-fx-background: white;");

        final Button okBtn = new Button(languageManager.getString("close"));
        okBtn.setOnAction(e -> this.betsWindow.close());

        final VBox dialogVBox = new VBox(10, scrollPane, okBtn);
        dialogVBox.setPadding(new Insets(10 * 2));
        dialogVBox.setAlignment(Pos.CENTER);

        this.betsWindow = this.createDialogScene(dialogVBox, Modality.WINDOW_MODAL, languageManager.getString("bets"));
        this.betsWindow.showAndWait();
    }

    /**
     * Shows a dialog to inform the user it's game over.
     */
    public void alertAndQuit() {
        final Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle(languageManager.getString("game_over"));
        confirmDialog.setHeaderText(languageManager.getString("no_more_money"));

        final ButtonType okBtn = new ButtonType(languageManager.getString("exit"));

        confirmDialog.getButtonTypes().setAll(okBtn);
        final Optional<ButtonType> result = confirmDialog.showAndWait();

        if (result.isPresent() && Objects.equals(result.get(), okBtn)) {
            audioManager.playSound("click");
            sceneManager.switchToMainMenu();
        }
    }

    /**
     * Starts the animation of Roulette wheel.
     * @param event the mouse event on image view.
     */
    public void spinWheel(final MouseEvent event) {
        final Object img = event.getSource();
        if (img instanceof ImageView imageView) {
            final RotateTransition rotateTransition = new RotateTransition(Duration.seconds(this.ANIMATION_TIME), imageView);

            rotateTransition.setByAngle(Math.random() * this.CIRCLES);
            rotateTransition.setCycleCount(this.CYCLE_COUNT);
            rotateTransition.setInterpolator(Interpolator.EASE_BOTH);

            // Play the animation
            rotateTransition.play();
        }
    }

    private Stage createDialogScene(final Parent sceneRoot, final Modality mode, final String title) {
        final Stage window = new Stage();
        window.initModality(mode);
        window.setTitle(title);

        final Scene dialogScene = new Scene(sceneRoot, DIALOG_SIZE, DIALOG_SIZE);
        window.setScene(dialogScene);
        return window;
    }
}
