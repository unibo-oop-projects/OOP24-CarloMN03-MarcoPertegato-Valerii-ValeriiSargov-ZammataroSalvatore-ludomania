package ludomania;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ludomania.controller.MainMenuController;

public class Ludomania extends Application {
    private final static Integer defaultWidth = 800;
    private final static Integer defaultHeight = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new MainMenuController().getView(), defaultWidth, defaultHeight));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}