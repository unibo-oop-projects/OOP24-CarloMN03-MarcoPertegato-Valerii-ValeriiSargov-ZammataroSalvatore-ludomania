package ludomania.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.cosmetics.CosmeticTheme;
import ludomania.handler.CosmeticMenuHandler;

/**
 * A builder class for creating the cosmetic customization menu view.
 * <p>
 * Constructs a JavaFX layout that allows the user to select and change
 * card themes, background themes, and fiche themes.
 * <p>
 * The view is localized using the provided {@link LanguageManager} and
 * graphical elements are loaded via the {@link ImageProvider}.
 *
 * Implements the {@link ViewBuilder} interface.
 */

public final class CosmeticMenuViewBuilder implements ViewBuilder {
    private static final int FICHE_VALUE = 50;
    private final CosmeticMenuHandler eventHandler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;

    /**
     * Constructs a CosmeticMenuViewBuilder with the necessary dependencies.
     *
     * @param eventHandler    the handler for user interactions within the cosmetic
     *                        menu
     * @param languageManager the manager responsible for providing localized text
     * @param imageProvider   the provider for supplying image and SVG resources
     */
    public CosmeticMenuViewBuilder(final CosmeticMenuHandler eventHandler,
            final LanguageManager languageManager,
            final ImageProvider imageProvider) {
        this.eventHandler = eventHandler;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
    }

    @Override
    public Parent build() {
        final VBox result = new VBox(10,
                titleSection(),
                backgroundSelectionSection(),
                cardSelectionSection(),
                ficheSelectionSection(),
                goBackSection());
        result.setAlignment(Pos.CENTER);
        return result;
    }

    private Node titleSection() {
        return new Label(languageManager.getString("shop"));
    }

    private Node backgroundSelectionSection() {
        return createSelectionSection(
                eventHandler.getBackgroundThemes(),
                eventHandler.getSelectedBackgroundTheme(),
                theme -> new Rectangle(100, 100, Color.web(theme.getCosmetic())),
                eventHandler::handleBackgroundChange);
    }

    private Node cardSelectionSection() {
        return createSelectionSection(
                eventHandler.getCardThemes(),
                eventHandler.getSelectedCardTheme(),
                theme -> imageProvider.svgHelperMethod(theme.getCosmetic(Rank.ACE, Suit.CLUBS)),
                eventHandler::handleCardChange);
    }

    private Node ficheSelectionSection() {
        return createSelectionSection(
                eventHandler.getFicheThemes(),
                eventHandler.getSelectedFicheTheme(),
                theme -> imageProvider.svgHelperMethod(theme.getCosmetic(FICHE_VALUE)),
                eventHandler::handleFicheChange);
    }

    private <T> Node createSelectionSection(final List<T> items,
            final CosmeticTheme selectedTheme,
            final Function<T, Node> graphicCreator,
            final Consumer<T> changeHandler) {
        final ToggleGroup toggleGroup = new ToggleGroup();
        final HBox container = new HBox();
        container.setAlignment(Pos.BASELINE_CENTER);

        items.forEach(item -> {
            final ToggleButton button = createThemeToggleButton(
                    item,
                    selectedTheme,
                    graphicCreator,
                    toggleGroup);
            container.getChildren().add(button);
        });

        toggleGroup.selectedToggleProperty().addListener((obs, old, newToggle) -> {
            if (newToggle != null) {
                changeHandler.accept((T) newToggle.getUserData());
            }
        });

        return container;
    }

    private <T> ToggleButton createThemeToggleButton(final T item,
            final CosmeticTheme selectedTheme,
            final Function<T, Node> graphicCreator,
            final ToggleGroup toggleGroup) {
        final ToggleButton button = new ToggleButton();

        if (isSelectedTheme(item, selectedTheme)) {
            button.setSelected(true);
        }

        button.setUserData(item);
        button.setGraphic(graphicCreator.apply(item));
        button.setToggleGroup(toggleGroup);

        return button;
    }

    private <T> boolean isSelectedTheme(final T item, final CosmeticTheme selectedTheme) {
        try {
            return item.getClass().getMethod("getTheme").invoke(item).equals(selectedTheme);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return false;
        }
    }

    private Node goBackSection() {
        final Button button = new Button(languageManager.getString("go_back_button"));
        button.setOnMouseClicked(e -> eventHandler.handleBack());
        return button;
    }
}