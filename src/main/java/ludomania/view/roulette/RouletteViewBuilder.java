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
    private static final String SEP = File.separator;
    private static final String FXML_FILE_NAME = "RouletteViewTemplate.fxml";
    private static final String FXML_STYLE_FILE_NAME = "RouletteView.css";
    private static final String FXML_FILE_PATH = new File(System.getProperty("user.dir")).getPath()
            + SEP + "src"
            + SEP + "main"
            + SEP + "java"
            + SEP + "ludomania"
            + SEP + "view"
            + SEP + "roulette"
            + SEP + "resources"
            + SEP + FXML_FILE_NAME;

    private static final String FXML_STYLE_FILE_PATH = new File(System.getProperty("user.dir")).getPath()
            + SEP + "src"
            + SEP + "main"
            + SEP + "java"
            + SEP + "ludomania"
            + SEP + "view"
            + SEP + "roulette"
            + SEP + "resources"
            + SEP + FXML_STYLE_FILE_NAME;

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
            final File fxml = new File(this.FXML_FILE_PATH);

            final FXMLLoader loader = new FXMLLoader();
            loader.setController(controller);
            loader.setLocation(fxml.toURI().toURL());

            root = loader.<BorderPane>load();
            root.getStylesheets().add(this.FXML_STYLE_FILE_PATH);
        } catch (final IOException e) {
            root = new BorderPane();
            root.setCenter(new Label("ERRORE DURANTE IL CARICAMENTO DELLA VIEW"));
        }

        return root;
    }
}
