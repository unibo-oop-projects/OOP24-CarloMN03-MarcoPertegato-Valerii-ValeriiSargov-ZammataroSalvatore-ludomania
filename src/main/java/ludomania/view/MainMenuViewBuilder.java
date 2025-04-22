package ludomania.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.handler.MainMenuHandler;

public final class MainMenuViewBuilder implements ViewBuilder {
    private static final String GAME_1_IMAGE_ID = "game1";
    private static final String GAME_2_IMAGE_ID = "game2";
    private static final String GAME_3_IMAGE_ID = "game3";
    private static final String SHOPPING_CART_IMAGE_ID = "shoppingCart";
    private static final int TOP_RIGHT_BOTTOM_LEFT = 20;
    private static final int DEFAULT_SPACING = 10;

    private final List<VBox> gameFrames = new ArrayList<>();
    private final MainMenuHandler eventHandler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;

    public MainMenuViewBuilder(final MainMenuHandler eventHandler,
            final LanguageManager languageManager,
            final ImageProvider imageProvider) {
        this.eventHandler = eventHandler;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
    }

    @Override
    public Parent build() {
        final VBox root = new VBox(
                createStyledLabel("Ludomania", "heading-label"),
                createGameSelector(),
                createOptions());

        root.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/css/mainMenu.css")).toExternalForm());
        root.setAlignment(Pos.BASELINE_CENTER);
        return root;
    }

    private Node createGameSelector() {
        HBox gameContainer = createHorizontalContainer(
                gameBox(GAME_1_IMAGE_ID, 1),
                gameBox(GAME_2_IMAGE_ID, 2),
                gameBox(GAME_3_IMAGE_ID, 3));
        gameContainer.setPadding(new Insets(TOP_RIGHT_BOTTOM_LEFT));
        return gameContainer;
    }

    private Node gameBox(String imageId, int gameId) {
        ImageView imageView = createImageView(imageId);
        VBox gameFrame = createGameFrame(imageView);

        gameFrame.setOnMouseClicked(event -> {
            highLightSelectedGame(gameFrame);
            eventHandler.selectGame(gameId);
        });

        gameFrames.add(gameFrame);
        return gameFrame;
    }

    private ImageView createImageView(String imageId) {
        ImageView imageView = new ImageView(imageProvider.getImage(imageId));
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        return imageView;
    }

    private VBox createGameFrame(Node content) {
        VBox frame = new VBox(content);
        frame.setAlignment(Pos.BASELINE_CENTER);
        frame.setMinSize(0, 0);
        frame.getStyleClass().add("game-border");

        if (content instanceof ImageView) {
            VBox.setVgrow(content, Priority.ALWAYS);
            ((ImageView) content).fitWidthProperty().bind(frame.widthProperty().subtract(8));
        }

        HBox.setHgrow(frame, Priority.ALWAYS);
        return frame;
    }

    private void highLightSelectedGame(VBox selectedGameFrame) {
        gameFrames.forEach(frame -> frame.getStyleClass().remove("selected-game"));
        selectedGameFrame.getStyleClass().add("selected-game");
    }

    private Node createOptions() {
        return createHorizontalContainer(
                userLoginSection(),
                mainGameButton(),
                shopSign());
    }

    private HBox createHorizontalContainer(Node... children) {
        HBox container = new HBox(DEFAULT_SPACING, children);
        container.setAlignment(Pos.CENTER);
        return container;
    }

    private VBox createVerticalContainer(Node... children) {
        VBox container = new VBox(DEFAULT_SPACING, children);
        container.setAlignment(Pos.BASELINE_CENTER);
        return container;
    }

    private Node userLoginSection() {
        return createVerticalContainer(
                createButton("new_user", null),
                createButton("log_in", null));
    }

    private Node mainGameButton() {
        return createVerticalContainer(
                createButton("start", evt -> eventHandler.handleStartGame()),
                createButton("settings", evt -> eventHandler.handleSettings()),
                createButton("exit", evt -> eventHandler.handleExit()));
    }

    private Button createButton(String textKey, Consumer<javafx.scene.input.MouseEvent> handler) {
        Button button = new Button();
        setText(button, textKey);
        if (handler != null) {
            button.setOnMouseClicked(handler::accept);
        }
        return button;
    }

    private Node shopSign() {
        ImageView imageView = createImageView(SHOPPING_CART_IMAGE_ID);
        Label shopLabel = createLabel("shop");

        VBox shopFrame = createGameFrame(shopLabel, imageView);
        shopFrame.setOnMouseClicked(event -> eventHandler.handleCosmetics());
        gameFrames.add(shopFrame);

        return shopFrame;
    }

    private VBox createGameFrame(Node... children) {
        VBox frame = new VBox(children);
        frame.setAlignment(Pos.BASELINE_CENTER);
        return frame;
    }

    private Label createLabel(String textKey) {
        Label label = new Label();
        setText(label, textKey);
        return label;
    }

    private Node createStyledLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }

    private void setText(Labeled target, String property) {
        target.textProperty().bind(languageManager.bind(property));
    }
}