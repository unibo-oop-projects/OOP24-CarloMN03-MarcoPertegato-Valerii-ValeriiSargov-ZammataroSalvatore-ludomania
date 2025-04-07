package ludomania.controller;

import javafx.application.Platform;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import ludomania.core.AudioManager;
import ludomania.core.SceneManager;
import ludomania.handler.MainMenuHandler;
import ludomania.model.MainMenuModel;
import ludomania.view.MainMenuViewBuilder;

public class MainMenuController implements Controller, MainMenuHandler {
    private final Builder<Region> viewBuilder;
    SceneManager sceneManager;
    AudioManager audioManager;

    public MainMenuController(SceneManager sceneManager, AudioManager audioManager) {
        viewBuilder = new MainMenuViewBuilder(new MainMenuModel(), this, sceneManager.getLanguageManager());
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
    }

    @Override
    public Region getView() {
        return viewBuilder.build();
    }

    public void startGame() {
        System.out.println("ciao");
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