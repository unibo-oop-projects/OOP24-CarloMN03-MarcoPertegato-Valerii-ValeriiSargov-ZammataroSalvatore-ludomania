package ludomania.view;

import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Builder;
import ludomania.core.LanguageManager;
import ludomania.handler.MainMenuHandler;
import ludomania.model.MainMenuModel;

public class MainMenuViewBuilder implements Builder<Region> {
    private final MainMenuHandler eventHandler;

    public MainMenuViewBuilder(MainMenuModel model, MainMenuHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public Region build() {
        VBox results = new VBox(headingLabel("Ludomania"), createGameSelector(), createOptions());
        results.getStylesheets()
                .add(Objects.requireNonNull(this.getClass().getResource("/css/mainMenu.css")).toExternalForm());
        results.setAlignment(Pos.BASELINE_CENTER);
        results.getStyleClass().add("background");
        return results;
    }

    private Node createGameSelector() {
        HBox results = new HBox(6, gameBox(), gameBox(), gameBox());
        results.setPadding(new Insets(20));
        return results;
    }

    private Node gameBox() {
        Rectangle game = new Rectangle(150, 150);
        return new HBox(2, game);
    }

    private Node createOptions() {
        Node userLogin = userLoginSection();
        Node gameButtons = mainGameButton();
        Node shopSign = shopSign();
        HBox results = new HBox(10, userLogin, gameButtons, shopSign);

        results.setAlignment(Pos.CENTER);
        return results;
    }

    private Node userLoginSection() {
        Button newUserButton = new Button();
        Button logInButton = new Button();
        setText(newUserButton, "new_user");
        setText(logInButton, "log_in");
        logInButton.setOnMouseClicked(evt -> System.out.println("ciao!"));
        VBox result = new VBox(10, newUserButton, logInButton);
        result.setAlignment(Pos.BASELINE_LEFT);
        return result;
    }

    private Node mainGameButton() {
        Button startButton = new Button(), settingsButton = new Button(),
                exitButton = new Button();
        setText(exitButton, "exit");
        setText(startButton, "start");
        setText(settingsButton, "settings");
        startButton.setOnMouseClicked(evt -> eventHandler.handleStartGame());
        settingsButton.setOnMouseClicked(evt -> eventHandler.handleSettings());
        exitButton.setOnMouseClicked(evt -> eventHandler.handleExit());
        VBox result = new VBox(10, startButton, settingsButton, exitButton);
        result.setAlignment(Pos.BASELINE_CENTER);
        return result;
    }

    private Node shopSign() {
        Label shopSign = new Label();
        Label shoppingCartImage = new Label();
        VBox result = new VBox(10, shopSign, shoppingCartImage);
        result.setAlignment(Pos.BASELINE_RIGHT);
        return result;
    }

    private Node headingLabel(String contents) {
        return styledLabel(contents, "heading-label");
    }

    private Node styledLabel(String contents, String styleClass) {
        Label label = new Label(contents);
        label.getStyleClass().add(styleClass);
        return label;
    }

    void setText(Labeled target, String property) {
        target.textProperty().bind(LanguageManager.bind(property));
    }
}