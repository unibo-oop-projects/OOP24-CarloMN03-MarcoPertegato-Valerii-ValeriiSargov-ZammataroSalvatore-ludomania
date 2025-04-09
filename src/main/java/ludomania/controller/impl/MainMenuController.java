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
    private int selectedGameId = 1;

    public MainMenuController(final SceneManager sceneManager, final AudioManager audioManager) {
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        viewBuilder = new MainMenuViewBuilder(this, sceneManager.getLanguageManager());
    }

    @Override
    public Parent getView() {
        return viewBuilder.build();
    }

    @Override
    public void handleStartGame() {
        switch (selectedGameId) {
            case 1:
                handleSettings();
                break;
            case 2:
                handleExit();
                break;
            case 3:
                audioManager.playSound("click");
                audioManager.playSound("click");
                audioManager.playSound("click");
                audioManager.playSound("click");
                break;
            default:

        }
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

    @Override
    public void selectGame(int gameId) {
        this.selectedGameId = gameId;
    }

}