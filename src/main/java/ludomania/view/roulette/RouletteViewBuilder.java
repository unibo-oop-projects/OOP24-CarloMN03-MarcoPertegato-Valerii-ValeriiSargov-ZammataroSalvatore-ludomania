package ludomania.view.roulette;

import java.io.File;
import java.io.IOException;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import ludomania.controller.roulette.RouletteController;
import ludomania.view.ViewBuilder;

public class RouletteViewBuilder implements ViewBuilder {
    private final String SEP = File.separator;
    private final String FXML_FILE_NAME = "RouletteViewTemplate.fxml";
    private final String FXML_STYLE_FILE_NAME = "RouletteView.css";
    private final String FXML_FILE_PATH = new File(System.getProperty("user.dir")).getPath() +
            SEP + "src" +
            SEP + "main" +
            SEP + "java" +
            SEP + "ludomania" +
            SEP + "view"+
            SEP + "roulette" +
            SEP + "resources" +
            SEP + FXML_FILE_NAME;

    private final String FXML_STYLE_FILE_PATH = new File(System.getProperty("user.dir")).getPath() +
            SEP + "src" +
            SEP + "main" +
            SEP + "java" +
            SEP + "ludomania" +
            SEP + "view"+
            SEP + "roulette" +
            SEP + "resources" +
            SEP + FXML_STYLE_FILE_NAME;

    private final RouletteController controller;

    public RouletteViewBuilder(
            final RouletteController controller
    ) {
        this.controller = controller;
    }

    @Override
    public Parent build() {
        BorderPane root;

        try {
            File dbDirectory = new File(this.FXML_FILE_PATH);

            FXMLLoader loader = new FXMLLoader();
            loader.setController(controller);
            loader.setLocation(dbDirectory.toURI().toURL());

            root = loader.<BorderPane>load();
            root.getStylesheets().add(this.FXML_STYLE_FILE_PATH);
        } catch (final IOException e) {
            System.err.println(e.getMessage());
            root = new BorderPane();
            root.setCenter(new Label("ERRORE DURANTE IL CARICAMENTO DELLA VIEW"));
        }

        return root;
    }
}