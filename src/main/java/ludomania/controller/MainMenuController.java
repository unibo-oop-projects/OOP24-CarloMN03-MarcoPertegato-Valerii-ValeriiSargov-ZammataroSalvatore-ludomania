package ludomania.controller;

import javafx.application.Platform;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import ludomania.core.SceneManager;
import ludomania.handler.MainMenuHandler;
import ludomania.model.MainMenuModel;
import ludomania.view.MainMenuViewBuilder;

public class MainMenuController implements Controller, MainMenuHandler {
    private final Builder<Region> viewBuilder;
    SceneManager sceneManager;

    public MainMenuController(SceneManager sceneManager) {
        viewBuilder = new MainMenuViewBuilder(new MainMenuModel(), this);
        this.sceneManager = sceneManager;
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
        sceneManager.switchToSettings();
    }

    @Override
    public void handleExit() {
        Platform.exit();
    }

}