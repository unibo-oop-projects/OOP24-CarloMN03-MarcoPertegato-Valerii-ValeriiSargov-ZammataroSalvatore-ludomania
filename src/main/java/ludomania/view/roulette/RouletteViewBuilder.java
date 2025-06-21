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
    private final String sep = File.separator;
    private final String fxmlFileName = "RouletteViewTemplate.fxml";
    private final String fxmlStyleFileName = "RouletteView.css";
    private final String fxmlFilePath = new File(System.getProperty("user.dir")).getPath()
            + sep + "src"
            + sep + "main"
            + sep + "java"
            + sep + "ludomania"
            + sep + "view"
            + sep + "roulette"
            + sep + "resources"
            + sep + fxmlFileName;

    private final String fxmlStyleFilePath = new File(System.getProperty("user.dir")).getPath()
            + sep + "src"
            + sep + "main"
            + sep + "java"
            + sep + "ludomania"
            + sep + "view"
            + sep + "roulette"
            + sep + "resources"
            + sep + fxmlStyleFileName;

    private final RouletteController controller;

    public RouletteViewBuilder(final RouletteController controller) {
        this.controller = controller;
    }

    @Override
    public Parent build() {
        BorderPane root;

        try {
            final File fxml = new File(this.fxmlFilePath);

            final FXMLLoader loader = new FXMLLoader();
            loader.setController(controller);
            loader.setLocation(fxml.toURI().toURL());

            root = loader.<BorderPane>load();
            root.getStylesheets().add(this.fxmlStyleFilePath);
        } catch (final IOException e) {
            root = new BorderPane();
            root.setCenter(new Label("ERRORE DURANTE IL CARICAMENTO DELLA VIEW"));
        }

        return root;
    }
}
