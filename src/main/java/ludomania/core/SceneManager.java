package ludomania.core;

import javafx.scene.Scene;
import javafx.stage.Stage;
import ludomania.controller.MainMenuController;
import ludomania.settings.SettingsController;
import ludomania.settings.SettingsManager;

public class SceneManager {
    private final SettingsManager settingsManager;
    private final AudioManager audioManager;
    private final LanguageManager languageManager;

    private final Stage primaryStage;

    public SceneManager(Stage primaryStage, SettingsManager settingsManager, AudioManager audioManager,
            LanguageManager languageManager) {
        this.primaryStage = primaryStage;
        this.audioManager = audioManager;
        this.settingsManager = settingsManager;
        this.languageManager = languageManager;
        primaryStage.setWidth(settingsManager.resolutionWidthProperty().get());
        primaryStage.setHeight(settingsManager.resolutionHeightProperty().get());
        bindResolutionToStage();
        bindVolumeToManager();
        bindLanguageToManager();
    }

    public void switchToMainMenu() {
        int currentWidth = settingsManager.resolutionWidthProperty().get();
        int currentHeight = settingsManager.resolutionHeightProperty().get();
        audioManager.playMusic("devilTrigger");
        primaryStage
                .setScene(new Scene(new MainMenuController(this, audioManager).getView(), currentWidth, currentHeight));
    }

    public void switchToSettings() {
        int currentWidth = settingsManager.resolutionWidthProperty().get();
        int currentHeight = settingsManager.resolutionHeightProperty().get();
        primaryStage
                .setScene(new Scene(
                        new SettingsController(settingsManager, this, languageManager, audioManager).getView(),
                        currentWidth,
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

    private void bindLanguageToManager() {
        settingsManager.currentLocaleProperty().addListener((obs, oldLocale, newLocale) -> {
            if (newLocale != null) {
                languageManager.setLocale(newLocale);
            }
        });
    }

    private void bindVolumeToManager() {
        settingsManager.volumeProperty().addListener((obs, oldValue, newValue) -> {
            settingsManager.save(); // Salva ogni volta che il volume cambia
            audioManager.setMasterVolume(newValue.doubleValue());
        });
    }

    public LanguageManager getLanguageManager() {
        return this.languageManager;
    }
}