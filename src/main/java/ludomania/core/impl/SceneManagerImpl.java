package ludomania.core.impl;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ludomania.controller.impl.CosmeticController;
import ludomania.controller.impl.MainMenuController;
import ludomania.controller.impl.TrenteEtQuaranteController;
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
        initializeStageSettings();
        this.mainScene = createMainScene();
        primaryStage.setScene(mainScene);
    }

    private void initializeStageSettings() {
        if (!settingsManager.fullscreenProperty().get()) {
            primaryStage.setWidth(settingsManager.resolutionWidthProperty().get());
            primaryStage.setHeight(settingsManager.resolutionHeightProperty().get());
        }
        primaryStage.setFullScreen(settingsManager.fullscreenProperty().get());

        bindFullscreenToStage();
        bindResolutionToStage();
        bindVolumeToManager();
        bindLanguageToManager();
        bindCosmeticToProvider();
    }

    private Scene createMainScene() {
        Parent root = new MainMenuController(this, audioManager).getView();
        applyBackgroundToRoot(root);

        Scene scene = new Scene(root,
                settingsManager.resolutionWidthProperty().get(),
                settingsManager.resolutionHeightProperty().get());
        scene.setFill(imageProvider.getBackgroundColor());
        return scene;
    }

    private void applyBackgroundToRoot(Parent root) {
        Color bgColor = imageProvider.getBackgroundColor();
        String cssColor = String.format("#%02x%02x%02x",
                (int) (bgColor.getRed() * 255),
                (int) (bgColor.getGreen() * 255),
                (int) (bgColor.getBlue() * 255));
        root.setStyle("-fx-background-color: " + cssColor + ";");
    }

    @Override
    public void switchToMainMenu() {
        audioManager.playMusic("devilTrigger");
        Parent root = new MainMenuController(this, audioManager).getView();
        applyBackgroundToRoot(root);
        mainScene.setRoot(root);
    }

    @Override
    public void switchToSettings() {
        Parent root = new SettingsController(settingsManager, this, audioManager).getView();
        applyBackgroundToRoot(root);
        mainScene.setRoot(root);
    }

    @Override
    public void switchToCosmetics() {
        Parent root = new CosmeticController(settingsManager, this, audioManager).getView();
        applyBackgroundToRoot(root);
        mainScene.setRoot(root);
    }

    public void switchToTrenteEtQuarante() {
        audioManager.playMusic("furinaTheme");
        Parent root = new TrenteEtQuaranteController(this, audioManager).getView();
        applyBackgroundToRoot(root);
        mainScene.setRoot(root);
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

    private void bindCosmeticToProvider() {
        settingsManager.cardThemeProperty().addListener((obs, oldValue, newValue) -> {
            settingsManager.save();
            imageProvider.setCardTheme(newValue);
        });
        settingsManager.ficheThemeProperty().addListener((obs, oldValue, newValue) -> {
            settingsManager.save();
            imageProvider.setFicheTheme(newValue);
        });
        settingsManager.backgroundThemeProperty().addListener((obs, oldValue, newValue) -> {
            settingsManager.save();
            imageProvider.setBackgroundTheme(newValue);
            updateAllBackgrounds();
        });
    }

    private void updateAllBackgrounds() {
        if (mainScene.getRoot() != null) {
            applyBackgroundToRoot(mainScene.getRoot());
        }
        mainScene.setFill(imageProvider.getBackgroundColor());
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