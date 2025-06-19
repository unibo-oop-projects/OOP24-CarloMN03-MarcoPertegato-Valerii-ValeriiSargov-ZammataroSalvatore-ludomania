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

    private final BlackJackHandler handler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;

    private Label statusLabel;
    private Label statusLabelPuntate;
    private Label dealerLabel;
    private Label playerLabel;
    private Label winLabel;

    private VBox dealerBox;
    private VBox playerBox;

    private HBox dealerCards;
    private HBox playerCards;

    // Blackjack View Builder
    public BlackJackMenuViewBuilder(final BlackJackHandler eventHandler, 
            final LanguageManager languageManager,
            final ImageProvider imageProvider) {
        this.handler = eventHandler;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
    }

    @Override
    public Parent build() {
        BorderPane root = new BorderPane();

        // Top bar
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);
        topBar.setAlignment(Pos.CENTER);
        Label title = new Label("BLACKJACK");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        final Button rulesBtn = new Button(languageManager.getString("rule_button"));
        rulesBtn.setOnAction(e -> showRulesDialog());
        final Button exitButton = new Button();
        setText(exitButton, "exit");
        exitButton.setOnAction(e -> showExitConfirmation());
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(title, spacer, rulesBtn, exitButton);
        root.setTop(topBar);

        // Center cards
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        HBox cardBox = new HBox(40);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setPadding(new Insets(20));
        updateCardDisplay(cardBox);
        winLabel = new Label();
        winLabel.setStyle("-fx-font-size: 18px;");
        centerBox.getChildren().addAll(winLabel, cardBox);
        root.setCenter(centerBox);

        // Bottom section
        VBox bottomArea = new VBox(5);
        bottomArea.setAlignment(Pos.CENTER);
        bottomArea.setPadding(new Insets(10));

        // Fiches
        HBox ficheBar = new HBox(2);
        ficheBar.setAlignment(Pos.CENTER);
        statusLabelPuntate = new Label();
        IntegerProperty puntata = new SimpleIntegerProperty(0);

        getFiches().entrySet().stream()
        .sorted(Comparator.comparingInt(Map.Entry::getValue))
        .forEach(entry -> {
            Region ficheImg = entry.getKey();
            Integer ficheValue = entry.getValue();

            ficheImg.setPrefSize(50, 50);

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
        HBox actionButtons = new HBox(15);
        actionButtons.setAlignment(Pos.CENTER);

        Button startBtn = new Button();
        setText(startBtn, "start");

        Button cancelBtn = new Button();
        setText(cancelBtn, "cancel");

        final Runnable[] resetButtons = new Runnable[1];

        // Method to reset buttons to initial state
        resetButtons[0] = () -> {
            setText(startBtn, "start");
            setText(cancelBtn, "cancel");

            // Reset initial handlers
            startBtn.setOnAction(e -> {
                if (handler.getPlayerBalance() == 0 || handler.getPlayerBalance() < puntata.get()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid bet");
                    alert.setHeaderText("Insufficient balance");
                    alert.setContentText("You do not have enough balance to place the bet.");
                    alert.showAndWait();
                    return;
                } else if (puntata.get() == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
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
        HBox statusBar = new HBox();
        statusBar.setPadding(new Insets(5, 20, 0, 0));
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
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle(languageManager.getString("confirm_exit"));
            confirmDialog.setHeaderText(languageManager.getString("back_to_menu"));
            confirmDialog.setContentText(languageManager.getString("all_progress_lost"));
            ButtonType buttonYes = new ButtonType(languageManager.getString("yes"));
            ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmDialog.getButtonTypes().setAll(buttonYes, buttonNo);
            Optional<ButtonType> result = confirmDialog.showAndWait();
            if (result.isPresent() && result.get() == buttonYes) {
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

        Label rulesLabel = new Label(languageManager.getString("bj_rules_text"));
        rulesLabel.setWrapText(true);

        ScrollPane scrollPane = new ScrollPane(rulesLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        scrollPane.setStyle("-fx-background: white;");

        Button okBtn = new Button(languageManager.getString("exit"));
        okBtn.setOnAction(e -> dialog.close());

        VBox dialogVBox = new VBox(10, scrollPane, okBtn);
        dialogVBox.setPadding(new Insets(20));
        dialogVBox.setAlignment(Pos.CENTER);

        Scene dialogScene = new Scene(dialogVBox, 450, 450);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    /*
     * Method that updates the dealer and player cards 
     * every time it is called by passing it a cardBox.
     */
    private void updateCardDisplay(HBox cardBox) {
        cardBox.getChildren().clear();
        cardBox.setAlignment(Pos.CENTER);

        // Vertical space between dealer and player
        VBox mainBox = new VBox(20); 
        mainBox.setAlignment(Pos.CENTER);

        // Dealer
        dealerBox = new VBox(5);
        dealerBox.setAlignment(Pos.TOP_CENTER);
        dealerLabel = new Label("Dealer  ");
        dealerCards = new HBox(); // Carte più vicine tra loro
        dealerCards.setAlignment(Pos.CENTER);
        updateDealerBox(dealerCards, false);
        dealerBox.getChildren().addAll(dealerLabel, dealerCards);

        // Player
        playerBox = new VBox(5);
        playerBox.setAlignment(Pos.BOTTOM_CENTER);
        playerLabel = new Label("Player  ");
        playerCards = new HBox(2); 
        playerCards.setAlignment(Pos.CENTER);
        updatePlayerBox(playerCards);
        playerBox.getChildren().addAll(playerLabel, playerCards);

        // Add everything to the vertical container
        mainBox.getChildren().addAll(dealerBox, playerBox);

        // Inserts mainBox into the original horizontal container
        cardBox.getChildren().add(mainBox);
    }

    // Method that updates the statusLabel and winLabel
    private void updateViewAfterGame() {
        updateStatusLabel(statusLabel);
        updateWinLabel(winLabel);
    }

    /*
     * Method that updates the winLabel, 
     * inserting the text based on the outcome of the match.
     */
    private void updateWinLabel(Label label) {
        label.setText(handler.getGameOutcomeMessage());
    }

    /*
     * Method that updates the StatusLabel, inserting the updated 
     * information of the player, username and wallet.
     */
    private void updateStatusLabel(Label label) {
        label.setText("User: " + handler.getPlayerName()
                + " | Money: $" + String.format("%.2f", handler.getPlayerBalance()));
    }

    /*
     * Method that updates the PuntateLabel, inserting the updated 
     * information of the bet selected by the player.
     */
    private void updateStatusPuntateLabel(Label label, IntegerProperty puntata) {
        label.textProperty().bind(Bindings.createStringBinding(
            () -> "Bet: " + String.format("%.2f", (double) puntata.get()),
            puntata
        ));
    }
    
    /*
     * Method that updates the CardDealerPreLabel, inserting the value 
     * of the exposed card next to the dealer writing.
     */
    private void updateStatusCardDealerPreLabel(Label label) {
        List<Card> dealerCardsList = handler.getDealerHand().getCards();
        if (dealerCardsList.isEmpty()) {
            label.setText("Dealer");
            return;
        }
        int total = handler.getDealerTotal();
        int hiddenValue = dealerCardsList.get(0).getRank().getValue();
        label.setText("Dealer  " + (total - hiddenValue));
    }

    // Method that updates the dealer's card total
    private void updateStatusCardDealerFinalLabel(Label label) {
        label.setText("Dealer  " + String.format("%d", handler.getDealerTotal()));
    }

    // Method that updates the player's card total
    private void updateStatusCardPlayerLabel(Label label) {
        label.setText("Player  " + String.format("%d", handler.getPlayerTotal()));
    }

    /*
     * Method that updates the dealer's card box, showing a face-down 
     * card before the player finishes making his play.
     */
    private void updateDealerBox(HBox dealerCards, boolean showFullHand) {
        dealerCards.getChildren().clear();
        List<Region> dealerImages = showFullHand ? getDealerHandImages() : getDealerHandFirstImages();

        for (Region img : dealerImages) {
            img.setMaxSize(60, 100);
            dealerCards.getChildren().add(img);
        }

        if (showFullHand) {
            updateStatusCardDealerFinalLabel(dealerLabel);
        } else {
            updateStatusCardDealerPreLabel(dealerLabel);
        }
    }

    // Method that updates the player's card box
    private void updatePlayerBox(HBox playerCards) {
        for (Region img : getPlayerHandImages()) {
            img.setMaxSize(60, 100);
            playerCards.getChildren().add(img);
            updateStatusCardPlayerLabel(playerLabel);
        }
    }

    // Returns the list of cards in the player's hand
    public List<Region> getPlayerHandImages() {
        return getImagesFromHand(handler.getPlayerHand());
    }

    // Returns the list of cards in the dealer's hand
    public List<Region> getDealerHandImages() {
        return getImagesFromHand(handler.getDealerHand());
    }

    // Returns the list of cards in the dealer's hand, but with the top card hidden.
    public List<Region> getDealerHandFirstImages() {
        List<Region> images = new ArrayList<>();
        List<Card> dealerHand = handler.getDealerHand().getCards();

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

    // Returns the list of images of the cards in the hand
    private List<Region> getImagesFromHand(Hand hand) {
        if (hand == null || hand.getCards() == null) {
            return Collections.emptyList();
        }

        List<Region> images = new ArrayList<>();
        for (Card card : hand.getCards()) {
            Region img = imageProvider.getSVGCard(card.getRank(), card.getSuit());
            if (img != null) {
                images.add(img);
            }
        }
        return images;
    }

    // Returns the image and value map of the chips
    private Map<Region, Integer> getFiches() {
        Map<Region, Integer> fiches = new HashMap<>();
        int[] ficheValues = {1, 5, 10, 25, 100, 500};
        for (int value : ficheValues) {
            Region img = imageProvider.getSVGFiche(value);
            if (img != null) {
                fiches.put(img, value);
            }
        }
        return fiches;
    }

    // Method that sets the label text according to the language selected in the settings
    private void setText(final Labeled target, final String property) {
        target.textProperty().bind(languageManager.bind(property));
    }

    // Creates and returns a card back
    private Region createBackCard() {
        Region back = new Region();
        back.setPrefSize(60, 100);
        back.setStyle("-fx-background-color: navy; -fx-border-color: white; -fx-border-width: 2;");
        return back;
    }

}
