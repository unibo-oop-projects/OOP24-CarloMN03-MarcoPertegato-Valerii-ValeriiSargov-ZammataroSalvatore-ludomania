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
    private final Scene mainScene;

    public SceneManager(final Stage primaryStage, final SettingsManager settingsManager,
            final AudioManager audioManager,
            final LanguageManager languageManager) {
        this.primaryStage = primaryStage;
        this.audioManager = audioManager;
        this.settingsManager = settingsManager;
        this.languageManager = languageManager;
        if (!settingsManager.fullscreenProperty().get()) {
            primaryStage.setWidth(settingsManager.resolutionWidthProperty().get());
            primaryStage.setHeight(settingsManager.resolutionHeightProperty().get());
        }
        primaryStage.setFullScreen(settingsManager.fullscreenProperty().get());
        bindFullscreenToStage();
        bindResolutionToStage();
        bindVolumeToManager();
        bindLanguageToManager();
        this.mainScene = new Scene(new MainMenuController(this, audioManager).getView(),
                settingsManager.resolutionWidthProperty().get(),
                settingsManager.resolutionHeightProperty().get());
        primaryStage.setScene(mainScene);
    }

    public void switchToMainMenu() {
        audioManager.playMusic("devilTrigger");
        mainScene
                .setRoot(new MainMenuController(this, audioManager).getView());
    }

    public void switchToSettings() {
        mainScene
                .setRoot(new SettingsController(settingsManager, this, audioManager).getView());
    }

    private void bindFullscreenToStage() {
        settingsManager.fullscreenProperty().addListener((obs, oldVal, newVal) -> {
            primaryStage.setFullScreen(newVal);
        });
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