package ludomania.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ludomania.core.api.LanguageManager;
import ludomania.handler.MainMenuHandler;

public final class MainMenuViewBuilder implements ViewBuilder {
    private static final String GAME_1_IMAGE_PATH = "images/game1.png";
    private static final String GAME_2_IMAGE_PATH = "images/game2.png";
    private static final String GAME_3_IMAGE_PATH = "images/game3.png";
    private static final int TOP_RIGHT_BOTTOM_LEFT = 20;
    private final List<VBox> gameFrames = new ArrayList<>();
    private final MainMenuHandler eventHandler;
    private final LanguageManager languageManager;

    public MainMenuViewBuilder(final MainMenuHandler eventHandler, final LanguageManager languageManager) {
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
        final HBox results = new HBox(6, gameBox(GAME_1_IMAGE_PATH, 1), gameBox(GAME_2_IMAGE_PATH, 2),
                gameBox(GAME_3_IMAGE_PATH, 3));
        results.setPadding(new Insets(TOP_RIGHT_BOTTOM_LEFT));
        results.setAlignment(Pos.CENTER);
        results.setPrefHeight(Region.USE_COMPUTED_SIZE);
        HBox.setHgrow(results, Priority.ALWAYS);
        return results;
    }

    private Node gameBox(String imagePath, int gameId) {
        final ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        final VBox gameFrame = new VBox(imageView);
        gameFrame.setAlignment(Pos.BASELINE_CENTER);
        gameFrame.setMinSize(0, 0);
        VBox.setVgrow(imageView, Priority.ALWAYS);
        HBox.setHgrow(gameFrame, Priority.ALWAYS);
        imageView.fitWidthProperty().bind(gameFrame.widthProperty().subtract(8));
        gameFrame.getStyleClass().add("game-border");
        gameFrame.setOnMouseClicked(event -> {
            highLightSelectedGame(gameFrame);
            eventHandler.selectGame(gameId);
        });
        gameFrames.add(gameFrame);
        return gameFrame;
    }

    private void highLightSelectedGame(VBox selectedGameFrame) {
        for (VBox gameFrame : gameFrames) {
            gameFrame.getStyleClass().remove("selected-game");
        }
        selectedGameFrame.getStyleClass().add("selected-game");
    }

    private Node createOptions() {
        final Node userLogin = userLoginSection();
        final Node gameButtons = mainGameButton();
        final Node shopSign = shopSign();
        HBox.setHgrow(userLogin, Priority.ALWAYS);
        HBox.setHgrow(gameButtons, Priority.ALWAYS);
        HBox.setHgrow(shopSign, Priority.ALWAYS);
        final HBox results = new HBox(10, userLogin, gameButtons, shopSign);
        results.setAlignment(Pos.CENTER);
        return results;
    }

    private Node userLoginSection() {
        final Button newUserButton = new Button();
        final Button logInButton = new Button();
        setText(newUserButton, "new_user");
        setText(logInButton, "log_in");
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
        // final ImageView imageView = new ImageView(new
        // Image("images/shopping-cart.png"));
        final Label shopSign = new Label();
        setText(shopSign, "shop");
        // imageView.setPreserveRatio(true);
        // imageView.setCache(true);
        final VBox gameFrame = new VBox(shopSign);
        gameFrame.setAlignment(Pos.BASELINE_CENTER);
        gameFrame.setOnMouseClicked(event -> {
            eventHandler.handleShop();
        });
        gameFrames.add(gameFrame);
        return gameFrame;
    }

    private Node headingLabel(final String contents) {
        return styledLabel(contents, "heading-label");
    }

    private Node styledLabel(final String contents, final String styleClass) {
        final Label label = new Label(contents);
        label.getStyleClass().add(styleClass);
        return label;
    }

    void setText(final Labeled target, final String property) {
        target.textProperty().bind(languageManager.bind(property));
    }
}