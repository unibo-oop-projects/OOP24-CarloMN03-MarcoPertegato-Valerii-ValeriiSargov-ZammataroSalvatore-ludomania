package ludomania.view;

import java.util.Objects;
import java.util.function.Consumer;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import ludomania.handler.MainMenuHandler;

public final class LoginViewBuilder implements ViewBuilder {

    private final MainMenuHandler eventHandler;

    public LoginViewBuilder(final MainMenuHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public Parent build() {
        final VBox root = new VBox(
                createButton("exit", evt -> eventHandler.handleExit()));

        root.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/css/mainMenu.css")).toExternalForm());
        root.setAlignment(Pos.BASELINE_CENTER);
        return root;
    }
    
    private Button createButton(final String textKey, final Consumer<MouseEvent> handler) {
        final Button button = new Button();
        
        if (handler != null) {
            button.setOnMouseClicked(handler::accept);
        }
        return button;
    }


}
