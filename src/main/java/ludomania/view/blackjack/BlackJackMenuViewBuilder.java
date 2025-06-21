package ludomania.view.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Hand;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.handler.BlackJackHandler;
import ludomania.view.ViewBuilder;

/**
 * Class that creates and manages the blackjack screen.
 */
public class BlackJackMenuViewBuilder implements ViewBuilder {

    private static final int CARD_BOX_PADDING = 40;
    private static final int RIGHT_PADDING = 20;
    private static final int BOTTOM_AREA_SPACING = 5;
    private static final int ACTION_BUTTON_SPACING = 15;
    private static final int TOP_RIGHT_BOTTOM_LEFT = 40;
    private static final int PREF_SIZE = 50;
    private static final int DIALOG_SIZE = 450;
    private static final int MAX_SIZE = 60;
    private static final int PREF_HEIGHT = 300;
    private static final int TWENTYFIVE = 25;
    private static final int FIVEHUNDRED = 500;
    private static final String WHITE = "white";
    private static final String STYLE = "-fx-font-size:18px";

    private final BlackJackHandler handler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;

    private Label statusLabel;
    private Label statusLabelPuntate;
    private Label dealerLabel;
    private Label playerLabel;
    private Label winLabel;

    private HBox dealerCards;

    /**
     * Constructs a new {@code BlackJackMenuViewBuilder} with the required dependencies.
     *
     * @param eventHandler     the handler that manages game logic and events
     * @param languageManager  the manager used for internationalized text
     * @param imageProvider    the provider for retrieving image resources
     */
    public BlackJackMenuViewBuilder(final BlackJackHandler eventHandler, 
            final LanguageManager languageManager,
            final ImageProvider imageProvider) {
        this.handler = eventHandler;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
    }

    /**
     * Builds the complete Blackjack game view layout.
     *
     * @return the root {@link Parent} node representing the game view
     */
    @Override
    public final Parent build() {
        final BorderPane root = new BorderPane();

        // Top bar
        final HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);
        topBar.setAlignment(Pos.CENTER);
        final Label title = new Label("BLACKJACK");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        title.setTextFill(javafx.scene.paint.Paint.valueOf(WHITE));
        final Button rulesBtn = new Button(languageManager.getString("rule_button"));
        rulesBtn.setOnAction(e -> showRulesDialog());
        final Button exitButton = new Button();
        setText(exitButton, "exit");
        exitButton.setOnAction(e -> showExitConfirmation());
        final Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(title, spacer, rulesBtn, exitButton);
        root.setTop(topBar);

        // Center cards
        final VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        final HBox cardBox = new HBox(CARD_BOX_PADDING);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setPadding(new Insets(TOP_RIGHT_BOTTOM_LEFT));
        updateCardDisplay(cardBox);
        winLabel = new Label();
        winLabel.setStyle("-fx-font-size: 18px;");
        centerBox.getChildren().addAll(winLabel, cardBox);
        root.setCenter(centerBox);

        // Bottom section
        final VBox bottomArea = new VBox(BOTTOM_AREA_SPACING);
        bottomArea.setAlignment(Pos.CENTER);
        bottomArea.setPadding(new Insets(10));

        // Fiches
        final HBox ficheBar = new HBox(2);
        ficheBar.setAlignment(Pos.CENTER);
        statusLabelPuntate = new Label();
        final IntegerProperty puntata = new SimpleIntegerProperty(0);

        getFiches().entrySet().stream()
        .sorted(Comparator.comparingInt(Map.Entry::getValue))
        .forEach(entry -> {
            final Region ficheImg = entry.getKey();
            final Integer ficheValue = entry.getValue();

            ficheImg.setPrefSize(PREF_SIZE, PREF_SIZE);

            ficheImg.setOnMouseClicked(e -> {
                puntata.set(puntata.get() + ficheValue);
                updateStatusPuntateLabel(statusLabelPuntate, puntata);
            });
            if (puntata.get() != 0) {
                handler.handlePlaceBet(ficheValue);
            }

            ficheBar.getChildren().add(ficheImg);
        });
        ficheBar.getChildren().add(statusLabelPuntate);

        // Action
        final HBox actionButtons = new HBox(ACTION_BUTTON_SPACING);
        actionButtons.setAlignment(Pos.CENTER);

        final Button startBtn = new Button();
        setText(startBtn, "start");

        final Button cancelBtn = new Button();
        setText(cancelBtn, "cancel");

        final Runnable[] resetButtons = new Runnable[1];

        // Method to reset buttons to initial state
        resetButtons[0] = () -> {
            setText(startBtn, "start");
            setText(cancelBtn, "cancel");

            // Reset initial handlers
            startBtn.setOnAction(e -> {
                if (handler.getPlayerBalance() == 0 || handler.getPlayerBalance() < puntata.get()) {
                    final Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid bet");
                    alert.setHeaderText("Insufficient balance");
                    alert.setContentText("You do not have enough balance to place the bet.");
                    alert.showAndWait();
                    return;
                } else if (puntata.get() == 0) {
                    final Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid bet");
                    alert.setHeaderText("No bets placed");
                    alert.showAndWait();
                    return;
                } else {
                    handler.handlePlaceBet(puntata.get());
                    handler.handleStartGame();
                    updateCardDisplay(cardBox);
                    updateViewAfterGame();
                }

                // Go to the game phase (card/stand)
                setText(startBtn, "card");
                setText(cancelBtn, "stand");

                startBtn.setOnAction(ev -> {
                    handler.handleHit();
                    updateCardDisplay(cardBox);
                    updateStatusLabel(statusLabel);

                    if (handler.isGameOver()) {
                        updateStatusCardDealerFinalLabel(dealerLabel);
                        updateDealerBox(dealerCards, true);
                        updateViewAfterGame();

                        resetButtons[0].run();
                    }
                });

                cancelBtn.setOnAction(ev2 -> {
                    handler.handleStand(); 
                    updateCardDisplay(cardBox);
                    updateStatusCardDealerFinalLabel(dealerLabel);
                    updateDealerBox(dealerCards, true);
                    updateViewAfterGame();
                    resetButtons[0].run();
                });
            });

            cancelBtn.setOnAction(e -> {
                puntata.set(0);
                updateStatusPuntateLabel(statusLabelPuntate, new SimpleIntegerProperty(0));
                updateViewAfterGame();
            });
        };

        // Initialize the initial handlers
        resetButtons[0].run();

        actionButtons.getChildren().addAll(startBtn, cancelBtn);

        // Status bottom right
        final HBox statusBar = new HBox();
        statusBar.setPadding(new Insets(BOTTOM_AREA_SPACING, RIGHT_PADDING, 0, 0));
        statusBar.setAlignment(Pos.CENTER_RIGHT);
        statusLabel = new Label();
        updateStatusLabel(statusLabel);
        statusBar.getChildren().add(statusLabel);

        bottomArea.getChildren().addAll(ficheBar, actionButtons, statusBar);
        root.setBottom(bottomArea);

        return root;
    }

    /**
     * Displays a confirmation dialog to the user when they attempt to exit the current game/screen
     * and return to the main menu. It prompts the user with a message indicating that all unsaved
     * progress will be lost and asks for confirmation to proceed. If the user confirms ("Yes"),
     * it triggers the handler to navigate back to the main menu.
     */
    private void showExitConfirmation() {
        final Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle(languageManager.getString("confirm_exit"));
            confirmDialog.setHeaderText(languageManager.getString("back_to_menu"));
            confirmDialog.setContentText(languageManager.getString("all_progress_lost"));
            final ButtonType buttonYes = new ButtonType(languageManager.getString("yes"));
            final ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmDialog.getButtonTypes().setAll(buttonYes, buttonNo);
            final Optional<ButtonType> result = confirmDialog.showAndWait();
            if (result.isPresent() && result.get().equals(buttonYes)) {
                handler.handleExitToMenu();
            }
    }

    /**
     * Displays a modal dialog window containing the rules of the Blackjack game.
     * The rules text is retrieved from the `languageManager` and displayed within a scrollable
     * area to accommodate longer descriptions. The dialog also includes an "OK" button
     * which, when clicked, closes the dialog window. The dialog is application-modal,
     * meaning it blocks interaction with other application windows until it is closed.
     */
    private void showRulesDialog() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("BlackJack");

        final Label rulesLabel = new Label(languageManager.getString("bj_rules_text"));
        rulesLabel.setWrapText(true);

        final ScrollPane scrollPane = new ScrollPane(rulesLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(PREF_HEIGHT);
        scrollPane.setStyle("-fx-background: white;");

        final Button okBtn = new Button(languageManager.getString("exit"));
        okBtn.setOnAction(e -> dialog.close());

        final VBox dialogVBox = new VBox(10, scrollPane, okBtn);
        dialogVBox.setPadding(new Insets(RIGHT_PADDING));
        dialogVBox.setAlignment(Pos.CENTER);

        final Scene dialogScene = new Scene(dialogVBox, DIALOG_SIZE, DIALOG_SIZE);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    /**
     * Updates and reconstructs the card layout for both dealer and player.
     *
     * @param cardBox the container where cards should be displayed
     */
    private void updateCardDisplay(final HBox cardBox) {
        cardBox.getChildren().clear();
        cardBox.setAlignment(Pos.CENTER);

        // Vertical space between dealer and player
        final VBox mainBox = new VBox(RIGHT_PADDING); 
        mainBox.setAlignment(Pos.CENTER);

        // Dealer
        final VBox dealerBox = new VBox(BOTTOM_AREA_SPACING);
        dealerBox.setAlignment(Pos.TOP_CENTER);
        dealerLabel = new Label("Dealer  ");
        dealerLabel.setStyle(STYLE);
        dealerLabel.setTextFill(javafx.scene.paint.Paint.valueOf(WHITE));
        dealerCards = new HBox(); // Carte più vicine tra loro
        dealerCards.setAlignment(Pos.CENTER);
        updateDealerBox(dealerCards, false);
        dealerBox.getChildren().addAll(dealerLabel, dealerCards);

        // Player
        final VBox playerBox = new VBox(BOTTOM_AREA_SPACING);
        playerBox.setAlignment(Pos.BOTTOM_CENTER);
        playerLabel = new Label("Player  ");
        playerLabel.setStyle(STYLE);
        playerLabel.setTextFill(javafx.scene.paint.Paint.valueOf(WHITE));
        final HBox playerCards = new HBox(2); 
        playerCards.setAlignment(Pos.CENTER);
        updatePlayerBox(playerCards);
        playerBox.getChildren().addAll(playerLabel, playerCards);

        // Add everything to the vertical container
        mainBox.getChildren().addAll(dealerBox, playerBox);

        // Inserts mainBox into the original horizontal container
        cardBox.getChildren().add(mainBox);
    }

    /**
     * Updates game-related status labels after the end of a round,
     * including player balance and win messages.
     */
    private void updateViewAfterGame() {
        updateStatusLabel(statusLabel);
        updateWinLabel(winLabel);
    }

    /**
     * Sets the outcome message in the {@code winLabel} based on game results.
     *
     * @param label the label where the result message will be shown
     */
    private void updateWinLabel(final Label label) {
        label.setText(handler.getGameOutcomeMessage());
        label.setStyle("-fx-font-size:24px");
        label.setTextFill(javafx.scene.paint.Paint.valueOf(WHITE));
    }

    /**
     * Updates the player info label showing the current user name and balance.
     *
     * @param label the label to be updated with player status
     */
    private void updateStatusLabel(final Label label) {
        label.setText("User: " + handler.getPlayerName()
            + " | Money: $" + String.format("%.2f", handler.getPlayerBalance()));
        label.setStyle(STYLE);
        label.setTextFill(javafx.scene.paint.Paint.valueOf(WHITE));
    }

    /**
     * Binds the bet label to reflect changes in the current bet amount.
     *
     * @param label   the label to bind the bet text to
     * @param puntata the property holding the current bet value
     */
    private void updateStatusPuntateLabel(final Label label, final IntegerProperty puntata) {
        label.textProperty().bind(Bindings.createStringBinding(
            () -> "Bet: " + String.format("%.2f", (double) puntata.get()),
            puntata
        ));
        label.setStyle(STYLE);
        label.setTextFill(javafx.scene.paint.Paint.valueOf(WHITE));
    }

    /**
     * Updates the dealer label to show the partial total of visible cards,
     * hiding the value of the first (face-down) card.
     *
     * @param label the label to update
     */
    private void updateStatusCardDealerPreLabel(final Label label) {
        final List<Card> dealerCardsList = handler.getDealerHand().getCards();
        if (dealerCardsList.isEmpty()) {
            label.setText("Dealer");
            return;
        }
        final int total = handler.getDealerTotal();
        final int hiddenValue = dealerCardsList.get(0).getRank().getValue();
        label.setText("Dealer  " + (total - hiddenValue));
    }

    /**
     * Updates the dealer label to show the full hand total after all cards are revealed.
     *
     * @param label the dealer label to update
     */
    private void updateStatusCardDealerFinalLabel(final Label label) {
        label.setText("Dealer  " + String.format("%d", handler.getDealerTotal()));
    }

    /**
     * Updates the player label to show the current total of player's hand.
     *
     * @param label the player label to update
     */
    private void updateStatusCardPlayerLabel(final Label label) {
        label.setText("Player  " + String.format("%d", handler.getPlayerTotal()));
    }

    /**
     * Updates the dealer's card container with appropriate images.
     * If {@code showFullHand} is false, the first card is shown face-down.
     *
     * @param dealerCards   the container for dealer cards
     * @param showFullHand  true to reveal all cards, false to hide the first
     */
    private void updateDealerBox(final HBox dealerCards, final boolean showFullHand) {
        dealerCards.getChildren().clear();
        final List<Region> dealerImages = showFullHand ? getDealerHandImages() : getDealerHandFirstImages();

        for (final Region img : dealerImages) {
            img.setMaxSize(MAX_SIZE, 100);
            dealerCards.getChildren().add(img);
        }

        if (showFullHand) {
            updateStatusCardDealerFinalLabel(dealerLabel);
        } else {
            updateStatusCardDealerPreLabel(dealerLabel);
        }
    }

    /**
     * Updates the player's card container with the current hand.
     *
     * @param playerCards the container for the player's cards
     */
    private void updatePlayerBox(final HBox playerCards) {
        for (final Region img : getPlayerHandImages()) {
            img.setMaxSize(MAX_SIZE, 100);
            playerCards.getChildren().add(img);
            updateStatusCardPlayerLabel(playerLabel);
        }
    }

    /**
     * Returns a list of card images representing the player's hand.
     *
     * @return a list of {@link Region} nodes representing player cards
     */
    public List<Region> getPlayerHandImages() {
        return getImagesFromHand(handler.getPlayerHand());
    }

    /**
     * Returns a list of card images representing the dealer's full hand.
     *
     * @return a list of {@link Region} nodes representing dealer cards
     */
    public List<Region> getDealerHandImages() {
        return getImagesFromHand(handler.getDealerHand());
    }

    /**
     * Returns a list of card images representing the dealer's hand,
     * with the first card shown as face-down (back).
     *
     * @return a list of {@link Region} nodes representing dealer cards with the first hidden
     */
    public List<Region> getDealerHandFirstImages() {
        final List<Region> images = new ArrayList<>();
        final List<Card> dealerHand = handler.getDealerHand().getCards();

        for (int i = 0; i < dealerHand.size(); i++) {
            if (i == 0) {
                // First card: back
                images.add(createBackCard());
            } else {
                // Other cards: normal
                images.add(imageProvider.getSVGCard(dealerHand.get(i).getRank(), 
                        dealerHand.get(i).getSuit()));
            }
        }
        return images;
    }

    /**
     * Converts a {@link Hand} object into a list of image regions for display.
     *
     * @param hand the card hand to convert
     * @return a list of {@link Region} nodes representing card images
     */
    private List<Region> getImagesFromHand(final Hand hand) {
        if (hand == null || hand.getCards() == null) {
            return Collections.emptyList();
        }

        final List<Region> images = new ArrayList<>();
        for (final Card card : hand.getCards()) {
            final Region img = imageProvider.getSVGCard(card.getRank(), card.getSuit());
            if (img != null) {
                images.add(img);
            }
        }
        return images;
    }

    /**
     * Creates and returns a map of betting chip images mapped to their values.
     *
     * @return a map of {@link Region} representing fiches and their corresponding values
     */
    private Map<Region, Integer> getFiches() {
        final Map<Region, Integer> fiches = new HashMap<>();
        final int[] ficheValues = {1, BOTTOM_AREA_SPACING, 10, TWENTYFIVE, 100, FIVEHUNDRED};
        for (final int value : ficheValues) {
            final Region img = imageProvider.getSVGFiche(value);
            if (img != null) {
                fiches.put(img, value);
            }
        }
        return fiches;
    }

    /**
     * Binds the text of a labeled UI component to a key from the language manager.
     *
     * @param target   the target UI component to bind text to
     * @param property the key to retrieve localized text
     */
    private void setText(final Labeled target, final String property) {
        target.textProperty().bind(languageManager.bind(property));
    }

    /**
     * Creates a visual representation of a card back to display face-down cards.
     *
     * @return a {@link Region} styled as the back of a card
     */
    private Region createBackCard() {
        final Region back = new Region();
        back.setPrefSize(MAX_SIZE, 100);
        back.setStyle("-fx-background-color: navy; -fx-border-color: white; -fx-border-width: 2;");
        return back;
    }
}
