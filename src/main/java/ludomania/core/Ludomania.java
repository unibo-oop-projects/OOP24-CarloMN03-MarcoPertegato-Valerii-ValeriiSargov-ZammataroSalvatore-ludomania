package ludomania.core;

import javafx.application.Application;
import javafx.stage.Stage;
import ludomania.settings.SettingsManager;

public class Ludomania extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final SettingsManager settingsManager = new SettingsManager();
        final AudioManager audioManager = new AudioManagerImpl(settingsManager.volumeProperty().doubleValue());
        audioManager.initialize();
        final LanguageManager languageManager = new LanguageManager(settingsManager.currentLocaleProperty().get());
        final SceneManager sceneManager = new SceneManager(primaryStage, settingsManager, audioManager,
                languageManager);
        sceneManager.switchToMainMenu();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}