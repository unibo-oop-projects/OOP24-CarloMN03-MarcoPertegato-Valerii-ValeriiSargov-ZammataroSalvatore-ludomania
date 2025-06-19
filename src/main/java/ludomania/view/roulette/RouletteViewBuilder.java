package ludomania.view.roulette;

import java.io.File;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import ludomania.controller.roulette.RouletteController;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.view.ViewBuilder;

public class RouletteViewBuilder implements ViewBuilder {
    private static final String SEP = File.separator;
    private static final String FXML_FILE_NAME = "RouletteViewTemplate.fxml";
    private static final String FXML_STYLE_FILE_NAME = "RouletteView.css";
    private static final String FXML_FILE_PATH = 
    new File(System.getProperty("user.dir")).getPath() + SEP + "src" + SEP + "main" + SEP + "java" + SEP + "ludomania" + SEP + "view"+ SEP + "roulette" + SEP + "resources" + SEP + FXML_FILE_NAME;
    private static final String FXML_STYLE_FILE_PATH = 
    new File(System.getProperty("user.dir")).getPath() + SEP + "src" + SEP + "main" + SEP + "java" + SEP + "ludomania" + SEP + "view"+ SEP + "roulette" + SEP + "resources" + SEP + FXML_STYLE_FILE_NAME;
    
    private final RouletteController controller;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;
    
    public RouletteViewBuilder(
        final RouletteController controller,
        final LanguageManager languageManager,
        final ImageProvider imageProvider
    ) {
        this.controller = controller;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
    }
    
    @Override
    public Parent build() {
        BorderPane root;

        try {
            File dbDirectory = new File(FXML_FILE_PATH);

            FXMLLoader loader = new FXMLLoader();
            loader.setController(controller);
            loader.setLocation(dbDirectory.toURI().toURL());

            root = loader.<BorderPane>load();
            root.getStylesheets().add(FXML_STYLE_FILE_PATH);
        } catch(Exception e) {            
            System.err.println(e.getMessage());
            root = new BorderPane();
            root.setCenter(new Label("ERRORE DURANTE IL CARICAMENTO DELLA VIEW"));
        }

        return root;
    }
    
    // Method that sets the label text according to the language selected in the settings
    private void setText(final Labeled target, final String property) {
        target.textProperty().bind(languageManager.bind(property));
    }
}
