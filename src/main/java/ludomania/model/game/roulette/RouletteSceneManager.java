package ludomania.model.game.roulette;

import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ludomania.core.api.AudioManager;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.core.api.SceneManager;
import ludomania.cosmetics.FicheValue;

import java.util.Arrays;
import java.util.Optional;

public class RouletteSceneManager {
    private final int DIALOG_SIZE = 450;

    private final SceneManager sceneManager;
    private final LanguageManager languageManager;
    private final AudioManager audioManager;
    private final ImageProvider imageProvider;

    private final ToggleGroup ficheToggleGroup;

    public RouletteSceneManager(final SceneManager sceneManager, final AudioManager audioManager) {
        this.sceneManager = sceneManager;
        this.languageManager = sceneManager.getLanguageManager();
        this.audioManager = audioManager;
        this.imageProvider = sceneManager.getImageProvider();

        this.ficheToggleGroup = new ToggleGroup();
    }

    public void quitGame() {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle(languageManager.getString("confirm_exit"));
        confirmDialog.setHeaderText(languageManager.getString("back_to_menu"));
        confirmDialog.setContentText(languageManager.getString("all_progress_lost"));

        ButtonType buttonYes = new ButtonType(languageManager.getString("yes"));
        ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirmDialog.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = confirmDialog.showAndWait();

        if (result.isPresent() && result.get() == buttonYes) {
            audioManager.playSound("click");
            sceneManager.switchToMainMenu();
        }
    }

    public void highlightCarre(MouseEvent event) {
        if (event.getSource() instanceof Node clickedButton) {
            clickedButton.getParent().setStyle("-fx-border-color: #00eeff; -fx-border-width: 3px;");
        }
    }

    public void unhighlightCarre(MouseEvent event) {
        if (event.getSource() instanceof Node clickedButton) {
            clickedButton.getParent().setStyle("-fx-border-color: trasparent; -fx-border-width: 1px;");
        }
    }

    public void glowWheel(MouseEvent event) {
        if (event.getSource() instanceof ImageView node) {
            Glow glow = new Glow();

            glow.setLevel(0.7);
            node.setEffect(glow);
        }
    }

    public void unglowWheel(MouseEvent event) {
        if (event.getSource() instanceof ImageView node) {
            node.setEffect(null);
        }
    }

    public void attachFiches(Pane node, IntegerProperty controlProperty) {
        Arrays.stream(FicheValue.values())
                .sorted((a, b) -> -Integer.compare(b.getValue(), a.getValue()))
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
                Glow glow = new Glow();
                glow.setLevel(0.7);
                ((ToggleButton) newToggle).setEffect(glow);
            }

            if (newToggle != null) {
                controlProperty.setValue(((FicheValue)newToggle.getUserData()).getValue());
            }
        });
    }

    public void showRules() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Trente et Quarante");

        final Label rulesLabel = new Label(languageManager.getString("tq_rules_text"));
        rulesLabel.setWrapText(true);

        final ScrollPane scrollPane = new ScrollPane(rulesLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        scrollPane.setStyle("-fx-background: white;");

        final Button okBtn = new Button(languageManager.getString("exit"));
        okBtn.setOnAction(e -> dialog.close());

        final VBox dialogVBox = new VBox(10, scrollPane, okBtn);
        dialogVBox.setPadding(new Insets(10 * 2));
        dialogVBox.setAlignment(Pos.CENTER);

        final Scene dialogScene = new Scene(dialogVBox, DIALOG_SIZE, DIALOG_SIZE);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    public void showBets() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Trente et Quarante");

        final Label rulesLabel = new Label(languageManager.getString("tq_rules_text"));
        rulesLabel.setWrapText(true);

        final ScrollPane scrollPane = new ScrollPane(rulesLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        scrollPane.setStyle("-fx-background: white;");

        final Button okBtn = new Button(languageManager.getString("exit"));
        okBtn.setOnAction(e -> dialog.close());

        final VBox dialogVBox = new VBox(10, scrollPane, okBtn);
        dialogVBox.setPadding(new Insets(10 * 2));
        dialogVBox.setAlignment(Pos.CENTER);

        final Scene dialogScene = new Scene(dialogVBox, DIALOG_SIZE, DIALOG_SIZE);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }
}
