package ludomania.view.roulette;

import java.io.File;
import java.io.IOException;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import ludomania.controller.RouletteController;
import ludomania.view.ViewBuilder;

/**
 * Builds and manages the JavaFX view for the Roulette game.
 * Provides UI elements for chip selection, card visualization, bet zones,
 * and balance and bet tracking.
 */
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

    /**
     * Creates the Roulette view builder.
     * @param controller the controller that will be bound to the UI.
     */
    public RouletteViewBuilder(final RouletteController controller) {
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     * @return the roulette game scene root.
     */
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
