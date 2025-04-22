package ludomania.view;

import java.util.List;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CardTheme;
import ludomania.cosmetics.FicheTheme;
import ludomania.handler.CosmeticMenuHandler;

public class CosmeticMenuViewBuilder implements ViewBuilder {
    private static final int FICHE_VALUE = 50;
    private final CosmeticMenuHandler eventHandler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;

    public CosmeticMenuViewBuilder(final CosmeticMenuHandler eventHandler, final LanguageManager languageManager,
            final ImageProvider imageProvider) {
        this.eventHandler = eventHandler;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
    }

    @Override
    public Parent build() {
        VBox result = new VBox(10, titleSection(), backgroundSelectionSection(), cardSelectionSection(),
                ficheSelectionSection(), goBackSection());
        result.setAlignment(Pos.CENTER);
        result.setBackground(new Background(new BackgroundFill(
                imageProvider.getBackgroundColor(),
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        return result;
    }

    private Node titleSection() {
        Label result = new Label(languageManager.getString("shop"));
        return result;
    }

    private Node backgroundSelectionSection() {
        HBox result = new HBox(
                selectionBackgroundGroup(eventHandler.getBackgroundThemes()));
        result.setAlignment(Pos.BASELINE_CENTER);
        return result;
    }

    private Node cardSelectionSection() {
        HBox result = new HBox(
                selectionCardGroup(eventHandler.getCardThemes()));
        result.setAlignment(Pos.BASELINE_CENTER);
        result.setMaxHeight(100);
        return result;
    }

    private Node ficheSelectionSection() {
        HBox result = new HBox(
                selectionFicheGroup(eventHandler.getFicheThemes()));
        result.setAlignment(Pos.BASELINE_CENTER);
        return result;
    }

    private Node selectionFicheGroup(List<FicheTheme> cosmetics) {
        ToggleGroup selectableCosmetics = new ToggleGroup();
        HBox result = new HBox();
        for (FicheTheme theme : cosmetics) {
            ToggleButton cosmeticButton = new ToggleButton();
            String svg = theme.getCosmetic(FICHE_VALUE);
            cosmeticButton.setUserData(theme.getTheme());
            cosmeticButton.setGraphic(imageProvider.svgHelperMethod(svg));
            cosmeticButton.setToggleGroup(selectableCosmetics);
            result.getChildren().add(cosmeticButton);
        }
        selectableCosmetics.selectedToggleProperty().addListener((obs, oldSelectedToggle, newSelectedToggle) -> {
            if (newSelectedToggle != null) {
                eventHandler.handleFicheChange(newSelectedToggle.getUserData().toString());
            }
        });
        return result;
    }

    private Node selectionCardGroup(List<CardTheme> cosmetics) {
        ToggleGroup selectableCosmetics = new ToggleGroup();
        HBox result = new HBox();
        for (CardTheme theme : cosmetics) {
            ToggleButton cosmeticButton = new ToggleButton();
            String svg = theme.getCosmetic(Rank.ACE, Suit.CLUBS);
            cosmeticButton.setUserData(theme.getTheme());
            cosmeticButton.setGraphic(imageProvider.svgHelperMethod(svg));
            cosmeticButton.setToggleGroup(selectableCosmetics);
            result.getChildren().add(cosmeticButton);
        }
        selectableCosmetics.selectedToggleProperty().addListener((obs, oldSelectedToggle, newSelectedToggle) -> {
            if (newSelectedToggle != null) {
                System.out.println(newSelectedToggle.getUserData());
            }
        });
        return result;
    }

    private Node selectionBackgroundGroup(List<BackgroundTheme> cosmetics) {
        ToggleGroup selectableCosmetics = new ToggleGroup();
        HBox result = new HBox();
        for (BackgroundTheme theme : cosmetics) {
            ToggleButton cosmeticButton = new ToggleButton();
            String color = theme.getCosmetic();
            cosmeticButton.setUserData(theme.getTheme());
            cosmeticButton.setGraphic(new Rectangle(100, 100, Color.web(color)));
            cosmeticButton.setToggleGroup(selectableCosmetics);
            result.getChildren().add(cosmeticButton);
        }
        selectableCosmetics.selectedToggleProperty().addListener((obs, oldSelectedToggle, newSelectedToggle) -> {
            if (newSelectedToggle != null) {
                System.out.println(newSelectedToggle.getUserData());
            }
        });
        return result;
    }

    private Node goBackSection() {
        Button result = new Button(languageManager.getString("go_back_button"));
        return result;
    }
}
