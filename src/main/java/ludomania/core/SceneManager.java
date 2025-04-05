package ludomania.core;

import javafx.scene.Scene;
import javafx.stage.Stage;
import ludomania.controller.MainMenuController;
import ludomania.settings.SettingsController;
import ludomania.settings.SettingsManager;

public class SceneManager {
    private final SettingsManager settingsManager;
    private final Stage primaryStage;

    public SceneManager(Stage primaryStage, SettingsManager settingsManager) {
        this.primaryStage = primaryStage;
        this.settingsManager = settingsManager;
        primaryStage.setWidth(settingsManager.resolutionWidthProperty().get());
        primaryStage.setHeight(settingsManager.resolutionHeightProperty().get());
        bindResolutionToStage();
    }

    public void switchToMainMenu() {
        int currentWidth = settingsManager.resolutionWidthProperty().get();
        int currentHeight = settingsManager.resolutionHeightProperty().get();
        primaryStage.setScene(new Scene(new MainMenuController(this).getView(), currentWidth, currentHeight));
    }

    public void switchToSettings() {
        int currentWidth = settingsManager.resolutionWidthProperty().get();
        int currentHeight = settingsManager.resolutionHeightProperty().get();
        primaryStage
                .setScene(new Scene(new SettingsController(settingsManager, this).getView(), currentWidth,
                        currentHeight));
    }

    private void bindResolutionToStage() {
        settingsManager.resolutionWidthProperty().addListener((obs, oldVal, newVal) -> {
            primaryStage.setWidth(newVal.intValue());
        });

        settingsManager.resolutionHeightProperty().addListener((obs, oldVal, newVal) -> {
            primaryStage.setHeight(newVal.intValue());
        });
    }

}