package ludomania.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.handler.MainMenuHandler;

/**
 * A builder class for creating the main menu view of the application.
 * <p>
 * Constructs a JavaFX layout including game selection frames, action buttons
 * (start, settings, exit), and access to the cosmetic cosmetic menu.
 * <p>
 * The view is fully localized using the provided {@link LanguageManager}
 * and uses images supplied by the {@link ImageProvider}.
 * 
 * Implements the {@link ViewBuilder} interface.
 */

public final class MainMenuViewBuilder implements ViewBuilder {
    private static final String GAME_1_IMAGE_ID = "game1";
    private static final String GAME_2_IMAGE_ID = "game2";
    private static final String GAME_3_IMAGE_ID = "game3";
    private static final String COSMETIC_MENU_IMAGE_ID = "cosmeticIcon";
    private static final int TOP_RIGHT_BOTTOM_LEFT = 20;
    private static final int DEFAULT_SPACING = 10;

    private final List<VBox> gameFrames = new ArrayList<>();
    private final MainMenuHandler eventHandler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;

    /**
     * Constructs a MainMenuViewBuilder with the required dependencies.
     *
     * @param eventHandler    the handler for user interactions in the main menu
     * @param languageManager the manager responsible for localizing UI elements
     * @param imageProvider   the provider for supplying image resources
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "References to languageManager and imageProvider are shared intentionally as they are immutable or managed externally."
    )
    public MainMenuViewBuilder(final MainMenuHandler eventHandler,
            final LanguageManager languageManager,
            final ImageProvider imageProvider) {
        this.eventHandler = Objects.requireNonNull(eventHandler);
        this.languageManager = Objects.requireNonNull(languageManager);
        this.imageProvider = Objects.requireNonNull(imageProvider);
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
        final HBox gameContainer = createHorizontalContainer(
                gameBox(GAME_1_IMAGE_ID, 1),
                gameBox(GAME_2_IMAGE_ID, 2),
                gameBox(GAME_3_IMAGE_ID, 3));
        gameContainer.setPadding(new Insets(TOP_RIGHT_BOTTOM_LEFT));
        return gameContainer;
    }

    private Node gameBox(final String imageId, final int gameId) {
        final ImageView imageView = createImageView(imageId);
        final VBox gameFrame = createGameFrame(imageView);

        gameFrame.setOnMouseClicked(event -> {
            highLightSelectedGame(gameFrame);
            eventHandler.selectGame(gameId);
        });

        gameFrames.add(gameFrame);
        return gameFrame;
    }

    private ImageView createImageView(final String imageId) {
        final ImageView imageView = new ImageView(imageProvider.getImage(imageId));
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        return imageView;
    }

    private VBox createGameFrame(final Node content) {
        final VBox frame = new VBox(content);
        frame.setAlignment(Pos.BASELINE_CENTER);
        frame.setMinSize(0, 0);
        frame.getStyleClass().add("game-border");

        if (content instanceof ImageView imageView) {
            VBox.setVgrow(content, Priority.ALWAYS);
            imageView.fitWidthProperty().bind(frame.widthProperty().subtract(8));
        }

        HBox.setHgrow(frame, Priority.ALWAYS);
        return frame;
    }

    private void highLightSelectedGame(final VBox selectedGameFrame) {
        gameFrames.forEach(frame -> frame.getStyleClass().remove("selected-game"));
        selectedGameFrame.getStyleClass().add("selected-game");
    }

    private Node createOptions() {
        return createHorizontalContainer(
                mainGameButton(),
                cosmeticMenuSign());
    }

    private HBox createHorizontalContainer(final Node... children) {
        final HBox container = new HBox(DEFAULT_SPACING, children);
        container.setAlignment(Pos.CENTER);
        return container;
    }

    private VBox createVerticalContainer(final Node... children) {
        final VBox container = new VBox(DEFAULT_SPACING, children);
        container.setAlignment(Pos.BASELINE_CENTER);
        return container;
    }

    private Node mainGameButton() {
        return createVerticalContainer(
                createButton("start", evt -> eventHandler.handleStartGame()),
                createButton("settings", evt -> eventHandler.handleSettings()),
                createButton("exit", evt -> eventHandler.handleExit()));
    }

    private Button createButton(final String textKey, final Consumer<MouseEvent> handler) {
        final Button button = new Button();
        setText(button, textKey);
        if (handler != null) {
            button.setOnMouseClicked(handler::accept);
        }
        return button;
    }

    private Node cosmeticMenuSign() {
        final ImageView imageView = createImageView(COSMETIC_MENU_IMAGE_ID);
        final Label menuLabel = createLabel("cosmetic_menu");
        final VBox menuFrame = createGameFrame(menuLabel, imageView);
        menuFrame.setOnMouseClicked(event -> eventHandler.handleCosmetics());
        gameFrames.add(menuFrame);
        return menuFrame;
    }

    private VBox createGameFrame(final Node... children) {
        final VBox frame = new VBox(children);
        frame.setAlignment(Pos.BASELINE_CENTER);
        return frame;
    }

    private Label createLabel(final String textKey) {
        final Label label = new Label();
        setText(label, textKey);
        return label;
    }

    private Node createStyledLabel(final String text, final String styleClass) {
        final Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }

    private void setText(final Labeled target, final String property) {
        target.textProperty().bind(languageManager.bind(property));
    }
}
