package ludomania.core;

import javafx.application.Application;
import javafx.stage.Stage;
import ludomania.settings.SettingsManager;

public class Ludomania extends Application {
    private SceneManager sceneManager;
    private SettingsManager settingsManager;
    private AudioManager audioManager;
    private LanguageManager languageManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.settingsManager = new SettingsManager();
        this.audioManager = new AudioManager(settingsManager.volumeProperty().doubleValue());
        this.languageManager = new LanguageManager(settingsManager.currentLocaleProperty().get());

        this.sceneManager = new SceneManager(primaryStage, settingsManager, audioManager, languageManager);

        sceneManager.switchToMainMenu();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}