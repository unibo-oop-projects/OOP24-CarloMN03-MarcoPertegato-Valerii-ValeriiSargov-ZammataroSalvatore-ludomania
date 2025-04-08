package ludomania.view;

import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Builder;
import ludomania.core.LanguageManager;
import ludomania.handler.MainMenuHandler;

public class MainMenuViewBuilder implements Builder<Parent> {
    private final MainMenuHandler eventHandler;
    private final LanguageManager languageManager;

    public MainMenuViewBuilder(final MainMenuHandler eventHandler,final  LanguageManager languageManager) {
        this.eventHandler = eventHandler;
        this.languageManager = languageManager;
    }

    @Override
    public Parent build() {
        final VBox results = new VBox(headingLabel("Ludomania"), createGameSelector(), createOptions());
        results.getStylesheets()
                .add(Objects.requireNonNull(this.getClass().getResource("/css/mainMenu.css")).toExternalForm());
        results.setAlignment(Pos.BASELINE_CENTER);
        results.getStyleClass().add("background");
        return results;
    }

    private Node createGameSelector() {
        final HBox results = new HBox(6, gameBox(), gameBox(), gameBox());
        results.setPadding(new Insets(20));
        return results;
    }

    private Node gameBox() {
        final Rectangle game = new Rectangle(150, 150);
        return new HBox(2, game);
    }

    private Node createOptions() {
        final Node userLogin = userLoginSection();
        final Node gameButtons = mainGameButton();
        final Node shopSign = shopSign();
        final HBox results = new HBox(10, userLogin, gameButtons, shopSign);

        results.setAlignment(Pos.CENTER);
        return results;
    }

    private Node userLoginSection() {
        final Button newUserButton = new Button();
        final Button logInButton = new Button();
        setText(newUserButton, "new_user");
        setText(logInButton, "log_in");
        //logInButton.setOnMouseClicked(evt -> );
        final VBox result = new VBox(10, newUserButton, logInButton);
        result.setAlignment(Pos.BASELINE_LEFT);
        return result;
    }

    private Node mainGameButton() {
        final Button startButton = new Button(), settingsButton = new Button(),
                exitButton = new Button();
        setText(exitButton, "exit");
        setText(startButton, "start");
        setText(settingsButton, "settings");
        startButton.setOnMouseClicked(evt -> eventHandler.handleStartGame());
        settingsButton.setOnMouseClicked(evt -> eventHandler.handleSettings());
        exitButton.setOnMouseClicked(evt -> eventHandler.handleExit());
        final VBox result = new VBox(10, startButton, settingsButton, exitButton);
        result.setAlignment(Pos.BASELINE_CENTER);
        return result;
    }

    private Node shopSign() {
        final Label shopSign = new Label();
        final Label shoppingCartImage = new Label();
        final VBox result = new VBox(10, shopSign, shoppingCartImage);
        result.setAlignment(Pos.BASELINE_RIGHT);
        return result;
    }

    private Node headingLabel(final String contents) {
        return styledLabel(contents, "heading-label");
    }

    private Node styledLabel(final String contents, final String styleClass) {
        final Label label = new Label(contents);
        label.getStyleClass().add(styleClass);
        return label;
    }

    void setText(final Labeled target,final  String property) {
        target.textProperty().bind(languageManager.bind(property));
    }
}