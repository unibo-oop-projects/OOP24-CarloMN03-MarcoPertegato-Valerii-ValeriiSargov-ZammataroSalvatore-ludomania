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
 * Main class for launching the Ludomania application.
 * <p>
 * This class starts the JavaFX application and sets up the various managers
 * required for the application's functionality, including settings, image,
 * audio,
 * language, and scene managers. After initialization, the main menu scene is
 * displayed.
 * </p>
 */

public final class Ludomania extends Application {
    /**
     * The main entry point for the application.
     * <p>
     * This method starts the application by calling the launch(String[])
     * method from JavaFX.
     * </p>
     * 
     * @param args command line arguments (not used in this case)
     */

    public static void main(final String[] args) {
        launch(args);
    }

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
