package ludomania.core.impl;

import java.util.Objects;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ludomania.controller.BlackJackController;
import ludomania.controller.CosmeticController;
import ludomania.controller.MainMenuController;
import ludomania.controller.RouletteController;
import ludomania.controller.TrenteEtQuaranteController;
import ludomania.core.api.AudioManager;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.core.api.SceneManager;
import ludomania.settings.api.SettingsManager;
import ludomania.settings.impl.SettingsController;

/**
 * Implementation of the {@link SceneManager} interface responsible for managing
 * the scenes of the application.
 * <p>
 * This class handles scene switching, stage settings, and bindings for
 * properties such as fullscreen, resolution, volume, language, and cosmetic
 * themes. It ensures that the application responds to user settings and changes
 * them dynamically within the application.
 * </p>
 */
public final class SceneManagerImpl implements SceneManager {

    private static final String JUSTIFICATION = "Binding to the manager are shared intentionally";
    private final SettingsManager settingsManager;
    private final AudioManager audioManager;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;

    private final Stage primaryStage;
    private final Scene mainScene;

    /**
     * Constructs a new {@link SceneManagerImpl}.
     * <p>
     * Initializes the stage settings, creates the main scene, and sets it on
     * the primary stage.
     * </p>
     *
     * @param primaryStage the main stage of the application
     * @param settingsManager the settings manager to access application
     * settings
     * @param audioManager the audio manager for handling audio settings
     * @param languageManager the language manager for handling locale and
     * language settings
     * @param imageProvider the image provider for managing themes and
     * backgrounds
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "References to languageManager and imageProvider are shared intentionally"
    )
    public SceneManagerImpl(final Stage primaryStage, final SettingsManager settingsManager,
            final AudioManager audioManager,
            final LanguageManager languageManager, final ImageProvider imageProvider) {
        this.primaryStage = Objects.requireNonNull(primaryStage);
        this.audioManager = Objects.requireNonNull(audioManager);
        this.settingsManager = Objects.requireNonNull(settingsManager);
        this.languageManager = Objects.requireNonNull(languageManager);
        this.imageProvider = Objects.requireNonNull(imageProvider);
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
        final Parent root = new MainMenuController(this, audioManager).getView();
        applyBackgroundToRoot(root);

        final Scene scene = new Scene(root,
                settingsManager.resolutionWidthProperty().get(),
                settingsManager.resolutionHeightProperty().get());
        scene.setFill(imageProvider.getBackgroundColor());
        return scene;
    }

    private void applyBackgroundToRoot(final Parent root) {
        final Color bgColor = imageProvider.getBackgroundColor();
        final String cssColor = String.format("#%02x%02x%02x",
                Math.round(bgColor.getRed() * 255),
                Math.round(bgColor.getGreen() * 255),
                Math.round(bgColor.getBlue() * 255));
        root.setStyle("-fx-background-color: " + cssColor + ";");
    }

    @Override
    public void switchToMainMenu() {
        audioManager.playMusic("devilTrigger");
        final Parent root = new MainMenuController(this, audioManager).getView();
        applyBackgroundToRoot(root);
        mainScene.setRoot(root);
    }

    @Override
    public void switchToSettings() {
        final Parent root = new SettingsController(settingsManager, this, audioManager).getView();
        applyBackgroundToRoot(root);
        mainScene.setRoot(root);
    }

    @Override
    public void switchToCosmetics() {
        final Parent root = new CosmeticController(settingsManager, this, audioManager).getView();
        applyBackgroundToRoot(root);
        mainScene.setRoot(root);
    }


    @Override
    public void switchToRoulette() {
        audioManager.playMusic("furinaTheme");
        final Parent root = new RouletteController(this, audioManager).getView();
        applyBackgroundToRoot(root);
        mainScene.setRoot(root);
    }
    @SuppressFBWarnings(
        value = "EI",
        justification = JUSTIFICATION
    )
    private void bindFullscreenToStage() {
        settingsManager.fullscreenProperty().addListener((obs, oldVal, newVal) -> {
            primaryStage.setFullScreen(newVal);
        });
    }
    @SuppressFBWarnings(
        value = "EI",
        justification = JUSTIFICATION
    )
    private void bindResolutionToStage() {
        settingsManager.resolutionWidthProperty().addListener((obs, oldVal, newVal) -> {
            primaryStage.setWidth(newVal.intValue());
        });

        settingsManager.resolutionHeightProperty().addListener((obs, oldVal, newVal) -> {
            primaryStage.setHeight(newVal.intValue());
        });
    }
    @SuppressFBWarnings(
        value = "EI",
        justification = JUSTIFICATION
    )
    private void bindLanguageToManager() {
        settingsManager.currentLocaleProperty().addListener((obs, oldLocale, newLocale) -> {
            if (newLocale != null) {
                languageManager.setLocale(newLocale);
            }
        });
    }
    @SuppressFBWarnings(
        value = "EI",
        justification = JUSTIFICATION
    )
    private void bindVolumeToManager() {
        settingsManager.volumeProperty().addListener((obs, oldValue, newValue) -> {
            settingsManager.save();
            audioManager.setMasterVolume(newValue.doubleValue());
        });
    }
    @SuppressFBWarnings(
        value = "EI",
        justification = JUSTIFICATION
    )
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
    @SuppressFBWarnings(
        value = "EI",
        justification = JUSTIFICATION
    )
    @Override
    public LanguageManager getLanguageManager() {
        return this.languageManager;
    }

    @SuppressFBWarnings(
        value = "EI",
        justification = JUSTIFICATION
    )
    @Override
    public ImageProvider getImageProvider() {
        return this.imageProvider;
    }

    @Override
    public void switchToBlackJackMenu() {
        audioManager.playMusic("devilTrigger");
        final Parent root = new BlackJackController(this, audioManager).getView();
        applyBackgroundToRoot(root);
        mainScene.setRoot(root);
    }

    @Override
    public void switchToTrenteEtQuarante() {
        audioManager.playMusic("furinaTheme");
        final Parent root = new TrenteEtQuaranteController(this).getView();
        applyBackgroundToRoot(root);
        mainScene.setRoot(root);
    }
}
