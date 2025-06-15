package ludomania.view.roulette;

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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:///C:/data/hello-world.fxml"));
        VBox vbox = loader.<VBox>load();

        return vbox;
    }
    
    // Method that sets the label text according to the language selected in the settings
    private void setText(final Labeled target, final String property) {
        target.textProperty().bind(languageManager.bind(property));
    }
}
