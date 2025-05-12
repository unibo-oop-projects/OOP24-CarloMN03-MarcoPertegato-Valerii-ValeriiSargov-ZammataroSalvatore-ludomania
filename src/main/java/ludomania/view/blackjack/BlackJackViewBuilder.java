package ludomania.view.blackjack;

import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ludomania.handler.BlackJackHandler;

/**
 * Costruisce la schermata di gioco del Blackjack.
 */
public final class BlackJackViewBuilder {

    private static final int DEFAULT_SPACING = 10;
    private static final int PLAYER_SPACING = 80;
    private static final int PADDING = 10;

    private final BlackJackHandler eventHandler;
    
    public BlackJackViewBuilder(final BlackJackHandler handler) {
        this.eventHandler = handler;
    }

    public Parent build() {
        final BorderPane root = new BorderPane();
        root.getStylesheets().add(
            Objects.requireNonNull(getClass().getResource("/css/blackjack.css")).toExternalForm()
        );

        root.setTop(createTopBar());
        root.setCenter(createTable());
        root.setBottom(createControls());

        return root;
    }

    private Node createTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(PADDING));
        topBar.setAlignment(Pos.CENTER);
        topBar.setSpacing(10);

        Label title = new Label("BLACKJACK");
        title.getStyleClass().add("blackjack-title");
        title.setTextFill(Color.color(1, 0, 0));

        Button exitBtn = new Button("EXIT");
        exitBtn.setOnAction(e -> eventHandler.handleExit());

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        topBar.getChildren().addAll(title, spacer1, spacer2, exitBtn);
        return topBar;
    }

    private Node createTable() {
        VBox table = new VBox(DEFAULT_SPACING);
        table.setAlignment(Pos.TOP_CENTER);

        // Dealer Card Area
        HBox dealerBox = new HBox();
        dealerBox.setAlignment(Pos.CENTER);
        dealerBox.getChildren().add(createDealerArea());
        VBox.setMargin(dealerBox, new Insets(0, 0, 80, 0));
        table.getChildren().add(dealerBox);

        // Player Areas
        HBox players = new HBox(PLAYER_SPACING);
        players.setAlignment(Pos.BOTTOM_CENTER);
        
        players.getChildren().addAll(
            createPlayerArea("player1", 0),
            createPlayerArea("player2", 0),
            createPlayerArea("player3", 0),
            createPlayerArea("player4", 0)
        );

        table.getChildren().add(players);
        return table;
    }

    private Node createControls() {
        HBox controlPanel = new HBox(DEFAULT_SPACING);
        controlPanel.setAlignment(Pos.CENTER);
        controlPanel.setPadding(new Insets(PADDING));

        HBox chips = new HBox(DEFAULT_SPACING);
        chips.setAlignment(Pos.CENTER);
        for (int value : new int[]{1, 5, 25, 50, 100, 500}) {
            chips.getChildren().add(createChip(value));
        }

        Button dealBtn = new Button("CARTE");
        dealBtn.setOnAction(e -> eventHandler.handleDrawCard("222"));

        Button cancelBtn = new Button("ANNULLA");
        cancelBtn.setOnAction(e -> eventHandler.handleCancel("222"));

        controlPanel.getChildren().addAll(chips, dealBtn, cancelBtn);
        return controlPanel;
    }

    private VBox createPlayerArea(String name, int money) {
        VBox playerBox = new VBox(DEFAULT_SPACING);
        playerBox.setAlignment(Pos.CENTER);

        StackPane avatar = new StackPane();
        avatar.getChildren().add(new Label("○")); // Placeholder avatar

        VBox cards = new VBox();
        cards.getChildren().add(createCardPlaceholder());

        Label info = new Label(name + "\n" + money + " $");
        info.setAlignment(Pos.CENTER);
        info.setStyle("-fx-font-weight: bold");

        playerBox.getChildren().addAll(cards, avatar, info);
        return playerBox;
    }

    private VBox createDealerArea() {
        VBox dealerBox = new VBox(DEFAULT_SPACING);
        dealerBox.setAlignment(Pos.CENTER);
        dealerBox.setPadding( new Insets(PADDING));

        Label info = new Label("BJ DEALER\n");
        info.setAlignment(Pos.CENTER);
        info.setStyle("-fx-font-weight: bold");

        Label payout = new Label("IL BJ PAGA 3 A 2");
        payout.getStyleClass().add("payout-label");

        VBox cards = new VBox();
        cards.getChildren().add(createCardPlaceholder());

        dealerBox.getChildren().addAll(info, cards, payout);
        return dealerBox;
    }

    private Node createCardPlaceholder() {
        Region card = new Region();
        card.setPrefSize(40, 60);
        card.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        return card;
    }

    private Node createChip(int value) {
        Label chip = new Label(String.valueOf(value));
        chip.getStyleClass().add("chip");
        chip.setOnMouseClicked(e -> eventHandler.handleBet(value, "222"));
        return chip;
    }
}

