package ludomania.core;

import javafx.application.Application;
import javafx.stage.Stage;
import ludomania.settings.SettingsManager;

public class Ludomania extends Application {
    private SceneManager sceneManager;
    private SettingsManager settingsManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.settingsManager = new SettingsManager();
        this.sceneManager = new SceneManager(primaryStage, settingsManager);
        sceneManager.switchToMainMenu();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}