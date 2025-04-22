package ludomania.view;

import java.util.List;
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

public class CosmeticMenuViewBuilder implements ViewBuilder {
    private static final int FICHE_VALUE = 50;
    private final CosmeticMenuHandler eventHandler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;

    public CosmeticMenuViewBuilder(final CosmeticMenuHandler eventHandler,
            final LanguageManager languageManager,
            final ImageProvider imageProvider) {
        this.eventHandler = eventHandler;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
    }

    @Override
    public Parent build() {
        VBox result = new VBox(10,
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

    private <T> Node createSelectionSection(List<T> items,
            CosmeticTheme selectedTheme,
            Function<T, Node> graphicCreator,
            java.util.function.Consumer<T> changeHandler) {
        ToggleGroup toggleGroup = new ToggleGroup();
        HBox container = new HBox();
        container.setAlignment(Pos.BASELINE_CENTER);

        items.forEach(item -> {
            ToggleButton button = createThemeToggleButton(
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

    private <T> ToggleButton createThemeToggleButton(T item,
            CosmeticTheme selectedTheme,
            Function<T, Node> graphicCreator,
            ToggleGroup toggleGroup) {
        ToggleButton button = new ToggleButton();

        if (isSelectedTheme(item, selectedTheme)) {
            button.setSelected(true);
        }

        button.setUserData(item);
        button.setGraphic(graphicCreator.apply(item));
        button.setToggleGroup(toggleGroup);

        return button;
    }

    private <T> boolean isSelectedTheme(T item, CosmeticTheme selectedTheme) {
        try {
            // Using reflection to call getTheme() on different theme types
            return item.getClass().getMethod("getTheme").invoke(item).equals(selectedTheme);
        } catch (Exception e) {
            return false;
        }
    }

    private Node goBackSection() {
        Button button = new Button(languageManager.getString("go_back_button"));
        button.setOnMouseClicked(e -> eventHandler.handleBack());
        return button;
    }
}