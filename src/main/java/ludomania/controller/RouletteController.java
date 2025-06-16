package ludomania.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.util.Builder;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.view.roulette.RouletteViewBuilder;

public class RouletteController implements Controller {
    private final Builder<Parent> viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;

    public RouletteController(
        final SceneManager sceneManager,
        final AudioManager audioManager
    ) {
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        viewBuilder = new RouletteViewBuilder(this, sceneManager.getLanguageManager(),
        sceneManager.getImageProvider());
    }

    @Override
    public Parent getView() {
        return viewBuilder.build();
    }

    @FXML
    public void buttonClicked(MouseEvent e) {
        System.out.println("Button clicked");
    }

}
