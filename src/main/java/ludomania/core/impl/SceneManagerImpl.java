package ludomania.core.impl;

import javafx.scene.Scene;
import javafx.stage.Stage;
import ludomania.controller.impl.MainMenuController;
import ludomania.core.api.AudioManager;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.core.api.SceneManager;
import ludomania.settings.api.SettingsManager;
import ludomania.settings.impl.SettingsController;

public final class SceneManagerImpl implements SceneManager {
    private final SettingsManager settingsManager;
    private final AudioManager audioManager;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;

    private final Stage primaryStage;
    private final Scene mainScene;

    public SceneManagerImpl(final Stage primaryStage, final SettingsManager settingsManager,
            final AudioManager audioManager,
            final LanguageManager languageManager, final ImageProvider imageProvider) {
        this.primaryStage = primaryStage;
        this.audioManager = audioManager;
        this.settingsManager = settingsManager;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
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
        System.out.println(imageProvider.getBackgroundColor());
        primaryStage.setScene(mainScene);
    }

    @Override
    public void switchToMainMenu() {
        audioManager.playMusic("devilTrigger");
        mainScene
                .setRoot(new MainMenuController(this, audioManager).getView());
    }

    @Override
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
            settingsManager.save();
            audioManager.setMasterVolume(newValue.doubleValue());
        });
    }

    @Override
    public LanguageManager getLanguageManager() {
        return this.languageManager;
    }

    @Override
    public ImageProvider getImageProvider() {
        return this.imageProvider;
    }
}