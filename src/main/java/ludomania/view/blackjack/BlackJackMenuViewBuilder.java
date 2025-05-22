package ludomania.view.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Hand;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.handler.BlackJackHandler;
import ludomania.view.ViewBuilder;

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
        Button exitButton = new Button();
        setText(exitButton, "exit");
        exitButton.setOnAction(e -> handler.handleExitToMenu());
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(title, spacer, exitButton);
        root.setTop(topBar);

        // Center cards
        VBox centerBox = new VBox();
        HBox cardBox = new HBox(40);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setPadding(new Insets(20));
        updateCardDisplay(cardBox);
        winLabel = new Label();
        centerBox.getChildren().addAll(winLabel, cardBox);
        root.setCenter(centerBox);

        // Bottom section
        VBox bottomArea = new VBox(5);
        bottomArea.setAlignment(Pos.CENTER);
        bottomArea.setPadding(new Insets(10));

        // Fiches
        HBox ficheBar = new HBox(2);
        statusLabelPuntate = new Label();
        ficheBar.setAlignment(Pos.CENTER);
        IntegerProperty puntata = new SimpleIntegerProperty(0);
        for (Map.Entry<Region, Integer> entry : getFiches().entrySet()) {
            Region ficheImg = entry.getKey();
            Integer ficheValue = entry.getValue();

            // Usa il valore come testo del bottone e l'immagine come graphic
            Button ficheButton = new Button("", ficheImg);

            ficheButton.setOnAction(e -> {
                puntata.set(puntata.get() + ficheValue);
                updateStatusPuntateLabel(statusLabelPuntate, puntata);
            });
            if (puntata.get() != 0) {
                handler.handlePlaceBet(ficheValue);
            }
            ficheBar.getChildren().add(ficheButton);
        }
        ficheBar.getChildren().add(statusLabelPuntate);

        // Azione
        HBox actionButtons = new HBox(15);
        actionButtons.setAlignment(Pos.CENTER);

        Button startBtn = new Button();
        setText(startBtn, "start");

        Button cancelBtn = new Button();
        setText(cancelBtn, "cancel");

        final Runnable[] resetButtons = new Runnable[1];

        // Metodo per resettare i pulsanti allo stato iniziale
        resetButtons[0] = () -> {
            setText(startBtn, "start");
            setText(cancelBtn, "cancel");

            // Reimposta handler iniziali
            startBtn.setOnAction(e -> {
                handler.handlePlaceBet(puntata.get());
                handler.handleStartGame();
                updateCardDisplay(cardBox);
                updateStatusLabel(statusLabel);
                updateWinLabel(winLabel);

                // Passa alla fase di gioco (card/stand)
                setText(startBtn, "card");
                setText(cancelBtn, "stand");

                startBtn.setOnAction(ev -> {
                    handler.handleHit();
                    updateCardDisplay(cardBox);
                    updateStatusLabel(statusLabel);

                    if (handler.isGameOver()) {
                        updateStatusCardDealerFinalLabel(dealerLabel);
                        updateDealerBox(dealerCards, true);

                        updateWinLabel(winLabel);
                        updateStatusLabel(statusLabel); // <-- qui!

                        resetButtons[0].run();
                    }
                });

                cancelBtn.setOnAction(ev2 -> {
                    handler.handleStand(); 
                    updateCardDisplay(cardBox);
                    updateStatusCardDealerFinalLabel(dealerLabel);
                    updateDealerBox(dealerCards, true);
                    updateWinLabel(winLabel);
                    updateStatusLabel(statusLabel);
                    resetButtons[0].run();
                });
            });

            cancelBtn.setOnAction(e -> {
                puntata.set(0);
                updateStatusLabel(statusLabel);
                updateStatusPuntateLabel(statusLabelPuntate, new SimpleIntegerProperty(0));
                updateWinLabel(winLabel);
            });
        };

        // Inizializza gli handler iniziali
        resetButtons[0].run();

        actionButtons.getChildren().addAll(startBtn, cancelBtn);

        // Stato in basso a destra
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

    private void updateCardDisplay(HBox cardBox) {
        cardBox.getChildren().clear();
        cardBox.setAlignment(Pos.CENTER);

        VBox mainBox = new VBox(20); // Spazio verticale tra dealer e player
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
        playerCards = new HBox(2); // Carte più vicine tra loro
        playerCards.setAlignment(Pos.CENTER);
        updatePlayerBox(playerCards);
        playerBox.getChildren().addAll(playerLabel, playerCards);

        // Aggiunge tutto al contenitore verticale
        mainBox.getChildren().addAll(dealerBox, playerBox);

        // Inserisce mainBox nel contenitore orizzontale originale
        cardBox.getChildren().add(mainBox);
    }

    private void updateWinLabel(Label label) {
        label.setText(handler.getGameOutcomeMessage());
    }

    private void updateStatusLabel(Label label) {
        label.setText("User: " + handler.getPlayerName()
                + " | Money: $" + String.format("%.2f", handler.getPlayerBalance()));
    }

    private void updateStatusPuntateLabel(Label label, IntegerProperty puntata) {
        label.textProperty().bind(Bindings.createStringBinding(
            () -> "Bet: " + String.format("%.2f", (double) puntata.get()),
            puntata
        ));
    }

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

    private void updateStatusCardDealerFinalLabel(Label label) {
        label.setText("Dealer  " + String.format("%d", handler.getDealerTotal()));
    }

    private void updateStatusCardPlayerLabel(Label label) {
        label.setText("Player  " + String.format("%d", handler.getPlayerTotal()));
    }

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

    private void updatePlayerBox(HBox playerCards) {
        for (Region img : getPlayerHandImages()) {
            img.setMaxSize(60, 100);
            playerCards.getChildren().add(img);
            updateStatusCardPlayerLabel(playerLabel);
        }
    }

    public List<Region> getPlayerHandImages() {
        return getImagesFromHand(handler.getPlayerHand());
    }

    public List<Region> getDealerHandImages() {
        return getImagesFromHand(handler.getDealerHand());
    }

    public List<Region> getDealerHandFirstImages() {
        List<Region> images = new ArrayList<>();
        List<Card> dealerHand = handler.getDealerHand().getCards();

        for (int i = 0; i < dealerHand.size(); i++) {
            if (i == 0) {
                // Prima carta: dorso
                images.add(createBackCard());
            } else {
                // Altre carte: normali
                images.add(imageProvider.getSVGCard(dealerHand.get(i).getRank(), dealerHand.get(i).getSuit()));
            }
        }
        return images;
    }

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

    private Map<Region, Integer> getFiches() {
        Map<Region, Integer> fiches = new HashMap<>();
        int[] ficheValues = {1, 5, 10, 25, 100};
        for (int value : ficheValues) {
            Region img = imageProvider.getSVGFiche(value);
            if (img != null) {
                fiches.put(img, value);
            }
        }
        return fiches;
    }

    private void setText(final Labeled target, final String property) {
        target.textProperty().bind(languageManager.bind(property));
    }

    private Region createBackCard() {
        Region back = new Region();
        back.setPrefSize(60, 100);
        back.setStyle("-fx-background-color: navy; -fx-border-color: white; -fx-border-width: 2;");
        return back;
    }

}
