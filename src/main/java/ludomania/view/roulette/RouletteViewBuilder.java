package ludomania.view.roulette;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.handler.BlackJackHandler;
import ludomania.view.ViewBuilder;

public class RouletteViewBuilder implements ViewBuilder {
    private static final String SEP = File.separator;
    private static final String FXML_FILE_NAME = "RouletteViewTemplate.fxml";
    private static final String FXML_FILE_PATH = 
    new File(System.getProperty("user.dir")).getPath() + SEP + "src" + SEP + "main" + SEP + "java" + SEP + "view" + SEP + FXML_FILE_NAME;
    
    private final BlackJackHandler handler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;
    
    public RouletteViewBuilder(
    final BlackJackHandler handler,
    final LanguageManager languageManager,
    final ImageProvider imageProvider
    ) {
        this.handler = handler;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
    }
    
    @Override
    public Parent build() {
        BorderPane root = new BorderPane();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URI(FXML_FILE_PATH).toURL());
            VBox vbox = loader.<VBox>load();

            root.setTop(vbox);
            
        } catch(Exception e) {
            
        }
        return root;
    }
    
    // Method that sets the label text according to the language selected in the settings
    private void setText(final Labeled target, final String property) {
        target.textProperty().bind(languageManager.bind(property));
    }
}
