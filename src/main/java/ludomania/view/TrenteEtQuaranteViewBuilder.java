package ludomania.view;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.cosmetics.FicheValue;
import ludomania.handler.TrenteEtQuaranteHandler;

/**
 * Builds and manages the JavaFX view for the Trente et Quarante game.
 * Provides UI elements for chip selection, card visualization, bet zones,
 * and balance and bet tracking.
 */
public final class TrenteEtQuaranteViewBuilder implements ViewBuilder {

    private static final String BORDER_WIDTH = "-fx-border-width: 1; ";
    private static final String WHITE_BORDER = "-fx-border-color: white; ";
    private static final String TRANSPARENT_BACKGROUND = "-fx-background-color: transparent; ";
    private static final int ROW_LABEL_WIDTH = 60;
    private static final int BET_LABEL_WIDTH = 120;
    private static final int BET_LABEL_HEIGHT = 50;
    private static final int CARD_BOX_HEIGHT = 160;
    private static final int BET_LIST_WIDTH = 200;
    private static final int SCROLL_BAR_HEIGHT = 300;
    private static final int DIALOG_SIZE = 450;
    private static final int TITLE_SIZE = 36;
    private static final int FONT_SIZE = 16;
    private static final int OFFSET = 10;
    private final TrenteEtQuaranteHandler eventHandler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;
    private final ToggleGroup ficheToggleGroup;
    private final Label usernameLabel;
    private final Label balanceLabel;
    private final Label noirTotalLabel;
    private final Label rougeTotalLabel;
    private final List<Label> betZonesLabels;
    private final VBox betList;
    private HBox noirCardsBox;
    private HBox rougeCardsBox;

    /**
     * Constructs the Trente et Quarante game view builder.
     *
     * @param eventHandler    the event handler for game actions
     * @param languageManager the language manager for localization
     * @param imageProvider   the provider for chip images
     */
    public TrenteEtQuaranteViewBuilder(final TrenteEtQuaranteHandler eventHandler,
            final LanguageManager languageManager,
            final ImageProvider imageProvider) {
        this.eventHandler = eventHandler;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
        this.ficheToggleGroup = new ToggleGroup();
        this.usernameLabel = new Label();
        this.balanceLabel = new Label();
        this.noirTotalLabel = new Label();
        this.rougeTotalLabel = new Label();
        this.betList = new VBox(OFFSET);
        this.betZonesLabels = new ArrayList<>();
    }

    @Override
    public Parent build() {
        final BorderPane root = new BorderPane();
        root.setPadding(new Insets(OFFSET));

        root.setTop(createTopBar());

        root.setBottom(createBottomBar());

        root.setLeft(createBetList());

        root.setRight(createDoneButton());

        root.setCenter(createCenter());

        eventHandler.handleStartGame();

        return root;
    }

    private Node createTopBar() {
        final HBox topBar = new HBox(OFFSET);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(OFFSET));

        usernameLabel.setText("Test Player");
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setFont(Font.font(FONT_SIZE));

        final Label title = new Label("TrenteEtQuarante");
        title.setFont(Font.font("Serif", TITLE_SIZE));
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font-weight: bold;");

        final Button homeBtn = new Button(languageManager.getString("go_back_button"));
        homeBtn.setOnMouseClicked(e -> eventHandler.handleReturnHome());

        final Button rulesBtn = new Button(languageManager.getString("rule_button"));
        rulesBtn.setOnAction(e -> showRulesDialog());

        final Region leftSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);

        final Region rightSpacer = new Region();
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        topBar.getChildren().addAll(usernameLabel, leftSpacer, title, rightSpacer, rulesBtn, homeBtn);

        return topBar;
    }

    private void showRulesDialog() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Trente et Quarante");

        final Label rulesLabel = new Label(languageManager.getString("tq_rules_text"));
        rulesLabel.setWrapText(true);

        final ScrollPane scrollPane = new ScrollPane(rulesLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(SCROLL_BAR_HEIGHT);
        scrollPane.setStyle("-fx-background: white;");

        final Button okBtn = new Button(languageManager.getString("exit"));
        okBtn.setOnAction(e -> dialog.close());

        final VBox dialogVBox = new VBox(OFFSET, scrollPane, okBtn);
        dialogVBox.setPadding(new Insets(OFFSET * 2));
        dialogVBox.setAlignment(Pos.CENTER);

        final Scene dialogScene = new Scene(dialogVBox, DIALOG_SIZE, DIALOG_SIZE);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    private Node createBottomBar() {
        final HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(OFFSET));
        bottomBar.setSpacing(OFFSET);

        final Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        balanceLabel.setText(languageManager.getString("money") + " : xxx €");
        balanceLabel.setTextFill(Color.WHITE);
        balanceLabel.setFont(Font.font(FONT_SIZE));

        bottomBar.getChildren().addAll(createFicheBar(), spacer, balanceLabel);

        return bottomBar;
    }

    private Node createFicheBar() {
        final HBox ficheBar = new HBox(OFFSET);
        ficheBar.setAlignment(Pos.CENTER_LEFT);
        ficheBar.setPadding(new Insets(OFFSET));

        Arrays.stream(FicheValue.values())
        .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
        .forEach(ficheValue -> {
            final ToggleButton button = new ToggleButton();
            button.setGraphic(imageProvider.getSVGFiche(ficheValue.getValue()));
            button.setStyle(
                TRANSPARENT_BACKGROUND
                + "-fx-background-insets: 0; "
                + "-fx-padding: 0;"
            );
            button.setUserData(ficheValue);
            button.setToggleGroup(ficheToggleGroup);
            ficheBar.getChildren().add(button);
        });

        ficheToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (oldToggle != null) {
                ((ToggleButton) oldToggle).setStyle(
                    TRANSPARENT_BACKGROUND
                    + "-fx-background-insets: 0; "
                    + "-fx-padding: 0;"
                );
            }
            if (newToggle != null) {
                ((ToggleButton) newToggle).setStyle(
                    TRANSPARENT_BACKGROUND
                    + "-fx-background-insets: 0; " 
                    + "-fx-padding: 0; "
                    + WHITE_BORDER
                    + "-fx-border-width: 2;"
                );
            }
        });
        return ficheBar;
    }

    private Node createBetList() {
        betList.setPadding(new Insets(OFFSET));
        betList.setStyle("-fx-background-color: white;");
        betList.setPrefWidth(BET_LIST_WIDTH);

        final ScrollPane scrollPane = new ScrollPane(betList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(SCROLL_BAR_HEIGHT);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: white");

        return scrollPane;
    }

    private Node createDoneButton() {
        final VBox rightBox = new VBox(OFFSET);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        rightBox.setPadding(new Insets(OFFSET));

        final Button doneButton = new Button(languageManager.getString("done"));
        doneButton.setOnAction(e -> {
            eventHandler.handleNewRound();
        });

        rightBox.getChildren().add(doneButton);

        return rightBox;
    }

    private Node createCenter() {
        final VBox centerBox = new VBox(OFFSET);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(OFFSET));
        VBox.setVgrow(centerBox, Priority.ALWAYS);

        centerBox.getChildren().addAll(createCardRows(), createBetZones());

        return centerBox;
    }

    private Node createCardRows() {
        final VBox cardRows = new VBox(OFFSET);
        cardRows.setPadding(new Insets(OFFSET));

        this.noirCardsBox = createCardBox();
        final ScrollPane noirScrollPane = createCardScrollPane(noirCardsBox);
        final HBox noirRow = createSingleRow("Noir", noirScrollPane, noirTotalLabel);

        this.rougeCardsBox = createCardBox();
        final ScrollPane rougeScrollPane = createCardScrollPane(rougeCardsBox);
        final HBox rougeRow = createSingleRow("Rouge", rougeScrollPane, rougeTotalLabel);

        cardRows.getChildren().addAll(noirRow, rougeRow);

        return cardRows;
    }

    private HBox createCardBox() {
        final HBox cardBox = new HBox(OFFSET / 2);
        cardBox.setMinHeight(CARD_BOX_HEIGHT);
        cardBox.setStyle(
            WHITE_BORDER
            + BORDER_WIDTH
        );
        cardBox.setAlignment(Pos.CENTER_LEFT);
        return cardBox;
    }

    private ScrollPane createCardScrollPane(final HBox cardBox) {
        final ScrollPane scrollPane = new ScrollPane(cardBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(CARD_BOX_HEIGHT);
        scrollPane.setStyle(
            TRANSPARENT_BACKGROUND
            + "-fx-border-color: transparent;"
        );
        return scrollPane;
    }

    private HBox createSingleRow(final String labelText, final ScrollPane scrollPane, final Label totalLabel) {
        final Label rowLabel = new Label(labelText);
        rowLabel.setTextFill(Color.WHITE);
        rowLabel.setFont(Font.font(FONT_SIZE));
        rowLabel.setMinWidth(ROW_LABEL_WIDTH);

        totalLabel.setText("Total: 0");
        totalLabel.setTextFill(Color.WHITE);
        totalLabel.setFont(Font.font(FONT_SIZE));

        final HBox row = new HBox(OFFSET, rowLabel, scrollPane, totalLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);
        return row;
    }

    private Node createBetZones() {
        final HBox zonesBox = new HBox();
        zonesBox.setSpacing(0);
        zonesBox.setPadding(new Insets(OFFSET / 2));
        zonesBox.setAlignment(Pos.CENTER);

        for (final String name : List.of("Noir", "Rouge", "Couleur", "Enverse")) {
            final Label zone = new Label(name);
            zone.setTextFill(Color.WHITE);
            zone.setAlignment(Pos.CENTER);
            zone.setPrefHeight(BET_LABEL_HEIGHT);
            zone.setPrefWidth(BET_LABEL_WIDTH);
            zone.setStyle(
                WHITE_BORDER
                + BORDER_WIDTH
                + TRANSPARENT_BACKGROUND
            );
            zone.setOnMouseClicked(e -> {
                eventHandler.handleBetPlacement(name);
            });

            betZonesLabels.add(zone);
            zonesBox.getChildren().add(zone);
        }

        ficheToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
        final boolean enabled = newToggle != null;
        for (final Label zoneLabel : betZonesLabels) {
                zoneLabel.setDisable(!enabled);
                if (enabled) {
                    zoneLabel.setStyle(
                        WHITE_BORDER
                        + BORDER_WIDTH
                        + TRANSPARENT_BACKGROUND
                    );
                } else {
                    zoneLabel.setStyle(
                        "-fx-border-color: gray; "
                        + BORDER_WIDTH
                        + "-fx-background-color: #333333;"
                    );
                }
            }
        });

        if (getSelectedFiche() == null) {
            for (final Label zoneLabel : betZonesLabels) {
                zoneLabel.setDisable(true);
                zoneLabel.setStyle(
                    "-fx-border-color: gray; "
                    + BORDER_WIDTH
                    + "-fx-background-color: #333333;"
                );
            }
        }

        return zonesBox;
    }

    /**
     * Updates the displayed total value for the Noir row.
     *
     * @param total the card total to display
     */
    public void setNoirTotal(final int total) {
        noirTotalLabel.setText("Tot: " + total);
    }

    /**
     * Updates the displayed total value for the Rouge row.
     *
     * @param total the card total to display
     */
    public void setRougeTotal(final int total) {
        rougeTotalLabel.setText("Tot: " + total);
    }

    /**
     * Sets the displayed username at the top of the screen.
     *
     * @param username the name of the user
     */
    public void setUsername(final String username) {
        usernameLabel.setText(username);
    }

    /**
     * Sets the displayed balance of the user.
     *
     * @param balance the user's balance in chips
     */
    public void setBalance(final double balance) {
        balanceLabel.setText(languageManager.getString("money") + " " + balance + " $");
    }

    /**
     * Adds a textual description of a placed bet to the list.
     *
     * @param betDescription a string describing the bet
     */
    public void addBet(final String betDescription) {
        final Label betLabel = new Label(betDescription);
        betLabel.setWrapText(true);
        betList.getChildren().add(betLabel);
    }

    /**
     * Clears all displayed bets from the list.
     */
    public void clearBets() {
        betList.getChildren().clear();
    }

    /**
     * Returns the currently selected chip value, if any.
     *
     * @return the selected chip value or null if none is selected
     */
    public FicheValue getSelectedFiche() {
        final ToggleButton selectedToggle = (ToggleButton) ficheToggleGroup.getSelectedToggle();
        if (selectedToggle != null) {
            return (FicheValue) selectedToggle.getUserData();
        }
        return null;
    }

    /**
     * Adds a card to the Noir row using its rank and suit.
     *
     * @param rank the rank of the card (e.g., ACE, KING, TEN)
     * @param suit the suit of the card (e.g., HEARTS, SPADES)
     */
    public void addCardToNoir(final Rank rank, final Suit suit) {
        noirCardsBox.getChildren().add(imageProvider.getSVGCard(rank, suit));
    }

    /**
     * Adds a card to the Rouge row using its rank and suit.
     *
     * @param rank the rank of the card (e.g., ACE, KING, TEN)
     * @param suit the suit of the card (e.g., HEARTS, SPADES)
     */
    public void addCardToRouge(final Rank rank, final Suit suit) {
        noirCardsBox.getChildren().add(imageProvider.getSVGCard(rank, suit));
    }

    /**
     * Removes all cards from the Noir row.
     */
    public void clearNoirCards() {
        noirCardsBox.getChildren().clear();
    }

    /**
     * Removes all cards from the Rouge row.
     */
    public void clearRougeCards() {
        rougeCardsBox.getChildren().clear();
    }
}
