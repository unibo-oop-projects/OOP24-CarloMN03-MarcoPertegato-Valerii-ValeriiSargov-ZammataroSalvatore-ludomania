package ludomania.model.game.roulette;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ludomania.core.api.AudioManager;
import ludomania.core.api.LanguageManager;
import ludomania.core.api.SceneManager;

import java.util.Optional;

public class RouletteSceneManager {
    private final SceneManager sceneManager;
    private final LanguageManager languageManager;
    private final AudioManager audioManager;

    public RouletteSceneManager(final SceneManager sceneManager, final AudioManager audioManager) {
        this.sceneManager = sceneManager;
        this.languageManager = sceneManager.getLanguageManager();
        this.audioManager = audioManager;
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
        Object source = event.getSource();

        if (source instanceof Node) {
            Node clickedButton = (Node) source;
            clickedButton.getParent().setStyle("-fx-border-color: #00eeff; -fx-border-width: 3px;");
        }
    }

    public void unhighlightCarre(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Node) {
            Node clickedButton = (Node) source;
            clickedButton.getParent().setStyle("-fx-border-color: trasparent; -fx-border-width: 1px;");
        }
    }

    public void glowWheel(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof ImageView) {
            ImageView node = (ImageView) source;
            Glow glow = new Glow();

            glow.setLevel(0.7);
            node.setEffect(glow);
        }
    }

    public void unglowWheel(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof ImageView) {
            ImageView node = (ImageView) source;
            node.setEffect(null);
        }
    }
}
