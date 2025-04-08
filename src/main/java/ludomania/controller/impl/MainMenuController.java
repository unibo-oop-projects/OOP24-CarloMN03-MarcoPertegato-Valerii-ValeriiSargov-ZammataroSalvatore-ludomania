package ludomania.controller.impl;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.util.Builder;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.handler.MainMenuHandler;
import ludomania.view.MainMenuViewBuilder;

public final class MainMenuController implements Controller, MainMenuHandler {
    private final Builder<Parent> viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;

    public MainMenuController(final SceneManager sceneManager, final AudioManager audioManager) {
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        viewBuilder = new MainMenuViewBuilder(this, sceneManager.getLanguageManager());
    }

    @Override 
    public Parent getView() {
        return viewBuilder.build();
    }

    public void startGame() {
    }

    @Override
    public void handleStartGame() {
    }

    @Override
    public void handleSettings() {
        audioManager.playSound("click");
        sceneManager.switchToSettings();
    }

    @Override
    public void handleExit() {
        Platform.exit();
    }

}