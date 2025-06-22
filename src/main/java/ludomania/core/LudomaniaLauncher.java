package ludomania.core;

import java.util.HashMap;

import javafx.application.Application;
import javafx.stage.Stage;
import ludomania.core.api.AudioManager;
import ludomania.core.api.ImageManager;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.core.api.SceneManager;
import ludomania.core.impl.AudioManagerImpl;
import ludomania.core.impl.CosmeticSetImpl;
import ludomania.core.impl.ImageManagerImpl;
import ludomania.core.impl.ImageProviderImpl;
import ludomania.core.impl.LanguageManagerImpl;
import ludomania.core.impl.SceneManagerImpl;
import ludomania.settings.api.SettingsManager;
import ludomania.settings.impl.SettingsManagerImpl;

/**
 * JavaFX application launcher for Ludomania.
 * <p>
 * This class extends {@link Application} and serves as the entry point when the
 * JavaFX runtime starts the graphical user interface. It initializes all core
 * managers needed by the application, including:
 * </p>
 * <ul>
 *   <li>{@link SettingsManager} – for managing user settings such as volume, themes, and locale</li>
 *   <li>{@link ImageManager} – for loading and managing application images</li>
 *   <li>{@link AudioManager} – for managing and playing audio resources</li>
 *   <li>{@link LanguageManager} – for handling internationalization and localization</li>
 *   <li>{@link SceneManager} – for controlling scene transitions within the application</li>
 * </ul>
 * <p>
 * After initializing these managers, the main menu is set as the initial scene and the application window is shown.
 * </p>
 */
public final class LudomaniaLauncher extends Application {

    /**
     * Called by JavaFX to initialize the graphical user interface.
     * <p>
     * This method creates and initializes all the necessary managers (such as
     * {@link SettingsManager}, {@link ImageManager}, {@link AudioManager},
     * {@link LanguageManager}, and {@link SceneManager}), and sets up the initial
     * scene.
     * </p>
     *
     * @param primaryStage the main {@link Stage} of the application
     */

    @Override
    public void start(final Stage primaryStage) {
        final SettingsManager settingsManager = new SettingsManagerImpl();
        final ImageManager imageManager = new ImageManagerImpl(new HashMap<>());
        imageManager.init();
        final ImageProvider imageProvider = new ImageProviderImpl(imageManager,
                new CosmeticSetImpl(settingsManager.cardThemeProperty().get(),
                        settingsManager.backgroundThemeProperty().get(), settingsManager.ficheThemeProperty().get()));

        final AudioManager audioManager = new AudioManagerImpl(settingsManager.volumeProperty().doubleValue());
        audioManager.initialize();
        final LanguageManager languageManager = new LanguageManagerImpl(settingsManager.currentLocaleProperty().get());
        final SceneManager sceneManager = new SceneManagerImpl(primaryStage, settingsManager, audioManager,
                languageManager, imageProvider);
        sceneManager.switchToMainMenu();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
