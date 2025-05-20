package ludomania.view.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Hand;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.handler.BlackJackHandler;
import ludomania.view.ViewBuilder;

public class BlackJackMenuViewBuilder implements ViewBuilder {

    private final BlackJackHandler handler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;

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
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> showExitConfirmation());
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(title, spacer, exitButton);
        root.setTop(topBar);

        // Center cards
        HBox cardBox = new HBox(40);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setPadding(new Insets(20));
        updateCardDisplay(cardBox);
        root.setCenter(cardBox);

        // Bottom section
        VBox bottomArea = new VBox(10);
        bottomArea.setAlignment(Pos.CENTER);
        bottomArea.setPadding(new Insets(10));

        // Fiches
        HBox ficheBar = new HBox(10);
        ficheBar.setAlignment(Pos.CENTER);
        int[] ficheValues = {10, 50, 100, 500};
        for (int value : ficheValues) {
            ImageView ficheImg = new ImageView();
            ficheImg.setFitHeight(50);
            ficheImg.setPreserveRatio(true);
            Button ficheButton = new Button("", ficheImg);
            ficheButton.setOnAction(e -> {
                handler.handlePlaceBet(value);
                updateStatusLabel(statusLabel);
            });
            ficheBar.getChildren().add(ficheButton);
        }

        // Azione
        HBox actionButtons = new HBox(10);
        actionButtons.setAlignment(Pos.CENTER);
        Button startBtn = new Button("Inizia Partita");
        startBtn.setOnAction(e -> {
            handler.handleStartGame();
            updateCardDisplay(cardBox);
            updateStatusLabel(statusLabel);
        });

        Button cancelBtn = new Button("Annulla Puntata");
        cancelBtn.setOnAction(e -> {
            handler.handleCancelBet();
            updateStatusLabel(statusLabel);
        });

        Button hitButton = new Button();
        setText(hitButton, "card");
        hitButton.setOnAction(e -> {
            handler.handleHit();
            updateCardDisplay(cardBox);
            updateStatusLabel(statusLabel);
        });

        Button standButton = new Button("Stai");
        standButton.setOnAction(e -> {
            handler.handleStand();
            updateCardDisplay(cardBox);
            updateStatusLabel(statusLabel);
        });

        actionButtons.getChildren().addAll(hitButton, standButton);
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

    private Label statusLabel;

    private void updateCardDisplay(HBox cardBox) {
        cardBox.getChildren().clear();

        VBox dealerBox = new VBox(5);
        dealerBox.setAlignment(Pos.CENTER);
        Label dealerLabel = new Label("Dealer");
        HBox dealerCards = new HBox(5);
        for (Region img : getDealerHandImages()) {
            img.setMaxSize(60, 100);
            dealerCards.getChildren().add(img);
        }
        dealerBox.getChildren().addAll(dealerLabel, dealerCards);

        VBox playerBox = new VBox(5);
        playerBox.setAlignment(Pos.CENTER);
        Label playerLabel = new Label("Tu");
        HBox playerCards = new HBox(5);
        for (Region img : getPlayerHandImages()) {
            img.setMaxSize(60, 100);
            dealerCards.getChildren().add(img);
        }
        playerBox.getChildren().addAll(playerLabel, playerCards);

        cardBox.getChildren().addAll(dealerBox, playerBox);
    }

    private void updateStatusLabel(Label label) {
        label.setText("Utente: " + handler.getPlayerName()
                + " | Saldo: €" + String.format("%.2f", handler.getPlayerBalance()));
    }

    private void showExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Window stage = new Stage();
        alert.initOwner(stage);
        alert.setTitle("Conferma uscita");
        alert.setHeaderText("Vuoi davvero uscire?");
        alert.setContentText("Tornerai al menu principale.");

        ButtonType yes = new ButtonType("Sì");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yes, no);
        alert.showAndWait().ifPresent(type -> {
            if (type == yes) {
                handler.handleExitToMenu();
            }
        });
    }

    public List<Region> getPlayerHandImages() {
        return getImagesFromHand(handler.getPlayerHand());
    }

    public List<Region> getDealerHandImages() {
        return getImagesFromHand(handler.getDealerHand());
    }

    private List<Region> getImagesFromHand(Hand hand) {
        if (hand == null || hand.getCards() == null) {
            return Collections.emptyList();
        }

        List<Region> images = new ArrayList<>();
        for (Card card : hand.getCards()) {
            // Supponendo che card abbia un metodo getId() che restituisce int
            
            Region img = imageProvider.getSVGCard(card.getRank(), card.getSuit());
            if (img != null) {
                images.add(img);
            }
        }
        return images;
    }

    private void setText(final Labeled target, final String property) {
        target.textProperty().bind(languageManager.bind(property));
    }
}
